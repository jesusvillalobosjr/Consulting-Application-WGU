package com.example.c195.Model;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

/**Helper class dealing with database queries.*/
public abstract class applicationHelper {
    public static String currentAccountUser = null;

    /**Method to verify user login.
     * Method determines whether a user has been granted access or not.
     * @param username username of attempted login.
     * @param password password of attempted login.
     * @return Boolean on whether the user login was correct or not.*/
    public static boolean accountVerification(String username,String password) throws SQLException {
        String sql = "SELECT User_Name,Password FROM client_schedule.users WHERE User_Name = ?;";
        PreparedStatement prepState = JDBC.connection.prepareStatement(sql);
        prepState.setString(1,username);
        ResultSet resSet = prepState.executeQuery();
        while (resSet.next()){
            var accountPassword = resSet.getString("Password");

            if(accountPassword.equals(password))
                return true;
        }
        return false;
    }

    /**Method populates all users with User objects instantiated from the database table users.*/
    public static void populateUsers() throws SQLException {
        String sql = "SELECT * FROM client_schedule.users;";
        PreparedStatement prepState = JDBC.connection.prepareStatement(sql);
        ResultSet resSet = prepState.executeQuery();
        while (resSet.next()){
            int ID = resSet.getInt("User_ID");
            String username = resSet.getString("User_Name");
            String password = resSet.getString("Password");
            Timestamp created = resSet.getTimestamp("Create_Date");
            String createdBy = resSet.getString("Created_By");
            Timestamp lastUpdated = resSet.getTimestamp("Last_Update");
            String lastUpdatedBy = resSet.getString("Last_Updated_By");

            User.addUser(new User(ID,username,password,created,createdBy,lastUpdated,lastUpdatedBy));
        }
    }

    /**Method populates all customers with Customer objects instantiated from the database table customers.*/
    public static void populateCustomers() throws SQLException {
        String sql = "SELECT * FROM client_schedule.customers;";
        PreparedStatement prepState = JDBC.connection.prepareStatement(sql);
        ResultSet resSet = prepState.executeQuery();
        while (resSet.next()){
            int ID = resSet.getInt("Customer_ID");
            String name = resSet.getString("Customer_Name");
            String address = resSet.getString("Address");
            String postalCode = resSet.getString("Postal_Code");
            String phoneNumber = resSet.getString("Phone");
            Timestamp dateCreated = resSet.getTimestamp("Create_Date");
            String createdBy = resSet.getString("Created_By");
            Timestamp lastUpdate = resSet.getTimestamp("Last_Update");
            String lastUpdatedBy = resSet.getString("Last_Updated_By");
            String division = getDivision(resSet.getInt("Division_ID"));

            var customer = new Customer(ID,name,address,postalCode,phoneNumber,dateCreated,createdBy,lastUpdate,lastUpdatedBy,division);

            Customer.addCustomer(customer);
        }
    }

    /**Method that shows customers in a table view.
     * @param table Customer table view.
     * @param customerID Customer ID table column.
     * @param customerName Customer name table column.
     * @param customerNumber Customer phone number table column.
     * @param customerAddress Customer address table column.
     * @param customerZip Customer Zip code table column.
     * @param createdDate Customer created date table column.
     * @param createdByCol Customer created by table column.
     * @param updatedDate Customer last updated date table column.
     * @param updatedByCol Customer last updated by table column.
     * @param division Customer division table column.*/
    public static void showCustomersInTableView(TableView<Customer> table,TableColumn<Customer,Integer> customerID,TableColumn<Customer,String> customerName, TableColumn<Customer,String> customerNumber,TableColumn<Customer,String> customerAddress, TableColumn<Customer,String> customerZip,TableColumn<Customer,Timestamp> createdDate, TableColumn<Customer,String> createdByCol,TableColumn<Customer,Timestamp> updatedDate,TableColumn<Customer,String> updatedByCol, TableColumn<Customer,String> division){
        table.setItems(Customer.getCustomers());
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddress.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customerNumber.setCellValueFactory(new PropertyValueFactory<>("customerNumber"));
        customerZip.setCellValueFactory(new PropertyValueFactory<>("customerZip"));
        createdDate.setCellValueFactory(new PropertyValueFactory<>("customerDateCreated"));
        createdByCol.setCellValueFactory(new PropertyValueFactory<>("customerCreatedBy"));
        updatedDate.setCellValueFactory(new PropertyValueFactory<>("customerLastUpdated"));
        updatedByCol.setCellValueFactory(new PropertyValueFactory<>("customerUpdatedBy"));
        division.setCellValueFactory(new PropertyValueFactory<>("customerDivision"));
    }

