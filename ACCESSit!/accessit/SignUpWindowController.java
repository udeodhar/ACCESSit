/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  javafx.event.ActionEvent
 *  javafx.fxml.FXML
 *  javafx.fxml.Initializable
 *  javafx.scene.control.Button
 *  javafx.scene.control.CheckBox
 *  javafx.scene.control.Label
 *  javafx.scene.control.PasswordField
 *  javafx.scene.control.TextField
 *  javafx.scene.paint.Paint
 */
package accessit;

import accessit.ACCESSit;
import accessit.ClientForCommunication;
import accessit.LogInWindowController;
import java.io.IOException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;

public class SignUpWindowController
implements Initializable {
    private boolean isUsernameAvailable;
    @FXML
    private Button checkUsernameAvailabilityButton;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField confirmPasswordTextField;
    private TextField passworddTextField;
    @FXML
    private Button sendEmailButton;
    @FXML
    private TextField oTPCodeTextField;
    @FXML
    private Button submitButton;
    @FXML
    private CheckBox termsAndConditionsCheckBox;
    @FXML
    private Button backButton;
    @FXML
    private Button helpPasswordButton;
    @FXML
    private Button troubleEmailButton;
    @FXML
    private Label availableLabel;
    @FXML
    private Label invalidLabel;
    @FXML
    private PasswordField passwordTextField;

    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void onCheckUsernameAvailabilityButtonClicked(ActionEvent event) throws IOException {
        if (!this.usernameTextField.getText().isEmpty()) {
            String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
            boolean b = ClientForCommunication.checkUsernameCommunication(this.usernameTextField.getText());
            if (b && this.emailTextField.getText().matches(EMAIL_REGEX)) {
                this.availableLabel.setText("Available");
                this.availableLabel.setTextFill(Paint.valueOf((String)"Green"));
                this.availableLabel.setVisible(true);
                this.isUsernameAvailable = true;
            } else {
                this.availableLabel.setText("Entered Username isn't available.");
                this.availableLabel.setTextFill(Paint.valueOf((String)"Red"));
                this.availableLabel.setVisible(true);
                this.isUsernameAvailable = false;
            }
        }
    }

    @FXML
    private void onSendEmailButtonClicked(ActionEvent event) throws IOException {
        if (!this.passwordTextField.getText().equals(this.confirmPasswordTextField.getText())) {
            this.invalidLabel.setText("Passwords do not match!");
            this.invalidLabel.setTextFill(Paint.valueOf((String)"Red"));
            this.invalidLabel.setVisible(true);
            this.passwordTextField.setStyle("-fx-border-color: red ;");
            this.confirmPasswordTextField.setStyle("-fx-border-color: red ;");
        } else if (this.isUsernameAvailable) {
            boolean flag = ClientForCommunication.sendMail(this.emailTextField.getText());
            if (flag) {
                this.invalidLabel.setText("OTP sent!");
                this.invalidLabel.setTextFill(Paint.valueOf((String)"Green"));
                this.invalidLabel.setVisible(true);
            } else {
                this.invalidLabel.setText("Sorry cannot send OTP");
                this.invalidLabel.setTextFill(Paint.valueOf((String)"Red"));
                this.invalidLabel.setVisible(true);
            }
        } else {
            this.invalidLabel.setText("Enter available username!!!");
            this.invalidLabel.setTextFill(Paint.valueOf((String)"Black"));
            this.invalidLabel.setVisible(true);
        }
    }

    @FXML
    private void onSubmitButtonClicked(ActionEvent event) throws IOException, NoSuchAlgorithmException {
        if (!this.passwordTextField.getText().equals(this.confirmPasswordTextField.getText())) {
            this.invalidLabel.setText("Passwords do not match!");
            this.invalidLabel.setTextFill(Paint.valueOf((String)"Red"));
            this.invalidLabel.setVisible(true);
            this.passwordTextField.setStyle("-fx-border-color: red ;");
            this.confirmPasswordTextField.setStyle("-fx-border-color: red ;");
        } else if (this.firstNameTextField.getText().isEmpty()) {
            this.availableLabel.setText("Marked field is invalid or cannot be empty.");
            this.availableLabel.setTextFill(Paint.valueOf((String)"Red"));
            this.availableLabel.setVisible(true);
            this.firstNameTextField.setStyle("-fx-border-color: red ;");
        } else if (this.lastNameTextField.getText().isEmpty()) {
            this.availableLabel.setText("Marked field is invalid or cannot be empty.");
            this.availableLabel.setTextFill(Paint.valueOf((String)"Red"));
            this.availableLabel.setVisible(true);
            this.lastNameTextField.setStyle("-fx-border-color: red ;");
        } else if (this.emailTextField.getText().isEmpty()) {
            this.availableLabel.setText("Marked field is invalid or cannot be empty.");
            this.availableLabel.setTextFill(Paint.valueOf((String)"Red"));
            this.availableLabel.setVisible(true);
            this.emailTextField.setStyle("-fx-border-color: red ;");
        } else if (this.usernameTextField.getText().isEmpty()) {
            this.availableLabel.setText("Marked field is invalid or cannot be empty.");
            this.availableLabel.setTextFill(Paint.valueOf((String)"Red"));
            this.availableLabel.setVisible(true);
            this.usernameTextField.setStyle("-fx-border-color: red ;");
        } else if (!this.isUsernameAvailable) {
            this.invalidLabel.setText("Enter available username!!!");
            this.invalidLabel.setTextFill(Paint.valueOf((String)"Red"));
            this.invalidLabel.setVisible(true);
        } else {
            boolean flag = ClientForCommunication.signUpCommunication(this.firstNameTextField.getText(), this.lastNameTextField.getText(), this.emailTextField.getText(), this.usernameTextField.getText(), this.passwordTextField.getText(), Integer.parseInt(this.oTPCodeTextField.getText()));
            if (flag) {
                ACCESSit.showStage();
                LogInWindowController.closeStage();
            } else {
                this.availableLabel.setText("Sorry but our server is too busy!");
                this.availableLabel.setTextFill(Paint.valueOf((String)"Red"));
            }
        }
    }

    @FXML
    private void onTermsAndConditionsBoxActtion(ActionEvent event) {
        this.submitButton.setDisable(!this.termsAndConditionsCheckBox.isSelected());
    }

    @FXML
    private void onBackButtonClicked(ActionEvent event) throws IOException {
        ACCESSit.showStage();
        LogInWindowController.closeStage();
    }

    @FXML
    private void onHelpPasswordButtonClicked(ActionEvent event) {
    }

    @FXML
    private void onTroubleButtonClicked(ActionEvent event) {
    }
}

