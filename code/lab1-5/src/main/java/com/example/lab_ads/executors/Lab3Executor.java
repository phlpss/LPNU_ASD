package com.example.lab_ads.executors;

import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A class for performing operations on matrices and displaying results.
 */
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
public class Lab3Executor {
    Text originalArrayText;
    Text updatedArrayText;
    Text sortedArrayText;
    Text isSortedArrayText;
    Text timeElapsedText;
    TextArea outputTextArea;

    @NonFinal
    List<List<Double>> matrix = new ArrayList<>();
    private static final DecimalFormat df = new DecimalFormat("#.##");

    /**
     * Generate a random matrix of the given size and perform sorting and display operations.
     *
     * @param size The size of the matrix.
     */
    public void doThingsWithArray(int size) {
        matrix = generateMatrix(size);
        printMatrix(matrix, originalArrayText, "Original Matrix:\n");

        // sort matrix, display operations and traces the execution time of the method
        long start = System.currentTimeMillis();
        List<List<Double>> sortedMatrix = sortMatrix(matrix);
        long end = System.currentTimeMillis();

        // display message if the matrix sorted correctly
        if (isSorted(matrix)) {
            isSortedArrayText.setText(sortedArrayText.getText() + "\n\nCorrectly Sorted :)");
        } else {
            isSortedArrayText.setText(sortedArrayText.getText() + "\n\nIncorrectly Sorted :(");
        }

        printMatrix(sortedMatrix, sortedArrayText, "Sorted Matrix:\n");

        timeElapsedText.setText("Sort executed in " + (end - start) + " milliseconds");
        matrix.clear();
    }

    protected List<List<Double>> generateMatrix(int size) {
        Random random = new Random();
        double randomValue, roundedValue;

        for (int i = 0; i < size; i++) {
            // generate each of row
            List<Double> row = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                randomValue = random.nextDouble(10) + 1;
                roundedValue = Double.parseDouble(df.format(randomValue));
                row.add(roundedValue);
            }
            matrix.add(row);
        }

        return matrix;
    }

    protected Double calculateSumOfRow(List<Double> row) {
        Double sum = 0.0;
        for (Double i : row) {
            sum += i;
        }
        return Double.parseDouble(df.format(sum));
    }

    protected List<List<Double>> sortMatrix(List<List<Double>> inputMatrix) {
        // if there are only one row in matrix then stop recursion
        if (inputMatrix.size() <= 1) {
            return inputMatrix;
        }

        // chose a row from matrix as the pivot (the first for example)
        List<Double> pivot = new ArrayList<>(inputMatrix.get(0));
        Double pivotSum = calculateSumOfRow(pivot);

        // create three parts of the matrix: sum of elements in row < pivot
        //                                   sum of elements in row == pivot
        //                                   sum of elements in row > pivot
        List<List<Double>> less = new ArrayList<>();
        List<List<Double>> equals = new ArrayList<>();
        List<List<Double>> greater = new ArrayList<>();

        // calculate sum of elements in each of row and put row in correct place
        for (List<Double> row : inputMatrix) {
            if (calculateSumOfRow(row) < pivotSum) {
                less.add(row);
            } else if (calculateSumOfRow(row) > pivotSum) {
                greater.add(row);
            } else {
                equals.add(row);
            }
        }

        // sort each of the partition recursively and add it to sorted matrix
        List<List<Double>> sortedMatrix = new ArrayList<>();
        sortedMatrix.addAll(sortMatrix(less));
        sortedMatrix.addAll(equals);
        sortedMatrix.addAll(sortMatrix(greater));

        // Update the TextArea with the current state of the sorted matrix
        updateOutputTextArea(sortedMatrix);
        return sortedMatrix;
    }

    private void updateOutputTextArea(List<List<Double>> inputMatrix) {
        // Clear the existing content in the TextArea
        StringBuilder outputText = new StringBuilder();

        for (List<Double> row : inputMatrix) {
            outputText.append("| ");
            for (Double value : row) {
//                outputText.append(String.format("%.2f", value));
                outputText.append(value);
                outputText.append("    ");
            }
            outputText.append("\n");
        }
        outputText.append("\n");
        outputTextArea.appendText(outputText.toString());
    }

    protected boolean isSorted(List<List<Double>> inputMatrix) {
        Double first;
        for (List<Double> row : inputMatrix) {
            first = calculateSumOfRow(row);
            if (calculateSumOfRow(row) < first) {
                return false;
            }
        }
        return true;
    }

    protected void printMatrix(List<List<Double>> inputMatrix, Text text, String title) {
        StringBuilder matrixText = new StringBuilder(title);

        for (List<Double> row : inputMatrix) {
            matrixText.append("| ");
            for (Double value : row) {
                matrixText.append(value).append("    ");
            }
            matrixText.append("\t= ").append(calculateSumOfRow(row)).append("\n");
        }

        text.setText(matrixText.toString());
    }

}
