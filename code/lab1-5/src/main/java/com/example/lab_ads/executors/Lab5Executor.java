package com.example.lab_ads.executors;

import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;


public class Lab5Executor extends ArrayLabExecutor<Double> {
    public Lab5Executor(Text originalArrayText, Text updatedArrayText, Text sortedArrayText, Text isSortedArrayText, Text timeElapsedText, TextArea outputTextArea) {
        super(originalArrayText, updatedArrayText, sortedArrayText, isSortedArrayText, timeElapsedText, outputTextArea);
    }

    private static final List<Double> array = new ArrayList<>();
    private static final DecimalFormat df = new DecimalFormat("#.#");

    @Override
    protected List<Double> generateList(int size) {
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            // Generate random values between -4 and 4
            double randomValue = (random.nextDouble() * 8) - 4;

            // Round the randomValue to 2 decimals
            double roundedValue = Double.parseDouble(df.format(randomValue));

            array.add(roundedValue);
        }
        return array;
    }

    @Override
    protected List<Double> updateList() {
        for (int i = 0; i < array.size(); i++) {
            Double currentElem = array.get(i);
            if (i % 2 == 0) {
                array.set(i, Double.parseDouble(df.format(Math.tan(currentElem) - currentElem)));
            } else {
                array.set(i, Double.parseDouble(df.format(Math.abs(currentElem))));
            }
        }
        return array;
    }


    @Override
    protected List<Double> sortList(List<Double> originalArray) {
        // 1: find left and right bound of array
        BigDecimal minElem = minValue(originalArray);
        BigDecimal maxElem = maxValue(originalArray);

        // 2: Init a countOccurrences of length max
        // 3: count the occurrences of each element and put it into the countArray as values
        List<Integer> countOccurences = countOccurrences(minElem, maxElem);

        // 4: update the countArray by adding previous element to current
        countOccurences = updateCounts(countOccurences);

        // 5: shift elements in countArray to the right
        countOccurences = shiftCounts(countOccurences);

        // 6: put elements in correct place
        originalArray = countSorted(originalArray, countOccurences, minElem);

        return originalArray;
    }

    @Override
    protected boolean isSorted(List<Double> inputArray) {
        for (int i = 1; i < inputArray.size(); i++) {
            if (inputArray.get(i - 1) > inputArray.get(i)) {
                return false;
            }
        }
        return true;
    }

    protected BigDecimal calculateValue(List<Double> inputArray, boolean isMax) {
        if (inputArray.isEmpty()) {
            throw new IllegalArgumentException("The inputArray is empty.");
        }
        BigDecimal result = BigDecimal.valueOf(inputArray.get(0));

        for (int i = 1; i < inputArray.size(); i++) {
            double currentValue = inputArray.get(i);
            if ((isMax && currentValue > result.doubleValue()) || (!isMax && currentValue < result.doubleValue())) {
                result = new BigDecimal(currentValue);
            }
        }

        return result.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal maxValue(List<Double> inputArray) {
        return calculateValue(inputArray, true);
    }

    public BigDecimal minValue(List<Double> inputArray) {
        return calculateValue(inputArray, false);
    }

    protected List<Integer> countOccurrences(BigDecimal sizeOfMin, BigDecimal sizeOfMax) {
        List<Integer> result = new ArrayList<>();
        BigDecimal step = new BigDecimal("0.1");

        for (BigDecimal i = sizeOfMin; i.compareTo(sizeOfMax) <= 0; i = i.add(step)) {
            int count = 0;
            for (double value : array) {
                if (value == i.doubleValue()) {
                    count++;
                }
            }
            result.add(count);
        }

        return result;
    }

    protected List<Integer> updateCounts(List<Integer> inputArray) {
        List<Integer> result = new ArrayList<>(inputArray.size());

        result.add(inputArray.get(0));

        for (int i = 1; i < inputArray.size(); i++) {
            int value = inputArray.get(i) + result.get(i - 1);
            result.add(value);
        }
        return result;
    }

    protected List<Integer> shiftCounts(List<Integer> inputArray) {
        List<Integer> result = new ArrayList<>(List.copyOf(inputArray));

        for (int i = result.size() - 1; i > 0; i--) {
            result.set(i, inputArray.get(i - 1));
        }
        result.set(0, 0);

        return result;
    }

    protected List<Double> countSorted(List<Double> originalArray, List<Integer> countOccurrences, BigDecimal shift) {
        List<Double> result = new ArrayList<>(originalArray);

        for (Double aDouble : originalArray) {
            int index = (int) ((BigDecimal.valueOf(aDouble).subtract(shift).multiply(BigDecimal.TEN)).doubleValue());

            result.set(countOccurrences.get(index), aDouble);

            int increment = countOccurrences.get(index) + 1;
            countOccurrences.set(index, increment);
        }
        return result;
    }
}


