package com.example.c195.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;

/**Class for Customer objects..*/
public class Customer {
    private int customerID;
    private String customerName;
    private String customerAddress;
    private String customerZip;
    private String customerNumber;
    private Timestamp customerDateCreated;
    private String customerCreatedBy;
    private Timestamp customerLastUpdated;
    private String customerUpdatedBy;
    private String customerDivision;

    /**ObservableList for the Customer objects appointments.*/
    private ObservableList<Appointment> customerAppointments = FXCollections.observableArrayList();

    /**Static ObservableList for all customers in the company.*/
    private static ObservableList<Customer> customers = FXCollections.observableArrayList();

    /**Constructor for the Customer class.
     * @param customerID ID for the Customer.
     * @param customerName Name for the Customer.
     * @param customerAddress Address for the Customer.
     * @param customerZip Zip code for the customer.
     * @param customerNumber Phone number for the Customer.
     * @param customerDateCreated Date and time customer was created.
     * @param customerCreatedBy User that created the Customer.
     * @param customerLastUpdated Date and time Customer was updated.
     * @param customerUpdatedBy User that updated Customer.
     * @param customerDivision  Division for the Customer.*/
    public Customer(int customerID,String customerName, String customerAddress, String customerZip, String customerNumber, Timestamp customerDateCreated, String customerCreatedBy, Timestamp customerLastUpdated, String customerUpdatedBy, String customerDivision) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerZip = customerZip;
        this.customerNumber = customerNumber;
        this.customerDateCreated = customerDateCreated;
        this.customerCreatedBy = customerCreatedBy;
        this.customerLastUpdated = customerLastUpdated;
        this.customerUpdatedBy = customerUpdatedBy;
        this.customerDivision = customerDivision;
    }

    /**Method adds Customer into ObservableList with all customers.
     * @param customer Customer to be added into the customers ObservableList.*/
    public static void addCustomer(Customer customer){
        customers.add(customer);
    }

    /**Method removes Customer from ObservableList with all customers.
     * @param customer Customer to be removed from the customers ObservableList.*/
    public static void removeCustomer(Customer customer) { customers.remove(customer);}

    /**Method gets ObservableList with all customers.
     * @return  ObservableList of all customers.*/
    public static ObservableList<Customer> getCustomers(){
        return customers;
    }

    /**Getter for the ID of the Customer object.
     * @return Integer ID of the Customer object.*/
    public int getCustomerID() {
        return customerID;
    }

    /**Getter for the name of the Customer object.
     * @return String name of the Customer object.*/
    public String getCustomerName() {
        return customerName;
    }

    /**Getter for the address of the Customer object.
     * @return String address of the Customer object.*/
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**Getter for the zip code of the Customer object.
     * @return String zip code of the Customer object.*/
    public String getCustomerZip() {
        return customerZip;
    }

    /**Getter for the phone number of the Customer object.
     * @return String phone number of the Customer object.*/
    public String getCustomerNumber() {
        return customerNumber;
    }

    /**Getter for the date and time of creation of the Customer object.
     * @return Timestamp date and time of creation of the Customer object.*/
    public Timestamp getCustomerDateCreated() {
        return customerDateCreated;
    }

    /**Getter for the user that created the Customer object.
     * @return String user that created the Customer object.*/
    public String getCustomerCreatedBy() {
        return customerCreatedBy;
    }

    /**Getter for the date and time of last update of the Customer object.
     * @return Timestamp date and time of last update of the Customer object.*/
    public Timestamp getCustomerLastUpdated() {
        return customerLastUpdated;
    }

    /**Getter for the user that last updated the Customer object.
     * @return String user that last updated the Customer object.*/
    public String getCustomerUpdatedBy() {
        return customerUpdatedBy;
    }

    /**Getter for the division of the Customer object.
     * @return String division of the Customer object.*/
    public String getCustomerDivision() {
        return customerDivision;
    }

    /**Getter for the index in customers ObservableList for a Customer Object.
     * @param customer Customer Object to get ID for.
     * @return Integer index of the Customer object in customers ObservableList.*/
    public static int getIndex(Customer customer){
        return customers.indexOf(customer);
    }

    /**Getter for a Customer object in the customers ObservableList.
     * Lambda function in getCustomer filters the customers ObservableList leaving the correct Customer Object.
     * @param customer Customer to get from customers ObservableList.
     * @return Customer from the customers ObservableList.*/
    public static Customer getCustomer(Customer customer){
        var filtered = customers.filtered(cust -> cust.getCustomerID() == customer.getCustomerID());
        var customerSelected = filtered.get(0);
        return customerSelected;
    }

    /**Getter for a Customer object in the customers ObservableList.
     * Lambda function in getCustomer filters the customers ObservableList leaving the correct Customer Object.
     * @param id Customer ID to get Customer from customers ObservableList.
     * @return Customer from the customers ObservableList.*/
    public static Customer getCustomer(int id){
        var filtered = customers.filtered(customer -> customer.getCustomerID() == id);
        var customerSelected = filtered.get(0);
        return customerSelected;
    }

    /**Getter for customers ObservableList.
     * @return ObservableList of all customers.*/
    public ObservableList<Appointment> getCustomerAppointments(){
        return customerAppointments;
    }

    /**Adds appointment for Customer object.
     * @param appointment Appointment object to be added for Customer objects appointment ObservableList.*/
    public void addAppointmentForCustomer(Appointment appointment){
        customerAppointments.add(appointment);
    }

    /**Removes appointment from Customer object.
     * @param appointment Appointment object to be removed from Customer objects appointment ObservableList.*/
    public void removeAppointmentForCustomer(Appointment appointment){
        customerAppointments.remove(appointment);
    }

    /**Getter for appointment index of Customer object appointments.
     * @param apptID ID of Appointment object to be found in Customer objects appointments ObservableList.
     * @return Integer index of Appointment.*/
    public int getAppointmentIndex(int apptID){
        for(var appointment : customerAppointments){
            if(apptID == appointment.getApptID())
                return customerAppointments.indexOf(appointment);
        }
        return -1;
    }
}
