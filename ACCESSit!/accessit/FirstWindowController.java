/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  javafx.collections.ObservableList
 *  javafx.fxml.FXML
 *  javafx.fxml.FXMLLoader
 *  javafx.fxml.Initializable
 *  javafx.scene.Parent
 *  javafx.scene.Scene
 *  javafx.scene.control.Label
 *  javafx.scene.image.Image
 *  javafx.scene.image.ImageView
 *  javafx.stage.Stage
 */
package accessit;

import ServerPackage.UserInfo;
import accessit.ACCESSit;
import accessit.AlertBox;
import accessit.ClientForCommunication;
import accessit.FileSystem;
import accessit.LogInWindowController;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class FirstWindowController
implements Initializable {
    static Stage window1;
    public static boolean flag;
    @FXML
    private ImageView logoImageView;
    @FXML
    private Label label;
    File userInfo;

    public void initialize(URL url, ResourceBundle rb) {
        this.label.setVisible(true);
        System.out.println("Checkpoint 1: accessit.FirstWindowController.initialize()");
    }

    public void setUp() {
        this.userInfo = new File(FileSystem.dirname + File.separatorChar + "ACCESSit" + File.separatorChar + "UserInfo.ser");
        System.out.println("File exists:" + this.userInfo.exists());
        flag = this.userInfo.exists();
        if (this.userInfo.exists()) {
            UserInfo ui = FileSystem.readFile();
            System.out.println("File read");
            System.out.println("Is file null: " + ui);
            int a = ClientForCommunication.logInCommunication(ui.getUsername(), ui.getPass());
            System.out.println("Read integer = " + a);
            Parent root = null;
            if (a == 0) {
                flag = false;
                ui = ClientForCommunication.getUserInfo(ui.getUsername());
                FileSystem.create(ui);
                try {
                    System.out.println("Before loading HomeWindow.fxml");
                    root = (Parent)FXMLLoader.load((URL)this.getClass().getResource("HomeWindow.fxml"));
                    System.out.println("After loading HomeWindow.fxml");
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("ACCESSit!");
                    stage.getIcons().add((Object)new Image("accessit/Logo.jpg"));
                    stage.setResizable(false);
                    stage.show();
                }
                catch (IOException ex) {
                    Logger.getLogger(LogInWindowController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Object root1 = null;
                flag = false;
                try {
                    System.out.println("Loading log in window");
                    root = (Parent)FXMLLoader.load((URL)this.getClass().getResource("LogInWindow.fxml"));
                    System.out.println("Log in window loaded into root");
                    Scene scene1 = new Scene(root);
                    System.out.println("Scene set");
                    AlertBox.display("ACCESSit - LogIn", "Sorry! Please enter your credentials again...");
                    FirstWindowController.show(scene1);
                }
                catch (IOException ex) {
                    Logger.getLogger(ACCESSit.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Exception Occured");
                }
            }
        } else {
            Parent root = null;
            try {
                System.out.println("File exists() = false");
                System.out.println("Loading log in window");
                root = (Parent)FXMLLoader.load((URL)this.getClass().getResource("LogInWindow.fxml"));
                System.out.println("Log in window loaded into root");
                Scene scene1 = new Scene(root);
                System.out.println("Scene set");
                FirstWindowController.show(scene1);
            }
            catch (IOException ex) {
                Logger.getLogger(ACCESSit.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Exception Occured");
            }
        }
    }

    public static void show(Scene scene1) {
        window1 = new Stage();
        window1.setScene(scene1);
        window1.setTitle("ACCESSit! - LogIn window");
        window1.getIcons().add((Object)new Image("accessit/Logo.jpg"));
        window1.show();
    }

    public static void close() {
        window1.close();
    }
}