    /**Method to add a customer into the database.
     * @param customerName Name for database field.
     * @param customerAddress Address for database field.
     * @param customerZip Zip code for database field.
     * @param customerPhone Phone number for database field.
     * @param createdDateAndTime Create date and time for database field.
     * @param createdBy Created by string for database field.
     * @param dateAndTimeUpdated Last updated date and time for database field.
     * @param updatedBy Updated by string for database field.
     * @param customerDivision Customer division for database field.*/
    public static void addCustomer(String customerName,String customerAddress,String customerZip,String customerPhone,Timestamp createdDateAndTime,String createdBy,Timestamp dateAndTimeUpdated,String updatedBy,String customerDivision) throws SQLException {
        String sql = "INSERT INTO client_schedule.customers (Customer_Name,Address,Postal_Code,Phone,Create_Date,Created_By,Last_Update,Last_Updated_By,Division_ID) VALUES (?,?,?,?,?,?,?,?,?);";
        PreparedStatement prepState = JDBC.connection.prepareStatement(sql);
        prepState.setString(1,customerName);
        prepState.setString(2,customerAddress);
        prepState.setString(3,customerZip);
        prepState.setString(4,customerPhone);
        prepState.setObject(5,createdDateAndTime);
        prepState.setString(6,createdBy);
        prepState.setObject(7,dateAndTimeUpdated);
        prepState.setString(8,updatedBy);
        prepState.setInt(9,getDivisionID(customerDivision));
        prepState.executeUpdate();
    }

    /**Method to modify a customer into the database.
     * @param customerID Customer ID for database field.
     * @param customerName Name for database field.
     * @param customerAddress Address for database field.
     * @param customerZip Zip code for database field.
     * @param customerPhone Phone number for database field.
     * @param lastUpdate Last updated date and time for database field.
     * @param updatedBy Updated by string for database field.
     * @param divisionID Customer division ID for database field.*/
    public static void updateCustomer(int customerID,String customerName,String customerAddress,String customerZip,String customerPhone,Timestamp lastUpdate, String updatedBy,int divisionID) throws SQLException {
        String sql = "UPDATE client_schedule.customers SET Customer_Name = ?,Address = ?,Postal_Code = ?,Phone = ?,Last_Update = ?,Last_Updated_By = ?,Division_ID = ? WHERE Customer_ID = ?;";
        PreparedStatement prepState = JDBC.connection.prepareStatement(sql);
        prepState.setString(1,customerName);
        prepState.setString(2,customerAddress);
        prepState.setString(3,customerZip);
        prepState.setString(4,customerPhone);
        prepState.setObject(5,lastUpdate);
        prepState.setString(6,updatedBy);
        prepState.setInt(7,divisionID);
        prepState.setInt(8,customerID);
        prepState.executeUpdate();
    }

    /**Method to get Customer from database.
     * @param customer Customer object to match ID for..
     * @return Customer object matched with the argument Customer based on ID.*/
    public static Customer getCustomer(Customer customer) throws SQLException {
        String sql = "SELECT * FROM client_schedule.customers WHERE Customer_ID = ?;";
        PreparedStatement prepState = JDBC.connection.prepareStatement(sql);
        prepState.setInt(1,customer.getCustomerID());
        ResultSet resSet = prepState.executeQuery();
        while (resSet.next()){
            int ID = resSet.getInt("Customer_ID");
            String name = resSet.getString("Customer_Name");
            String Address = resSet.getString("Address");
            String zip = resSet.getString("Postal_Code");
            String phone = resSet.getString("Phone");
            Timestamp createdAt = resSet.getTimestamp("Create_Date");
            String createdBy = resSet.getString("Created_By");
            Timestamp lastUpdated = resSet.getTimestamp("Last_Update");
            String lastUpdatedBy = resSet.getString("Last_Updated_By");
            String divisionID = getDivision(resSet.getInt("Division_ID"));

            return new Customer(ID,name,Address,zip,phone,createdAt,createdBy,lastUpdated,lastUpdatedBy,divisionID);
        }
        return null;
    }

