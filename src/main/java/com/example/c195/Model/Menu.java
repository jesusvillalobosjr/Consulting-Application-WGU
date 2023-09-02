package com.example.c195.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.sql.Timestamp;
import java.time.*;

/**Helper class for menu's*/
public class Menu {

    public static String[] countries = {"U.S","UK","Canada"};
    public static String[] usaFirstLevel = {"Alabama","Alaska","Arizona","Arkansas","California","Colorado","Connecticut","Delaware","Florida","Georgia","Hawaii","Idaho","Illinois","Indiana","Iowa","Kansas","Kentucky","Louisiana","Maine","Maryland","Massachusetts","Michigan","Minnesota","Mississippi","Missouri","Montana","Nebraska","Nevada","New Hampshire","New Jersey","New Mexico","New York","North Carolina","North Dakota","Ohio","Oklahoma","Oregon","Pennsylvania","Rhode Island","South Carolina","South Dakota","Tennessee","Texas","Utah","Vermont","Virginia","Washington","West Virginia","Wisconsin","Wyoming"};
    public static String[] canadaFirstLevel = {"Alberta","British Columbia","Manitoba","New Brunswick","Newfoundland and Labrador","Northwest Territories","NovaScotia","Nunavut","Ontario","Prince Edward Island","Quebec","Saskatchewan","Yukon"};
    public static String[] ukFirstLevel = {"England","Northern Ireland","Scotland","Wales"};
    public static String[] weekFilter = {"This Week","One Week Prior","Two Weeks Prior","Three Weeks Prior"};
    public static String[] monthFilter = {"January","February","March","April","May","June","July","August","September","October","November","December"};

    /**Method sets border of text field to red.
     * @param textField Text field to change border for.*/
    public static void setBordersRed(TextField textField){
        textField.setStyle("-fx-text-box-border: red");
    }

    /**Method sets border of choice box to red.
     * @param choiceBox Choice box to change border for.*/
    public static void setBordersRed(ChoiceBox<String> choiceBox){
        choiceBox.setStyle("-fx-border-color: red");
    }

    /**Method sets border of spinner to red.
     * @param spinner Spinner to change border for.*/
    public static void setBordersRed(Spinner<Integer> spinner) { spinner.setStyle("-fx-border-color: red"); }

    /**Method sets border of date picker to red.
     * @param datePicker Date picker to change border for.*/
    public static void setBordersRed(DatePicker datePicker) { datePicker.setStyle("-fx-border-color: red"); }

    /**Method sets border of text field to grey.
     * @param textField Text field to change border for.*/
    public static void setBordersGrey(TextField textField){
        textField.setStyle("-fx-text-box-border: grey");
    }

    /**Method sets border of choice box to grey.
     * @param choiceBox Choice box to change border for.*/
    public static void setBordersGrey(ChoiceBox<String> choiceBox){
        choiceBox.setStyle("-fx-border-color: grey");
    }

    /**Method sets border of date picker to grey.
     * @param datePicker Date picker to change border for.*/
    public static void setBordersGrey(DatePicker datePicker) { datePicker.setStyle("-fx-border-color: grey"); }

    /**Method sets the options for the division choice box based on the country choice box value.
     * @param customerCountry Country choice box to get value from.
     * @param customerDivision Division choice box to set choices of.*/
    public static void setDivision(ChoiceBox<String> customerCountry,ChoiceBox<String> customerDivision){
        String value = customerCountry.getValue();
        if(value.equals("U.S")){
            customerDivision.setValue("Select Division");
            customerDivision.getItems().setAll(Menu.usaFirstLevel);
        }else if(value.equals("UK")){
            customerDivision.setValue("Select Division");
            customerDivision.getItems().setAll(Menu.ukFirstLevel);
        }else{
            customerDivision.setValue("Select Division");
            customerDivision.getItems().setAll(Menu.canadaFirstLevel);
        }
    }

    /**Method sets contacts choice box based on all contacts of the company.
     * @param contacts choice box to set choices for.*/
    public static void setContacts(ChoiceBox<Integer> contacts){
        var first = Contact.getContactsList().get(0).getContactID();
        contacts.setValue(first);
        int size = Contact.getContactsList().size();
        Integer[] contactIDs = new Integer[size];
        for(int i = 0; i < size; i++){
            contactIDs[i] = Contact.getContactsList().get(i).getContactID();
        }
        contacts.getItems().addAll(contactIDs);
    }

