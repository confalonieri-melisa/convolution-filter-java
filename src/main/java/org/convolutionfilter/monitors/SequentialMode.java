package org.convolutionfilter.monitors;

public class SequentialMode implements FilterMode {
    private int remainingRegions;

    @Override
    public synchronized void prepareFilter(int filterIndex, int numRegions) {
        remainingRegions = numRegions;
    }

    @Override
    public synchronized void awaitFilter() throws InterruptedException {
        while (remainingRegions > 0) {
            wait();
        }
    }

    @Override
    public void beforeRegion(int filterIndex, int regionId) {
        // No hay espera antes de procesar una región
    }

    @Override
    public synchronized void afterRegion(int filterIndex, int regionId) {
        remainingRegions--;
        if (remainingRegions == 0) {
            notifyAll();
        }
    }
}