/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  javafx.beans.property.ReadOnlyObjectProperty
 *  javafx.beans.value.ChangeListener
 *  javafx.beans.value.ObservableValue
 *  javafx.collections.ObservableList
 *  javafx.concurrent.Worker
 *  javafx.concurrent.Worker$State
 *  javafx.event.ActionEvent
 *  javafx.fxml.FXML
 *  javafx.fxml.Initializable
 *  javafx.scene.Scene
 *  javafx.scene.control.Button
 *  javafx.scene.control.ChoiceBox
 *  javafx.scene.control.Label
 *  javafx.scene.input.InputMethodEvent
 *  javafx.scene.web.WebEngine
 *  javafx.scene.web.WebView
 *  javafx.stage.Stage
 *  javafx.stage.Window
 */
package accessit;

import AccessAPI.AccessAPI;
import AccessAPI.DropBoxAccess;
import AccessAPI.GoogleDriveAccess;
import AccessAPI.OneDriveAccess;
import accessit.AlertBox;
import accessit.ClientForCommunication;
import java.io.PrintStream;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.Window;

public class AddAccountWindowController
implements Initializable {
    @FXML
    private ChoiceBox<String> choiceBox;
    @FXML
    private Button addButton;
    @FXML
    private Label label;
    @FXML
    private WebView ww;
    static AccessAPI accessAPIRef = null;
    static String code;

    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("In AddAccount's initialize()");
        this.choiceBox.getItems().add((Object)"Dropbox");
        this.choiceBox.getItems().add((Object)"Google Drive");
        this.choiceBox.getItems().add((Object)"One Drive");
    }

    @FXML
    private void onTextChanged(InputMethodEvent event) throws Exception {
    }

    @FXML
    private void onCloseButtonClicked(ActionEvent event) {
        ((Stage)this.addButton.getScene().getWindow()).close();
    }

    @FXML
    private void onAddButtonClicked(ActionEvent event) {
        final WebEngine weng = this.ww.getEngine();
        if (!((String)this.choiceBox.getValue()).isEmpty()) {
            this.addButton.setVisible(false);
            this.label.setVisible(false);
            this.choiceBox.setVisible(false);
            this.ww.setVisible(true);
            switch ((String)this.choiceBox.getValue()) {
                case "Google Drive": {
                    accessAPIRef = new GoogleDriveAccess();
                    break;
                }
                case "Dropbox": {
                    accessAPIRef = new DropBoxAccess();
                    break;
                }
                case "One Drive": {
                    accessAPIRef = new OneDriveAccess();
                    break;
                }
            }
            try {
                weng.load(accessAPIRef.getURL());
                weng.getLoadWorker().stateProperty().addListener((ChangeListener)new ChangeListener<Worker.State>(){

                    public void changed(ObservableValue ov, Worker.State oldState, Worker.State newState) {
                        AddAccountWindowController.code = weng.getLocation();
                        System.out.println(AddAccountWindowController.code);
                        if (AddAccountWindowController.code.startsWith("https://accessitblog.wordpress.com/") || AddAccountWindowController.code.startsWith("https://login.live.com/oauth20_desktop.srf")) {
                            try {
                                System.out.println("Checkpoint 1" + AddAccountWindowController.getCode());
                                Map<String, Object> map = AddAccountWindowController.accessAPIRef.Authenticate(AddAccountWindowController.getCode());
                                System.out.println(map);
                                String email = AddAccountWindowController.accessAPIRef.getEmail((String)map.get("access_token"));
                                if (AddAccountWindowController.accessAPIRef instanceof DropBoxAccess) {
                                    ClientForCommunication.storeDropboxInfo(map, email);
                                } else if (AddAccountWindowController.accessAPIRef instanceof GoogleDriveAccess) {
                                    ClientForCommunication.storeGDriveInfo(map, email);
                                } else if (AddAccountWindowController.accessAPIRef instanceof OneDriveAccess) {
                                    System.out.println("One Drive map: " + map);
                                    ClientForCommunication.storeOneDriveInfo(map, email);
                                }
                                AddAccountWindowController.this.close();
                            }
                            catch (Exception ex) {
                                Logger.getLogger(AddAccountWindowController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                });
            }
            catch (Exception ex) {
                Logger.getLogger(AddAccountWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void close() {
        AlertBox.display("SUCCESS", "Account is linked successfully");
        ((Stage)this.ww.getScene().getWindow()).close();
    }

    public static String getCode() {
        return code;
    }

    public static AccessAPI getRef() {
        return accessAPIRef;
    }

}