    /**Method sets customers choice box based on all customers of the company.
     * @param customers choice box to set choices for.*/
    public static void setCustomers(ChoiceBox<Integer> customers){
        var first = Customer.getCustomers().get(0).getCustomerID();
        customers.setValue(first);
        int size = Customer.getCustomers().size();
        Integer[] customerIDs = new Integer[size];
        for(int i = 0; i < size; i++){
            customerIDs[i] = Customer.getCustomers().get(i).getCustomerID();
        }
        customers.getItems().addAll(customerIDs);
    }

    /**Method sets users choice box based on all users of the company.
     * @param users choice box to set choices for.*/
    public static void setUsers(ChoiceBox<Integer> users){
        var first = User.getUsers().get(0).getID();
        users.setValue(first);
        int size = User.getUsers().size();
        Integer[] userIDs = new Integer[size];
        for(int i = 0; i < size; i++){
            userIDs[i] = User.getUsers().get(i).getID();
        }
        users.getItems().addAll(userIDs);
    }

    /**Method sets type choice box based on all types of appointments.
     * Method gets all types listed for Appointments,excludes repeating ones,
     * and adds them as choices for the types choice box.
     * @param types choice box to set choices for.*/
    public static void setType(ChoiceBox<String> types){
        ObservableList<String> typeStrings = FXCollections.observableArrayList();
        for(var appointment : Appointment.getAppointments()){
            if(!typeStrings.contains(appointment.getType()))
                typeStrings.add(appointment.getType());
        }
        String[] typesFiltered = typeStrings.toArray(new String[0]);
        types.setValue(typesFiltered[0]);
        types.getItems().addAll(typesFiltered);
    }

    /**Method to verify input for aCustomer.
     * Method ensures every input field has a value, for fields that don't have a value
     * that field is turned red causing for an invalid Customer.
     * @param name Name value for Customer.
     * @param phone Phone number value for Customer.
     * @param country Country value for Customer.
     * @param division Division value for Customer.
     * @param address Address value for Customer.
     * @param zip Zip code value for Customer.
     * @param customerName Text field that holds Customer name.
     * @param customerPhone Text field that holds Customer phone number.
     * @param customerCountry Choice box that holds Customer country.
     * @param customerDivision Choice box that holds Customer division.
     * @param customerAddress Text field that holds Customer address.
     * @param customerZip Text field that holds Customer Zip code.
     * @return boolean on whether a customers input is valid or not.*/
    public static boolean verifyInputCustomer(String name,String phone,String country,String division,String address,String zip,TextField customerName,TextField customerPhone,ChoiceBox<String> customerCountry,ChoiceBox<String> customerDivision,TextField customerAddress,TextField customerZip){
        boolean valid = true;
        if(name.equals("")){
            Menu.setBordersRed(customerName);
            valid = false;
        }
        if(phone.equals("")){
            Menu.setBordersRed(customerPhone);
            valid = false;
        }
        if(country.equals("Select Country")){
            Menu.setBordersRed(customerCountry);
            valid = false;
        }
        if(division.equals("Select Division")){
            Menu.setBordersRed(customerDivision);
            valid = false;
        }
        if(address.equals("")){
            Menu.setBordersRed(customerAddress);
            valid = false;
        }
        if(zip.equals("")) {
            Menu.setBordersRed(customerZip);
            valid = false;
        }
        if(!valid){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            fxmlHelper.showAlert(alert,"Invalid Input","Missing fields are marked in red and need to be filled.");
            return valid;
        }
        return true;
    }

    /**Method to verify input when for an Appointment.
     * Method ensures every input field has a value, for fields that don't have a value
     * that field is turned red causing for an invalid Appointment.
     * @param title Title value for Appointment.
     * @param description Description value for Appointment.
     * @param location Location value for Appointment..
     * @param type Type value for Appointment.
     * @param startDate Starting date value for Appointment.
     * @param apptTitleText Text field that holds Appointment title.
     * @param apptDescriptionText Text field that holds Appointment description.
     * @param apptLocationText Text field that holds Appointment location.
     * @param apptTypeText Text field that holds Appointment type.
     * @param apptStartDate Text field that holds Appointment start date.
     * @return boolean on whether an appointments input is valid.*/
    public static boolean verifyInputAppointment(String title, String description, String location, String type, LocalDate startDate, TextField apptTitleText, TextField apptDescriptionText, TextField apptLocationText, TextField apptTypeText, DatePicker apptStartDate){
        boolean valid = true;
        if(title.equals("")){
            Menu.setBordersRed(apptTitleText);
            valid = false;
        }
        if(description.equals("")){
            Menu.setBordersRed(apptDescriptionText);
            valid = false;
        }
        if(location.equals("")){
            Menu.setBordersRed(apptLocationText);
            valid = false;
        }
        if(type.equals("")){
            Menu.setBordersRed(apptTypeText);
            valid = false;
        }
        if(startDate == null){
            Menu.setBordersRed(apptStartDate);
            valid = false;
        }
        return valid;
    }

