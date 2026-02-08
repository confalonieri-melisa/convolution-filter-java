package org.convolutionfilter;

import java.io.IOException;
import java.util.List;

import org.convolutionfilter.config.Config;
import org.convolutionfilter.model.ImageData;
import org.convolutionfilter.model.ImagePartitioner;
import org.convolutionfilter.model.ImageRegion;
import org.convolutionfilter.model.Filter;
import org.convolutionfilter.monitors.*;
import org.convolutionfilter.workers.ConvolutionTask;
import org.convolutionfilter.workers.PoisonPill;
import org.convolutionfilter.workers.Task;
import org.convolutionfilter.workers.ThreadPool;

public class Main {
    public static void main(String[] args) {
        try {
            Config config = new Config();
            int numWorkers = config.getNumWorkers();

            Buffer buffer = new Buffer(config.getBufferSize());
            WorkerCounter counter = new WorkerCounter(numWorkers);
            ThreadPool pool = new ThreadPool(numWorkers, buffer, counter);

            Filter[] filters = config.getFilters();
            FilterMode mode = config.isConcurrentMode()
                    ? new ConcurrentMode(filters.length, numWorkers)
                    : new SequentialMode();

            ImageData[] images = createImagePipeline(config, filters);

            long startTime = System.currentTimeMillis();
            pool.start();

            executeFilters(buffer, numWorkers, mode, images, filters);
            sendPoisonPills(buffer, numWorkers);

            counter.awaitWorkers();
            images[3].save(config.getOutputPath());

            long endTime = System.currentTimeMillis();
            System.out.println("Tiempo demorado: " + (endTime - startTime) / 1000.0 + " segundos");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static ImageData[] createImagePipeline(Config config, Filter[] filters) throws IOException {
        ImageData[] images = new ImageData[4];
        images[0] = new ImageData(config.getInputPath());

        int radius = filters[0].getRadius();
        for (int i = 0; i < 3; i++) {
            int newHeight = images[i].getHeight() - 2 * radius;
            int newWidth = images[i].getWidth() - 2 * radius;
            images[i+1] = new ImageData(newWidth, newHeight, images[i]);
        }

        return images;
    }

    private static void executeFilters(Buffer buffer, int numWorkers, FilterMode mode, ImageData[] images, Filter[] filters) throws InterruptedException {
        ImagePartitioner partitioner = new ImagePartitioner();

        for (int f = 0; f < 3; f++) {
            int height = images[f+1].getHeight();
            List<ImageRegion> regions = partitioner.partition(height, numWorkers);

            mode.prepareFilter(f, regions.size());

            for (ImageRegion region : regions) {
                Task task = new ConvolutionTask(images[f], images[f+1], region, filters[f], f, mode);
                buffer.put(task);
            }

            mode.awaitFilter();
        }
    }

    private static void sendPoisonPills(Buffer buffer, int numWorkers) throws InterruptedException {
        for (int i = 0; i < numWorkers; i++) {
            buffer.put(new PoisonPill());
        }
    }
}






