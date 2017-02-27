/*
 * Decompiled with CFR 0_118.
 */
package ServerPackage;

import java.io.Serializable;

public class Account_info
implements Serializable {
    private static final long serialVersionUID = 1;
    private int cloudno;
    private String email;

    public Account_info(String email, int cn) {
        this.email = email;
        this.cloudno = cn;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCloudno(int cloudno) {
        this.cloudno = cloudno;
    }

    public String getEmail() {
        return this.email;
    }

    public int getCloudno() {
        return this.cloudno;
    }
}

