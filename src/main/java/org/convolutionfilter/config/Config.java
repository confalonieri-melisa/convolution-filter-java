package org.convolutionfilter.config;

import org.convolutionfilter.model.Filter;

import java.io.IOException;
import java.util.Scanner;

public class Config {
    private static final String INPUT_DIR = "images/input/";
    private static final String OUTPUT_DIR = "images/output/";
    private static final String FILTER_DIR = "data/";

    private String inputPath;
    private String outputPath;
    private int bufferSize;
    private int numWorkers;
    private String mode;
    private int filterSize;
    private Filter[] filters;

    public Config() throws IOException {
        loadFromStdin();
    }

    public void loadFromStdin() throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nombre del archivo de entrada (ej: input.jpg): ");
        this.inputPath = INPUT_DIR + scanner.nextLine();

        System.out.print("Nombre del archivo de salida (ej: output.jpg): ");
        this.outputPath = OUTPUT_DIR + scanner.nextLine();

        System.out.print("Tamaño del buffer: ");
        this.bufferSize = Integer.parseInt(scanner.nextLine());

        System.out.print("Cantidad de workers: ");
        this.numWorkers = Integer.parseInt(scanner.nextLine());

        System.out.print("Modo de ejecución (sequential/concurrent): ");
        this.mode = scanner.nextLine();

        System.out.print("Tamaño del filtro (3 o 5): ");
        this.filterSize = Integer.parseInt(scanner.nextLine());

        System.out.print("Nombre del archivo de filtros (ej: sharpen.txt): ");
        String filterPath = FILTER_DIR + scanner.nextLine();

        FilterLoader loader = new FilterLoader();
        this.filters = loader.loadFromFile(filterPath, filterSize);

        scanner.close();
        validate();
    }

    public void validate() {
        if(bufferSize <= 0) {
            throw new IllegalArgumentException("El tamaño de buffer debe ser mayor que 0");
        }

        if(numWorkers <= 0) {
            throw new IllegalArgumentException("La cantidad de workers debe ser mayor que 0");
        }

        if(!mode.equals("sequential") && !mode.equals("concurrent")) {
            throw new IllegalArgumentException("El modo debe ser 'sequential' o 'concurrent'");
        }

        if(filterSize != 3 && filterSize != 5) {
            throw new IllegalArgumentException("El tamaño del filtro debe ser 3 o 5");
        }
    }

    public boolean isConcurrentMode() {
        return mode.equals("concurrent");
    }

    public String getInputPath() {
        return inputPath;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public int getNumWorkers() {
        return numWorkers;
    }

    public Filter[] getFilters() {
        return filters;
    }
}

