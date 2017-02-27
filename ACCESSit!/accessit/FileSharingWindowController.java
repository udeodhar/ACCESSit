/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  javafx.beans.property.ObjectProperty
 *  javafx.beans.value.ChangeListener
 *  javafx.beans.value.ObservableValue
 *  javafx.collections.ObservableList
 *  javafx.event.ActionEvent
 *  javafx.fxml.FXML
 *  javafx.fxml.Initializable
 *  javafx.scene.control.Button
 *  javafx.scene.control.ChoiceBox
 *  javafx.scene.control.Label
 *  javafx.scene.control.TextField
 */
package accessit;

import AccessAPI.GoogleDriveAccess;
import AccessAPI.OneDriveAccess;
import ServerPackage.Account_info;
import ServerPackage.UserInfo;
import accessit.AlertBox;
import accessit.ClientForCommunication;
import accessit.FileSystem;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FileSharingWindowController
implements Initializable {
    @FXML
    private ChoiceBox<String> roleSelectChoiceBox;
    @FXML
    private Label unameSenderLabel;
    @FXML
    private Label emailIDTargetLabel;
    @FXML
    private Button submitButton;
    @FXML
    private Label unameReceiverLabel;
    @FXML
    private TextField unameTextField;
    @FXML
    private Label emailIDSenderLabel;
    @FXML
    private ChoiceBox<String> emailChoiceBox;
    @FXML
    private Label otpReceiverLabel;
    @FXML
    private TextField oTPTextField;
    private static String cid;
    private static String token;
    private static String path;
    private static String fileName;

    public static void setData(String cid1, String token1, String path1, String fileName1) {
        cid = cid1;
        token = token1;
        path = path1;
        fileName = fileName1;
    }

    public void initialize(URL url, ResourceBundle rb) {
        this.submitButton.setDisable(true);
        this.roleSelectChoiceBox.getItems().add((Object)"Sender");
        this.roleSelectChoiceBox.getItems().add((Object)"Receiver");
        this.roleSelectChoiceBox.valueProperty().addListener((ChangeListener)new ChangeListener<String>(){

            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.equalsIgnoreCase("Sender")) {
                    FileSharingWindowController.this.displaySenderRelatedItems();
                }
                if (newValue.equalsIgnoreCase("Receiver")) {
                    FileSharingWindowController.this.displayReceiverRelatedItems();
                }
            }
        });
    }

    private void hideSenderRelatedItems() {
        this.unameSenderLabel.setVisible(false);
        this.unameTextField.setVisible(false);
        this.emailIDSenderLabel.setVisible(false);
        this.emailChoiceBox.setVisible(false);
        this.submitButton.setDisable(false);
    }

    private void hideReceiverRelatedItems() {
        this.unameReceiverLabel.setVisible(false);
        this.unameTextField.setVisible(false);
        this.emailIDTargetLabel.setVisible(false);
        this.emailChoiceBox.setVisible(false);
        this.otpReceiverLabel.setVisible(false);
        this.oTPTextField.setVisible(false);
        this.submitButton.setDisable(false);
    }

    private void displaySenderRelatedItems() {
        this.unameSenderLabel.setVisible(true);
        this.unameTextField.setVisible(true);
        this.submitButton.setDisable(false);
    }

    private void displayReceiverRelatedItems() {
        this.unameReceiverLabel.setVisible(true);
        this.unameTextField.setVisible(true);
        this.emailIDTargetLabel.setVisible(true);
        this.emailChoiceBox.setVisible(true);
        this.otpReceiverLabel.setVisible(true);
        this.oTPTextField.setVisible(true);
        this.submitButton.setDisable(false);
        UserInfo ui = FileSystem.readFile();
        ArrayList<Account_info> accounts = ui.getAccountList();
        for (Account_info account : accounts) {
            String temp = null;
            switch (account.getCloudno()) {
                case 0: {
                    temp = "Dropbox";
                    break;
                }
                case 1: {
                    temp = "Googledrive";
                    break;
                }
                case 2: {
                    temp = "Onedrive";
                }
            }
            this.emailChoiceBox.getItems().add((Object)(account.getEmail() + "-" + temp));
        }
    }

    @FXML
    private void onSubmitButtonClicked(ActionEvent event) {
        UserInfo ui = FileSystem.readFile();
        if (((String)this.roleSelectChoiceBox.getValue()).equalsIgnoreCase("Sender")) {
            Random rand = new Random();
            int temp = rand.nextInt();
            AlertBox.display("OTP", "Generated OTP is: " + temp);
            ClientForCommunication.send(token, cid, ui.getUsername(), this.unameTextField.getText(), "" + temp + "", path, fileName);
        } else if (((String)this.roleSelectChoiceBox.getValue()).equalsIgnoreCase("Receiver")) {
            Map<String, Object> m;
            String temp = (String)this.emailChoiceBox.getValue();
            String tempToken = null;
            String[] split = temp.split("-");
            String email = split[0];
            String cid1 = split[1];
            System.out.println("Email: " + email + "\n" + "cid: " + cid1);
            if (cid1.equalsIgnoreCase("dropbox")) {
                tempToken = ClientForCommunication.getToken(0, email, ui.getUsername());
            } else if (cid1.equalsIgnoreCase("Googledrive")) {
                m = null;
                try {
                    m = new GoogleDriveAccess().getAccessToken(ClientForCommunication.getToken(0, email, ui.getUsername()));
                    tempToken = (String)m.get("access_token");
                }
                catch (IOException var9_11) {}
            } else if (cid1.equalsIgnoreCase("Onedrive")) {
                m = null;
                try {
                    m = new OneDriveAccess().getAccessToken(ClientForCommunication.getToken(0, email, ui.getUsername()));
                    tempToken = (String)m.get("access_token");
                }
                catch (IOException var9_12) {
                    // empty catch block
                }
            }
            String token2 = ClientForCommunication.getToken(0, email, ui.getUsername());
            ClientForCommunication.recv(tempToken, cid1, this.unameTextField.getText(), ui.getUsername(), this.oTPTextField.getText());
        }
    }

}