    /**Method to verify times and dates for an Appointment.
     * Method ensures the date and times for an Appointment are valid
     * ,for fields that are not valid the field is turned red. Checks against
     * business days and hours of company in Eastern Time.
     * causing for an invalid Appointment.
     * @param startDateTime Start date and time value for the Appointment.
     * @param endDateTime End date and time value for Appointment.
     * @param apptStartTimeHours Spinner that holds Appointment hour of start time.
     * @param apptStartTimeMinutes Spinner that holds Appointment minutes of start time.
     * @param apptEndTimeHours Spinner that holds Appointment hours of end time.
     * @param apptEndTimeMinutes Spinner that holds Appointment minutes of end time.
     * @param apptStartDate Date picker that holds Appointment start date.
     * @return boolean on whether an appointments input is valid.*/
    public static boolean verifyTimeAndDate(ZonedDateTime startDateTime, ZonedDateTime endDateTime, DatePicker apptStartDate, Spinner<Integer> apptStartTimeHours, Spinner<Integer> apptStartTimeMinutes, Spinner<Integer> apptEndTimeHours, Spinner<Integer> apptEndTimeMinutes){
        ZonedDateTime etStart = ZonedDateTime.ofInstant(startDateTime.toInstant(), ZoneId.of("America/New_York"));
        ZonedDateTime etEnd = ZonedDateTime.ofInstant(endDateTime.toInstant(),ZoneId.of("America/New_York"));
        LocalDate easterTimeStartDateScheduled = etStart.toLocalDate();
        LocalDate easterTimeEndDateScheduled = etEnd.toLocalDate();
        LocalTime easternTimeStartTime =  etStart.toLocalTime();
        LocalTime easternTimeEndTime = etEnd.toLocalTime();
        var valid = true;
        if(easternTimeStartTime.compareTo(Appointment.getOpenTime()) == -1 || easternTimeEndTime.compareTo(Appointment.getOpenTime()) == -1){
            valid = false;
        }
        if(easternTimeStartTime.compareTo(Appointment.getCloseTime()) == 1 || easternTimeEndTime.compareTo(Appointment.getCloseTime()) == 1){
            valid = false;
        }
        if(easternTimeStartTime.compareTo(easternTimeEndTime) == 1){
            valid = false;
        }
        if(easterTimeStartDateScheduled.getDayOfWeek().equals(DayOfWeek.SATURDAY) || easterTimeEndDateScheduled.getDayOfWeek().equals(DayOfWeek.SUNDAY)){
            Menu.setBordersRed(apptStartDate);
            valid = false;
        }
        if(!valid){
            Menu.setBordersRed(apptStartTimeHours);
            Menu.setBordersRed(apptStartTimeMinutes);
            Menu.setBordersRed(apptEndTimeHours);
            Menu.setBordersRed(apptEndTimeMinutes);
        }
        return valid;
    }

    //Lambda function in overlappingAddAppointment allows to filter appointments of the date chosen.

    /**Method to check for overlapping dates and times for Appointment.
     * Method ensures the date and times for an Appointment are valid
     * ,checking against other appointments of a specific customer.
     * @param customerID Customer ID for Appointment.
     * @param appointmentDate Date for Appointment.
     * @param apptStartTimeHours Spinner that holds Appointment hour of start time.
     * @param apptStartTimeMinutes Spinner that holds Appointment minutes of start time.
     * @param apptEndTimeHours Spinner that holds Appointment hours of end time.
     * @param apptEndTimeMinutes Spinner that holds Appointment minutes of end time.
     * @param endTime End Time for Appointment.
     * @param startTime Start Time for Appointment.
     * @return boolean on whether an appointments time and date are valid.*/
    public static boolean overlappingAddAppointment(int customerID,LocalDate appointmentDate,LocalTime startTime,LocalTime endTime,Spinner<Integer> apptStartTimeHours,Spinner<Integer> apptStartTimeMinutes,Spinner<Integer> apptEndTimeHours,Spinner<Integer> apptEndTimeMinutes){
        boolean overlap = false;
        var customer = Customer.getCustomer(customerID);
        var customerAppointments = customer.getCustomerAppointments();
        var sameDateAppointments = customerAppointments.filtered(appointment -> appointment.getStartDate().equals(appointmentDate));
        for(var appointment : sameDateAppointments){
            overlap = notValidAppointmentTimes(appointment,startTime,endTime);
            if(overlap)
                break;
        }
        if(overlap){
            Menu.setBordersRed(apptStartTimeHours);
            Menu.setBordersRed(apptStartTimeMinutes);
            Menu.setBordersRed(apptEndTimeHours);
            Menu.setBordersRed(apptEndTimeMinutes);
        }
        return overlap;
    }

