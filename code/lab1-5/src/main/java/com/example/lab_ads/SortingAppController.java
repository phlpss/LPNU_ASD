package com.example.lab_ads;

import com.example.lab_ads.executors.*;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class SortingAppController {
    @FXML
    private TextField numberInput;
    @FXML
    private Button button;
    @FXML
    private Text originalArrayText;
    @FXML
    private Text updatedArrayText;
    @FXML
    private Text sortedArrayText;
    @FXML
    private Text isSortedArrayText;
    @FXML
    private Text timeElapsedText;
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private TextArea outputTextArea;
    private int arraySize = 0;

    public void initialize() {
        String[] algorithms = {"lab 1: Selection Sort", "lab 2: Shell Sort", "lab 3: Quicksort", "lab 4: Merge Sort", "lab 5: Counting Sort", "lab 6: The Winner"};
        choiceBox.getItems().addAll(algorithms);
        choiceBox.setValue(algorithms[0]);

        button.setOnAction(e -> {
            clearText();

            // Create a pause transition
            PauseTransition pause = new PauseTransition(Duration.seconds(0.5));

            // Set what should happen after the pause
            pause.setOnFinished(event -> {
                // user input arraySize and if it`s valid, do lab
                arraySize = Integer.parseInt(numberInput.getText());
                if (isArraySizeValid(arraySize)) {
                    getChoice(choiceBox);
                } else {
                    originalArrayText.setText("Invalid dimension! Try again");
                }
            });

            // Start the pause
            pause.play();
        });
    }

    private void getChoice(ChoiceBox<String> choiceBox) {
        switch (choiceBox.getValue()) {
            case "lab 1: Selection Sort":
                // todo add factory methods
                // factory method should build the objects
                clearText();
                ArrayLabExecutor<Integer> executor1 = new Lab1Executor(originalArrayText, updatedArrayText, sortedArrayText, timeElapsedText, isSortedArrayText, outputTextArea);
                executor1.doThingsWithArray(arraySize);
                break;

            case "lab 2: Shell Sort":
                clearText();
                ArrayLabExecutor<String> executor2 = new Lab2Executor(originalArrayText, updatedArrayText, sortedArrayText, timeElapsedText, isSortedArrayText, outputTextArea);
                executor2.doThingsWithArray(arraySize);
                break;
            case "lab 3: Quicksort":
                clearText();
                Lab3Executor executor3 = new Lab3Executor(originalArrayText, updatedArrayText, sortedArrayText, timeElapsedText, isSortedArrayText, outputTextArea);
                executor3.doThingsWithArray(arraySize);
                break;
            case "lab 4: Merge Sort":
                clearText();
                ArrayLabExecutor<Double> executor4 = new Lab4Executor(originalArrayText, updatedArrayText, sortedArrayText, timeElapsedText, isSortedArrayText, outputTextArea);
                executor4.doThingsWithArray(arraySize);
                break;
            case "lab 5: Counting Sort":
                clearText();
                ArrayLabExecutor<Double> executor5 = new Lab5Executor(originalArrayText, updatedArrayText, sortedArrayText, timeElapsedText, isSortedArrayText, outputTextArea);
                executor5.doThingsWithArray(arraySize);
                break;
            case "lab 6: The Winner":
                clearText();
                Lab6Executor executor6 = new Lab6Executor(outputTextArea, originalArrayText);
                executor6.doThingsWithArray(arraySize);
                break;
        }
    }

    private void clearText() {
        // Clear the text fields first
        originalArrayText.setText("");
        updatedArrayText.setText("");
        sortedArrayText.setText("");
        timeElapsedText.setText("");
        outputTextArea.clear();
    }

    private boolean isArraySizeValid(int arraySize) {
        return arraySize > 0 && arraySize < 1000000;
    }
}