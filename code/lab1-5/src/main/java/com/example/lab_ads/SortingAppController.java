package com.example.lab_ads;

import com.example.lab_ads.executors.ArrayLabExecutor;
import com.example.lab_ads.executors.Lab1Executor;
import com.example.lab_ads.executors.Lab2Executor;
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
        String[] algorithms = {"lab 1: Selection Sort", "lab 2: Shell Sort", "lab 3", "lab 4", "lab 5"};
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
                    originalArrayText.setText("Invalid size of array! Try again");
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
            case "lab 3":
                clearText();
                originalArrayText.setText("Lab 3 haven`t created yet :(");
                break;
            case "lab 4":
                clearText();
                originalArrayText.setText("Lab 3 haven`t created yet :(");
                break;
            case "lab 5":
                clearText();
                originalArrayText.setText("Lab 3 haven`t created yet :(");
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