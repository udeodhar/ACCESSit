/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  javafx.fxml.FXML
 *  javafx.fxml.Initializable
 *  javafx.scene.control.Label
 *  javafx.scene.image.Image
 *  javafx.scene.image.ImageView
 */
package accessit;

import ServerPackage.UserInfo;
import accessit.FileSystem;
import java.io.File;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ViewProfileWindowController
implements Initializable {
    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private ImageView profiePicture;

    public void initialize(URL url, ResourceBundle rb) {
        UserInfo ui = FileSystem.readFile();
        this.firstNameLabel.setText(ui.getFirstName());
        this.lastNameLabel.setText(ui.getLastName());
        this.emailLabel.setText(ui.getEmail());
        this.usernameLabel.setText(ui.getUsername());
        System.out.println("Profile picture path:");
        System.out.println(System.getProperty("java.io.tmpdir") + "ACCESSit" + File.separatorChar + "ProfilePicture.jpg");
        this.profiePicture.setImage(new Image("File:" + File.separatorChar + System.getProperty("java.io.tmpdir") + "ACCESSit" + File.separatorChar + "ProfilePicture.jpg"));
    }
}

