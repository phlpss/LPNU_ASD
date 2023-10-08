package com.example.lab_ads.executors;

import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Lab4Executor extends ArrayLabExecutor<Double> {
    public Lab4Executor(Text originalArrayText, Text updatedArrayText, Text sortedArrayText, Text isSortedArrayText, Text timeElapsedText, TextArea outputTextArea) {
        super(originalArrayText, updatedArrayText, sortedArrayText, isSortedArrayText, timeElapsedText, outputTextArea);
    }

    private static final List<Double> array = new ArrayList<>();
    private static final DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected List<Double> generateList(int size) {
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            // Generate random values between -100 and 100
            double randomValue = (random.nextDouble() * 20) - 10;

            // Round the randomValue to 2 decimals
            double roundedValue = Double.parseDouble(df.format(randomValue));

            array.add(roundedValue);
        }
        return array;
    }

    /**
     * The negative elements of the array are multiplied by the minimum element
     */
    @Override
    protected List<Double> updateList() {
        Double min = minValue();
        double roundedValue;

        for (int i = 0; i < array.size(); i++) {
            Double element = array.get(i);
            if (element < 0) {
                roundedValue = Double.parseDouble(df.format(element * min));
                array.set(i, roundedValue);
            }
        }

        return array;
    }

    /**
     * Returns sorted array in descending order using Merge Sort algorithm
     */
    @Override
    protected List<Double> sortList(List<Double> inputArray) {
        int size = inputArray.size();

        if (size <= 1) {
            return inputArray; // Base case: already sorted
        }

        int middle = size / 2;
        List<Double> leftPart = inputArray.subList(0, middle);
        List<Double> rightPart = inputArray.subList(middle, size);

        leftPart = sortList(leftPart);
        rightPart = sortList(rightPart);

        return merge(leftPart, rightPart);
    }

    @Override
    protected boolean isSorted(List<Double> array) {
        for (int i = 1; i < array.size(); i++) {
            if (array.get(i - 1) < array.get(i)) {
                return false;
            }
        }
        return true;
    }

    protected List<Double> merge(List<Double> leftPart, List<Double> rightPart) {
        List<Double> result = new ArrayList<>();
        int i = 0, j = 0;

        while (i < leftPart.size() && j < rightPart.size()) {
            if (leftPart.get(i) >= rightPart.get(j)) {
                result.add(leftPart.get(i));
                i++;
            } else {
                result.add(rightPart.get(j));
                j++;
            }
        }

        // Add remaining elements from leftPart (if any)
        while (i < leftPart.size()) {
            result.add(leftPart.get(i));
            i++;
        }

        // Add remaining elements from rightPart (if any)
        while (j < rightPart.size()) {
            result.add(rightPart.get(j));
            j++;
        }

        outputTextArea.appendText(result + "\n");
        return result;
    }

    protected Double minValue() {
        Double minValue = array.get(0);
        for (Double i : array) {
            if (i < minValue) {
                minValue = i;
            }
        }
        return minValue;
    }
}
