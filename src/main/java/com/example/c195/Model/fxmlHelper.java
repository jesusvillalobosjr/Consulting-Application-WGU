package com.example.c195.Model;

import com.example.c195.Main.SchedulingApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.io.IOException;

/**Helper class to change scenes and show alerts.*/
public abstract class fxmlHelper {
    private static Stage stage;
    private static Parent scene;

    /**Method to change scene and the source object being a button.
     * @param event References event object created by the source object.
     * @param location Location of fxml file to change to.*/
    public static void changeSceneButton(ActionEvent event, String location) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(SchedulingApplication.class.getResource(location));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**Method to change scene and the source object being a radio button.
     * @param event References event object created by the source object.
     * @param location Location of fxml file to change to.*/
    public static void changeSceneRadio(ActionEvent event, String location) throws IOException {
        stage = (Stage)((RadioButton)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(SchedulingApplication.class.getResource(location));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**Method to show alert.
     * @param alert Alert object..
     * @param title Title of alert.
     * @param content Content of alert.*/
    public static void showAlert(Alert alert,String title,String content){
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }
}
