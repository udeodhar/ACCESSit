/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  javafx.application.Platform
 *  javafx.beans.property.DoubleProperty
 *  javafx.beans.property.ReadOnlyDoubleProperty
 *  javafx.beans.property.ReadOnlyObjectProperty
 *  javafx.beans.value.ChangeListener
 *  javafx.beans.value.ObservableValue
 *  javafx.collections.FXCollections
 *  javafx.collections.ObservableList
 *  javafx.event.ActionEvent
 *  javafx.event.Event
 *  javafx.event.EventHandler
 *  javafx.fxml.FXML
 *  javafx.fxml.FXMLLoader
 *  javafx.fxml.Initializable
 *  javafx.scene.Node
 *  javafx.scene.Parent
 *  javafx.scene.Scene
 *  javafx.scene.control.Button
 *  javafx.scene.control.Label
 *  javafx.scene.control.ListView
 *  javafx.scene.control.MenuItem
 *  javafx.scene.control.MultipleSelectionModel
 *  javafx.scene.control.ProgressIndicator
 *  javafx.scene.control.RadioButton
 *  javafx.scene.control.TextField
 *  javafx.scene.control.Toggle
 *  javafx.scene.control.ToggleGroup
 *  javafx.scene.image.Image
 *  javafx.scene.image.ImageView
 *  javafx.scene.input.MouseEvent
 *  javafx.scene.layout.HBox
 *  javafx.scene.layout.VBox
 *  javafx.scene.paint.Paint
 *  javafx.stage.FileChooser
 *  javafx.stage.Modality
 *  javafx.stage.Stage
 *  javafx.stage.StageStyle
 *  javafx.stage.Window
 *  javafx.stage.WindowEvent
 *  javax.json.JsonObject
 */
package accessit;

import AccessAPI.AccessAPI;
import AccessAPI.DropBoxAccess;
import AccessAPI.GoogleDriveAccess;
import AccessAPI.OneDriveAccess;
import Helper.MetaData;
import Helper.TaskDownloader;
import ServerPackage.Account_info;
import ServerPackage.UserInfo;
import accessit.ACCESSit;
import accessit.AddAccountWindowController;
import accessit.AlertBox;
import accessit.ClientForCommunication;
import accessit.FeatureAPI;
import accessit.FileSharingWindowController;
import accessit.FileSystem;
import accessit.GetPasswordFromWindow;
import accessit.GetStringFromNewWindow;
import accessit.JSONHandler;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javax.json.JsonObject;

