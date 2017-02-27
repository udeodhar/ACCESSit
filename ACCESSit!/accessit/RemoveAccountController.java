/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  javafx.collections.ObservableList
 *  javafx.event.ActionEvent
 *  javafx.fxml.FXML
 *  javafx.fxml.Initializable
 *  javafx.scene.control.ChoiceBox
 */
package accessit;

import AccessAPI.GoogleDriveAccess;
import AccessAPI.OneDriveAccess;
import ServerPackage.Account_info;
import ServerPackage.UserInfo;
import accessit.ClientForCommunication;
import accessit.FileSystem;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;

public class RemoveAccountController
implements Initializable {
    @FXML
    private ChoiceBox<String> accountChoiceBox;

    public void initialize(URL url, ResourceBundle rb) {
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
            this.accountChoiceBox.getItems().add((Object)(account.getEmail() + "-" + temp));
        }
    }

    @FXML
    private void onSubmitButtonClicked(ActionEvent event) {
        Map<String, Object> m;
        int cloudnum = -1;
        UserInfo ui = FileSystem.readFile();
        String temp = (String)this.accountChoiceBox.getValue();
        String tempToken = null;
        String[] split = temp.split("-");
        String email = split[0];
        String cid1 = split[1];
        System.out.println("Email: " + email + "\n" + "cid: " + cid1);
        if (cid1.equalsIgnoreCase("dropbox")) {
            cloudnum = 0;
            tempToken = ClientForCommunication.getToken(0, email, ui.getUsername());
        } else if (cid1.equalsIgnoreCase("Googledrive")) {
            cloudnum = 1;
            m = null;
            try {
                m = new GoogleDriveAccess().getAccessToken(ClientForCommunication.getToken(0, email, ui.getUsername()));
                tempToken = (String)m.get("access_token");
            }
            catch (IOException var10_10) {}
        } else if (cid1.equalsIgnoreCase("Onedrive")) {
            cloudnum = 2;
            m = null;
            try {
                m = new OneDriveAccess().getAccessToken(ClientForCommunication.getToken(0, email, ui.getUsername()));
                tempToken = (String)m.get("access_token");
            }
            catch (IOException var10_11) {
                // empty catch block
            }
        }
        String token2 = ClientForCommunication.getToken(0, email, ui.getUsername());
        ClientForCommunication.unlink(cloudnum, ui.getUsername(), ui.getEmail());
    }
}

