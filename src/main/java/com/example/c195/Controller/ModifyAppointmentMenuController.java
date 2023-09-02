package com.example.c195.Controller;

import com.example.c195.Model.*;
import com.example.c195.Model.Menu;
import com.example.c195.Model.User;
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

/**Class that works as the controller for the "Modify Appointment Menu" view.*/
public class ModifyAppointmentMenuController implements Initializable {

    @FXML
    private TextField apptDescriptionText;

    @FXML
    private DatePicker apptEndDate;

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
    private ChoiceBox<Integer> contacts;

    @FXML
    private ChoiceBox<Integer> customers;

    @FXML
    private ChoiceBox<Integer> users;

    private Appointment appointmentToModify = null;

    /**Method changes description text box border color to grey.
     * Method will have the description text box, when clicked, to return to its
     * original grey background.
     * @param event References event object created by the source object.*/
    @FXML
    void apptDescriptionClicked(MouseEvent event) {
        Menu.setBordersGrey(apptDescriptionText);
    }

    /**Method changes location text box border color to grey.
     * Method will have the location text box, when clicked, to return to its
     * original grey background.
     * @param event References event object created by the source object.*/
    @FXML
    void apptLocationClicked(MouseEvent event) {
        Menu.setBordersGrey(apptDescriptionText);
    }

    /**Method changes start date picker border color to grey.
     * Method will have the start date picker, when clicked, to return to its
     * original grey background.
     * @param event References event object created by the source object.*/
    @FXML
    void apptStartDateClicked(ActionEvent event) {
        Menu.setBordersGrey(apptDescriptionText);
    }

    /**Method changes title text box border color to grey.
     * Method will have the title text box, when clicked, to return to its
     * original grey background.
     * @param event References event object created by the source object.*/
    @FXML
    void apptTitleClicked(MouseEvent event) {
        Menu.setBordersGrey(apptDescriptionText);
    }

    /**Method changes type text box border color to grey.
     * Method will have the type text box, when clicked, to return to its
     * original grey background.
     * @param event References event object created by the source object.*/
    @FXML
    void apptTypeClicked(MouseEvent event) {
        Menu.setBordersGrey(apptDescriptionText);
    }

    /**Method returns user to the "All Appointments Menu".
     * Method will open a new scene returning the user back to the menu with
     * all appointments.
     * @param event References event object created by the source object.*/
    @FXML
    void cancel(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneButton(event,"/com/example/c195/MainMenuAllAppointments.fxml");
    }

