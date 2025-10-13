package com.example.oop_lab_3_2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class Main extends Application {

    private Model model;
    private TextField textFieldA, textFieldB, textFieldC;
    private Spinner<Integer> spinnerA, spinnerB, spinnerC;
    private Slider sliderA, sliderB, sliderC;

    @Override
    public void start(Stage stage) {
        model = new Model();

        textFieldA = new TextField(String.valueOf(model.getA()));
        textFieldB = new TextField(String.valueOf(model.getB()));
        textFieldC = new TextField(String.valueOf(model.getC()));

        setupCustomTextField(textFieldA, "A"); setupCustomTextField(textFieldB, "B"); setupCustomTextField(textFieldC, "C");

        spinnerA = new Spinner<>(0, 100, model.getA());
        spinnerB = new Spinner<>(0, 100, model.getB());
        spinnerC = new Spinner<>(0, 100, model.getC());

        spinnerA.valueProperty().addListener((obs, oldV, newV) -> { model.setA(newV); updateView(); });
        spinnerB.valueProperty().addListener((obs, oldV, newV) -> { model.setB(newV); updateView(); });
        spinnerC.valueProperty().addListener((obs, oldV, newV) -> { model.setC(newV); updateView(); });

        sliderA = new Slider(0, 100, model.getA());
        sliderB = new Slider(0, 100, model.getB());
        sliderC = new Slider(0, 100, model.getC());

        sliderA.valueProperty().addListener((obs, oldV, newV) -> { model.setA(newV.intValue()); updateView(); });
        sliderB.valueProperty().addListener((obs, oldV, newV) -> { model.setB(newV.intValue()); updateView(); });
        sliderC.valueProperty().addListener((obs, oldV, newV) -> { model.setC(newV.intValue()); updateView(); });

        Label lbl_abc = new Label("A      B       C");
        lbl_abc.setStyle("-fx-font-weight: bold; -fx-font-size: 70;");
        VBox colA = new VBox(20, textFieldA, spinnerA, sliderA);
        VBox colB = new VBox(20, textFieldB, spinnerB, sliderB);
        VBox colC = new VBox(20, textFieldC, spinnerC, sliderC);
        FlowPane flow = new FlowPane(50, 10, colA, colB, colC);

        BorderPane root = new BorderPane();
        root.setTop(lbl_abc);
        BorderPane.setAlignment(lbl_abc, Pos.CENTER);
        root.setCenter(flow);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 600, 240);
        stage.setScene(scene);
        stage.setTitle("Лаборатораная работа № 3.1");
        stage.show();
    }

    private void setupCustomTextField(TextField tf, String name) {
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

    @Override
    public void stop() {
        model.saveNumbers();
    }

    public static void main(String[] args) {
        launch();
    }
}

