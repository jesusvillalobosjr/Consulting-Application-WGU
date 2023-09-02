package com.example.c195.Main;

import com.example.c195.Model.JDBC;
import com.example.c195.Model.applicationHelper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

/**Class for the customer and appointment application.
 * @author Jesus Villalobos Jr.*/
public class SchedulingApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SchedulingApplication.class.getResource("/com/example/c195/logInForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Log In");
        stage.setScene(scene);
        stage.show(); 
    }

    /**Main class that populates application with database information and proceeds to launch the program.*/
    public static void main(String[] args) throws SQLException, IOException {
        JDBC.openConnection();
        applicationHelper.populateCustomers();
        applicationHelper.populateContacts();
        applicationHelper.populateUsers();
        applicationHelper.populateAppointments();
        launch();
        JDBC.closeConnection();
    }
}