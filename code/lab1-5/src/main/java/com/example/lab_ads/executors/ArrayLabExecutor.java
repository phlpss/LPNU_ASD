package com.example.lab_ads.executors;

import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

import java.util.Arrays;
import java.util.List;


@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PROTECTED)
public abstract class ArrayLabExecutor<T> {
    Text originalArrayText;
    Text updatedArrayText;
    Text sortedArrayText;
    Text isSortedArrayText;
    Text timeElapsedText;
    TextArea outputTextArea;
    @NonFinal
    List<T> list;

    public void doThingsWithArray(int size) {
        list = generateList(size);
        originalArrayText.setText("Original Array:\t\t" + Arrays.toString(new List[]{list}));

        List<T> updatedArray = updateList();
        updatedArrayText.setText("Updated Array:\t" + Arrays.toString(new List[]{updatedArray}));

        long start = System.currentTimeMillis();
        List<T> sortedArray = sortList();
        long end = System.currentTimeMillis();

        if (isSorted(list)) {
            isSortedArrayText.setText(sortedArrayText.getText() + "\n\nCorrectly Sorted :)");
        } else {
            isSortedArrayText.setText(sortedArrayText.getText() + "\n\nIncorrectly Sorted :(");
        }
        sortedArrayText.setText("Sorted Array:\t\t" + Arrays.toString(new List[]{sortedArray}));
        timeElapsedText.setText("Sort executed in " + (end - start) + " milliseconds");
        list.clear();
    }

    protected abstract List<T> generateList(int size);
    protected abstract List<T> updateList();
    protected abstract List<T> sortList();
    protected abstract boolean isSorted(List<T> array);
}