    /**Method to get the newest customer in database.*/
    public static Customer getCustomer() throws SQLException {
        String sql = "SELECT * FROM client_schedule.customers WHERE Customer_ID = (SELECT MAX(Customer_ID) FROM client_schedule.customers);";
        PreparedStatement prepState = JDBC.connection.prepareStatement(sql);
        ResultSet resSet = prepState.executeQuery();
        while (resSet.next()){
            int ID = resSet.getInt("Customer_ID");
            String name = resSet.getString("Customer_Name");
            String Address = resSet.getString("Address");
            String zip = resSet.getString("Postal_Code");
            String phone = resSet.getString("Phone");
            Timestamp createdAt = resSet.getTimestamp("Create_Date");
            String createdBy = resSet.getString("Created_By");
            Timestamp lastUpdated = resSet.getTimestamp("Last_Update");
            String lastUpdatedBy = resSet.getString("Last_Updated_By");
            String divisionID = getDivision(resSet.getInt("Division_ID"));

            return new Customer(ID,name,Address,zip,phone,createdAt,createdBy,lastUpdated,lastUpdatedBy,divisionID);
        }
        return null;
    }

    /**Method to remove customer from database.
     * @param customer Customer Object to ger ID from.*/
    public static void removeCustomer(Customer customer) throws SQLException {
        String sql = "DELETE FROM client_schedule.customers WHERE Customer_ID = ?;";
        PreparedStatement prepState = JDBC.connection.prepareStatement(sql);
        prepState.setInt(1,customer.getCustomerID());
        prepState.executeUpdate();
    }

    /**Method to get division ID from the division String.
     * @param division Division string.
     * @return Integer division ID.*/
    public static int getDivisionID(String division) throws SQLException {
        String sql = "SELECT * FROM client_schedule.first_level_divisions;";
        PreparedStatement prepState = JDBC.connection.prepareStatement(sql);
        ResultSet resSet = prepState.executeQuery();
        while (resSet.next()){
            if(division.equals(resSet.getString("Division")))
                return resSet.getInt("Division_ID");
        }
        return 0;
    }

    /**Method to get division string from division ID.
     * @param divisionID ID of division.
     * @return String division from division ID.*/
    public static String getDivision(int divisionID) throws SQLException{
        String sql = "SELECT * FROM client_schedule.first_level_divisions WHERE Division_ID = ?;";
        PreparedStatement prepState = JDBC.connection.prepareStatement(sql);
        prepState.setInt(1,divisionID);
        ResultSet resSet = prepState.executeQuery();
        while (resSet.next()){
            if(divisionID == resSet.getInt("Division_ID"))
                return resSet.getString("Division");
        }
        return null;
    }

    /**Method to get country ID from Customer object.
     * Method gets country ID by using a Customer objects division ID.
     * @param customer Customer object.
     * @return Integer country ID.*/
    public static int getCountryID(Customer customer) throws SQLException {
        String sql = "SELECT * FROM client_schedule.first_level_divisions WHERE Division_ID = ?;";
        PreparedStatement prepState = JDBC.connection.prepareStatement(sql);
        var customerDivID = getDivisionID(customer.getCustomerDivision());
        prepState.setInt(1,customerDivID);
        ResultSet resSet = prepState.executeQuery();
        while ((resSet.next())){
            if(resSet.getInt("Division_ID") == customerDivID)
                return resSet.getInt("Country_ID");
        }
        return 0;
    }

