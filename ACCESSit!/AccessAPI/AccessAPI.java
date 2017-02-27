/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  javax.json.JsonObject
 */
package AccessAPI;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import javax.json.JsonObject;

public abstract class AccessAPI {
    protected int statusCode = 0;

    public abstract int size(String var1, String var2);

    public abstract Map<String, Object> Authenticate(String var1) throws Exception;

    public abstract String getURL() throws URISyntaxException;

    public abstract Map<String, Object> getAccessToken(String var1) throws IOException;

    public abstract byte[] Download(String var1, String var2);

    public abstract String getEmail(String var1);

    public abstract JsonObject getFilesFromPath(String var1, String var2);

    public abstract boolean Delete(String var1, String var2);

    public abstract String UnLink(String var1);

    public abstract JsonObject Search(String var1, String var2);

    public abstract void Upload(String var1, File var2, String var3);

    public abstract String shareLink(String var1, String var2);

    public abstract JsonObject createFolder(String var1, String var2, String var3);

    public abstract byte[] Download(String var1, String var2, int var3, int var4);

    public abstract boolean isFolder(String var1, String var2);

    public abstract JsonObject getMetadata(String var1, String var2);

    public int getStatusCode() {
        return this.statusCode;
    }
}

