package com.example.c195.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

/**Class for Appointment objects*/
public class Appointment {
    private int apptID;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private Timestamp dateCreated;
    private String createdBy;
    private Timestamp lastUpdated;
    private String updatedBy;
    private int customerID;
    private int userID;
    private int contactID;

    /**Static variable for the opening time of the company.*/
    private final static LocalTime openTime = LocalTime.of(8,0,0);

    /**Static variable for the closing time of the company.*/
    private final static LocalTime closeTime = LocalTime.of(22,0,0);

    /**Static variable that holds all appointments.*/
    private static ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    /**Constructor for the Appointment class.
     * @param apptID ID of Appointment
     * @param title Title for the Appointment
     * @param description Description for the Appointment
     * @param location Location for the Appointment
     * @param type Type for the Appointment.
     * @param startDate Start Date for the Appointment.
     * @param endDate End date for the Appointment.
     * @param startTime Start time for the Appointment.
     * @param endTime End time for the Appointment.
     * @param dateCreated Created date and time for the Appointment.
     * @param createdBy User that created Appointment.
     * @param lastUpdated Updated date and time for the Appointment.
     * @param updatedBy User that updated the Appointment.
     * @param customerID Customer ID for the appointment.
     * @param userID User ID for the Appointment.
     * @param contactID Contact ID for the Appointment.*/
    public Appointment(int apptID, String title, String description, String location, String type, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime, Timestamp dateCreated, String createdBy, Timestamp lastUpdated, String updatedBy, int customerID,int userID,int contactID) {
        this.apptID = apptID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.dateCreated = dateCreated;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.updatedBy = updatedBy;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**Getter for customer ID.
     * @return Integer ID of the customer for the Appointment object.*/
    public int getCustomerID() {
        return customerID;
    }

    /**Getter for user ID.
     * @return Integer ID of the user for the Appointment object.*/
    public int getUserID() {
        return userID;
    }

    /**Getter for contact ID.
     * @return Integer ID of the contact for the Appointment object.*/
    public int getContactID() {
        return contactID;
    }

    /**Getter for appointment ID.
     * @return Integer ID of the Appointment object.*/
    public int getApptID() {
        return apptID;
    }

    /**Getter for title of the Appointment.
     * @return String title for the Appointment object.*/
    public String getTitle() {
        return title;
    }

    /**Getter for description of the Appointment.
     * @return String description for the Appointment object.*/
    public String getDescription() {
        return description;
    }

    /**Getter for location of the Appointment.
     * @return String location for the Appointment object.*/
    public String getLocation() {
        return location;
    }

    /**Getter for type of the Appointment.
     * @return String type for the Appointment object.*/
    public String getType() {
        return type;
    }

    /**Getter for start date of the Appointment.
     * @return LocalDate starting date for the Appointment object.*/
    public LocalDate getStartDate() {
        return startDate;
    }

    /**Getter for end date of the Appointment.
     * @return LocalDate ending date for the Appointment object.*/
    public LocalDate getEndDate() {
        return endDate;
    }

    /**Getter for start time of the Appointment.
     * @return LocalTime starting time for the Appointment object.*/
    public LocalTime getStartTime() {
        return startTime;
    }

    /**Getter for end time of the Appointment.
     * @return LocalTime end time for the Appointment object.*/
    public LocalTime getEndTime() {
        return endTime;
    }

    /**Getter for created date and time of the Appointment.
     * @return Timestamp created date and time for the Appointment object.*/
    public Timestamp getDateCreated() {
        return dateCreated;
    }

    /**Getter for the user that created the Appointment.
     * @return String user that created the Appointment object.*/
    public String getCreatedBy() {
        return createdBy;
    }

    /**Getter for last updated date and time of the Appointment.
     * @return Timestamp updated date and time for the Appointment object.*/
    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    /**Getter for the user that updated the Appointment.
     * @return String user that updated the Appointment object.*/
    public String getUpdatedBy() {
        return updatedBy;
    }

    /**Getter for opening time of the company.
     * @return LocalTime opening time of the company.*/
    public static LocalTime getOpenTime(){
        return openTime;
    }

    /**Getter for closing time of the company.
     * @return LocalTime closing time of the company.*/
    public static LocalTime getCloseTime(){
        return closeTime;
    }

    /**Getter for all appointments of the company.
     * @return ObservableList of Appointments for all company Appointments.*/
    public static ObservableList<Appointment> getAppointments(){
        return appointments;
    }

    /**Adds Appointment to company Appointments
     * @param appointment Appointment object to be added.*/
    public static void addAppointment(Appointment appointment){
        appointments.add(appointment);
    }

    /**Removes Appointment from company Appointments
     * @param appointment Appointment object to be removed.*/
    public static void removeAppointment(Appointment appointment){
        appointments.remove(appointment);
    }

    /**Getter for the index of an Appointment object.
     * @param appointment Appointment to find the index of.
     * @return Integer index of the appointment object.*/
    public static int getAppointmentIndex(Appointment appointment){
        return appointments.indexOf(appointment);
    }
}
