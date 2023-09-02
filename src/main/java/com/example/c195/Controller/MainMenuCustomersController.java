package com.example.c195.Controller;
import com.example.c195.Model.Customer;
import com.example.c195.Model.applicationHelper;
import com.example.c195.Model.fxmlHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;

/**Class that works as the controller for the "Main Menu Customers" view.*/
public class MainMenuCustomersController implements Initializable {

    Stage stage;
    Parent scene;
    @FXML
    private ToggleGroup Select;

    @FXML
    private TableColumn<Customer, String> customerAddress;

    @FXML
    private TableColumn<Customer, String> customerCreatedBy;

    @FXML
    private TableColumn<Customer, Timestamp> customerDateCreated;

    @FXML
    private TableColumn<Customer, String> customerDivision;

    @FXML
    private TableColumn<Customer, Integer> customerID;

    @FXML
    private TableColumn<Customer, Timestamp> customerLastUpdated;

    @FXML
    private TableColumn<Customer, String> customerName;

    @FXML
    private TableColumn<Customer, String> customerNumber;

    @FXML
    private TableColumn<Customer, String> customerUpdatedBy;

    @FXML
    private TableColumn<Customer, String> customerZip;

    @FXML
    private TableView<Customer> customers;

    /**Method logs out user of application.
     *@param event References event object created by the source object.*/
    @FXML
    public void logout(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneButton(event,"/com/example/c195/loginForm.fxml");
    }

    /**Method changes view to the "All Appointments Menu".
     *@param event References event object created by the source object.*/
    @FXML
    public void changeAllApptMenu(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneRadio(event,"/com/example/c195/MainMenuAllAppointments.fxml");
    }

    /**Method changes view to the "Appointments by Week Menu".
     *@param event References event object created by the source object.*/
    @FXML
    public void changeWeeklyAppt(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneRadio(event,"/com/example/c195/MainMenuWeeklyAppointments.fxml");
    }

    /**Method changes view to the "Appointments by Month Menu".
     *@param event References event object created by the source object.*/
    @FXML
    public void changeMonthlyAppt(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneRadio(event,"/com/example/c195/MainMenuMonthlyAppointments.fxml");
    }

    /**Method deletes a selected Customer.
     * Method gets selected customer, ensures that there are no existing
     * appointments for the customer prior to deleting. If appointments exist
     * for the customer or no customer is selected an error message is displeyed.
     *@param event References event object created by the source object.*/
    @FXML
    public void deleteCustomer(ActionEvent event) throws SQLException {
        var customer = customers.getSelectionModel().getSelectedItem();
        if(customer == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            fxmlHelper.showAlert(alert,"No Customer Selected","Customer needs to be selected in order for deletion.");
            return;
        }
        if(!customer.getCustomerAppointments().isEmpty()){
            System.out.println();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            fxmlHelper.showAlert(alert,"Customer Has Appointments","Customer cannot be deleted if they have existing appointments.");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure you want to delete customer?");
        Optional<ButtonType> option = alert.showAndWait();
        if(option.get() == ButtonType.OK){
            Customer.removeCustomer(customer);
            applicationHelper.removeCustomer(customer);
        }
    }

    /**Method to change view to "Modify Customer Menu".
     * Method gets selected Customer and sends the Customer object
     * To the Modify Customer Controller. If no Customer is selected an
     * error message is displayed to the user.
     * @param event References event object created by the source object.*/
    @FXML
    public void modifyCustomer(ActionEvent event) throws IOException, SQLException {
        var customer = customers.getSelectionModel().getSelectedItem();
        if(customer == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            fxmlHelper.showAlert(alert,"Customer Not Selected","In order to modify a customer, a customer must be selected.");
            return;
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/c195/ModifyCustomerMenu.fxml"));
        loader.load();
        ModifyCustomerMenuController MCMC = loader.getController();
        MCMC.setModifyCustomerFields(customer);

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**Method changes view to the "Add Customer Menu".
     * @param event References event object created by the source object.*/
    @FXML
    public void createCustomer(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneButton(event,"/com/example/c195/AddCustomerMenu.fxml");
    }

    /**Method changes view to the "Reports Contact Menu".
     * @param event References event object created by the source object.*/
    @FXML
    public void changeReport(ActionEvent event) throws IOException {
        fxmlHelper.changeSceneButton(event,"/com/example/c195/ReportsContacts.fxml");
    }

    /**Method initializes the "Main Menu Customers" view.
     * Method displays all customers in table view.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        applicationHelper.showCustomersInTableView(customers,customerID,customerName,customerNumber,customerAddress,customerZip,customerDateCreated,customerCreatedBy,customerLastUpdated,customerUpdatedBy,customerDivision);
    }
}
