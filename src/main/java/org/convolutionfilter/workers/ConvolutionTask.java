package org.convolutionfilter.workers;

import org.convolutionfilter.model.ImageData;
import org.convolutionfilter.model.ImageRegion;
import org.convolutionfilter.model.Filter;
import org.convolutionfilter.monitors.FilterMode;

public class ConvolutionTask extends Task {
	ImageData source;
	ImageData output;
	ImageRegion region;
	Filter filter;
	int filterIndex;
	FilterMode mode;
	
	public ConvolutionTask(ImageData source, ImageData output, ImageRegion region, Filter filter, int filterIndex, FilterMode mode) {
		this.source = source;
		this.output = output;
		this.region = region;
		this.filter = filter;
		this.filterIndex = filterIndex;
		this.mode = mode;
	}
	
    @Override
    public void run() {
        try {
			mode.beforeRegion(filterIndex, region.getId());
			processRegion();
			mode.afterRegion(filterIndex, region.getId());
        } catch (InterruptedException e) {
			Thread.currentThread().interrupt();
        }
    }

	private void processRegion() {
		for (int row = region.getStartRow(); row < region.getEndRow(); row++) {
			for (int col = 0; col < output.getWidth(); col++) {
				this.applyFilter(row,col);
			}
		}
	}

	private void applyFilter(int outputRow, int outputCol) {
		for (int channel = 0; channel < source.getNumBands(); channel++) {
			double resultPixel = calculatePixel(outputRow, outputCol, channel);
			output.setSample(outputCol, outputRow, channel, resultPixel);
    	}
	}

	private double calculatePixel(int outputRow, int outputCol, int channel) {
		int filterSize = filter.getSize();
		int radius = filter.getRadius();

		double convolution = 0.0;
		
		for (int filterRow = 0; filterRow < filterSize; filterRow++) {
			for (int filterCol = 0; filterCol < filterSize; filterCol++) {
				int offsetRow = filterRow - radius;
				int offsetCol = filterCol - radius;

				int sourceRow = outputRow + radius + offsetRow;
				int sourceCol = outputCol + radius + offsetCol;

				double pixelVal = source.getSample(sourceCol, sourceRow, channel);
				double filterVal = filter.getValue(filterRow, filterCol);

				convolution += pixelVal * filterVal;
	    	}
    	}
		return convolution;
	}
}
