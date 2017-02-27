/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  javafx.collections.ObservableList
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.geometry.Pos
 *  javafx.scene.Node
 *  javafx.scene.Parent
 *  javafx.scene.Scene
 *  javafx.scene.control.Button
 *  javafx.scene.control.Label
 *  javafx.scene.control.TextField
 *  javafx.scene.layout.VBox
 *  javafx.stage.Modality
 *  javafx.stage.Stage
 */
package accessit;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class GetStringFromNewWindow {
    static String str;

    public static String display(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250.0);
        Label label = new Label();
        label.setText(message);
        TextField tf = new TextField();
        Button addButton = new Button("ADD");
        addButton.setOnAction(e -> {
            str = tf.getText();
            window.close();
        }
        );
        Button cancelButton = new Button("CANCEL");
        cancelButton.setOnAction(e -> {
            str = null;
            window.close();
        }
        );
        VBox layout = new VBox(10.0);
        layout.getChildren().addAll((Object[])new Node[]{label, tf, addButton, cancelButton});
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene((Parent)layout);
        window.setScene(scene);
        window.showAndWait();
        return str;
    }
}

