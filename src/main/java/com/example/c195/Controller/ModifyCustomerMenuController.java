package com.example.c195.Controller;

import com.example.c195.Model.Customer;
import com.example.c195.Model.Menu;
import com.example.c195.Model.applicationHelper;
import com.example.c195.Model.fxmlHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

/**Class that works as the controller for the "Modify Customer Menu" view.*/
public class ModifyCustomerMenuController implements Initializable {

    @FXML
    private TextField customerAddress;

    @FXML
    private ChoiceBox<String> customerCountry;

    @FXML
    private ChoiceBox<String> customerDivision;

    @FXML
    private TextField customerID;

    @FXML
    private TextField customerName;

    @FXML
    private TextField customerPhone;

    @FXML
    private TextField customerZip;

    private Customer customerToModify = null;

    /**Method turns border from the name text box to grey.
     * Method will have the name text box return to a grey border when clicked.
     * @param event References event object created by the source object.*/
    @FXML
    public void nameClicked(MouseEvent event){
        Menu.setBordersGrey(customerName);
    }

    /**Method turns border from the phone number text box to grey.
     * Method will have the phone number text box return to a grey border when clicked.
     * @param event References event object created by the source object.*/
    @FXML
    public void numberClicked(MouseEvent event){
        Menu.setBordersGrey(customerPhone);
    }

    /**Method turns border from the country choice box to grey.
     * Method will have the country choice box return to a grey border when clicked.
     * @param event References event object created by the source object.*/
    @FXML
    public void countryClicked(MouseEvent event){
        Menu.setBordersGrey(customerCountry);
    }

    /**Method turns border from the division choice box to grey.
     * Method will have the division choice box return to a grey border when clicked.
     * @param event References event object created by the source object.*/
    @FXML
    public void divisionClicked(MouseEvent event){
        Menu.setBordersGrey(customerDivision);
    }

    /**Method turns border from the address text box to grey.
     * Method will have the address text box return to a grey border when clicked.
     * @param event References event object created by the source object.*/
    @FXML
    public void addressClicked(MouseEvent event){
        Menu.setBordersGrey(customerAddress);
    }

    /**Method turns border from the zip code text box to grey.
     * Method will have the zip text box return to a grey border when clicked.
     * @param event References event object created by the source object.*/
    @FXML
    public void zipClicked(MouseEvent event){
        Menu.setBordersGrey(customerZip);
    }

    /**Method to return to the "Customer Menu".
     * Method return the user to the "Main Menu Customers" when clicked.
     * @param event References event object created by the source object.*/
    @FXML
    public void cancel(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneButton(event,"/com/example/c195/MainMenuCustomers.fxml");
    }

    /**Method to add customers.
     * Method will take input from input fields, verify the input is valid,
     * and modify the customer into the database and replace it in the customers
     * ObservableList. If input is not valid an error message is shown.
     * @param event References event object created by the source object.*/
    @FXML
    public void saveModifiedCustomer(ActionEvent event) throws SQLException, IOException {
        String name = customerName.getText();
        String phone = customerPhone.getText();
        String country = customerCountry.getValue();
        String division = customerDivision.getValue();
        String address = customerAddress.getText();
        String zip = customerZip.getText();

        boolean validCustomer = Menu.verifyInputCustomer(name,phone,country,division,address,zip,customerName,customerPhone,customerCountry,customerDivision,customerAddress,customerZip);

        if(validCustomer){
            var timeUpdated = Timestamp.from(Instant.now().truncatedTo(ChronoUnit.MILLIS));
            applicationHelper.updateCustomer(Integer.parseInt(customerID.getText()),name,address,zip,phone,timeUpdated,applicationHelper.currentAccountUser,applicationHelper.getDivisionID(division));
            var customer = applicationHelper.getCustomer(customerToModify);
            var index = Customer.getIndex(customerToModify);
            Customer.getCustomers().set(index,customer);
            fxmlHelper.changeSceneButton(event,"/com/example/c195/MainMenuCustomers.fxml");
        }
    }

    /**Method sets division choice box values.
     * Method will set the divisions choice box when a country is selected.
     * @param event References event object created by the source object.*/
    @FXML
    public void selectCountry(ActionEvent event) throws IOException{
        Menu.setDivision(customerCountry,customerDivision);
    }

    /**Method to pass in appointment object into "Modify Customer Menu" view.
     * Method receives Customer to modify, filling fields with variable from
     * the Customer object.
     * @param customer Customer object to be modified. */
    public void setModifyCustomerFields(Customer customer) throws SQLException {
        customerToModify = customer;
        customerID.setText(String.valueOf(customer.getCustomerID()));
        customerName.setText(customer.getCustomerName());
        customerPhone.setText(customer.getCustomerNumber());
        customerCountry.setValue(applicationHelper.getCountry(applicationHelper.getCountryID(customer)));
        customerDivision.setValue(customer.getCustomerDivision());
        customerAddress.setText(customer.getCustomerAddress());
        customerZip.setText(customer.getCustomerZip());
    }

    /**Method to initialize the "Modify Customer Menu" view.
     * Method sets country choice box with country values as choices.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerCountry.getItems().setAll(Menu.countries);
    }
}

