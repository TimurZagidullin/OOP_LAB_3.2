package com.example.oop_lab_3_2;

import javafx.fxml.FXML;
import javafx.scene.control.*;
public class Controller implements ModelListener {

    private Model model;
    @FXML private TextField textFieldA, textFieldB, textFieldC;
    @FXML private Spinner<Integer> spinnerA, spinnerB, spinnerC;
    @FXML private Slider sliderA, sliderB, sliderC;

    @FXML
    public void initialize() {
        model = new Model();
        model.addListener(this);

        spinnerA.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, model.getA()));
        spinnerC.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, model.getC()));

        SpinnerValueFactory.IntegerSpinnerValueFactory factoryB = new SpinnerValueFactory.IntegerSpinnerValueFactory(model.getA(), model.getC(), model.getB());
        spinnerB.setValueFactory(factoryB);

        sliderA.setMin(0); sliderA.setMax(100); sliderA.setValue(model.getA());
        sliderB.setMin(model.getA()); sliderB.setMax(model.getC()); sliderB.setValue(model.getB());
        sliderC.setMin(0); sliderC.setMax(100); sliderC.setValue(model.getC());

        textFieldA.setText(String.valueOf(model.getA()));
        textFieldB.setText(String.valueOf(model.getB()));
        textFieldC.setText(String.valueOf(model.getC()));

        addListeners();
    }

    private void addListeners() {
        setupTextField(textFieldA, "A");
        setupTextField(textFieldB, "B");
        setupTextField(textFieldC, "C");

        spinnerA.valueProperty().addListener((obs, oldV, newV) -> model.setA(newV));
        spinnerC.valueProperty().addListener((obs, oldV, newV) -> model.setC(newV));

        spinnerB.valueProperty().addListener((obs, oldV, newV) -> {
            if (newV < model.getA()) {
                spinnerB.getValueFactory().setValue(model.getA());
            } else if (newV > model.getC()) {
                spinnerB.getValueFactory().setValue(model.getC());
            } else {
                model.setB(newV);
            }
        });

        sliderA.valueProperty().addListener((obs, oldV, newV) -> model.setA(newV.intValue()));
        sliderC.valueProperty().addListener((obs, oldV, newV) -> model.setC(newV.intValue()));

        sliderB.valueProperty().addListener((obs, oldV, newV) -> {
            int value = newV.intValue();
            if (value < model.getA()) {
                sliderB.setValue(model.getA());
            } else if (value > model.getC()) {
                sliderB.setValue(model.getC());
            } else {
                model.setB(value);
            }
        });
    }

    @Override
    public void onModelChanged() {
        updateView();
    }

    private void updateView() {
        if (!textFieldA.getText().equals(String.valueOf(model.getA())))
            textFieldA.setText(String.valueOf(model.getA()));
        if (!textFieldB.getText().equals(String.valueOf(model.getB())))
            textFieldB.setText(String.valueOf(model.getB()));
        if (!textFieldC.getText().equals(String.valueOf(model.getC())))
            textFieldC.setText(String.valueOf(model.getC()));

        spinnerA.getValueFactory().setValue(model.getA());
        spinnerB.getValueFactory().setValue(model.getB());
        spinnerC.getValueFactory().setValue(model.getC());

        ((SpinnerValueFactory.IntegerSpinnerValueFactory)spinnerB.getValueFactory()).setMin(model.getA());
        ((SpinnerValueFactory.IntegerSpinnerValueFactory)spinnerB.getValueFactory()).setMax(model.getC());

        sliderA.setValue(model.getA());
        sliderB.setValue(model.getB());
        sliderC.setValue(model.getC());
    }

    public void save() {
        model.saveNumbers();
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
    }

    private void resetTextField(TextField tf, String name) {
        switch (name) {
            case "A": tf.setText(String.valueOf(model.getA())); break;
            case "B": tf.setText(String.valueOf(model.getB())); break;
            case "C": tf.setText(String.valueOf(model.getC())); break;
        }
    }
}