    //Lambda function in overlappingModifyAppointment allows to filter appointments of the date chosen and if the same customer
    //is selected it ensures for the filtered appointments to ignore the current appointment.

    /**Method to check for overlapping dates and times for Appointment.
     * Method ensures the date and times for an Appointment are valid
     * ,checking against other appointments of a specific customer.
     * @param customerID Customer ID for Appointment.
     * @param appointmentDate Date for Appointment.
     * @param apptStartTimeHours Spinner that holds Appointment hour of start time.
     * @param apptStartTimeMinutes Spinner that holds Appointment minutes of start time.
     * @param apptEndTimeHours Spinner that holds Appointment hours of end time.
     * @param apptEndTimeMinutes Spinner that holds Appointment minutes of end time.
     * @param endTime End Time for Appointment.
     * @param startTime Start Time for Appointment.
     * @param appointmentToModify Appointment object that is being modified.
     * @return boolean on whether an appointments time and date are valid.*/
    public static boolean overlappingModifyAppointment(int customerID,LocalDate appointmentDate,LocalTime startTime,LocalTime endTime,Spinner<Integer> apptStartTimeHours,Spinner<Integer> apptStartTimeMinutes,Spinner<Integer> apptEndTimeHours,Spinner<Integer> apptEndTimeMinutes,Appointment appointmentToModify){
        boolean overlap = false;
        var customer = Customer.getCustomer(customerID);
        var customerAppointments = customer.getCustomerAppointments();
        var sameDateAppointments = customerAppointments.filtered(appointment -> appointment.getStartDate().equals(appointmentDate));
        if(customerID == appointmentToModify.getCustomerID()){
            sameDateAppointments = sameDateAppointments.filtered(appointment -> appointment.getApptID() != appointmentToModify.getApptID());
        }
        for(var appointment : sameDateAppointments){
            overlap = notValidAppointmentTimes(appointment,startTime,endTime);
            if(overlap)
                break;
        }
        if(overlap){
            Menu.setBordersRed(apptStartTimeHours);
            Menu.setBordersRed(apptStartTimeMinutes);
            Menu.setBordersRed(apptEndTimeHours);
            Menu.setBordersRed(apptEndTimeMinutes);
        }
        return overlap;
    }

    //Lambda function in overlapMessageModify allows to filter appointments of the date chosen and if the same customer
    //is selected it ensures for the filtered appointments to ignore the current appointment.

    /**Method that sends out message of Appointments that are being overlapped.
     * @param appointmentToModify Appointment object being modified.
     * @param startTime Start time of new Appointment.
     * @param endTime End time of new Appointment.
     * @param appointmentDate Date of new Appointment.
     * @param customerID Customer ID of new Appointment.
     * @return String message of appointments that overlap.*/
    public static String overlapMessageModify(int customerID,LocalDate appointmentDate,LocalTime startTime,LocalTime endTime,Appointment appointmentToModify){
        StringBuffer message = new StringBuffer();
        var customer = Customer.getCustomer(customerID);
        var customerAppointments = customer.getCustomerAppointments();
        var sameDateAppointments = customerAppointments.filtered(appointment -> appointment.getStartDate().equals(appointmentDate));
        if(customerID == appointmentToModify.getCustomerID()){
            sameDateAppointments = sameDateAppointments.filtered(appointment -> appointment.getApptID() != appointmentToModify.getApptID());
        }
        for(var appointment : sameDateAppointments){
            if(notValidAppointmentTimes(appointment,startTime,endTime)){
                message.append("Appointment ID: " + appointment.getApptID() + " Date: " + appointment.getStartDate() + " Time: " + appointment.getStartTime() + " To " + appointment.getEndTime() + " ");
            }
        }
        return message.toString();
    }

    //Lambda function in overlapMessageAdd allows to filter appointments of the date chosen.

