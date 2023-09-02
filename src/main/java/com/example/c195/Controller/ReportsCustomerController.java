package com.example.c195.Controller;

import com.example.c195.Model.*;
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

/**Class that works as the controller for the "Reports Customer" view.*/
public class ReportsCustomerController implements Initializable {

    @FXML
    private Label amountOfCustomer;

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
    private ChoiceBox<Integer> selectCustomer;

    /**Method to change to "Reports Contacts" view.
     * @param event References event object created by the source object.*/
    @FXML
    void changeContacts(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneRadio(event,"/com/example/c195/ReportsContacts.fxml");
    }

    /**Method to change to "Reports Month" view.
     * @param event References event object created by the source object.*/
    @FXML
    void changeToMonths(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneRadio(event,"/com/example/c195/ReportsMonth.fxml");
    }

    /**Method to change to "Reports Type" view.
     * @param event References event object created by the source object.*/
    @FXML
    void changeToType(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneRadio(event,"/com/example/c195/ReportsType.fxml");
    }

    /**Method to change to "Main Menu Customers" view.
     * @param event References event object created by the source object.*/
    @FXML
    void goBack(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneButton(event,"/com/example/c195/MainMenuCustomers.fxml");
    }

    /**Method to filter appointments depending on select customer choice box value.
     * @param event References event object created by the source object.*/
    @FXML
    public void selectCustomerAppointments(ActionEvent event){
        var selectedCustomerID = selectCustomer.getValue();
        var customer = Customer.getCustomer(selectedCustomerID);
        var filteredAppointments = customer.getCustomerAppointments();
        applicationHelper.showAppointmentInReport(filteredAppointments,apptID,apptTitle,apptDescription,apptType,apptStartDate,apptStartTime,apptEndDate,apptEndTime,customerID,appointments);
        amountOfCustomer.setText(String.valueOf(filteredAppointments.size()));
    }

    /**Method initializes "Reports Customers" view.
     * Method sets choices for select customer choice box as well as the initial value.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        var first = Customer.getCustomers().get(0).getCustomerID();
        selectCustomer.setValue(first);
        Menu.setCustomers(selectCustomer);
    }
}

