package org.convolutionfilter.model;

public class Filter {
    private double[][] matrix;

    public Filter(double[][] matrix) {
        this.matrix = matrix;
    }

	public int getSize() {
		return matrix.length;
	}

	public double getValue(int row, int col) {
		return matrix[row][col];
	}

	public int getRadius() {
		return this.getSize() / 2;
	}
}
