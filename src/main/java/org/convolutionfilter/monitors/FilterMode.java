package org.convolutionfilter.monitors;

public interface FilterMode {
    void prepareFilter(int filterIndex, int numRegions);
    void awaitFilter() throws InterruptedException;
    void beforeRegion(int filterIndex, int regionId) throws InterruptedException;
    void afterRegion(int filterIndex, int regionId);
}
