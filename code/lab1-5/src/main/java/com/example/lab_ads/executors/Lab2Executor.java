package com.example.lab_ads.executors;

import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Lab2Executor extends ArrayLabExecutor<String> {
    private static List<String> cities = List.of(
            "Kyiv", "Lviv", "Oslo", "Trondheim", "Bergen", "Drammen", "New York",
            "Los Angeles", "Chicago", "Houston", "Phoenix", "Philadelphia", "San Antonio", "San Diego",
            "Dallas", "San Jose", "Kristiansand", "Bodo", "Mysen", "Bryne", "London", "Paris", "Berlin",
            "Madrid", "Rome", "Amsterdam", "Brussels", "Lisbon", "Warsaw", "Prague", "Vienna", "Budapest",
            "Dublin", "Copenhagen", "Athens", "Stockholm", "Helsinki", "Tallinn", "Riga", "Vilnius",
            "Tokyo", "Beijing", "Seoul", "Bangkok", "Sydney", "Melbourne", "Auckland", "Wellington", "Mumbai",
            "Delhi", "Cairo", "Cape Town", "Nairobi", "Lima", "Buenos Aires", "Rio de Janeiro", "Sao Paulo",
            "Bogota", "Caracas", "Mexico City"
    );
    private static List<String> array = new ArrayList<String>();

    public Lab2Executor(Text originalArrayText, Text updatedArrayText, Text sortedArrayText, Text timeElapsedText, Text isSortedArrayText, TextArea outputTextArea) {
        super(originalArrayText, updatedArrayText, sortedArrayText, timeElapsedText, isSortedArrayText, outputTextArea);
    }


    @Override
    protected List<String> generateList(int size) {
        Random random = new Random();

        for (int i = 0; i < size; i++) {
            int randomIndex = random.nextInt(cities.size());
            array.add(cities.get(randomIndex));
        }
        return array;
    }

    @Override
    protected List<String> updateList() {
        array.removeIf(city -> city.length() > 8);
        return array;
    }

    @Override
    protected List<String> sortList() {
        StringBuilder stringBuilder = new StringBuilder();
        int arraySize = array.size();
        for (int gap = arraySize / 2; gap > 0; gap /= 2) {
            // Проходимось по елементам масиву
            for (int i = gap; i < arraySize; i++) {
                String currentElement = array.get(i);
                int j;
                // Порівнюємо елементи на відстані gap та переміщуємо їх, якщо потрібно
                for (j = i; j >= gap && array.get(j - gap).compareTo(currentElement) > 0; j -= gap) {
                    array.set(j, array.get(j - gap));
                }
                array.set(j, currentElement);
            }
            stringBuilder.append(Arrays.toString(new List[]{array})).append("\n");
        }
        outputTextArea.appendText(stringBuilder.toString());
        return array;
    }

    @Override
    protected boolean isSorted(List<String> array) {
        for (int i = 1; i < array.size(); i++) {
            if (array.get(i).compareTo(array.get(i - 1)) < 0) {
                return false;
            }
        }
        return true;
    }
}