public class HomeWindowController
implements Initializable {
    String token;
    ArrayList<String> ar;
    Map<String, String> gmap;
    static ArrayList<String> path;
    static ArrayList<String> gpath;
    private static Stage s;
    final ToggleGroup group = new ToggleGroup();
    ToggleGroup group2 = new ToggleGroup();
    static String email;
    public static UserInfo ui;
    @FXML
    private Label welcomeLabel;
    @FXML
    private ImageView profilePictureImageView;
    @FXML
    private Label usernameLabel;
    @FXML
    private ListView<HBox> listView;
    @FXML
    private RadioButton dropboxRadioButton;
    @FXML
    private RadioButton googledriveRadioButton;
    @FXML
    private RadioButton onedriveRadioButton;
    @FXML
    private TextField searchTextField;
    @FXML
    private VBox leftPane;
    @FXML
    private RadioButton noneRadioButton;
    @FXML
    private Button downloadButton;
    @FXML
    private Button addFolderButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button shareButton;
    @FXML
    private Button shareLinkButton;
    @FXML
    private Button uploadButton;
    AccessAPI accessRef;
    static int cloudNum;
    RadioButton rbTemp;
    JsonObject obj;
    ArrayList<Account_info> ai;
    ArrayList<Account_info> ai12 = new ArrayList();
    @FXML
    private Button pauseButton;
    @FXML
    private Button resumeButton;
    @FXML
    private VBox pIPlane;
    TaskDownloader td;
    private static boolean resetPasswordFlag;
    @FXML
    private MenuItem about;
    private static Stage s2;

    public static void closeResetWindow() {
        s2.close();
    }

    public static boolean getFlag() {
        return resetPasswordFlag;
    }

    public void initialize(URL url, ResourceBundle rb) {
        path = new ArrayList();
        path.add("");
        gpath = new ArrayList();
        gpath.add("root");
        ui = FileSystem.readFile();
        System.out.println("UI: " + ui);
        String str = ui.getFirstName();
        this.welcomeLabel.setText("Welcome " + str);
        this.usernameLabel.setText(ui.getUsername());
        File dest = new File(FileSystem.dirname + "ACCESSit" + File.separatorChar + "ProfilePicture.jpg");
        Image i = new Image("File:/" + dest.toString());
        this.profilePictureImageView.setImage(i);
        this.dropboxRadioButton.setToggleGroup(this.group);
        this.googledriveRadioButton.setToggleGroup(this.group);
        this.onedriveRadioButton.setToggleGroup(this.group);
        this.noneRadioButton.setToggleGroup(this.group);
        this.noneRadioButton.setSelected(true);
        this.group.selectedToggleProperty().addListener((ov, t, t1) -> {
            boolean flag;
            String cloudName = this.extractName(t1.toString());
            System.out.println(this.extractName(t1.toString()));
            if (cloudName.equalsIgnoreCase("dropbox")) {
                cloudNum = 0;
                this.accessRef = new DropBoxAccess();
                this.addFolderButton.setDisable(false);
                flag = true;
                while (flag) {
                    try {
                        this.leftPane.getChildren().remove(11);
                    }
                    catch (Exception e) {
                        flag = false;
                    }
                }
                this.ai = ui.getAccountList();
                for (Account_info ai1 : this.ai) {
                    if (ai1.getCloudno() != 0) continue;
                    this.rbTemp = new RadioButton();
                    this.rbTemp.setToggleGroup(this.group2);
                    this.rbTemp.setText(ai1.getEmail());
                    this.rbTemp.setSelected(true);
                    this.leftPane.getChildren().add((Object)this.rbTemp);
                    this.ai12.add(ai1);
                }
                this.ai12.clear();
                System.out.println("Is array list empty: " + this.ai12.isEmpty());
            } else if (cloudName.equalsIgnoreCase("googledrive")) {
                this.addFolderButton.setDisable(true);
                flag = true;
                while (flag) {
                    try {
                        this.leftPane.getChildren().remove(11);
                    }
                    catch (Exception e) {
                        flag = false;
                    }
                }
                cloudNum = 1;
                this.accessRef = new GoogleDriveAccess();
                this.ai = ui.getAccountList();
                for (Account_info ai1 : this.ai) {
                    if (ai1.getCloudno() != 1) continue;
                    this.rbTemp = new RadioButton();
                    this.rbTemp.setToggleGroup(this.group2);
                    this.rbTemp.setText(ai1.getEmail());
                    this.rbTemp.setSelected(true);
                    this.leftPane.getChildren().add((Object)this.rbTemp);
                    this.ai12.add(ai1);
                }
                this.ai12.clear();
                System.out.println("Is array list empty: " + this.ai12.isEmpty());
                System.out.println("Is array list empty: " + this.ai12.isEmpty());
            } else if (cloudName.equalsIgnoreCase("onedrive")) {
                this.addFolderButton.setDisable(true);
                this.listView.getItems().clear();
                flag = true;
                while (flag) {
                    try {
                        this.leftPane.getChildren().remove(11);
                    }
                    catch (Exception e) {
                        flag = false;
                    }
                }
                cloudNum = 2;
                this.accessRef = new OneDriveAccess();
                this.ai = ui.getAccountList();
                for (Account_info ai1 : this.ai) {
                    if (ai1.getCloudno() != 2) continue;
                    this.rbTemp = new RadioButton();
                    this.rbTemp.setToggleGroup(this.group2);
                    this.rbTemp.setText(ai1.getEmail());
                    this.rbTemp.setSelected(true);
                    this.leftPane.getChildren().add((Object)this.rbTemp);
                    this.ai12.add(ai1);
                }
                this.ai12.clear();
            }
            if (cloudName.equalsIgnoreCase("none")) {
                this.addFolderButton.setDisable(true);
                this.listView.getItems().clear();
                flag = true;
                while (flag) {
                    try {
                        this.leftPane.getChildren().remove(11);
                    }
                    catch (Exception e) {
                        flag = false;
                    }
                }
                this.refresh();
            }
        }
        );
        this.group2.selectedToggleProperty().addListener((ov, t, t1) -> {
            path.clear();
            path.add("");
            gpath = new ArrayList();
            gpath.add("root");
            System.out.println("In 2nd observer");
            System.out.println(this.extractName(this.group2.getSelectedToggle().toString()));
            email = this.extractName(this.group2.getSelectedToggle().toString());
            this.token = ClientForCommunication.getToken(cloudNum, email, ui.getUsername());
            System.out.println("Token: " + this.token);
            this.ai12.clear();
            this.ai12.add(new Account_info(email, cloudNum));
            this.setArrayList(cloudNum);
            this.refresh();
        }
        );
        this.listView.setOnMouseClicked(click -> {
            if (click.getClickCount() == 2) {
                HBox currentItemSelected = (HBox)this.listView.getSelectionModel().getSelectedItem();
                System.out.println(((Node)currentItemSelected.getChildren().get(0)).toString());
                String temp = this.extractName(((Node)currentItemSelected.getChildren().get(1)).toString());
                System.out.println(temp);
                System.out.println(((Node)currentItemSelected.getChildren().get(2)).toString());
                if (this.extractName(((Node)currentItemSelected.getChildren().get(2)).toString()).equals("fo")) {
                    if (!this.extractName(((Node)currentItemSelected.getChildren().get(1)).toString()).equals("..")) {
                        System.out.println("Previous path: " + this.getPath() + "/" + temp);
                        if (cloudNum == 0) {
                            System.out.println("Token: " + this.token);
                            this.obj = this.accessRef.getFilesFromPath(this.token, this.getPath() + "/" + temp);
                        } else if (cloudNum == 1) {
                            String temp1 = this.extractName(((Node)currentItemSelected.getChildren().get(3)).toString());
                            System.out.println(temp1);
                            Map<String, Object> m = null;
                            try {
                                m = new GoogleDriveAccess().getAccessToken(this.token);
                            }
                            catch (IOException ex) {
                                Logger.getLogger(HomeWindowController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            this.obj = this.accessRef.getFilesFromPath((String)m.get("access_token"), temp1);
                            gpath.add(temp1);
                        } else if (cloudNum == 2) {
                            Map<String, Object> m = null;
                            try {
                                m = new OneDriveAccess().getAccessToken(this.token);
                            }
                            catch (IOException ex) {
                                Logger.getLogger(HomeWindowController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            this.obj = this.accessRef.getFilesFromPath((String)m.get("access_token"), temp);
                        }
                        System.out.println((Object)this.obj);
                        path.add("/" + temp);
                        System.out.println(path);
                        System.out.println("Received object");
                        this.listView.setItems(null);
                        this.displayFolders(cloudNum);
                        this.displayFiles(cloudNum);
                        System.out.println("Calls to display files and folders executed");
                    } else if (path.size() > 1) {
                        System.out.println("Path size: " + path.size());
                        path.remove(path.size() - 1);
                        gpath.remove(gpath.size() - 1);
                        System.out.println("Path after back button is pressed: " + this.getPath());
                        Map<String, Object> m = null;
                        if (cloudNum == 0) {
                            this.obj = this.accessRef.getFilesFromPath(this.token, this.getPath());
                        }
                        if (cloudNum == 1) {
                            try {
                                m = new GoogleDriveAccess().getAccessToken(this.token);
                            }
                            catch (IOException ex) {
                                Logger.getLogger(HomeWindowController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            this.obj = this.accessRef.getFilesFromPath((String)m.get("access_token"), gpath.get(gpath.size() - 1));
                        }
                        if (cloudNum == 2) {
                            try {
                                m = new OneDriveAccess().getAccessToken(this.token);
                            }
                            catch (IOException ex) {
                                Logger.getLogger(HomeWindowController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            this.obj = this.accessRef.getFilesFromPath((String)m.get("access_token"), this.getPath());
                        }
                        System.out.println((Object)this.obj);
                        System.out.println(path);
                        System.out.println("Received object");
                        this.listView.setItems(null);
                        this.displayFolders(cloudNum);
                        this.displayFiles(cloudNum);
                        System.out.println("Calls to display files and folders executed");
                    }
                    this.refresh();
                }
            }
            if (click.getClickCount() == 1) {
                // empty if block
            }
        }
        );
    }

    public void setArrayList(int cloudNum) {
        if (this.ai12.isEmpty()) {
            System.out.println("Removing all elements...");
            this.listView.getItems().clear();
            this.refresh();
        } else {
            Object m;
            if (cloudNum == 0) {
                this.obj = this.accessRef.getFilesFromPath(this.token, "");
            } else if (cloudNum == 1) {
                m = null;
                try {
                    m = new GoogleDriveAccess().getAccessToken(this.token);
                    this.obj = this.accessRef.getFilesFromPath((String)m.get("access_token"), "root");
                }
                catch (IOException ex) {
                    Logger.getLogger(HomeWindowController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (cloudNum == 2) {
                m = null;
                try {
                    m = this.accessRef.getAccessToken(this.token);
                    System.out.println("Access Token is:" + (String)m.get("access_token"));
                    this.obj = this.accessRef.getFilesFromPath((String)m.get("access_token"), "/");
                }
                catch (IOException ex) {
                    Logger.getLogger(HomeWindowController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.out.println("Got JsonObject");
            System.out.println((Object)this.obj);
            for (Account_info ai1 : this.ai12) {
                System.out.println("ai1:" + ai1);
                System.out.println("Email: " + ai1.getEmail());
                System.out.println("Username: " + ui.getUsername());
                System.out.println("Token:" + this.token);
                this.displayFolders(cloudNum);
                this.displayFiles(cloudNum);
            }
        }
    }

    public void displayFiles(int cloudNum) {
        if (cloudNum == 1) {
            this.gmap = (Map)JSONHandler.extractFiles(this.obj, cloudNum);
            for (Map.Entry<String, String> entry : this.gmap.entrySet()) {
                HBox h = new HBox();
                h.getChildren().add((Object)new ImageView("accessit/FileIcon.png"));
                h.getChildren().add((Object)new Label(entry.getValue()));
                Label l = new Label("fi");
                l.setVisible(false);
                h.getChildren().add((Object)l);
                Label ll = new Label(entry.getKey());
                ll.setVisible(false);
                h.getChildren().add((Object)ll);
                this.listView.getItems().add((Object)h);
            }
        } else {
            this.ar = (ArrayList)JSONHandler.extractFiles(this.obj, cloudNum);
            ObservableList observableList = FXCollections.observableArrayList(this.ar);
            for (String str1 : this.ar) {
                HBox h = new HBox();
                h.getChildren().add((Object)new ImageView("accessit/FileIcon.png"));
                h.getChildren().add((Object)new Label(str1));
                Label l = new Label("fi");
                l.setVisible(false);
                h.getChildren().add((Object)l);
                this.listView.getItems().add((Object)h);
            }
        }
    }

    public void displayFolders(int cloudNum) {
        if (cloudNum == 1) {
            System.out.println("Before extract folder");
            this.gmap = (Map)JSONHandler.extractFolder(this.obj, cloudNum);
            System.out.println("After extract folder");
            HBox h1 = new HBox();
            h1.getChildren().add((Object)new ImageView("accessit/FolderIcon.jpg"));
            h1.getChildren().add((Object)new Label(".."));
            Label l1 = new Label("fo");
            l1.setVisible(false);
            h1.getChildren().add((Object)l1);
            ObservableList obslist = FXCollections.observableArrayList((Object[])new HBox[]{h1});
            this.listView.setItems(obslist);
            for (Map.Entry<String, String> entry : this.gmap.entrySet()) {
                HBox h = new HBox();
                h.getChildren().add((Object)new ImageView("accessit/FolderIcon.jpg"));
                System.out.println("entry.getValue(): " + entry.getValue());
                h.getChildren().add((Object)new Label(entry.getValue()));
                Label l = new Label("fo");
                l.setVisible(false);
                h.getChildren().add((Object)l);
                Label ll = new Label(entry.getKey());
                ll.setVisible(false);
                h.getChildren().add((Object)ll);
                this.listView.getItems().add((Object)h);
            }
        } else {
            System.out.println("Before extract folder");
            this.ar = (ArrayList)JSONHandler.extractFolder(this.obj, cloudNum);
            System.out.println("After extract folder");
            HBox h1 = new HBox();
            h1.getChildren().add((Object)new ImageView("accessit/FolderIcon.jpg"));
            h1.getChildren().add((Object)new Label(".."));
            Label l1 = new Label("fo");
            l1.setVisible(false);
            h1.getChildren().add((Object)l1);
            ObservableList obslist = FXCollections.observableArrayList((Object[])new HBox[]{h1});
            this.listView.setItems(obslist);
            for (String str1 : this.ar) {
                HBox h = new HBox();
                h.getChildren().add((Object)new ImageView("accessit/FolderIcon.jpg"));
                h.getChildren().add((Object)new Label(str1));
                Label l = new Label("fo");
                l.setVisible(false);
                h.getChildren().add((Object)l);
                this.listView.getItems().add((Object)h);
            }
        }
    }

    @FXML
    private void onSearchButtonClicked(ActionEvent event) {
        if (!this.searchTextField.getText().isEmpty()) {
            ArrayList<String> dropboxTokenList = new ArrayList<String>();
            ArrayList<String> oneDriveTokenList = new ArrayList<String>();
            ArrayList<String> googleDriveTokenList = new ArrayList<String>();
            ArrayList accountList = new ArrayList();
            UserInfo newUI = FileSystem.readFile();
            accountList = newUI.getAccountList();
            for (Object accountListTemp : accountList) {
                String t;
                if (accountListTemp.getCloudno() == 0) {
                    t = ClientForCommunication.getToken(0, accountListTemp.getEmail(), ui.getUsername());
                    dropboxTokenList.add(t);
                }
                if (accountListTemp.getCloudno() == 1) {
                    t = ClientForCommunication.getToken(1, accountListTemp.getEmail(), ui.getUsername());
                    try {
                        Map<String, Object> token1 = new GoogleDriveAccess().getAccessToken(t);
                        googleDriveTokenList.add((String)token1.get("access_token"));
                    }
                    catch (IOException ex) {
                        Logger.getLogger(HomeWindowController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (accountListTemp.getCloudno() != 2) continue;
                t = ClientForCommunication.getToken(2, accountListTemp.getEmail(), ui.getUsername());
                try {
                    Map<String, Object> token1 = new OneDriveAccess().getAccessToken(t);
                    oneDriveTokenList.add((String)token1.get("access_token"));
                }
                catch (IOException ex) {
                    Logger.getLogger(HomeWindowController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.out.println("==========================================Tokens==========================================");
            System.out.println("Google");
            System.out.println(googleDriveTokenList);
            System.out.println("Dropbox");
            System.out.println(dropboxTokenList);
            System.out.println("One Drive");
            System.out.println(oneDriveTokenList);
            ArrayList<Map<String, String>> result = FeatureAPI.Search(googleDriveTokenList, oneDriveTokenList, dropboxTokenList, this.searchTextField.getText());
            this.listView.getItems().clear();
            for (Map m : result) {
                Label l;
                if (((String)m.get("cid")).equalsIgnoreCase("0")) {
                    l = new Label((String)m.get("name") + "\n" + "Path: " + (String)m.get("path") + "\n" + "Email: " + (String)m.get("email"));
                    l.setTextFill(Paint.valueOf((String)"Blue"));
                    this.listView.getItems().add((Object)new HBox(new Node[]{l}));
                    continue;
                }
                if (((String)m.get("cid")).equalsIgnoreCase("1")) {
                    l = new Label((String)m.get("name") + "\n" + "Path: " + (String)m.get("path") + "\n" + "Email: " + (String)m.get("email"));
                    l.setTextFill(Paint.valueOf((String)"Green"));
                    this.listView.getItems().add((Object)new HBox(new Node[]{l}));
                    continue;
                }
                if (!((String)m.get("cid")).equalsIgnoreCase("2")) continue;
                l = new Label((String)m.get("name") + "\n" + "Path: " + (String)m.get("path") + "\n" + "Email: " + (String)m.get("email"));
                l.setTextFill(Paint.valueOf((String)"Red"));
                this.listView.getItems().add((Object)new HBox(new Node[]{l}));
            }
            this.refresh();
        }
    }

    @FXML
    private void onShareButtonClicked(ActionEvent event) {
        String tempToken = null;
        String filename = null;
        try {
            Map<String, Object> m;
            filename = this.extractName(((Node)((HBox)this.listView.getSelectionModel().getSelectedItem()).getChildren().get(1)).toString());
            if (cloudNum == 0) {
                tempToken = this.token;
            } else if (cloudNum == 1) {
                m = null;
                try {
                    m = new GoogleDriveAccess().getAccessToken(this.token);
                    tempToken = (String)m.get("access_token");
                }
                catch (IOException ex) {
                    Logger.getLogger(HomeWindowController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (cloudNum == 2) {
                m = null;
                try {
                    m = this.accessRef.getAccessToken(this.token);
                    tempToken = (String)m.get("access_token");
                }
                catch (IOException ex) {
                    Logger.getLogger(HomeWindowController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        catch (NullPointerException m) {
            // empty catch block
        }
        String temp = null;
        switch (cloudNum) {
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
        FileSharingWindowController.setData(temp, tempToken, this.getPath() + "/" + filename, filename);
        Stage s = new Stage();
        Parent root = null;
        try {
            root = (Parent)FXMLLoader.load((URL)this.getClass().getResource("FileSharingWindow.fxml"));
        }
        catch (IOException ex) {
            Logger.getLogger(HomeWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        s.setScene(scene);
        s.initModality(Modality.APPLICATION_MODAL);
        s.initStyle(StageStyle.UTILITY);
        s.setResizable(false);
        s.setTitle("Share File");
        s.setOnCloseRequest(e -> {
            this.closeStage1();
        }
        );
        s.showAndWait();
    }

    @FXML
    private void onDownloadButtonClicked(ActionEvent event) {
        Map<String, Object> m;
        String tempToken = null;
        Object fos = null;
        String filename = this.extractName(((Node)((HBox)this.listView.getSelectionModel().getSelectedItem()).getChildren().get(1)).toString());
        ProgressIndicator pi = new ProgressIndicator(0.0);
        HBox htemp = new HBox();
        htemp.getChildren().add((Object)pi);
        htemp.getChildren().add((Object)new Label(filename));
        this.pIPlane.getChildren().add((Object)htemp);
        if (cloudNum == 0) {
            tempToken = this.token;
        } else if (cloudNum == 1) {
            m = null;
            try {
                m = this.accessRef.getAccessToken(this.token);
                tempToken = (String)m.get("access_token");
            }
            catch (IOException ex) {
                Logger.getLogger(HomeWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (cloudNum == 2) {
            m = null;
            try {
                m = this.accessRef.getAccessToken(this.token);
                tempToken = (String)m.get("access_token");
            }
            catch (IOException ex) {
                Logger.getLogger(HomeWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (cloudNum == 1) {
            String fileID = this.extractName(((Node)((HBox)this.listView.getSelectionModel().getSelectedItem()).getChildren().get(3)).toString());
            System.out.println("File ID: " + fileID);
            System.out.println("For google drive we are sending\nToken: " + tempToken + "\n" + "File ID: " + fileID);
            int sizeOfFile = new GoogleDriveAccess().size(tempToken, fileID);
            this.td = new TaskDownloader(new MetaData(filename, cloudNum, fileID, sizeOfFile, 0), this.accessRef, tempToken);
        } else {
            int sizeOfFile = this.accessRef.size(tempToken, this.getPath() + "/" + filename);
            this.td = new TaskDownloader(new MetaData(filename, cloudNum, this.getPath() + "/" + filename, sizeOfFile, 0), this.accessRef, tempToken);
        }
        pi.progressProperty().unbind();
        pi.progressProperty().bind((ObservableValue)this.td.progressProperty());
        Thread thread = new Thread((Runnable)((Object)this.td));
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    private void onAddAccountClicked(ActionEvent event) {
        Stage s = new Stage();
        Parent root = null;
        try {
            root = (Parent)FXMLLoader.load((URL)this.getClass().getResource("AddAccountWindow.fxml"));
        }
        catch (IOException ex) {
            Logger.getLogger(HomeWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        s.setScene(scene);
        s.initModality(Modality.APPLICATION_MODAL);
        s.initStyle(StageStyle.UTILITY);
        s.setResizable(false);
        s.setTitle("Add Account");
        s.setOnCloseRequest(e -> {
            this.closeStage1();
        }
        );
        s.showAndWait();
        System.out.println("Checkpoint 2: " + AddAccountWindowController.getCode());
        ui = ClientForCommunication.getUserInfo(ui.getUsername());
        boolean flag1 = true;
        while (flag1) {
            try {
                System.out.println("Removing: " + ((Node)this.leftPane.getChildren().get(11)).getId());
                this.leftPane.getChildren().remove(11);
            }
            catch (Exception e) {
                flag1 = false;
            }
        }
        this.ai12.clear();
        try {
            this.ai.clear();
        }
        catch (NullPointerException e) {
            // empty catch block
        }
        this.ai = ui.getAccountList();
        System.out.println("/***************************************ai: " + this.ai);
        for (Account_info ai1 : this.ai) {
            if (ai1.getCloudno() == 0 && this.extractName(this.group.getSelectedToggle().toString()).equalsIgnoreCase("dropbox")) {
                System.out.println("*****************************************************************************************Cloud button is dropbox...");
                this.rbTemp = new RadioButton(ai1.getEmail());
                this.rbTemp.setToggleGroup(this.group2);
                this.leftPane.getChildren().add((Object)this.rbTemp);
                this.rbTemp.setSelected(true);
            }
            if (ai1.getCloudno() == 1 && this.extractName(this.group.getSelectedToggle().toString()).equalsIgnoreCase("googledrive")) {
                System.out.println("*****************************************************************************************Cloud button is google drive...");
                this.rbTemp = new RadioButton(ai1.getEmail());
                this.rbTemp.setToggleGroup(this.group2);
                this.leftPane.getChildren().add((Object)this.rbTemp);
                this.rbTemp.setSelected(true);
            }
            if (ai1.getCloudno() != 2 || !this.extractName(this.group.getSelectedToggle().toString()).equalsIgnoreCase("onedrive")) continue;
            System.out.println("*****************************************************************************************Cloud button is onedrive...");
            this.rbTemp = new RadioButton(ai1.getEmail());
            this.rbTemp.setToggleGroup(this.group2);
            this.leftPane.getChildren().add((Object)this.rbTemp);
            this.rbTemp.setSelected(true);
        }
    }

    public void closeStage1() {
        ui = ClientForCommunication.getUserInfo(ui.getUsername());
    }

    @FXML
    private void viewProfileClicked(ActionEvent event) {
        Stage s = new Stage();
        Parent root = null;
        try {
            root = (Parent)FXMLLoader.load((URL)this.getClass().getResource("ViewProfileWindow.fxml"));
        }
        catch (IOException ex) {
            Logger.getLogger(HomeWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        s.setScene(scene);
        s.initModality(Modality.APPLICATION_MODAL);
        s.setTitle("Add Account");
        s.showAndWait();
    }

    @FXML
    private void editProfileClicked(ActionEvent event) {
        s = new Stage();
        Parent root = null;
        try {
            root = (Parent)FXMLLoader.load((URL)this.getClass().getResource("EditProfileWindow.fxml"));
        }
        catch (IOException ex) {
            Logger.getLogger(HomeWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Scene scene = new Scene(root);
        s.setScene(scene);
        s.initModality(Modality.APPLICATION_MODAL);
        s.setTitle("Add Account");
        s.setOnCloseRequest(e -> {
            this.refresh();
        }
        );
        s.showAndWait();
    }

    private void refresh() {
        ((Stage)this.leftPane.getScene().getWindow()).hide();
        ((Stage)this.leftPane.getScene().getWindow()).show();
    }

    public static void closeStage() {
        s.close();
    }

    @FXML
    private void logOutClicked(ActionEvent event) {
        ACCESSit.showStage();
        File f = new File(FileSystem.dirname + "ACCESSit" + File.separatorChar + "UserInfo.ser");
        FileSystem.deleteFile(f);
        f = new File(FileSystem.dirname + "ACCESSit" + File.separatorChar + "ProfilePicture.jpg");
        FileSystem.deleteFile(f);
        f = new File(FileSystem.dirname + "HomeDir.ser");
        FileSystem.deleteFile(f);
        f = new File(FileSystem.dirname + "tmpFile");
        FileSystem.deleteFile(f);
        Platform.exit();
    }

    @FXML
    private void onUploadButtonClicked(ActionEvent event) {
        Map<String, Object> m;
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File f = fileChooser.showOpenDialog((Window)stage);
        System.out.println("Here uploading file: " + this.getPath());
        if (cloudNum == 0) {
            this.accessRef.Upload(this.getPath(), f, this.token);
        }
        if (cloudNum == 1) {
            try {
                System.out.println("Uploadin file at: " + this.getPath());
                m = this.accessRef.getAccessToken(this.token);
                this.accessRef.Upload((String)m.get("access_token"), f, gpath.get(gpath.size() - 1));
            }
            catch (IOException ex) {
                Logger.getLogger(HomeWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (cloudNum == 2) {
            try {
                System.out.println("Uploadin file at: " + this.getPath());
                m = this.accessRef.getAccessToken(this.token);
                this.accessRef.Upload(this.getPath(), f, (String)m.get("access_token"));
            }
            catch (IOException ex) {
                Logger.getLogger(HomeWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.ai = ui.getAccountList();
        this.ai12.clear();
        for (Account_info ai1 : this.ai) {
            if (ai1.getCloudno() != cloudNum || !this.extractName(this.group2.getSelectedToggle().toString()).equals(ai1.getEmail())) continue;
            this.ai12.add(ai1);
        }
        this.setArrayList(cloudNum);
        this.ai12.clear();
        path.clear();
        path.add("");
        ((Stage)this.listView.getScene().getWindow()).hide();
        ((Stage)this.listView.getScene().getWindow()).show();
    }

    public String extractName(String s) {
        int a = 0;
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); ++i) {
            if (a == 0 && s.charAt(i) == '\'') {
                ++a;
            }
            if (a != true || s.charAt(i) == '\'') continue;
            sb.append(s.charAt(i));
        }
        String str = sb.toString();
        return str;
    }

    @FXML
    private void onAddFolderButtonClicked(ActionEvent event) {
        String str = GetStringFromNewWindow.display("Add Folder", "Enter name of folder");
        if (!str.isEmpty()) {
            new DropBoxAccess().createFolder(this.token, this.getPath(), str);
        }
    }

    @FXML
    private void onDeleteButtonClicked(ActionEvent event) {
        String str = GetPasswordFromWindow.display("Delete", "Enter your ACCESSit! password:");
        int a = 1;
        if (str != null) {
            a = ClientForCommunication.logInCommunication(FileSystem.readFile().getUsername(), str);
        }
        if (a == 0) {
            String filename = this.extractName(((Node)((HBox)this.listView.getSelectionModel().getSelectedItem()).getChildren().get(1)).toString());
            if (cloudNum == 0) {
                this.accessRef.Delete(this.token, this.getPath() + "/" + filename);
            }
            if (cloudNum == 1) {
                String fileID = this.extractName(((Node)((HBox)this.listView.getSelectionModel().getSelectedItem()).getChildren().get(3)).toString());
                System.out.println("File ID: " + fileID);
                String fileID1 = this.extractName(((Node)((HBox)this.listView.getSelectionModel().getSelectedItem()).getChildren().get(2)).toString());
                System.out.println("File ID1: " + fileID1);
                try {
                    Map<String, Object> m = this.accessRef.getAccessToken(this.token);
                    this.accessRef.Delete((String)m.get("access_token"), fileID);
                }
                catch (IOException ex) {
                    Logger.getLogger(HomeWindowController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cloudNum == 2) {
                try {
                    Map<String, Object> m = this.accessRef.getAccessToken(this.token);
                    this.accessRef.Delete((String)m.get("access_token"), this.getPath() + "/" + filename);
                }
                catch (IOException ex) {
                    Logger.getLogger(HomeWindowController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            this.ai = ui.getAccountList();
            this.ai12.clear();
            for (Account_info ai1 : this.ai) {
                if (ai1.getCloudno() != cloudNum || !this.extractName(this.group2.getSelectedToggle().toString()).equals(ai1.getEmail())) continue;
                this.ai12.add(ai1);
            }
            this.setArrayList(cloudNum);
            path.clear();
            path.add("");
            this.ai12.clear();
            ((Stage)this.listView.getScene().getWindow()).hide();
            ((Stage)this.listView.getScene().getWindow()).show();
        } else {
            AlertBox.display("Sorry!", "Your password was incorrect");
        }
    }

    @FXML
    private void onShareLinkButtonClicked(ActionEvent event) {
        Map<String, Object> m;
        String filename = this.extractName(((Node)((HBox)this.listView.getSelectionModel().getSelectedItem()).getChildren().get(1)).toString());
        String str = null;
        if (cloudNum == 0) {
            str = this.accessRef.shareLink(this.token, this.getPath() + "/" + filename);
        } else if (cloudNum == 1) {
            m = null;
            try {
                m = this.accessRef.getAccessToken(this.token);
            }
            catch (IOException ex) {
                Logger.getLogger(HomeWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
            String fileID = this.extractName(((Node)((HBox)this.listView.getSelectionModel().getSelectedItem()).getChildren().get(3)).toString());
            System.out.println("File ID: " + fileID);
            System.out.println("Token: " + m.get("access_token"));
            str = this.accessRef.shareLink((String)m.get("access_token"), fileID);
        } else if (cloudNum == 2) {
            m = null;
            try {
                m = this.accessRef.getAccessToken(this.token);
            }
            catch (IOException ex) {
                Logger.getLogger(HomeWindowController.class.getName()).log(Level.SEVERE, null, ex);
            }
            str = this.accessRef.shareLink((String)m.get("access_token"), this.getPath() + "/" + filename);
        }
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        StringSelection strSel = new StringSelection(str);
        clipboard.setContents(strSel, null);
        AlertBox.display("SUCCESS!", "Link copied to clipboard");
    }

    @FXML
    private void onRemoveAccountButtonClicked(ActionEvent event) {
    }

    private String getPath() {
        String str = "";
        int i = 0;
        for (String s : path) {
            System.out.println("path[" + i + "]" + s);
            str = str + s;
            ++i;
        }
        return str;
    }

    @FXML
    private void onPauseButtonClicked(ActionEvent event) {
        this.td.onPause();
        AlertBox.display("Pause", "All threads are paused!!");
    }

    @FXML
    private void onResumeButtonClicked(ActionEvent event) {
        this.td.onResume();
        AlertBox.display("Resume", "All threads are resumed!!");
    }

    @FXML
    private void changePasswordClicked(ActionEvent event) {
        resetPasswordFlag = true;
        s2 = new Stage();
        Parent root = null;
        try {
            root = (Parent)FXMLLoader.load((URL)this.getClass().getResource("ResetPasswordWindow.fxml"));
            Scene scene = new Scene(root);
            s2.setScene(scene);
            s2.initModality(Modality.APPLICATION_MODAL);
            s2.setTitle("Reset Password");
            s2.showAndWait();
        }
        catch (IOException ex) {
            Logger.getLogger(HomeWindowController.class.getName()).log(Level.SEVERE, null, ex);
        }
        resetPasswordFlag = false;
    }

    @FXML
    private void aboutClicked(ActionEvent event) {
    }

    static {
        resetPasswordFlag = false;
    }
}

