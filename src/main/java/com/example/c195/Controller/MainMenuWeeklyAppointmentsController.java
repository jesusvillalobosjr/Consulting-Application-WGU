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
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

/**Class that works as the controller for the "Appointments by Week Menu" view.*/
public class MainMenuWeeklyAppointmentsController implements Initializable {
    Stage stage;
    Parent scene;
    @FXML
    private ToggleGroup select;

    @FXML
    private ChoiceBox<String> selectWeek;

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
    private TableColumn<Appointment, Integer> userID;

    /**Method to change view to "Main Menu All Customers".
     * @param event References event object created by the source object.*/
    @FXML
    public void changeToCustomers(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneRadio(event,"/com/example/c195/MainMenuCustomers.fxml");
    }

    /**Method to change view to "Main Menu All Appointments".
     * @param event References event object created by the source object.*/
    @FXML
    public void changeToAllAppts(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneRadio(event,"/com/example/c195/MainMenuAllAppointments.fxml");
    }

    /**Method to change view to "Appointments by Month Menu".
     * @param event References event object created by the source object.*/
    @FXML
    public void changeToApptsMonth(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneRadio(event,"/com/example/c195/MainMenuMonthlyAppointments.fxml");
    }

    //Lambda function allows for quick and easy filtering of Appointments for selected week.

    /**Method filters table view based on the select week choice box value.
     * @param event References event object created by the source object.*/
    @FXML
    public void changeFilter(ActionEvent event){
        Timestamp ts = Timestamp.from(Instant.now());
        LocalDate date = ts.toLocalDateTime().toLocalDate();
        var day = date.getDayOfWeek();
        if(selectWeek.getValue().equals("This Week")){
            date = date.minusDays(resetToFirstDayOfWeek(day,0));
        }else if(selectWeek.getValue().equals("One Week Prior")){
            date = date.minusDays(resetToFirstDayOfWeek(day,1));
        }else if(selectWeek.getValue().equals("Two Weeks Prior")){
            date = date.minusDays(resetToFirstDayOfWeek(day,2));
        }else{
            date = date.minusDays(resetToFirstDayOfWeek(day,3));
        }
        final LocalDate startDate = date;
        final LocalDate endDate = date.plusDays(6);
        var appointmentsFilter = Appointment.getAppointments().filtered(appointment -> appointment.getStartDate().compareTo(startDate) >= 0 && appointment.getStartDate().compareTo(endDate) <= 0);
        applicationHelper.showAppointmentsInTable(appointments,apptID,apptTitle,apptDescription,apptLocation,apptType,apptStartDate,apptEndDate,apptStartTime,apptEndTime,apptDateCreated,apptCreatedBy,apptLastUpdated,apptUpdatedBy,customerID,userID,contactID,appointmentsFilter);
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

    /**Method changes view to the "Reports Contacts" page.
     *@param event References event object created by the source object.*/
    @FXML
    public void changeToReports(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneButton(event,"/com/example/c195/ReportsContacts.fxml");
    }

    /**Method sets value to current week and the options for the select week choice box.*/
    private void setWeekFilter(){
        selectWeek.setValue(Menu.weekFilter[0]);
        selectWeek.getItems().addAll(Menu.weekFilter);
    }

    /**Method resets date to Monday based on the day of the week and select week choice box value.
     *@param day Day of the current week.
     *@param week Week or weeks representing the select week choice box value
     * returns Integer days to subtract.*/
    private int resetToFirstDayOfWeek(DayOfWeek day, int week){
        switch (day){
            case MONDAY:
                return 0 + (week * 7);
            case TUESDAY:
                return 1 + (week * 7);
            case WEDNESDAY:
                return 2 + (week * 7);
            case THURSDAY:
                return 3 + (week * 7);
            case FRIDAY:
                return 4 + (week * 7);
            case SATURDAY:
                return 5 + (week * 7);
            case SUNDAY:
                return 6 + (week * 7);
        }
        return -1;
    }

    /**Intializes "Appointments by Week Menu" view
     * Method sets the options for the select month choice box.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setWeekFilter();
    }
}
