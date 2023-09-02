package com.example.c195.Controller;
import com.example.c195.Model.*;
import com.example.c195.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**Class acts as a controller for "Main Menu All Appointments" view.*/
public class MainMenuAllAppointmentsController implements Initializable {

    private Stage stage;
    private Parent scene;
    @FXML
    private TableView<Appointment> appointments;

    @FXML
    private TableColumn<Appointment, String> apptCreatedBy;

    @FXML
    private TableColumn<Appointment, Timestamp> apptDateCreated;

    @FXML
    private TableColumn<Appointment, String> apptDescription;

    @FXML
    private TableColumn<Appointment, LocalDate> apptEndDate;

    @FXML
    private TableColumn<Appointment, LocalTime> apptEndTime;

    @FXML
    private TableColumn<Appointment, Integer> apptID;

    @FXML
    private TableColumn<Appointment, Timestamp> apptLastUpdated;

    @FXML
    private TableColumn<Appointment, String> apptLocation;

    @FXML
    private TableColumn<Appointment, LocalDate> apptStartDate;

    @FXML
    private TableColumn<Appointment, LocalTime> apptStartTime;

    @FXML
    private TableColumn<Appointment, String> apptTitle;

    @FXML
    private TableColumn<Appointment, String> apptType;

    @FXML
    private TableColumn<Appointment, String> apptUpdatedBy;

    @FXML
    private TableColumn<Appointment, Integer> contactID;

    @FXML
    private TableColumn<Appointment, Integer> customerID;

    @FXML
    private ToggleGroup select;

    @FXML
    private TableColumn<Appointment, Integer> userID;

    /**Method to change view to "Appointments by Month Menu".
     * @param event References event object created by the source object.*/
    @FXML
    public void changeToApptMonth(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneRadio(event,"/com/example/c195/MainMenuMonthlyAppointments.fxml");
    }

    /**Method to change view to "Appointments by Week Menu".
     * @param event References event object created by the source object.*/
    @FXML
    public void changeToApptWeek(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneRadio(event,"/com/example/c195/MainMenuWeeklyAppointments.fxml");
    }

    /**Method to change view to "Main Menu All Customers".
     * @param event References event object created by the source object.*/
    @FXML
    public void changeToCustomers(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneRadio(event,"/com/example/c195/MainMenuCustomers.fxml");
    }

    /**Method to change view to "Add Appointment Menu".
     * @param event References event object created by the source object.*/
    @FXML
    public void createAppointment(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneButton(event,"/com/example/c195/AddAppointmentMenu.fxml");
    }

    /**Method deletes Appointment selected.
     * Method will delete selected Appointment, removing it the Appointment, User,
     * Contact,Customer objects as well as the database. If appointment not selected the
     * program will let user know with a pop up error.
     * @param event References event object created by the source object.*/
    @FXML
    public void deleteAppointment(ActionEvent event) throws SQLException {
        var appointment = appointments.getSelectionModel().getSelectedItem();
        if(appointment == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            fxmlHelper.showAlert(alert,"No Appointment Selected","Appointment needs to be selected in order for deletion.");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete appointment?");
        Optional<ButtonType> option = alert.showAndWait();
        if(option.get() == ButtonType.OK){
            Appointment.removeAppointment(appointment);
            Customer.getCustomer(appointment.getCustomerID()).removeAppointmentForCustomer(appointment);
            Contact.getContact(appointment.getContactID()).removeAppointmentForContact(appointment);
            User.getUser(appointment.getUserID()).removeAppointment(appointment);
            applicationHelper.removeAppointment(appointment);
        }
    }

    /**Method logs user out of program.
     * @param event References event object created by the source object.*/
    @FXML
    public void logout(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneButton(event,"/com/example/c195/logInForm.fxml");
    }

    /**Method to change view to "Modify Appointment Menu".
     * Method gets selected appointment and sends the Appointment object
     * To the Modify Appointment Controller. If no appointment is selected an
     * error message is displayed to the user.
     * @param event References event object created by the source object.*/
    @FXML
    public void modifyAppointment(ActionEvent event) throws IOException {
        var appt = appointments.getSelectionModel().getSelectedItem();
        if(appt == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            fxmlHelper.showAlert(alert,"Appointment Not Selected","In order to modify an appointment, an appointment must be selected.");
            return;
        }

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/c195/ModifyAppointmentMenu.fxml"));
        loader.load();
        ModifyAppointmentMenuController MAMC = loader.getController();
        MAMC.setModifyAppointmentFields(appt);

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**Method changes view to the "Reports Contacts" page.
     *@param event References event object created by the source object.*/
    @FXML
    public void changeToReports(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneButton(event,"/com/example/c195/ReportsContacts.fxml");
    }

    /**Method initializes the "Main Menu ALl Appointments" view
     * Method shows all Appointments in Tableview.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        applicationHelper.showAppointmentsInTable(appointments,apptID,apptTitle,apptDescription,apptLocation,apptType,apptStartDate,apptEndDate,apptStartTime,apptEndTime,apptDateCreated,apptCreatedBy,apptLastUpdated,apptUpdatedBy,customerID,userID,contactID);
    }
}
