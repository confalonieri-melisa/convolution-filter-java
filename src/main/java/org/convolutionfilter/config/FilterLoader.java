package org.convolutionfilter.config;

import org.convolutionfilter.model.Filter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilterLoader {

    public Filter[] loadFromFile(String path, int size) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        List<String> lines = readNonEmptyLines(reader);
        reader.close();

        Filter[] filters = new Filter[3];
        int lineIndex = 0;
        for(int i = 0; i < 3 && lineIndex < lines.size(); i++){
            filters[i] = parseFilterMatrix(lines, lineIndex, size);
            lineIndex += size;
        }

        completeWithIdentity(filters, size);
        return filters;
    }

    private List<String> readNonEmptyLines(BufferedReader reader) throws IOException {
        List<String> lines = new ArrayList<>();
        String line = reader.readLine();
        while(line != null) {
            line = line.trim();
            if(!line.isEmpty() && !line.startsWith("#")){
                lines.add(line);
            }
            line = reader.readLine();
        }
        return lines;
    }

    private Filter parseFilterMatrix(List<String> lines, int lineIndex, int size) {
        double[][] matrix = new double[size][size];

        for(int i = 0; i < size; i++){
            String[] values = lines.get(lineIndex + i).split("\\s+");
            for(int j = 0; j < size; j++){
                matrix[i][j] = Double.parseDouble(values[j]);
            }
        }

        return new Filter(matrix);
    }

    private void completeWithIdentity(Filter[] filters, int size) {
        int kernelsLoaded = (int) Arrays.stream(filters).filter(k -> k != null).count();
        for(int i = kernelsLoaded; i < 3; i++) {
            filters[i] = createIdentityKernel(size);
        }
    }

    private Filter createIdentityKernel(int size) {
        double[][] matrix = new double[size][size];
        int center = size / 2;
        matrix[center][center] = 1.0;
        return new Filter(matrix);
    }
}