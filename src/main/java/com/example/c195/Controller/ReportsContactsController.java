package com.example.c195.Controller;

import com.example.c195.Model.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**Class that works as the controller for the "Reports Contacts" view.*/
public class ReportsContactsController implements Initializable {

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
    private ChoiceBox<Integer> selectContact;

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

    /**Method to change to "Reports Type" view.
     * @param event References event object created by the source object.*/
    @FXML
    public void changeToType(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneRadio(event,"/com/example/c195/ReportsType.fxml");
    }

    /**Method to change to "Main Menu Customers" view.
     * @param event References event object created by the source object.*/
    @FXML
    public void goBack(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneButton(event,"/com/example/c195/MainMenuCustomers.fxml");
    }

    /**Method to filter appointments depending on select contact choice box value.
     * @param event References event object created by the source object.*/
    @FXML
    public void filterContactAppointments(ActionEvent event){
        var selectedContactID = selectContact.getValue();
        ObservableList<Appointment> list = Contact.getContact(selectedContactID).getAppointmentSchedule();
        applicationHelper.showAppointmentInReport(list,apptID,apptTitle,apptDescription,apptType,apptStartDate,apptStartTime,apptEndDate,apptEndTime,customerID,appointments);
    }

    /**Method initializes "Reports Contacts" view.
     * Method sets choices for select contact choice box.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Menu.setContacts(selectContact);
    }
}

