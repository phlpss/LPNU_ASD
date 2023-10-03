package com.example.lab_ads.executors;

import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Lab3Executor extends ArrayLabExecutor<Lab3Executor.RowWrapper> {
    public Lab3Executor(Text originalArrayText,
                        Text updatedArrayText,
                        Text sortedArrayText,
                        Text isSortedArrayText,
                        Text timeElapsedText, TextArea outputTextArea) {
        super(originalArrayText, updatedArrayText, sortedArrayText, isSortedArrayText, timeElapsedText, outputTextArea);
    }

    private static final DecimalFormat df = new DecimalFormat("#.##");
    List<List<Double>> matrix = new ArrayList<>();
    List<RowWrapper> sums;

    @Override
    public void doThingsWithArray(int size) {
        sums = generateList(size);

        StringBuilder stringBuilderSums = new StringBuilder();
        for (RowWrapper rowWrapper : sums) {
            stringBuilderSums.append(rowWrapper);
        }
        originalArrayText.setText("Original:\t\t" + stringBuilderSums);

        long start = System.currentTimeMillis();
        List<RowWrapper> sortedArray = sortList(sums);
        long end = System.currentTimeMillis();

        if (isSorted(sortedArray)) {
            isSortedArrayText.setText(sortedArrayText.getText() + "\n\nCorrectly Sorted :)");
        } else {
            isSortedArrayText.setText(sortedArrayText.getText() + "\n\nIncorrectly Sorted :(");
        }

        StringBuilder stringBuilderSorted = new StringBuilder();
        for (RowWrapper rowWrapper : sortedArray) {
            stringBuilderSorted.append(rowWrapper);
        }
        sortedArrayText.setText("Sorted:\t\t" + stringBuilderSorted);
        timeElapsedText.setText("Sort executed in " + (end - start) + " milliseconds");
        list.clear();
    }

    @Override
    protected List<RowWrapper> generateList(int size) {
        Random random = new Random();
        double randomValue;
        double roundedValue;

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

        return matrix.stream()
                .map(Lab3Executor::getRowWrapper)
                .toList();
    }

    @Override
    protected List<RowWrapper> updateList() {
        return null;
    }

    @Override
    protected List<RowWrapper> sortList(List<RowWrapper> inputArray) {
        if (inputArray.size() <= 1) {
            return inputArray;
        }

        Double pivot = inputArray.get(0).getSum();

        // create three parts of the array:
        List<RowWrapper> less = new ArrayList<>();      // element < pivot
        List<RowWrapper> equals = new ArrayList<>();    // element == pivot
        List<RowWrapper> greater = new ArrayList<>();   // element > pivot

        // get sum of elements in each of row and put row in correct place
        for (RowWrapper rowWrapper : inputArray) {
            if (rowWrapper.getSum() < pivot) {
                less.add(rowWrapper);
            } else if (rowWrapper.getSum() > pivot) {
                greater.add(rowWrapper);
            } else {
                equals.add(rowWrapper);
            }
        }

        // sort each of the partition recursively and add it to sorted matrix
        List<RowWrapper> sortedList = new ArrayList<>();
        sortedList.addAll(sortList(less));
        sortedList.addAll(equals);
        sortedList.addAll(sortList(greater));

        // Update the TextArea with the current state of the sorted matrix
        updateOutputTextArea(sortedList);

        return sortedList;
    }

    @Override
    protected boolean isSorted(List<RowWrapper> inputArray) {
        for (int i = 1; i < inputArray.size() - 1; i++) {
            if (inputArray.get(i).getSum() < inputArray.get(i - 1).getSum()) {
                return false;
            }
        }
        return true;
    }

    private static RowWrapper getRowWrapper(List<Double> row) {
        double rowSum = row.stream().mapToDouble(el -> el).sum();
        return new RowWrapper(rowSum, row);
    }

    private void updateOutputTextArea(List<RowWrapper> inputMatrix) {
        // Clear the existing content in the TextArea
        StringBuilder outputText = new StringBuilder();
        for (RowWrapper row : inputMatrix) {
            outputText.append(row.toString());
        }
        outputText.append("\n");
        outputTextArea.appendText(outputText.toString());
    }

    @Data
    @AllArgsConstructor
    public static class RowWrapper {
        private Double sum;
        private List<Double> values;

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("\n");
            for (Double value : values) {
                stringBuilder.append(df.format(value)).append("  ");
            }
            stringBuilder.append("\t= ").append(Double.parseDouble(df.format(sum)));
            return stringBuilder.toString();
        }
    }
}
