/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  javafx.collections.ObservableList
 *  javafx.event.ActionEvent
 *  javafx.fxml.FXML
 *  javafx.fxml.Initializable
 *  javafx.scene.control.Label
 *  javafx.scene.control.TextField
 *  javafx.scene.image.Image
 *  javafx.scene.image.ImageView
 *  javafx.scene.paint.Paint
 *  javafx.stage.FileChooser
 *  javafx.stage.FileChooser$ExtensionFilter
 *  javafx.stage.Stage
 *  javafx.stage.Window
 */
package accessit;

import ServerPackage.UserInfo;
import accessit.ClientForCommunication;
import accessit.FileSystem;
import accessit.HomeWindowController;
import java.io.File;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class EditProfileWindowController
implements Initializable {
    @FXML
    private ImageView profilePictureView;
    @FXML
    private Label invalidlabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;

    public void initialize(URL url, ResourceBundle rb) {
        UserInfo ui = FileSystem.readFile();
        this.usernameLabel.setText(ui.getUsername());
        this.emailLabel.setText(ui.getEmail());
    }

    @FXML
    private void onUploadButtonClicked(ActionEvent event) {
        Stage s = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll((Object[])new FileChooser.ExtensionFilter[]{new FileChooser.ExtensionFilter("Image Files", new String[]{"*.png", "*.jpg", "*.gif", "*.jpeg"})});
        File f = fileChooser.showOpenDialog((Window)s);
        System.out.println("***********************************************");
        System.out.println(f);
        System.out.println("***********************************************");
        System.out.println(f.getAbsolutePath());
        System.out.println("***********************************************");
        File dest = new File(FileSystem.dirname + "ACCESSit" + File.separatorChar + "ProfilePicture.jpg");
        FileSystem.copyFileUsingFileStreams(f, dest);
        Image i = new Image("File:" + File.separatorChar + f.toString());
        this.profilePictureView.setImage(i);
        this.invalidlabel.setText("Restart the app to see change in profile picture");
        this.invalidlabel.setTextFill(Paint.valueOf((String)"Red"));
    }

    @FXML
    private void submitButtonClicked(ActionEvent event) {
        UserInfo ui = FileSystem.readFile();
        if (!this.firstNameTextField.getText().isEmpty() && !this.lastNameTextField.getText().isEmpty()) {
            ui.setFirstName(this.firstNameTextField.getText());
            ui.setLastName(this.lastNameTextField.getText());
            FileSystem.createFile(ui);
            boolean b = ClientForCommunication.editInfo(ui);
            if (b) {
                HomeWindowController.closeStage();
            } else {
                this.invalidlabel.setText("Sorry! Exception occured");
                this.invalidlabel.setTextFill(Paint.valueOf((String)"Red"));
            }
        } else {
            this.invalidlabel.setText("Enter data into all fields");
            this.invalidlabel.setTextFill(Paint.valueOf((String)"Red"));
        }
    }
}

