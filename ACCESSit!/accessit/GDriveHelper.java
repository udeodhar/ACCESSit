/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  com.sun.jersey.api.client.Client
 *  com.sun.jersey.api.client.ClientResponse
 *  com.sun.jersey.api.client.WebResource
 *  com.sun.jersey.api.client.WebResource$Builder
 *  javax.json.Json
 *  javax.json.JsonArray
 *  javax.json.JsonObject
 */
package accessit;

import AccessAPI.GoogleDriveAccess;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.io.Reader;
import java.io.StringReader;
import java.util.Stack;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

class GDriveHelper {
    private Stack<String> parents;
    private String token;

    public GDriveHelper(String tok) {
        this.token = tok;
        this.parents = new Stack();
    }

    public void build(String refid) {
        String parent = this.getParent(refid);
        if (!parent.equalsIgnoreCase("root")) {
            this.addname(this.getName(parent));
            this.build(parent);
            return;
        }
        this.addname(parent);
    }

    private void addname(String name) {
        this.parents.add(name);
    }

    public String getPath() {
        String path = "";
        while (!this.parents.isEmpty()) {
            path = path + "/" + this.parents.pop();
        }
        return path;
    }

    private String getParent(String refid) {
        Client c = new Client();
        WebResource wr = c.resource("https://www.googleapis.com/drive/v2/files/" + refid + "/parents");
        WebResource.Builder wb = wr.header("Authorization", (Object)("Bearer " + this.token));
        ClientResponse cr = (ClientResponse)wb.get(ClientResponse.class);
        String s = (String)cr.getEntity(String.class);
        JsonObject res = Json.createReader((Reader)new StringReader(s)).readObject();
        JsonArray arr = res.getJsonArray("items");
        JsonObject itm = arr.getJsonObject(0);
        if (itm.getBoolean("isRoot")) {
            return "root";
        }
        return itm.getString("id");
    }

    private String getName(String refid) {
        GoogleDriveAccess gda = new GoogleDriveAccess();
        JsonObject obj = gda.getMetadata(this.token, refid);
        String title = obj.getString("title");
        return title;
    }
}