    /**Method to get country string from country ID.
     * Method gets country string by using the country ID.
     * @param countryID ID of country..
     * @return String country.*/
    public static String getCountry(int countryID) throws SQLException {
        String sql = "SELECT * FROM client_schedule.countries WHERE Country_ID = ?;";
        PreparedStatement prepState = JDBC.connection.prepareStatement(sql);
        prepState.setInt(1,countryID);
        ResultSet resSet = prepState.executeQuery();
        while (resSet.next()){
            if (resSet.getInt("Country_ID") == countryID)
                return resSet.getString("Country");
        }
        return null;
    }

    /**Method populates all contacts with Contact objects instantiated from the database table contacts.*/
    public static void populateContacts() throws SQLException {
        String sql = "SELECT * FROM client_schedule.contacts;";
        PreparedStatement prepState = JDBC.connection.prepareStatement(sql);
        ResultSet resSet = prepState.executeQuery();
        while (resSet.next()){
            int id = resSet.getInt("Contact_ID");
            String name = resSet.getString("Contact_Name");
            String email = resSet.getString("Email");

            Contact.getContactsList().add(new Contact(id,name,email));
        }
    }

    /**Method populates all appointments with Appointment objects instantiated from the database table appointments.*/
    public static void populateAppointments() throws SQLException {
        String sql = "SELECT * FROM client_schedule.appointments;";
        PreparedStatement prepState = JDBC.connection.prepareStatement(sql);
        ResultSet resSet = prepState.executeQuery();
        while (resSet.next()){
            int ID = resSet.getInt("Appointment_ID");
            String title = resSet.getString("Title");
            String description = resSet.getString("Description");
            String location = resSet.getString("Location");
            String type = resSet.getString("Type");
            Timestamp startTimeStamp = resSet.getTimestamp("Start");
            Timestamp endTimeStamp = resSet.getTimestamp("End");
            Timestamp dateCreated = resSet.getTimestamp("Create_Date");
            String createdBy = resSet.getString("Created_By");
            Timestamp lastUpdate = resSet.getTimestamp("Last_Update");
            String lastUpdatedBy = resSet.getString("Last_Updated_By");
            int customerID = resSet.getInt("Customer_ID");
            int userID = resSet.getInt("User_ID");
            int contactID = resSet.getInt("Contact_ID");
            LocalDate startDate = startTimeStamp.toLocalDateTime().toLocalDate();
            LocalDate endDate = endTimeStamp.toLocalDateTime().toLocalDate();
            LocalTime startTime = startTimeStamp.toLocalDateTime().toLocalTime();
            LocalTime endTime = endTimeStamp.toLocalDateTime().toLocalTime();

            Appointment appt = new Appointment(ID,title,description,location,type,startDate,endDate,startTime,endTime,dateCreated,createdBy,lastUpdate,lastUpdatedBy,customerID,userID,contactID);
            Appointment.addAppointment(appt);
            Customer customer = Customer.getCustomer(customerID);
            customer.addAppointmentForCustomer(appt);
            Contact contact = Contact.getContact(contactID);
            contact.addAppointmentForContact(appt);
            User user = User.getUser(userID);
            user.addAppointmentForUser(appt);
        }
    }

