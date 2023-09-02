package com.example.c195.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**Class for Contact objects.*/
public class Contact {
    private int contactID;
    private String name;
    private String email;

    /**Constructor for the Contact class.
     * @param contactID ID for the Contact.
     * @param name Name for the Contact.
     * @param email Email for the Contact.*/
    public Contact(int contactID, String name, String email) {
        this.contactID = contactID;
        this.name = name;
        this.email = email;
    }

    /**Static ObservableList for all Contacts in the company.*/
    private static ObservableList<Contact> contactsList = FXCollections.observableArrayList();

    /**Observable list for all appointments for the Contact object.*/
    private ObservableList<Appointment> appointmentSchedule = FXCollections.observableArrayList();

    /**Method gets Observable list with all Contacts.
     * @return ObservableList of Contacts with all Contacts.*/
    public static ObservableList<Contact> getContactsList(){
        return contactsList;
    }

    /**Getter for the ID of the Contact object.
     * @return Integer ID of the Contact.*/
    public int getContactID() {
        return contactID;
    }

    /**Getter for the name of the Contact object.
     * @return String name of the Contact.*/
    public String getName() {
        return name;
    }

    /**Getter for the email of the Contact object.
     * @return String email of the Contact.*/
    public String getEmail() {
        return email;
    }

    /**Getter for appointment schedule of the Contact object.
     * @return ObservableList of Appointments for the Contact object.*/
    public ObservableList<Appointment> getAppointmentSchedule(){
        return appointmentSchedule;
    }

    /**Method adds Appointment into the Contact objects ObservableList.
     * @param appointment Appointment to be added into the Contact Objects appointment schedule.*/
    public void addAppointmentForContact(Appointment appointment){
        appointmentSchedule.add(appointment);
    }

    /**Method removes Appointment from the Contact objects ObservableList.
     * @param appointment Appointment to be removed from the Contact Objects appointment schedule.*/
    public void removeAppointmentForContact(Appointment appointment){
        appointmentSchedule.remove(appointment);
    }

    /**Method gets index of an Appointment in the appointment schedule for the Contact Object.
     * @param apptID ID of Appointment to get the index for.
     * @return Integer index of the Appointment object.*/
    public int getAppointmentIndex(int apptID){
        for(var appointment : appointmentSchedule){
            if(appointment.getApptID() == apptID){
                return appointmentSchedule.indexOf(appointment);
            }
        }
        return -1;
    }

    /**Method gets Contact object from all contacts.
     * @param ID ID for the contact to be returned.
     * @return Contact object.*/
    public static Contact getContact(int ID){
        for(var contact : contactsList){
            if(ID == contact.getContactID())
                return contact;
        }
        return null;
    }
}