    /**Method that sends out message of Appointments that are being overlapped.
     * @param startTime Start time of Appointment being added.
     * @param endTime End time of Appointment being added.
     * @param appointmentDate Date of Appointment being added.
     * @param customerID Customer ID of Appointment being added.
     * @return String message of appointments that overlap.*/
    public static String overlapMessageAdd(int customerID,LocalDate appointmentDate,LocalTime startTime,LocalTime endTime){
        StringBuffer message = new StringBuffer();
        var customer = Customer.getCustomer(customerID);
        var customerAppointments = customer.getCustomerAppointments();
        var sameDateAppointments = customerAppointments.filtered(appointment -> appointment.getStartDate().equals(appointmentDate));
        for(var appointment : sameDateAppointments){
            if(notValidAppointmentTimes(appointment,startTime,endTime));
            message.append("Appointment ID: " + appointment.getApptID() + " Date: " + appointment.getStartDate() + " Time: " + appointment.getStartTime() + " To " + appointment.getEndTime() + " ");
        }
        return message.toString();
    }

    /**Method to determine whether an Appointment time is valid or not.
     * @param appt Appointment being checked.
     * @param startTime Company start time.
     * @param endTime Company ent time
     * @return boolean on whether the Appointment is within business hours.*/
    public static boolean notValidAppointmentTimes(Appointment appt, LocalTime startTime,LocalTime endTime){
        return  startTime.compareTo(appt.getStartTime()) == 1 && startTime.compareTo(appt.getEndTime()) == -1
                || endTime.compareTo(appt.getStartTime()) == 1 && endTime.compareTo(appt.getEndTime()) == -1
                || startTime.compareTo(appt.getStartTime()) <= 0 && endTime.compareTo(appt.getEndTime()) >= 0;
    }

    /**Method to set select month choice box with an array of months.
     * @param selectMonth choice box to set choices. for.*/
    public static void setMonthsFilter(ChoiceBox<String> selectMonth){
        selectMonth.getItems().setAll(Menu.monthFilter);
    }

    /**Method to get the Month object value from a string month value.
     * @param month String month value.
     * @return Month object value.*/
    public static Month getMonth(String month){
        switch (month){
            case "January":
                return Month.JANUARY;
            case "February":
                return Month.FEBRUARY;
            case "March":
                return Month.MARCH;
            case "April":
                return Month.APRIL;
            case "May":
                return Month.MAY;
            case "June":
                return Month.JUNE;
            case "July":
                return Month.JULY;
            case "August":
                return Month.AUGUST;
            case "September":
                return Month.SEPTEMBER;
            case "October":
                return Month.OCTOBER;
            case "November":
                return Month.NOVEMBER;
            case "December":
                return Month.DECEMBER;
        }
        return null;
    }

    /**Method to get message for user regarding appointments within 15 minutes.
     * Method will send an alert with the information of an appointment or appointments
     * that overlap. If none overlap a message stating so will pop up.*/
    public static void appointmentWithinFifteenMinutes(){
        StringBuffer message = new StringBuffer();
        Timestamp ts = Timestamp.from(Instant.now());
        LocalDate today = ts.toLocalDateTime().toLocalDate();
        LocalTime now = ts.toLocalDateTime().toLocalTime();
        LocalTime fifteenMinutes = now.plusMinutes(15);
        var currentUser = User.getUser(applicationHelper.currentAccountUser);
        for(var userAppointment : currentUser.getUserAppointments()){
            if(appointmentWithinFifteenMinutes(today,now,fifteenMinutes,userAppointment))
                message.append("Appointment ID: " + userAppointment.getApptID() + " Date: " + userAppointment.getStartDate() + " Time: " + userAppointment.getStartTime() + " To " + userAppointment.getEndTime() + " ");
        }

        Alert alert = new Alert(Alert.AlertType.WARNING);
        if(message.isEmpty()){
            fxmlHelper.showAlert(alert,"Notice","User " + currentUser.getUserName() + " has no appointments within 15 minutes.");
        }else{
            fxmlHelper.showAlert(alert,"Notice","User " + currentUser.getUserName() + " has appointments within 15 minutes: \n" + message);
        }
    }

    /**Method to determine whether an Appointment is within 15 minutes.
     * @param date Current Date.
     * @param appointment Appointment Object.
     * @param now Time of when user logs in.
     * @param fifteenMinutes Time of 15 minutes after user logs in.
     * @return boolean on whether an Appointment is within fifteen minutes.*/
    private static boolean appointmentWithinFifteenMinutes(LocalDate date, LocalTime now, LocalTime fifteenMinutes, Appointment appointment){
        return appointment.getStartDate().equals(date) && (now.compareTo(appointment.getStartTime()) <= 0 && appointment.getStartTime().compareTo(fifteenMinutes) <= 0);
    }
}