    /**Method that shows appointments in a table view.
     * @param appointments Appointment table view.
     * @param customerID Appointment customer ID table column.
     * @param apptID Appointment ID table column.
     * @param apptTitle Appointment title table column.
     * @param apptDescription Appointment description table column.
     * @param apptLocation Appointment location table column.
     * @param apptType Appointment type table column.
     * @param apptStartDate Appointment start date table column.
     * @param apptEndDate Appointment end date table column.
     * @param apptStartTime Appointment start time, table column.
     * @param apptEndTime Appointment end time, table column.
     * @param apptDateCreated Appointment date created table column.
     * @param apptCreatedBy Appointment created by table column.
     * @param apptLastUpdated Appointment last updated table column.
     * @param apptUpdatedBy Appointment updated by table column.
     * @param contactID Appointment contact ID table column.
     * @param userID Appointment user ID table column.*/
    public static void showAppointmentsInTable(TableView<Appointment> appointments,TableColumn<Appointment, Integer> apptID,TableColumn<Appointment, String> apptTitle
            ,TableColumn<Appointment, String> apptDescription,TableColumn<Appointment, String> apptLocation,TableColumn<Appointment, String> apptType
            ,TableColumn<Appointment, LocalDate> apptStartDate,TableColumn<Appointment, LocalDate> apptEndDate,TableColumn<Appointment, LocalTime> apptStartTime
            ,TableColumn<Appointment, LocalTime> apptEndTime,TableColumn<Appointment, Timestamp> apptDateCreated,TableColumn<Appointment, String> apptCreatedBy
            ,TableColumn<Appointment, Timestamp> apptLastUpdated,TableColumn<Appointment, String> apptUpdatedBy,TableColumn<Appointment, Integer> customerID
            ,TableColumn<Appointment, Integer> userID,TableColumn<Appointment, Integer> contactID){

        appointments.setItems(Appointment.getAppointments());
        apptID.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        apptTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptType.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        apptEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        apptStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        apptEndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        apptDateCreated.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
        apptCreatedBy.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        apptLastUpdated.setCellValueFactory(new PropertyValueFactory<>("lastUpdated"));
        apptUpdatedBy.setCellValueFactory(new PropertyValueFactory<>("updatedBy"));
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        contactID.setCellValueFactory(new PropertyValueFactory<>("contactID"));
    }

    /**Method that shows filtered appointments in a table view.
     * @param appointments Appointment table view.
     * @param customerID Appointment customer ID table column.
     * @param apptID Appointment ID table column.
     * @param apptTitle Appointment title table column.
     * @param apptDescription Appointment description table column.
     * @param apptLocation Appointment location table column.
     * @param apptType Appointment type table column.
     * @param apptStartDate Appointment start date table column.
     * @param apptEndDate Appointment end date table column.
     * @param apptStartTime Appointment start time, table column.
     * @param apptEndTime Appointment end time, table column.
     * @param apptDateCreated Appointment date created table column.
     * @param apptCreatedBy Appointment created by table column.
     * @param apptLastUpdated Appointment last updated table column.
     * @param apptUpdatedBy Appointment updated by table column.
     * @param contactID Appointment contact ID table column.
     * @param userID Appointment user ID table column.
     * @param filteredAppointments filter appointments.*/
    public static void showAppointmentsInTable(TableView<Appointment> appointments, TableColumn<Appointment, Integer> apptID, TableColumn<Appointment, String> apptTitle
            , TableColumn<Appointment, String> apptDescription, TableColumn<Appointment, String> apptLocation, TableColumn<Appointment, String> apptType
            , TableColumn<Appointment, LocalDate> apptStartDate, TableColumn<Appointment, LocalDate> apptEndDate, TableColumn<Appointment, LocalTime> apptStartTime
            , TableColumn<Appointment, LocalTime> apptEndTime, TableColumn<Appointment, Timestamp> apptDateCreated, TableColumn<Appointment, String> apptCreatedBy
            , TableColumn<Appointment, Timestamp> apptLastUpdated, TableColumn<Appointment, String> apptUpdatedBy, TableColumn<Appointment, Integer> customerID
            , TableColumn<Appointment, Integer> userID, TableColumn<Appointment, Integer> contactID,  FilteredList<Appointment> filteredAppointments){

        appointments.setItems(filteredAppointments);
        apptID.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        apptTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        apptType.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        apptEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        apptStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        apptEndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        apptDateCreated.setCellValueFactory(new PropertyValueFactory<>("dateCreated"));
        apptCreatedBy.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        apptLastUpdated.setCellValueFactory(new PropertyValueFactory<>("lastUpdated"));
        apptUpdatedBy.setCellValueFactory(new PropertyValueFactory<>("updatedBy"));
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        userID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        contactID.setCellValueFactory(new PropertyValueFactory<>("contactID"));
    }

