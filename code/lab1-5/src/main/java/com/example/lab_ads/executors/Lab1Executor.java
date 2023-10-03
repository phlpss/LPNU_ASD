package com.example.lab_ads.executors;

import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Lab1Executor extends ArrayLabExecutor<Integer>{
    private static final List<Integer> array = new ArrayList<>();
    public Lab1Executor(Text originalArrayText, Text updatedArrayText, Text sortedArrayText, Text timeElapsedText, Text isSortedArrayText, TextArea outputTextArea) {
        super(originalArrayText, updatedArrayText, sortedArrayText, timeElapsedText, isSortedArrayText, outputTextArea);
    }

    @Override
    protected List<Integer> generateList(int size) {
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array.add(i, random.nextInt(100) + 1);
        }
        return array;
    }

    @Override
    protected List<Integer> updateList() {
        // if the element is divisible by 3 -- > delete
        // otherwise -- > raise to degree 2
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i) % 3 == 0) {
                array.remove(i);
                i--;
            } else {
                array.set(i, array.get(i) * array.get(i));
            }
        }
        return array;
    }

    @Override
    protected List<Integer> sortList(List<Integer> inputArray) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < inputArray.size() - 1; i++) {
            int minIndex = i;
            // get min index
            for (int j = i + 1; j < inputArray.size(); j++) {
                if (array.get(j) < array.get(minIndex)) {
                    minIndex = j;
                }
            }
            int temp = inputArray.get(minIndex);
            inputArray.set(minIndex, inputArray.get(i));
            inputArray.set(i, temp);
            stringBuilder.append(Arrays.toString(new List[]{inputArray})).append("\n");
        }
        outputTextArea.appendText(stringBuilder.toString());
        return inputArray;
    }

    @Override
    protected boolean isSorted(List<Integer> inputArray) {
        for (int i = 1; i < array.size(); i++) {
            if (array.get(i) < array.get(i - 1)) {
                return false;
            }
        }
        return true;
    }
}
