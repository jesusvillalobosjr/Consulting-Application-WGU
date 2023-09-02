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

/**Class that works as the controller for the "Add Customer Menu" .*/
public class AddCustomerController implements Initializable {
    @FXML
    private TextField customerName;
    @FXML
    private TextField customerPhone;
    @FXML
    private ChoiceBox<String> customerCountry;
    @FXML
    private ChoiceBox<String> customerDivision;
    @FXML
    private TextField customerAddress;
    @FXML
    private TextField customerZip;

    /**Method to add customers.
     * Method will take input from input fields, verify the input is valid,
     * and add the customer into the database and instantiate it with the Customer
     * class.
     * @param event References event object created by the source object.*/
    @FXML
    public void addCustomer(ActionEvent event) throws SQLException, IOException {
        String name = customerName.getText();
        String phone = customerPhone.getText();
        String country = customerCountry.getValue();
        String division = customerDivision.getValue();
        String address = customerAddress.getText();
        String zip = customerZip.getText();

        boolean validCustomer = Menu.verifyInputCustomer(name,phone,country,division,address,zip,customerName,customerPhone,customerCountry,customerDivision,customerAddress,customerZip);
        if(validCustomer){
            var createdTime = Timestamp.from(Instant.now().truncatedTo(ChronoUnit.MILLIS));
            applicationHelper.addCustomer(name,address,zip,phone,createdTime,applicationHelper.currentAccountUser,createdTime,applicationHelper.currentAccountUser,division);
            var customer = applicationHelper.getCustomer();
            Customer.addCustomer(customer);
            fxmlHelper.changeSceneButton(event,"/com/example/c195/MainMenuCustomers.fxml");
        }
    }

    /**Method to return to the "Customer Menu".
     * Method return the user to the "Main Menu Customers" when clicked.
     * @param event References event object created by the source object.*/
    @FXML
    public void cancelCustomer(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneButton(event,"/com/example/c195/MainMenuCustomers.fxml");
    }

    /**Method sets division choice box values.
     * Method will set the divisions choice box when a country is selected.
     * @param event References event object created by the source object.*/
    @FXML
    public void selectCountry(ActionEvent event) throws IOException{
        Menu.setDivision(customerCountry,customerDivision);
    }

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
    public void phoneNumberClicked(MouseEvent event){
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


    /**Method will initialize the "Add Customer Menu".
     * Method sets the value for the country and division choice boxes,
     * and sets all the items for the country and division choice boxes.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerCountry.setValue("Select Country");
        customerCountry.getItems().addAll(Menu.countries);
        customerDivision.setValue("Select Division");
        customerDivision.getItems().clear();
    }
}