    /**Method that shows filtered appointments in a table view.
     * @param appointments Appointment table view.
     * @param customerID Appointment customer ID table column.
     * @param apptID Appointment ID table column.
     * @param apptTitle Appointment title table column.
     * @param apptDescription Appointment description table column.
     * @param apptType Appointment type table column.
     * @param apptStartDate Appointment start date table column.
     * @param apptEndDate Appointment end date table column.
     * @param apptStartTime Appointment start time, table column.
     * @param apptEndTime Appointment end time, table column.
     * @param filtered filtered appointments.*/
    public static void showAppointmentInReport(ObservableList<Appointment> filtered, TableColumn<Appointment, Integer> apptID,TableColumn<Appointment
            , String> apptTitle,TableColumn<Appointment, String> apptDescription,TableColumn<Appointment,
            String> apptType,TableColumn<Appointment, LocalDate> apptStartDate,TableColumn<Appointment, LocalTime> apptStartTime,
            TableColumn<Appointment, LocalDate> apptEndDate,TableColumn<Appointment, LocalTime> apptEndTime,TableColumn<Appointment, Integer> customerID
            ,TableView<Appointment> appointments){

        appointments.setItems(filtered);
        apptID.setCellValueFactory(new PropertyValueFactory<>("apptID"));
        apptTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        apptDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        apptType.setCellValueFactory(new PropertyValueFactory<>("type"));
        apptStartDate.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        apptStartTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        apptEndDate.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        apptEndTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        customerID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    }

    /**Method to add appointment into database.
     * @param title Appointment title.
     * @param description Appointment description.
     * @param location Appointment location.
     * @param type Appointment type.
     * @param start Appointment start time and date.
     * @param end Appointment end time and date.
     * @param createdDate Appointment date created.
     * @param createdBy Appointment created by.
     * @param lastUpdate Appointment last update.
     * @param lastUpdatedBy Appointment last updated by.
     * @param customerID Appointment customer ID.
     * @param contactID Appointment contact ID.
     * @param userID Appointment user ID.*/
    public static void addAppointment(String title,String description,String location,String type, Timestamp start, Timestamp end, Timestamp createdDate,String createdBy, Timestamp lastUpdate,String lastUpdatedBy,int customerID,int userID,int contactID) throws SQLException {
        String sql = "INSERT INTO client_schedule.appointments (Title,Description,Location,Type,Start,End,Create_Date,Created_By,Last_Update,Last_Updated_By,Customer_ID,User_ID,Contact_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);";
        PreparedStatement prepState = JDBC.connection.prepareStatement(sql);
        prepState.setString(1,title);
        prepState.setString(2,description);
        prepState.setString(3,location);
        prepState.setString(4,type);
        prepState.setTimestamp(5,start);
        prepState.setTimestamp(6,end);
        prepState.setTimestamp(7,createdDate);
        prepState.setString(8,createdBy);
        prepState.setTimestamp(9,lastUpdate);
        prepState.setString(10,lastUpdatedBy);
        prepState.setInt(11,customerID);
        prepState.setInt(12,userID);
        prepState.setInt(13,contactID);
        prepState.executeUpdate();
    }

    /**Method gets most recent appointment in database.
     * @return Most recent appointment in database.*/
    public static Appointment getAppointment() throws SQLException {
        String sql = "SELECT * FROM client_schedule.appointments WHERE Appointment_ID = (SELECT MAX(Appointment_ID) FROM client_schedule.appointments);";
        PreparedStatement prepState = JDBC.connection.prepareStatement(sql);
        ResultSet resSet = prepState.executeQuery();
        while (resSet.next()){
            int ID = resSet.getInt("Appointment_ID");
            String title = resSet.getString("Title");
            String description = resSet.getString("Description");
            String location = resSet.getString("Location");
            String type = resSet.getString("Type");
            Timestamp start = resSet.getTimestamp("Start");
            Timestamp end = resSet.getTimestamp("End");
            Timestamp createdDate = resSet.getTimestamp("Create_Date");
            String createdBy = resSet.getString("Created_By");
            Timestamp lastUpdate = resSet.getTimestamp("Last_Update");
            String lastUpdatedBy = resSet.getString("Last_Updated_By");
            int customerID = resSet.getInt("Customer_ID");
            int userID = resSet.getInt("User_ID");
            int contactID = resSet.getInt("Contact_ID");
            LocalDate startDate = start.toLocalDateTime().toLocalDate();
            LocalDate endDate = end.toLocalDateTime().toLocalDate();
            LocalTime startTime = start.toLocalDateTime().toLocalTime();
            LocalTime endTime = end.toLocalDateTime().toLocalTime();

            return new Appointment(ID,title,description,location,type,startDate,endDate,startTime,endTime,createdDate,createdBy,lastUpdate,lastUpdatedBy,customerID,userID,contactID);
        }
        return null;
    }

