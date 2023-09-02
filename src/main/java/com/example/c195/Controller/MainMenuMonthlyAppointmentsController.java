package com.example.c195.Controller;

import com.example.c195.Model.Menu;
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
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Optional;
import java.util.ResourceBundle;

/**Class that works as the controller for the "Appointments by Month Menu" view.*/
public class MainMenuMonthlyAppointmentsController implements Initializable {
    Stage stage;
    Parent scene;
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
    private ChoiceBox<String> selectMonth;

    @FXML
    private TableColumn<Appointment, Integer> userID;

    /**Method to change view to "Main Menu All Appointments".
     * @param event References event object created by the source object.*/
    @FXML
    void changeToAllAppts(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneRadio(event,"/com/example/c195/MainMenuAllAppointments.fxml");
    }

    /**Method to change view to "Appointments by Week Menu".
     * @param event References event object created by the source object.*/
    @FXML
    void changeToApptsWeek(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneRadio(event,"/com/example/c195/MainMenuWeeklyAppointments.fxml");
    }

    /**Method to change view to "Main Menu Customers".
     * @param event References event object created by the source object.*/
    @FXML
    void changeToCustomer(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneRadio(event,"/com/example/c195/MainMenuCustomers.fxml");
    }

    /**Method to change view to "Add Appointment Menu".
     * @param event References event object created by the source object.*/
    @FXML
    void createAppointment(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneButton(event,"/com/example/c195/AddAppointmentMenu.fxml");
    }

    /**Method deletes Appointment selected.
     * Method will delete selected Appointment, removing it the Appointment, User,
     * Contact,Customer objects as well as the database. If appointment not selected the
     * program will let user know with a pop up error.
     * @param event References event object created by the source object.*/
    @FXML
    void deleteAppointment(ActionEvent event) throws SQLException {
        var appointment = appointments.getSelectionModel().getSelectedItem();
        if(appointment == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            fxmlHelper.showAlert(alert,"No Customer Selected","Customer needs to be selected in order for deletion.");
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
    void logout(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneButton(event,"/com/example/c195/logInForm.fxml");
    }

    /**Method to change view to "Modify Appointment Menu".
     * Method gets selected appointment and sends the Appointment object
     * To the Modify Appointment Controller. If no appointment is selected an
     * error message is displayed to the user.
     * @param event References event object created by the source object.*/
    @FXML
    void modifyAppointment(ActionEvent event) throws IOException {
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

    /**Method to filter appointments based on month selected.
     * Lambda function in selectMonthForAppt makes it easy to filter appointments to Appointments of the month selected.*/
    @FXML
    public void selectMonthForAppt(){
        var month = selectMonth.getValue();
        Month monthFilter = Menu.getMonth(month);
        final var monthSelected = monthFilter;
        var filteredAppointments = Appointment.getAppointments().filtered(appointment -> appointment.getStartDate().getMonth().compareTo(monthSelected) == 0);
        applicationHelper.showAppointmentsInTable(appointments,apptID,apptTitle,apptDescription,apptLocation,apptType,apptStartDate,apptEndDate,apptStartTime,apptEndTime,apptDateCreated,apptCreatedBy,apptLastUpdated,apptUpdatedBy,customerID,userID,contactID,filteredAppointments);
    }

    /**Method changes view to the "Reports Contacts" page.
     *@param event References event object created by the source object.*/
    @FXML
    public void changeToReports(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneButton(event,"/com/example/c195/ReportsContacts.fxml");
    }

    /**Method to set the current month value for the select month choice box.*/
    private void setCurrentMonth(){
        Timestamp ts = Timestamp.from(Instant.now());
        var currentMonth = ts.toLocalDateTime().toLocalDate().getMonth();
        switch (currentMonth){
            case JANUARY:
                selectMonth.setValue(Menu.monthFilter[0]);
                break;
            case FEBRUARY:
                selectMonth.setValue(Menu.monthFilter[1]);
                break;
            case MARCH:
                selectMonth.setValue(Menu.monthFilter[2]);
            case APRIL:
                selectMonth.setValue(Menu.monthFilter[3]);
                break;
            case MAY:
                selectMonth.setValue(Menu.monthFilter[4]);
                break;
            case JUNE:
                selectMonth.setValue(Menu.monthFilter[5]);
                break;
            case JULY:
                selectMonth.setValue(Menu.monthFilter[6]);
                break;
            case AUGUST:
                selectMonth.setValue(Menu.monthFilter[7]);
                break;
            case SEPTEMBER:
                selectMonth.setValue(Menu.monthFilter[8]);
                break;
            case OCTOBER:
                selectMonth.setValue(Menu.monthFilter[9]);
                break;
            case NOVEMBER:
                selectMonth.setValue(Menu.monthFilter[10]);
                break;
            case DECEMBER:
                selectMonth.setValue(Menu.monthFilter[11]);
                break;
        }
    }

    /**Method initializes "Appointments by Month View"
     * Method sets the current month and filters based on the month selected being the current month.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setCurrentMonth();
        Menu.setMonthsFilter(selectMonth);
    }
}

