package com.example.c195.Controller;

import com.example.c195.Model.Menu;
import com.example.c195.Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

/**Class that works as the controller for the "Add Appointment View" .*/
public class AddAppointmentMenuController implements Initializable {

    @FXML
    private TextField apptDescriptionText;

    @FXML
    private Spinner<Integer> apptEndTimeHours;

    @FXML
    private Spinner<Integer> apptEndTimeMinutes;

    @FXML
    private TextField apptIDText;

    @FXML
    private TextField apptLocationText;

    @FXML
    private DatePicker apptStartDate;

    @FXML
    private Spinner<Integer> apptStartTimeHours;

    @FXML
    private Spinner<Integer> apptStartTimeMinutes;

    @FXML
    private TextField apptTitleText;

    @FXML
    private TextField apptTypeText;

    @FXML
    private ChoiceBox<Integer> customers;

    @FXML
    private ChoiceBox<Integer> users;
    @FXML
    private ChoiceBox<Integer> contacts;

    /**Method to add appointment.
     *  Method adds appointment by getting all text from input fields, verifying
     * input to ensure a valid appointment is made. Appointments are added into the
     * database as well as the user,contact, and appointment objects for the selected
     * objects.
     * @param event References event object created by the source object.*/
    @FXML
    void addAppointment(ActionEvent event) throws SQLException, IOException {
        String title = apptTitleText.getText();
        String description = apptDescriptionText.getText();
        String location = apptLocationText.getText();
        String type = apptTypeText.getText();
        var startDate = apptStartDate.getValue();
        var startHours = apptStartTimeHours.getValue();
        var startMinutes = apptStartTimeMinutes.getValue();
        var endHours = apptEndTimeHours.getValue();
        var endMinutes = apptEndTimeMinutes.getValue();
        var valid = Menu.verifyInputAppointment(title,description,location,type,startDate,apptTitleText,apptDescriptionText,apptLocationText,apptTypeText,apptStartDate);
        if(!valid){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            fxmlHelper.showAlert(alert,"Invalid Input","Missing fields are marked in red and need to be filled.");
            return;
        }
        ZonedDateTime startDateTime = ZonedDateTime.of(startDate,LocalTime.of(startHours,startMinutes,0), ZoneId.systemDefault());
        ZonedDateTime endDateTime = ZonedDateTime.of(startDate,LocalTime.of(endHours,endMinutes,0), ZoneId.systemDefault());
        valid = Menu.verifyTimeAndDate(startDateTime,endDateTime,apptStartDate,apptStartTimeHours,apptStartTimeMinutes,apptEndTimeHours,apptEndTimeMinutes);
        if(!valid){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            fxmlHelper.showAlert(alert,"Invalid times", "Business hours are Monday-Friday 8 a.m to 10 p.m ET");
            return;
        }
        var createdTime = Timestamp.from(Instant.now().truncatedTo(ChronoUnit.MILLIS));
        Timestamp start = Timestamp.valueOf(startDateTime.toLocalDate() + " " + startDateTime.toLocalTime() + ":00");
        Timestamp end = Timestamp.valueOf(endDateTime.toLocalDate() + " " + endDateTime.toLocalTime() + ":00");
        int customerID = customers.getValue();
        int userID = users.getValue();
        int contactID = contacts.getValue();

        if(Menu.overlappingAddAppointment(customerID,startDate,start.toLocalDateTime().toLocalTime(),end.toLocalDateTime().toLocalTime(),apptStartTimeHours,apptStartTimeMinutes,apptEndTimeHours,apptEndTimeMinutes)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            fxmlHelper.showAlert(alert,"Overlap in Appointments", Menu.overlapMessageAdd(customerID,startDate,start.toLocalDateTime().toLocalTime(), end.toLocalDateTime().toLocalTime()));
            return;
        }

        applicationHelper.addAppointment(title,description,location,type,start,end,createdTime,applicationHelper.currentAccountUser,createdTime, applicationHelper.currentAccountUser,customerID,userID,contactID);

        var appt = applicationHelper.getAppointment();
        Appointment.addAppointment(appt);
        var customer = Customer.getCustomer(customerID);
        customer.addAppointmentForCustomer(appt);
        var contact = Contact.getContact(contactID);
        contact.addAppointmentForContact(appt);
        var user = User.getUser(userID);
        user.addAppointmentForUser(appt);

        fxmlHelper.changeSceneButton(event,"/com/example/c195/MainMenuAllAppointments.fxml");
    }

    /**Method changes description text box border color to grey.
     * Method will have the description text box, when clicked, to return to its
     * original grey background.
     * @param event References event object created by the source object.*/
    @FXML
    public void apptDescriptionClicked(MouseEvent event) {
        Menu.setBordersGrey(apptDescriptionText);
    }

    /**Method changes location text box border color to grey.
     * Method will have the location text box, when clicked, to return to its
     * original grey background.
     * @param event References event object created by the source object.*/
    @FXML
    public void apptLocationClicked(MouseEvent event) {
        Menu.setBordersGrey(apptLocationText);
    }

    /**Method changes start date picker border color to grey.
     * Method will have the start date picker, when clicked, to return to its
     * original grey background.
     * @param event References event object created by the source object.*/
    @FXML
    public void apptStartDateClicked(ActionEvent event) {
        Menu.setBordersGrey(apptStartDate);
    }

    /**Method changes title text box border color to grey.
     * Method will have the title text box, when clicked, to return to its
     * original grey background.
     * @param event References event object created by the source object.*/
    @FXML
    public void apptTitleClicked(MouseEvent event) {
        Menu.setBordersGrey(apptTitleText);
    }

    /**Method changes type text box border color to grey.
     * Method will have the type text box, when clicked, to return to its
     * original grey background.
     * @param event References event object created by the source object.*/
    @FXML
    public void apptTypeClicked(MouseEvent event) {
        Menu.setBordersGrey(apptTypeText);
    }

    /**Method returns user to the "All Appointments Menu".
     * Method will open a new scene returning the user back to the menu with
     * all appointments.
     * @param event References event object created by the source object.*/
    @FXML
    public void cancel(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneButton(event,"/com/example/c195/MainMenuAllAppointments.fxml");
    }


    /**Method to initialize time spinners.
     * Method will set the value of the spinners related to time. The hour spinners set from 0 to 23 with
     * an initial value of 0. The minute spinners set from 0 to 59 with an initial value of 0.*/
    private void setSpinners(){
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        SpinnerValueFactory<Integer> valueFactory3 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
        SpinnerValueFactory<Integer> valueFactory4 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        valueFactory.setValue(0);
        valueFactory2.setValue(0);
        valueFactory3.setValue(0);
        valueFactory4.setValue(0);
        apptStartTimeHours.setValueFactory(valueFactory);
        apptStartTimeMinutes.setValueFactory(valueFactory2);
        apptEndTimeHours.setValueFactory(valueFactory3);
        apptEndTimeMinutes.setValueFactory(valueFactory4);
    }

    /**Method to initialize the "Add Appointment Menu" by setting the spinners, contacts, customers, and users.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setSpinners();
        Menu.setContacts(contacts);;
        Menu.setCustomers(customers);
        Menu.setUsers(users);
    }
}

