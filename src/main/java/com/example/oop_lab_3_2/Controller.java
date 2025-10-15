package com.example.oop_lab_3_2;

import javafx.fxml.FXML;
import javafx.scene.control.*;
public class Controller {

    private Model model;
    @FXML private TextField textFieldA, textFieldB, textFieldC;
    @FXML private Spinner<Integer> spinnerA, spinnerB, spinnerC;
    @FXML private Slider sliderA, sliderB, sliderC;

    @FXML
    public void initialize() {
        model = new Model();

        spinnerA.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, model.getA()));
        spinnerB.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, model.getB()));
        spinnerC.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, model.getC()));

        sliderA.setValue(model.getA());
        sliderB.setValue(model.getB());
        sliderC.setValue(model.getC());

        textFieldA.setText(String.valueOf(model.getA()));
        textFieldB.setText(String.valueOf(model.getB()));
        textFieldC.setText(String.valueOf(model.getC()));

        addListeners();
    }

    private void addListeners() {

        setupTextField(textFieldA, "A");
        setupTextField(textFieldB, "B");
        setupTextField(textFieldC, "C");

        spinnerA.valueProperty().addListener((obs, oldV, newV) -> { model.setA(newV); updateView(); });
        spinnerB.valueProperty().addListener((obs, oldV, newV) -> { model.setB(newV); updateView(); });
        spinnerC.valueProperty().addListener((obs, oldV, newV) -> { model.setC(newV); updateView(); });

        sliderA.valueProperty().addListener((obs, oldV, newV) -> { model.setA(newV.intValue()); updateView(); });
        sliderB.valueProperty().addListener((obs, oldV, newV) -> { model.setB(newV.intValue()); updateView(); });
        sliderC.valueProperty().addListener((obs, oldV, newV) -> { model.setC(newV.intValue()); updateView(); });
    }

    private void setupTextField(TextField tf, String name) {
        tf.setOnAction(e -> updateModelFromTextField(tf, name));
        tf.focusedProperty().addListener((obs, oldF, newF) -> {
            if (!newF) updateModelFromTextField(tf, name);
        });
    }

    private void updateModelFromTextField(TextField tf, String name) {
        String text = tf.getText().trim();
        if (text.isEmpty()) {
            resetTextField(tf, name);
            return;
        }
        try {
            int value = Integer.parseInt(text);
            switch (name) {
                case "A": model.setA(value); break;
                case "B": model.setB(value); break;
                case "C": model.setC(value); break;
            }
        } catch (NumberFormatException ex) {
            resetTextField(tf, name);
        }
        updateView();
    }

    private void resetTextField(TextField tf, String name) {
        switch (name) {
            case "A": tf.setText(String.valueOf(model.getA())); break;
            case "B": tf.setText(String.valueOf(model.getB())); break;
            case "C": tf.setText(String.valueOf(model.getC())); break;
        }
    }

    private void updateView() {
        textFieldA.setText(String.valueOf(model.getA()));
        textFieldB.setText(String.valueOf(model.getB()));
        textFieldC.setText(String.valueOf(model.getC()));

        spinnerA.getValueFactory().setValue(model.getA());
        spinnerB.getValueFactory().setValue(model.getB());
        spinnerC.getValueFactory().setValue(model.getC());

        sliderA.setValue(model.getA());
        sliderB.setValue(model.getB());
        sliderC.setValue(model.getC());
    }

    public void save() { model.saveNumbers(); }
}
