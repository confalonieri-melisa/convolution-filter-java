package org.convolutionfilter.model;

public class ImageRegion {
    private int id;
    private int startRow;
    private int endRow;

    public ImageRegion(int id, int startRow, int endRow) {
        this.id = id;
        this.startRow = startRow;
        this.endRow = endRow;
    }

	public int getId() {
		return id;
	}

	public int getStartRow() {
		return startRow;
	}

	public int getEndRow() {
		return endRow;
	}
}
