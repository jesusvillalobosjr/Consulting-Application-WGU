package com.example.c195.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;

/**Class for User objects.*/
public class User {
    private int ID;
    private String userName;
    private String password;
    private Timestamp createdDate;
    private String createdBy;
    private Timestamp lastUpdated;
    private String updatedBy;

    /**Constructor for the User class
     * @param ID for User.
     * @param userName User name for User.
     * @param password Password for User.
     * @param createdDate Created date for User.
     * @param createdBy Creator of User.
     * @param lastUpdated Last update for User.
     * @param updatedBy Last person who updated User.*/
    public User(int ID, String userName, String password, Timestamp createdDate, String createdBy, Timestamp lastUpdated, String updatedBy) {
        this.ID = ID;
        this.userName = userName;
        this.password = password;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.lastUpdated = lastUpdated;
        this.updatedBy = updatedBy;
    }

    /**Static ObservableList of all users in the company*/
    private static ObservableList<User> users = FXCollections.observableArrayList();

    /**Observable for all appointments for a User object.*/
    private ObservableList<Appointment> appointments = FXCollections.observableArrayList();

    /**Getter for all User object appointments ObservableList
     * @return ObservableList of all User object appointments.*/
    public ObservableList<Appointment> getUserAppointments(){
        return appointments;
    }

    /**Adds Appointment for User object appointments.
     * @param appointment Appointment to be added.*/
    public void addAppointmentForUser(Appointment appointment){
        appointments.add(appointment);
    }

    /**Removes Appointment from User object appointments.
     * @param appointment Appointment to be removed.*/
    public void removeAppointment(Appointment appointment){
        appointments.remove(appointment);
    }

    /**Gets index of Appointment from Appointment ID.
     * @param apptID Appointment ID to find Appointment.
     * @return Integer index of Appointment.*/
    public int getAppointmentIndex(int apptID){
        for(var appointment : appointments){
            if(apptID == appointment.getApptID())
                return appointments.indexOf(appointment);
        }
        return -1;
    }

    /**Getter for ObservableList of all users in company*/
    public static ObservableList<User> getUsers(){
        return users;
    }

    /**Method adds user to ObservableList of all users.
     * @param user User to be added.*/
    public static void addUser(User user){
        users.add(user);
    }

    /**Getter for ID of User object.
     * @return Integer ID of User object.*/
    public int getID() {
        return ID;
    }

    /**Getter for username of User object.
     * @return String username of User object.*/
    public String getUserName() {
        return userName;
    }

    /**Getter for password of User object.
     * @return String password of User object.*/
    public String getPassword() {
        return password;
    }

    /**Getter for date and time created of User object.
     * @return LocalDate date and time created of User object.*/
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    /**Getter for user that created User object.
     * @return String created by of User object.*/
    public String getCreatedBy() {
        return createdBy;
    }

    /**Getter for date and time of last update of User object.
     * @return LocalDate date and time last updated of User object.*/
    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    /**Getter for user that last updated User object.
     * @return String updated by of User object.*/
    public String getUpdatedBy() {
        return updatedBy;
    }


    /**Setter for User ID.
     * @param ID ID for User.*/
    public void setID(int ID) {
        this.ID = ID;
    }

    /**Setter for User username.
     * @param userName Username for User.*/
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**Setter for User password.
     * @param password Password for User.*/
    public void setPassword(String password) {
        this.password = password;
    }

    /**Setter for User date created.
     * @param createdDate Date crated for User.*/
    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    /**Setter for User created by.
     * @param createdBy Created by for User.*/
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**Setter for User last updated.
     * @param lastUpdated Last updated for User.*/
    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**Setter for User updated by.
     * @param updatedBy Updated by for User.*/
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /** Static getter for User object from all users.
     * @param ID ID for User to find.
     * @return User found with ID.*/
    public static User getUser(int ID){
        for(var user : users){
            if(user.getID() == ID)
                return user;
        }
        return null;
    }

    /** Static getter for User object from all users.
     * @param userName Username for User to find.
     * @return User found with username.*/
    public static User getUser(String userName){
        for (var user : users){
            if(user.getUserName().equals(userName))
                return user;
        }
        return null;
    }
}
