/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  javafx.collections.ObservableList
 *  javafx.event.ActionEvent
 *  javafx.fxml.FXML
 *  javafx.fxml.FXMLLoader
 *  javafx.fxml.Initializable
 *  javafx.scene.Cursor
 *  javafx.scene.Parent
 *  javafx.scene.Scene
 *  javafx.scene.control.Button
 *  javafx.scene.control.Label
 *  javafx.scene.control.PasswordField
 *  javafx.scene.control.TextField
 *  javafx.scene.image.Image
 *  javafx.scene.layout.AnchorPane
 *  javafx.scene.paint.Paint
 *  javafx.stage.DirectoryChooser
 *  javafx.stage.Stage
 *  javafx.stage.Window
 */
package accessit;

import ServerPackage.UserInfo;
import accessit.ACCESSit;
import accessit.ClientForCommunication;
import accessit.FileSystem;
import accessit.FirstWindowController;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class LogInWindowController
implements Initializable {
    private static Stage stage;
    ArrayList<File> ar;
    @FXML
    private TextField logInTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private Label invalidLabel;
    @FXML
    private AnchorPane infoLabel;
    @FXML
    private Button resetPasswordButton;
    public static String filePath;

    public static String getHomeDirectory() {
        return FileSystem.getHomeDirectory();
    }

    public void initialize(URL url, ResourceBundle rb) {
    }

    public static void closeStage() {
        if (FirstWindowController.flag) {
            FirstWindowController.close();
        } else {
            stage.close();
        }
    }

    @FXML
    private void onLogInButtonClicked(ActionEvent event) {
        this.logInTextField.getScene().setCursor(Cursor.WAIT);
        boolean isUsernameValid = false;
        boolean isCombinationValid = false;
        stage = new Stage();
        Parent root = null;
        int a = ClientForCommunication.logInCommunication(this.logInTextField.getText(), this.passwordTextField.getText());
        System.out.println("Read integer = " + a);
        switch (a) {
            case 0: {
                UserInfo ui = ClientForCommunication.getUserInfo(this.logInTextField.getText());
                Stage stage = new Stage();
                DirectoryChooser directoryChooser = new DirectoryChooser();
                directoryChooser.setTitle("Choose Home Directory!");
                File f = directoryChooser.showDialog((Window)stage);
                filePath = f.toString();
                FileSystem.setHomeDirectory(filePath);
                System.out.println("File address: " + f.toString());
                System.out.println("Received as UserInfo from server: " + ui);
                FileSystem.create(ui);
                try {
                    System.out.println("Before loading HomeWindow.fxml");
                    root = (Parent)FXMLLoader.load((URL)this.getClass().getResource("HomeWindow.fxml"));
                    System.out.println("After loading HomeWindow.fxml");
                }
                catch (IOException ex) {
                    Logger.getLogger(LogInWindowController.class.getName()).log(Level.SEVERE, null, ex);
                }
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("ACCESSit!");
                stage.show();
                ACCESSit.closeStage();
                break;
            }
            case 1: {
                this.invalidLabel.setText("Username is invalid");
                this.invalidLabel.setTextFill(Paint.valueOf((String)"Red"));
                this.invalidLabel.setVisible(true);
                break;
            }
            case 2: {
                this.invalidLabel.setText("Combination of username and password is invalid");
                this.invalidLabel.setTextFill(Paint.valueOf((String)"Red"));
                this.invalidLabel.setVisible(true);
                break;
            }
            case 3: {
                this.invalidLabel.setText("Sorry! Server failed to process your request. Exception occured");
                this.invalidLabel.setTextFill(Paint.valueOf((String)"Red"));
                this.invalidLabel.setVisible(true);
            }
        }
        this.logInTextField.getScene().setCursor(Cursor.DEFAULT);
    }

    @FXML
    private void onSignUpButtonClicked(ActionEvent event) {
        stage = new Stage();
        Parent root = null;
        try {
            root = (Parent)FXMLLoader.load((URL)this.getClass().getResource("SignUpWindow.fxml"));
        }
        catch (IOException ex) {
            Logger.getLogger(LogInWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.getIcons().add((Object)new Image("accessit/Logo.jpg"));
        stage.show();
        ACCESSit.closeStage();
    }

    @FXML
    private void onResetPasswordButton(ActionEvent event) {
        stage = new Stage();
        Parent root = null;
        try {
            root = (Parent)FXMLLoader.load((URL)this.getClass().getResource("ResetPasswordWindow.fxml"));
        }
        catch (IOException ex) {
            Logger.getLogger(LogInWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.getIcons().add((Object)new Image("accessit/Logo.jpg"));
        stage.show();
        ACCESSit.closeStage();
    }
}