    /**Method to modify appointment.
     *  Method adds appointment by getting all text from input fields, verifying
     * input to ensure a valid appointment is made. Appointments are updated into the
     * database as well as the user,contact, and appointment objects for the selected
     * objects regardless of ID difference.
     * @param event References event object created by the source object.*/
    @FXML
    void modifyAppointment(ActionEvent event) throws SQLException, IOException {
        int ID = Integer.parseInt(apptIDText.getText());
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
        var updatedTime = Timestamp.from(Instant.now().truncatedTo(ChronoUnit.MILLIS));
        Timestamp start = Timestamp.valueOf(startDateTime.toLocalDate() + " " + startDateTime.toLocalTime() + ":00");
        Timestamp end = Timestamp.valueOf(endDateTime.toLocalDate() + " " + endDateTime.toLocalTime() + ":00");
        int customerID = customers.getValue();
        int userID = users.getValue();
        int contactID = contacts.getValue();

        if(Menu.overlappingModifyAppointment(customerID,startDate,start.toLocalDateTime().toLocalTime(),end.toLocalDateTime().toLocalTime(),apptStartTimeHours,apptStartTimeMinutes,apptEndTimeHours,apptEndTimeMinutes,appointmentToModify)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            fxmlHelper.showAlert(alert,"Overlap in Appointments",Menu.overlapMessageModify(customerID,startDate,start.toLocalDateTime().toLocalTime(),end.toLocalDateTime().toLocalTime(),appointmentToModify));
            return;
        }

        applicationHelper.updateAppointment(ID,title,description,location,type,start,end,updatedTime,applicationHelper.currentAccountUser,customerID,userID,contactID);
        var appointment = applicationHelper.getAppointment(ID);

        if(customerID != appointmentToModify.getCustomerID()){
            var customer = Customer.getCustomer(customerID);
            customer.addAppointmentForCustomer(appointment);
            var oldCustomer = Customer.getCustomer(appointmentToModify.getCustomerID());
            oldCustomer.removeAppointmentForCustomer(appointmentToModify);
        }else {
            var customer = Customer.getCustomer(customerID);
            var oldIndex = customer.getAppointmentIndex(ID);
            customer.getCustomerAppointments().set(oldIndex,appointment);
        }

        if(contactID != appointmentToModify.getContactID()){
            var newContact = Contact.getContact(contactID);
            newContact.addAppointmentForContact(appointment);
            var oldContact = Contact.getContact(appointmentToModify.getContactID());
            oldContact.removeAppointmentForContact(appointmentToModify);
        }else{
            var contact = Contact.getContact(contactID);
            var oldindex = contact.getAppointmentIndex(ID);
            contact.getAppointmentSchedule().set(oldindex,appointment);
        }

        if(userID != appointmentToModify.getUserID()){
            var newUser = User.getUser(userID);
            newUser.addAppointmentForUser(appointment);
            var oldUser = User.getUser(appointmentToModify.getUserID());
            oldUser.removeAppointment(appointmentToModify);
        }else{
            var user = User.getUser(userID);
            var oldIndex = user.getAppointmentIndex(ID);
            user.getUserAppointments().set(oldIndex,appointment);
        }

        int oldAppointmentIndex = Appointment.getAppointmentIndex(appointmentToModify);
        Appointment.getAppointments().set(oldAppointmentIndex,appointment);

        fxmlHelper.changeSceneButton(event,"/com/example/c195/MainMenuAllAppointments.fxml");
    }

    /**Method to initialize time spinners.
     * Method will set the value of the spinners related to time. The hour spinners set from 0 to 23 with
     * an initial value of 0. The minute spinners set from 0 to 59 with an initial value of 0. The initial values set
     * to the hours and minutes passed into the method.
     * @param startHour Appointment start hours.
     * @param endHour Appointment end hours.
     * @param startMinute Appointment start minutes.
     * @param endMinute Appointment end minutes.*/
    private void setSpinners(int startHour,int startMinute,int endHour,int endMinute){
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
        SpinnerValueFactory<Integer> valueFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        SpinnerValueFactory<Integer> valueFactory3 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23);
        SpinnerValueFactory<Integer> valueFactory4 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        valueFactory.setValue(startHour);
        valueFactory2.setValue(startMinute);
        valueFactory3.setValue(endHour);
        valueFactory4.setValue(endMinute);
        apptStartTimeHours.setValueFactory(valueFactory);
        apptStartTimeMinutes.setValueFactory(valueFactory2);
        apptEndTimeHours.setValueFactory(valueFactory3);
        apptEndTimeMinutes.setValueFactory(valueFactory4);
    }

    /**Method to pass in appointment object into "Modify Appointment Menu" view.
     * Method receives Appointment to modify, filling fields with variable from
     * the Appointment object.
     * @param appointment Appointment object to be modified. */
    public void setModifyAppointmentFields(Appointment appointment){
        appointmentToModify = appointment;
        apptIDText.setText(String.valueOf(appointment.getApptID()));
        apptTitleText.setText(appointment.getTitle());
        apptDescriptionText.setText(appointment.getDescription());
        apptLocationText.setText(appointment.getLocation());
        apptTypeText.setText(appointment.getType());
        apptStartDate.setValue(appointment.getStartDate());
        apptEndDate.setValue(appointment.getEndDate());
        setSpinners(appointment.getStartTime().getHour(),appointment.getStartTime().getMinute(),appointment.getEndTime().getHour(),appointment.getEndTime().getMinute());
        customers.setValue(appointment.getCustomerID());
        users.setValue(appointment.getUserID());
        contacts.setValue(appointment.getContactID());
    }

    /**Method to initialize the "Modify Appointment Menu" view.
     * Method sets choice boxes for contacts,customers,and users.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Menu.setContacts(contacts);
        Menu.setCustomers(customers);
        Menu.setUsers(users);
    }
}

