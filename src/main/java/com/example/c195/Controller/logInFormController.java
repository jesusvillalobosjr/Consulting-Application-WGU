package com.example.c195.Controller;

import com.example.c195.Model.Menu;
import com.example.c195.Model.applicationHelper;
import com.example.c195.Model.fxmlHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**Class that acts as a controller for the "Log In Form" view.*/
public class logInFormController implements Initializable {
    @FXML
    private Label titleLogIn;
    @FXML
    private Label username;
    @FXML
    private Label password;
    @FXML
    private Label languageSelectionLabel;
    @FXML
    private Label timeZoneLabel;
    @FXML
    private Label timeZone;
    @FXML
    private ChoiceBox<String> languageSelection;

    /**Array for languages choice box to be filled.
     * Array for languages choice box to be filled when in English.*/
    private String[] languages = {"English","French"};

    /**Array for languages choice box to be filled.
     * Array for languages choice box to be filled when in French.*/
    private String[] langues = {"Français","Anglaise"};
    @FXML
    private Button logInButton;

    @FXML
    private PasswordField passwordLogIn;

    @FXML
    private TextField usernameLogIn;

    /**Sets border of username to grey when clicked.*/
    @FXML
    void typingUsername(MouseEvent event){
        usernameLogIn.setStyle("-fx-text-box-border: grey");
    }

    /**Sets border of password to grey when clicked.*/
    @FXML
    public void passwordClicked(){
        passwordLogIn.setStyle("-fx-text-box-border: grey");
    }

    /**Method for logging in to a user account.
     * Method grabs information input by the user in input fields, if valid grants access to the
     * application, if denied the user has to reattempt log in.
     * @param event References event object created by the source object.*/
    @FXML
    public void logInButtonClicked(ActionEvent event) throws SQLException, IOException {
        String usernameInput = usernameLogIn.getText();
        String passwordInput = passwordLogIn.getText();
        var access = applicationHelper.accountVerification(usernameInput,passwordInput);
        if(access){
            applicationHelper.currentAccountUser = usernameInput;
            fxmlHelper.changeSceneButton(event,"/com/example/c195/MainMenuCustomers.fxml");
            Menu.appointmentWithinFifteenMinutes();
        }else{
            var alert = new Alert(Alert.AlertType.ERROR);
            usernameLogIn.setStyle("-fx-text-box-border: red");
            passwordLogIn.setStyle("-fx-text-box-border: red");
            if(languageSelection.getValue().equals("English")){
                fxmlHelper.showAlert(alert,"Invalid Login","Invalid username or password was input.");
            }else{
                ResourceBundle fr = ResourceBundle.getBundle("com.example.c195.toFrench_fr",Locale.getDefault());
                fxmlHelper.showAlert(alert,fr.getString("Invalidtitle"),fr.getString("Invalidcontent"));
            }
        }
        Timestamp ts = Timestamp.from(Instant.now());
        FileWriter fileWriter = new FileWriter("src/main/java/com/example/c195/login_activity.txt",true);
        PrintWriter pw = new PrintWriter(fileWriter);
        if(access){
            pw.println("Attempt Successful at " + ts);
            pw.close();
        }else{
            pw.println("Attempt unsuccessful at :" + ts);
            pw.close();
        }
    }

    /**Method changes the language of application.
     * Method translates text from either French or English depending on the user selection.
     * @param event References event object created by the source object.*/
    public void changeLanguage(ActionEvent event){
        try {
            var language = languageSelection.getValue();
            ResourceBundle fr = ResourceBundle.getBundle("com.example.c195.toFrench_fr",Locale.getDefault());
            if(language.equals("French")){
                setFrench(fr);
            } else if (language.equals("Anglaise")) {
                setEnglish();
            }
        }
        catch (NullPointerException exc){}
    }

    /**Method to set text into French.
     * @param fr ResourceBundle with French translations.*/
    private void setFrench(ResourceBundle fr){
        titleLogIn.setMinWidth(175);
        titleLogIn.setText(fr.getString("Log"));
        username.setText(fr.getString("Username"));
        password.setMinWidth(128);
        password.setText(fr.getString("Password"));
        logInButton.setText(fr.getString("Enter"));
        languageSelectionLabel.setText(fr.getString("Languageselection"));
        timeZoneLabel.setText(fr.getString("Timezone"));
        languageSelection.getItems().clear();
        languageSelection.setValue(langues[0]);
        languageSelection.getItems().setAll(langues);
    }

    /**Method to set text into English.*/
    private void setEnglish(){
        titleLogIn.setMinWidth(10);
        titleLogIn.setText("Log In");
        username.setText("Username:");
        password.setMinWidth(username.getMinWidth());
        password.setText("Password:");
        logInButton.setText("Enter");
        languageSelectionLabel.setText("Language Selection:");
        timeZoneLabel.setText("Time Zone:");
        languageSelection.getItems().clear();
        languageSelection.setValue(languages[0]);
        languageSelection.getItems().setAll(languages);
    }

    /**Method that initializes "Log In Form".
     * Method sets initial value for language choice box and adds all choices to the choice box.
     * Method also displays timezone the user is currently located in.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        languageSelection.setValue("English");
        languageSelection.getItems().addAll(languages);
        timeZone.setText(ZoneId.systemDefault().toString());
    }
}

