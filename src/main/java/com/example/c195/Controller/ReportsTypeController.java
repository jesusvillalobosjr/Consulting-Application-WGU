package com.example.c195.Controller;

import com.example.c195.Model.Appointment;
import com.example.c195.Model.Menu;
import com.example.c195.Model.applicationHelper;
import com.example.c195.Model.fxmlHelper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**Class that works as the controller for the "Reports Type" view.*/
public class ReportsTypeController implements Initializable {

    @FXML
    private Label amountOfType;

    @FXML
    private TableView<Appointment> appointments;

    @FXML
    private TableColumn<Appointment, String> apptDescription;

    @FXML
    private TableColumn<Appointment, LocalDate> apptEndDate;

    @FXML
    private TableColumn<Appointment, LocalTime> apptEndTime;

    @FXML
    private TableColumn<Appointment, Integer> apptID;

    @FXML
    private TableColumn<Appointment, LocalDate> apptStartDate;

    @FXML
    private TableColumn<Appointment, LocalTime> apptStartTime;

    @FXML
    private TableColumn<Appointment, String> apptTitle;

    @FXML
    private TableColumn<Appointment, String> apptType;

    @FXML
    private TableColumn<Appointment, Integer> customerID;

    @FXML
    private ToggleGroup reports;

    @FXML
    private ChoiceBox<String> selectType;

    /**Method to change to "Reports Contacts" view.
     * @param event References event object created by the source object.*/
    @FXML
    public void changeContacts(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneRadio(event,"/com/example/c195/ReportsContacts.fxml");
    }

    /**Method to change to "Reports Customer" view.
     * @param event References event object created by the source object.*/
    @FXML
    public void changeToCustomer(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneRadio(event,"/com/example/c195/ReportsCustomer.fxml");
    }

    /**Method to change to "Reports Month" view.
     * @param event References event object created by the source object.*/
    @FXML
    public void changeToMonth(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneRadio(event,"/com/example/c195/ReportsMonth.fxml");
    }

    /**Method to change to "Main Menu Customers" view.
     * @param event References event object created by the source object.*/
    @FXML
    public void goBack(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneButton(event,"/com/example/c195/MainMenuCustomers.fxml");
    }

    //Lambda function in selectTypeAppointments facilitates the ability to filter appointments by type.

    /**Method to filter appointments depending on select type choice box value.
     * @param event References event object created by the source object.*/
    @FXML
    public void selectTypeAppointments(ActionEvent event){
        String type = selectType.getValue();
        ObservableList<Appointment> filteredAppointments = Appointment.getAppointments().filtered(appointment -> appointment.getType().equals(type));
        applicationHelper.showAppointmentInReport(filteredAppointments,apptID,apptTitle,apptDescription,apptType,apptStartDate,apptStartTime,apptEndDate,apptEndTime,customerID,appointments);
        amountOfType.setText(String.valueOf(filteredAppointments.size()));
    }

    /**Method initializes "Reports Type" view.
     * Method sets choices for select type choice box.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Menu.setType(selectType);
    }
}
