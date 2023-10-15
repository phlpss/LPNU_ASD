package com.example.lab_ads.executors;

import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.Map;
import java.util.Map.Entry;


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
            // Generate random values between -8 and 8
            double randomValue = (random.nextDouble() * 4) - 2;

            // Round the randomValue to 2 decimals
            double roundedValue = Double.parseDouble(df.format(randomValue));

            array.add(roundedValue);
        }
        return array;
    }

    @Override
    protected List<Double> updateList() {
        // if (elem%2 ==  0) then (tan(elem) - elem)
        // else (|elem|)
        for (int i = 0; i < array.size(); i++) {
            Double currentElem = array.get(i);
            if (currentElem % 2 == 0) {
                Double newValue = Double.parseDouble(df.format(Math.tan(currentElem) - currentElem));
                array.set(i, newValue);
            } else {
                Double newValue = Double.parseDouble(df.format(Math.abs(currentElem)));
                array.set(i, newValue);
            }
        }
        return array;
    }


    @Override
    protected List<Double> sortList(List<Double> inputArray) {
        // 1: find max elem in array
        int maxElem = maxValue(inputArray);

        // 2: Init a countHashMap of length max+1 with
        //                                             keys = [0, max]
        //                                             values = [0 -> then counts]
        // 3: count the occurrences of each element and put it into the countArray as values
        Map<Double, Integer> countHashMap = countOccurences(maxElem);

        // 4: update the countArray by adding previous element to current
        countHashMap = updateCounts(countHashMap);

        // 5: shift elements in countArray to the right
        countHashMap = shiftCounts(countHashMap);

        // 6: put elements in correct place
        inputArray = countSorted(inputArray, countHashMap);

        return inputArray;
    }

    @Override
    protected boolean isSorted(List<Double> inputArray) {
        for (int i = 1; i < inputArray.size(); i++) {
            if (inputArray.get(i - 1) < inputArray.get(i)) {
                return false;
            }
        }
        return true;
    }

    protected int maxValue(List<Double> inputArray) {
        if (inputArray.isEmpty()) {
            throw new IllegalArgumentException("The inputArray is empty.");
        }
        Double maxValue = inputArray.get(0);
        for (int i = 0; i < inputArray.size(); i++) {
            if (inputArray.get(i) > maxValue) {
                maxValue = inputArray.get(i);
            }
        }
        return (int) Math.ceil(maxValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(array, df);
    }

    protected Map<Double, Integer> countOccurences(int size) {
        LinkedHashMap<Double, Integer> result = new LinkedHashMap<>(size);

        // Iterate over list [0.0, max]
        BigDecimal i = BigDecimal.ZERO;
        BigDecimal step = BigDecimal.valueOf(0.1);

        while (i.compareTo(BigDecimal.valueOf(size)) <= 0) {
            int count = 0;
            // Iterate over the original array to count occurrences
            for (Double element : array) {
                BigDecimal elementBigDecimal = BigDecimal.valueOf(element);
                if (i.compareTo(elementBigDecimal) == 0) {
                    count++;
                }
            }
            result.put(i.doubleValue(), count);
            i = i.add(step);
        }

        return result;
    }

    protected Map<Double, Integer> updateCounts(Map<Double, Integer> inputArray) {
        Map<Double, Integer> updatedMap = new LinkedHashMap<>();

        // Init a variable to keep track of the previous value
        Integer previousValue = null;

        // Iterate through the input map
        for (Entry<Double, Integer> entry : inputArray.entrySet()) {
            Double key = entry.getKey();
            Integer value = entry.getValue();

            // If this is not the first entry, add the previous value to the current one
            if (previousValue != null) {
                value += previousValue;
            }

            // Put the updated key-value pair in the new map
            updatedMap.put(key, value);

            // Update the previousValue for the next iteration
            previousValue = value;
        }

        return updatedMap;
    }

    protected Map<Double, Integer> shiftCounts(Map<Double, Integer> inputArray) {
        Map<Double, Integer> updatedMap = new LinkedHashMap<>();

        ListIterator<Integer> iterator = new ArrayList<>(inputArray.values()).listIterator(inputArray.size());

        int previousValue = 0;

        while (iterator.hasPrevious()) {
            int currentValue = iterator.previous();
            updatedMap.put((Double) inputArray.entrySet().toArray(new Entry[0])[iterator.nextIndex()].getKey(), previousValue);
            previousValue = currentValue;
        }

        return updatedMap;
    }

    protected List<Double> countSorted(List<Double> inputArray, Map<Double, Integer> inputCountHashMap) {
        List<Double> result = new ArrayList<>(inputArray.size());

        for (double currentElement : inputArray) {
            int indexOfCurrentElementInHashMap = findKeyIndexInMap(currentElement, inputCountHashMap);
            if (indexOfCurrentElementInHashMap >= 0) {
                result.add(indexOfCurrentElementInHashMap, currentElement);
            } else {
                result.remove(currentElement);
            }
        }
        return result;
    }

    protected int findKeyIndexInMap(double key, Map<Double, Integer> inputCountHashMap) {
        int index = -1;
        int currentIndex = 0;

        for (Map.Entry<Double, Integer> entry : inputCountHashMap.entrySet()) {
            if (entry.getKey().equals(key)) {
                index = currentIndex;
                break;
            }
            currentIndex++;
        }

        return index;
    }

}


