package com.example.c195.Controller;

import com.example.c195.Model.Appointment;
import com.example.c195.Model.Menu;
import com.example.c195.Model.applicationHelper;
import com.example.c195.Model.fxmlHelper;
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

/**Class that works as the controller for the "Reports Month" view.*/
public class ReportsMonthController implements Initializable {

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
    private ChoiceBox<String> selectMonth;

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

    //Lambda function in selectMonthAppointment facilitates the ability to filter appointments by month.

    /**Method to filter appointments depending on select month choice box value.
     * @param event References event object created by the source object.*/
    @FXML
    public void selectMonthAppointment(ActionEvent event){
        var month = Menu.getMonth(selectMonth.getValue());
        var filteredAppointmets = Appointment.getAppointments().filtered(appointment -> appointment.getStartDate().getMonth().equals(month));
        applicationHelper.showAppointmentInReport(filteredAppointmets,apptID,apptTitle,apptDescription,apptType,apptStartDate,apptStartTime,apptEndDate,apptEndTime,customerID,appointments);
        amountOfType.setText(String.valueOf(filteredAppointmets.size()));
    }

    /**Method initializes "Reports Month" view.
     * Method sets choices for select month choice box as well as the initial value.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectMonth.setValue(Menu.monthFilter[0]);
        Menu.setMonthsFilter(selectMonth);
    }
}

