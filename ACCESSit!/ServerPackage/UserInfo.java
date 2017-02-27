/*
 * Decompiled with CFR 0_118.
 */
package ServerPackage;

import ServerPackage.Account_info;
import java.io.Serializable;
import java.util.ArrayList;

public class UserInfo
implements Serializable {
    private static final long serialVersionUID = 1;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String pass;
    private ArrayList<Account_info> al;

    public UserInfo() {
    }

    public UserInfo(String fn, String ln, String uname, String em, String password, ArrayList<Account_info> a) {
        this.firstName = fn;
        this.lastName = ln;
        this.username = uname;
        this.email = em;
        this.pass = password;
        this.al = a;
    }

    public void setPass(String str) {
        this.pass = str;
    }

    public void setFirstName(String str) {
        this.firstName = str;
    }

    public void setLastName(String str) {
        this.lastName = str;
    }

    public void setUsername(String str) {
        this.username = str;
    }

    public void setEmail(String str) {
        this.email = str;
    }

    public String getPass() {
        return this.pass;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public ArrayList<Account_info> getAccountList() {
        return this.al;
    }
}

