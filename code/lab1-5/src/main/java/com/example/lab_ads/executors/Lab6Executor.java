package com.example.lab_ads.executors;

import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.util.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;

public class Lab6Executor {
    TextArea outputTextArea;
    Text winnerText;

    public Lab6Executor(javafx.scene.control.TextArea outputTextArea, javafx.scene.text.Text winnerText) {
        this.outputTextArea = outputTextArea;
        this.winnerText = winnerText;
    }

    public void doThingsWithArray(int size) {
        List<Integer> list1 = generateList(size);
        List<Integer> list2 = new ArrayList<>(list1);
        List<Integer> list3 = new ArrayList<>(list1);
        List<Integer> list4 = new ArrayList<>(list1);
        List<Integer> list5 = new ArrayList<>(list1);

        long selectionSort = measureTime(() -> selectionSort(list1));
        long shellSort = measureTime(() -> shellSort(list2));
        long quickSort = measureTime(() -> quickSort(list3));
        long mergeSort = measureTime(() -> mergeSort(list4));
        long countSort = measureTime(() -> countSort(list5));

        outputTextArea.appendText("Selection Sort:\t\t" + selectionSort + " milliseconds\n");
        outputTextArea.appendText("Shell Sort:\t\t" + shellSort + " milliseconds\n");
        outputTextArea.appendText("Quick Sort:\t\t" + quickSort + " milliseconds\n");
        outputTextArea.appendText("Merge Sort:\t\t" + mergeSort + " milliseconds\n");
        outputTextArea.appendText("Count Sort:\t\t" + countSort + " milliseconds\n");

        String winner = calculateTheFastestAlgorithm(selectionSort, shellSort, quickSort, mergeSort, countSort);
        winnerText.setText("\nThe winner: " + winner + "\n");
    }

    private long measureTime(Runnable sortFunction) {
        CompletableFuture<Void> future = CompletableFuture.runAsync(sortFunction);
        long startTime = System.currentTimeMillis();
        try {
            future.get(5, TimeUnit.MINUTES);
        } catch (InterruptedException | ExecutionException e) {
            // Handle the exception - maybe another kind of sorting error occurred
            e.printStackTrace();
        } catch (TimeoutException e) {
            outputTextArea.appendText("Error: Sorting took more than 5 minutes\n");
            return -1; // Indicate an error with a special value, or you can throw an exception.
        }
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    protected List<Integer> generateList(int size) {
        Random random = new Random();
        List<Integer> array = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            array.add(i, random.nextInt(1000) + 1);
        }
        return array;
    }

    protected void selectionSort(List<Integer> inputArray) {
        for (int i = 0; i < inputArray.size() - 1; i++) {
            int minIndex = i;
            // get min index
            for (int j = i + 1; j < inputArray.size(); j++) {
                if (inputArray.get(j) < inputArray.get(minIndex)) {
                    minIndex = j;
                }
            }
            int temp = inputArray.get(minIndex);
            inputArray.set(minIndex, inputArray.get(i));
            inputArray.set(i, temp);
        }
        validateSort(inputArray, "Selection Sort: Incorrectly sorted\n");
    }

    protected void shellSort(List<Integer> inputArray) {
        int arraySize = inputArray.size();
        for (int gap = arraySize / 2; gap > 0; gap /= 2) {
            // Проходимось по елементам масиву
            for (int i = gap; i < arraySize; i++) {
                int currentElement = inputArray.get(i);
                int j;
                // Порівнюємо елементи на відстані gap та переміщуємо їх, якщо потрібно
                for (j = i; j >= gap && inputArray.get(j - gap).compareTo(currentElement) > 0; j -= gap) {
                    inputArray.set(j, inputArray.get(j - gap));
                }
                inputArray.set(j, currentElement);
            }
        }

        validateSort(inputArray, "Shell Sort: Incorrectly sorted\n");
    }

    protected List<Integer> quickSort(List<Integer> inputArray) {
        if (inputArray.size() <= 1) {
            return inputArray;
        }

        int pivot = inputArray.get(0);

        // create three parts of the array:
        List<Integer> less = new ArrayList<>();      // element < pivot
        List<Integer> equals = new ArrayList<>();    // element == pivot
        List<Integer> greater = new ArrayList<>();   // element > pivot

        // get sum of elements in each of row and put row in correct place
        for (int i : inputArray) {
            if (i < pivot) {
                less.add(i);
            } else if (i > pivot) {
                greater.add(i);
            } else {
                equals.add(i);
            }
        }

        // sort each of the partition recursively and add it to sorted matrix
        List<Integer> sortedList = new ArrayList<>();
        sortedList.addAll(quickSort(less));
        sortedList.addAll(equals);
        sortedList.addAll(quickSort(greater));

        return sortedList;
    }

    protected List<Integer> mergeSort(List<Integer> inputArray) {
        if (inputArray.size() <= 1) {
            return inputArray;
        }

        int size = inputArray.size();
        int middle = size / 2;

        List<Integer> leftPart = inputArray.subList(0, middle);
        List<Integer> rightPart = inputArray.subList(middle, size);

        leftPart = mergeSort(leftPart);
        rightPart = mergeSort(rightPart);

        return merge(leftPart, rightPart);
    }

    protected List<Integer> merge(List<Integer> leftPart, List<Integer> rightPart) {
        List<Integer> result = new ArrayList<>();
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
        return result;
    }

    protected void countSort(List<Integer> inputArray) {
        int max = Collections.max(inputArray);
        int min = Collections.min(inputArray);
        int[] countArray = new int[max - min + 1];

        // Calculate count of occurrences
        for (int number : inputArray) {
            countArray[number - min]++;
        }

        // Populate the sorted values back to inputArray
        int index = 0;
        for (int i = 0; i < countArray.length; i++) {
            while (countArray[i] > 0) {
                inputArray.set(index++, i + min);
                countArray[i]--;
            }
        }

        // Let's separate out the validation part
        validateSort(inputArray, "Count Sort: Incorrectly sorted\n");
    }

    private void validateSort(List<Integer> array, String message) {
        if (!isSorted(array)) {
            outputTextArea.appendText(message);
            throw new IllegalStateException("Count Sort: Incorrectly sorted");
        }
    }

    protected boolean isSorted(List<Integer> inputArray) {
        return IntStream.range(1, inputArray.size())
                .noneMatch(i -> inputArray.get(i) < inputArray.get(i - 1));
    }

    protected String calculateTheFastestAlgorithm(long selectionSort, long shellSort, long quickSort,
                                                  long mergeSort, long countSort) {
        Map<String, Long> algorithmTimes = new LinkedHashMap<>();
        algorithmTimes.put("Selection Sort", selectionSort);
        algorithmTimes.put("Shell Sort", shellSort);
        algorithmTimes.put("Quick Sort", quickSort);
        algorithmTimes.put("Merge Sort", mergeSort);
        algorithmTimes.put("Count Sort", countSort);

        return Collections.min(algorithmTimes.entrySet(), Map.Entry.comparingByValue()).getKey();
    }
}
