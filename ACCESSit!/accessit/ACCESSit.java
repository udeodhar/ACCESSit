/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  javafx.application.Application
 *  javafx.collections.ObservableList
 *  javafx.fxml.FXMLLoader
 *  javafx.scene.Parent
 *  javafx.scene.Scene
 *  javafx.scene.image.Image
 *  javafx.stage.Stage
 *  javafx.stage.StageStyle
 */
package accessit;

import accessit.FirstWindowController;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ACCESSit
extends Application {
    static Stage window = null;
    static Scene scene;
    static Scene scene1;
    static Stage window1;

    public static void main(String[] args) {
        ACCESSit.launch((String[])args);
    }

    public void start(Stage stage) throws InterruptedException {
        window = stage;
        Parent root = null;
        try {
            root = (Parent)FXMLLoader.load((URL)this.getClass().getResource("FirstWindow.fxml"));
            scene = new Scene(root);
            window.initStyle(StageStyle.UNDECORATED);
            window.setScene(scene);
            window.getIcons().add((Object)new Image("accessit/Logo.jpg"));
            window.show();
            new FirstWindowController().setUp();
        }
        catch (IOException ex) {
            Logger.getLogger(ACCESSit.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("After showing first window");
        try {
            System.out.println("Loading log in window");
            root = (Parent)FXMLLoader.load((URL)this.getClass().getResource("LogInWindow.fxml"));
            System.out.println("Log in window loaded into root");
            scene1 = new Scene(root);
            System.out.println("Scene set");
        }
        catch (IOException ex) {
            Logger.getLogger(ACCESSit.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!FirstWindowController.flag) {
            window.close();
        }
    }

    public static void showStage() {
        FirstWindowController.show(scene1);
    }

    public static void closeStage() {
        FirstWindowController.close();
    }
}

