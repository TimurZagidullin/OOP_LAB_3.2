package com.example.oop_lab_3_2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class View extends Application {
    private Controller controller;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("lab_3_2.fxml"));
        Scene scene = new Scene(loader.load());
        controller = loader.getController();

        stage.setScene(scene);
        stage.setTitle("Лабораторная работа №3.1");
        stage.show();
    }

    @Override
    public void stop() {
        controller.save();
    }

    public static void main(String[] args) {
        launch();
    }
}