    /**Method gets appointment in database based on appointment ID.
     * @param ID Appointment ID.
     * @return Appointment in database matching ID.*/
    public static Appointment getAppointment(int ID) throws SQLException {
        String sql = "SELECT * FROM client_schedule.appointments WHERE Appointment_ID = ?";
        PreparedStatement prepState = JDBC.connection.prepareStatement(sql);
        prepState.setInt(1,ID);
        ResultSet resSet = prepState.executeQuery();
        while (resSet.next()){
            int apptID = resSet.getInt("Appointment_ID");
            String title = resSet.getString("Title");
            String description = resSet.getString("Description");
            String location = resSet.getString("Location");
            String type = resSet.getString("Type");
            Timestamp start = resSet.getTimestamp("Start");
            Timestamp end = resSet.getTimestamp("End");
            Timestamp createdDate = resSet.getTimestamp("Create_Date");
            String createdBy = resSet.getString("Created_By");
            Timestamp lastUpdate = resSet.getTimestamp("Last_Update");
            String lastUpdatedBy = resSet.getString("Last_Updated_By");
            int customerID = resSet.getInt("Customer_ID");
            int userID = resSet.getInt("User_ID");
            int contactID = resSet.getInt("Contact_ID");
            LocalDate startDate = start.toLocalDateTime().toLocalDate();
            LocalDate endDate = end.toLocalDateTime().toLocalDate();
            LocalTime startTime = start.toLocalDateTime().toLocalTime();
            LocalTime endTime = end.toLocalDateTime().toLocalTime();

            return new Appointment(ID,title,description,location,type,startDate,endDate,startTime,endTime,createdDate,createdBy,lastUpdate,lastUpdatedBy,customerID,userID,contactID);
        }
        return null;
    }

    /**Method to modify appointment into database.
     * @param title Appointment title.
     * @param description Appointment description.
     * @param location Appointment location.
     * @param type Appointment type.
     * @param start Appointment start time and date.
     * @param end Appointment end time and date.
     * @param lastUpdated Appointment last update.
     * @param updatedBy Appointment last updated by.
     * @param customerID Appointment customer ID.
     * @param contactID Appointment contact ID.
     * @param userID Appointment user ID.*/
    public static void updateAppointment(int ID,String title,String description,String location,String type,Timestamp start,Timestamp end,Timestamp lastUpdated,String updatedBy,int customerID,int userID, int contactID) throws SQLException {
        String sql = "UPDATE client_schedule.appointments SET Title = ?,Description = ?,Location = ?,Type = ?,Start = ?,End = ?,Last_Update = ?,Last_Updated_By = ?,Customer_ID = ?,User_ID = ?,Contact_ID = ? WHERE Appointment_ID = ?;";
        PreparedStatement prepState = JDBC.connection.prepareStatement(sql);
        prepState.setString(1,title);
        prepState.setString(2,description);
        prepState.setString(3,location);
        prepState.setString(4,type);
        prepState.setTimestamp(5,start);
        prepState.setTimestamp(6,end);
        prepState.setTimestamp(7,lastUpdated);
        prepState.setString(8,updatedBy);
        prepState.setInt(9,customerID);
        prepState.setInt(10,userID);
        prepState.setInt(11,contactID);
        prepState.setInt(12,ID);
        prepState.executeUpdate();
    }

    /**Method to remove appointment from database.
     * @param appointment Appointment to be removed.*/
    public static void removeAppointment(Appointment appointment) throws SQLException {
        String sql = "DELETE FROM client_schedule.appointments WHERE Appointment_ID = ?;";
        PreparedStatement prepState = JDBC.connection.prepareStatement(sql);
        prepState.setInt(1,appointment.getApptID());
        prepState.executeUpdate();
    }
}
