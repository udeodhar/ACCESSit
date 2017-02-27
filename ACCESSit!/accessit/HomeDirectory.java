/*
 * Decompiled with CFR 0_118.
 */
package accessit;

import java.io.Serializable;

public class HomeDirectory
implements Serializable {
    private String dir;

    public HomeDirectory(String dir) {
        this.dir = dir;
    }

    public void setDirectory(String dir) {
        this.dir = dir;
    }

    public String getDir() {
        return this.dir;
    }
}

