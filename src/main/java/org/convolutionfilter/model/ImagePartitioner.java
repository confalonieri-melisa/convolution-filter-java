package org.convolutionfilter.model;

import java.util.ArrayList;
import java.util.List;

public class ImagePartitioner {

    public List<ImageRegion> partition(int imageHeight, int numWorkers) {
        List<ImageRegion> regions = new ArrayList<>();
        int regionHeight = imageHeight / numWorkers;

        for (int i = 0; i < numWorkers; i++) {
            int start = i * regionHeight;
            int end = (i == numWorkers-1) ? imageHeight : start + regionHeight;
            regions.add(new ImageRegion(i, start, end));
        }

        return regions;
    }
}
