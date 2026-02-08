package org.convolutionfilter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PerformanceTest {
    private static final int TOTAL_RUNS = 13;

    public static void main(String[] args) throws IOException {
        String[] images = {"image512x512.jpg", "image2048x2048.jpg"};
        String filter = "3x3_full.txt";
        String[] modes = {"sequential", "concurrent"};
        int bufferSize = 32;
        int[] threads = {1, 4, 8, 16};
        int filterSize = 3;

        File results = new File("results.csv");
        FileWriter writer = new FileWriter(results);

        writer.write("Image,Mode,Threads,AverageTime(ms)\n");

        for (String image : images) {
            for (String mode : modes) {
                for (int thread : threads) {
                    List<Long> times = new ArrayList<>();

                    for (int run = 0; run < TOTAL_RUNS; run++) {
                        String fakeInput =
                                image + "\n"
                                + ("OUT_" + image + ".jpg\n")
                                + bufferSize + "\n"
                                + thread + "\n"
                                + mode + "\n"
                                + filterSize + "\n"
                                + filter + "\n";

                        InputStream stdinBackup = System.in;
                        System.setIn(new ByteArrayInputStream(fakeInput.getBytes()));

                        long startTime = System.currentTimeMillis();
                        Main.main(new String[]{});
                        long endTime = System.currentTimeMillis();

                        System.setIn(stdinBackup);

                        if (run >= 3) {
                            times.add(endTime - startTime);
                        }
                    }

                    long average = times.stream().mapToLong(Long::longValue).sum() / times.size();
                    writer.write(image + "," + mode + "," + thread + "," + average + "\n");
                }
            }
        }

        writer.close();
        System.out.println("PerformanceTest completado. Resultados guardados en 'results.csv'.");
    }
}
