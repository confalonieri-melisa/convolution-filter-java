package org.convolutionfilter.model;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

public class ImageData {
    private BufferedImage biImage;
    private WritableRaster raster;

    public ImageData(String path) throws IOException {
        File image = new File(path);
        this.biImage = ImageIO.read(image);
        this.raster = biImage.getRaster();
    }

    public ImageData (int width, int height, ImageData source) {
        this.raster = source.raster.createCompatibleWritableRaster(width, height);
        double[] data = new double[width * height * source.getNumBands()];
        this.raster.setPixels(0, 0, width, height, data);

        this.biImage = new BufferedImage(
                source.biImage.getColorModel(),
                raster,
                source.biImage.isAlphaPremultiplied(),
                null
        );
    }

    public void save(String path) throws IOException {
        File output = new File(path);
        ImageIO.write(biImage, "jpg", output);
    }

    public void setSample(int col, int row, int band, double value) {
        int newValue = (int) Math.max(0, Math.min(255, value));
        raster.setSample(col, row, band, newValue);
    }

    public int getNumBands() {
        return raster.getNumBands();
    }

    public double getSample(int x, int y, int band) {
        return raster.getSample(x, y, band);
    }

	public int getWidth() {
		return raster.getWidth();
	}

	public int getHeight() {
		return raster.getHeight();
	}
}
