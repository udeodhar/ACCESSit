/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  javafx.event.ActionEvent
 *  javafx.fxml.FXML
 *  javafx.fxml.Initializable
 *  javafx.scene.control.Button
 *  javafx.scene.control.Label
 *  javafx.scene.control.TextField
 *  javafx.scene.paint.Paint
 */
package accessit;

import accessit.ACCESSit;
import accessit.AlertBox;
import accessit.ClientForCommunication;
import accessit.HomeWindowController;
import accessit.LogInWindowController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Paint;

public class ResetPasswordWindowController
implements Initializable {
    @FXML
    private Button sendOTP;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField otpTextField;
    @FXML
    private TextField passTextField;
    @FXML
    private Button submitButton;
    @FXML
    private Button backButton;
    @FXML
    private Label invalidUsernameLabel;
    @FXML
    private Label invalidPasswordLabel;
    @FXML
    private TextField confirmPassTextField;

    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void onSendOTPClicked(ActionEvent event) throws IOException {
        if (!ClientForCommunication.sendOTP(this.usernameTextField.getText())) {
            this.invalidUsernameLabel.setText("Username is invalid or could not send email!");
            this.invalidUsernameLabel.setTextFill(Paint.valueOf((String)"Red"));
            this.invalidUsernameLabel.setVisible(true);
        } else {
            this.invalidUsernameLabel.setText("OTP sent!");
            this.invalidUsernameLabel.setTextFill(Paint.valueOf((String)"Green"));
            this.invalidUsernameLabel.setVisible(true);
        }
    }

    @FXML
    private void onSubmitButtonClicked(ActionEvent event) {
        if (!this.passTextField.getText().equals(this.confirmPassTextField.getText())) {
            this.invalidPasswordLabel.setText("Passwords do not match");
            this.invalidPasswordLabel.setTextFill(Paint.valueOf((String)"Red"));
            this.invalidPasswordLabel.setVisible(true);
        } else {
            boolean b = ClientForCommunication.resetPassword(this.passTextField.getText(), Integer.parseInt(this.otpTextField.getText()), this.usernameTextField.getText());
            if (b) {
                if (!HomeWindowController.getFlag()) {
                    ACCESSit.showStage();
                    LogInWindowController.closeStage();
                    AlertBox.display("ACCESSit", "Password changed successfully!!!");
                } else {
                    AlertBox.display("ACCESSit", "Password changed successfully!!!");
                    HomeWindowController.closeResetWindow();
                }
            }
        }
    }

    @FXML
    private void onBackButtonClicked(ActionEvent event) {
        ACCESSit.showStage();
        LogInWindowController.closeStage();
    }
}

