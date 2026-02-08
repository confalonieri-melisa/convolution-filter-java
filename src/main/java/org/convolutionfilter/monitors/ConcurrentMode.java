package org.convolutionfilter.monitors;

import java.util.ArrayList;
import java.util.List;

public class ConcurrentMode implements FilterMode {
    private boolean[][] completedRegions;
    private int numRegions;

    public ConcurrentMode(int numFilters, int numRegions) {
        this.numRegions = numRegions;
        this.completedRegions = new boolean[numFilters][numRegions];
    }

    @Override
    public synchronized void prepareFilter(int filterIndex, int numRegions) {
        completedRegions[filterIndex] = new boolean[numRegions];
    }

    @Override
    public void awaitFilter(){
        // No hay espera para continuar al próximo filtro
    }

    @Override
    public synchronized void beforeRegion(int filterIndex, int regionId) throws InterruptedException{
        if (filterIndex == 0) return;

        int[] neighbors = getNeighborRegions(regionId);
        for (int neighborId : neighbors) {
            while (!completedRegions[filterIndex-1][neighborId]) {
                wait();
            }
        }
    }

    @Override
    public synchronized void afterRegion(int filterIndex, int regionId) {
        completedRegions[filterIndex][regionId] = true;
        notifyAll();
    }

    private int[] getNeighborRegions(int regionId) {
        List<Integer> neighbors = new ArrayList<>();
        if (regionId > 0) {
            neighbors.add(regionId-1);
        }
        neighbors.add(regionId);
        if (regionId < numRegions - 1) {
            neighbors.add(regionId+1);
        }
        return neighbors.stream().mapToInt(Integer::intValue).toArray();
    }
}
