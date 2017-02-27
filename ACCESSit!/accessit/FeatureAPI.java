/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  com.sun.jersey.api.client.Client
 *  com.sun.jersey.api.client.ClientResponse
 *  com.sun.jersey.api.client.RequestBuilder
 *  com.sun.jersey.api.client.WebResource
 *  com.sun.jersey.api.client.WebResource$Builder
 *  javax.json.Json
 *  javax.json.JsonArray
 *  javax.json.JsonObject
 *  javax.json.JsonObjectBuilder
 *  javax.json.JsonReader
 *  org.json.simple.JSONObject
 */
package accessit;

import AccessAPI.DropBoxAccess;
import AccessAPI.GoogleDriveAccess;
import AccessAPI.OneDriveAccess;
import accessit.GDriveHelper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.RequestBuilder;
import com.sun.jersey.api.client.WebResource;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import org.json.simple.JSONObject;

public abstract class FeatureAPI {
    public static ArrayList<Map<String, String>> Search(ArrayList<String> gToken, ArrayList<String> oToken, ArrayList<String> dToken, String query) {
        String email;
        JsonArray arr;
        int i;
        int j;
        JsonObject tmp;
        ArrayList<Map<String, String>> resp = new ArrayList<Map<String, String>>();
        HashMap dmap = new HashMap();
        HashMap omap = new HashMap();
        DropBoxAccess dapi = new DropBoxAccess();
        GoogleDriveAccess gapi = new GoogleDriveAccess();
        OneDriveAccess oapi = new OneDriveAccess();
        for (i = 0; i < gToken.size(); ++i) {
            email = gapi.getEmail(gToken.get(i));
            JsonObject result = gapi.Search(gToken.get(i), query);
            JsonArray items = result.getJsonArray("files");
            for (j = 0; j < items.size(); ++j) {
                tmp = items.getJsonObject(j);
                String title = tmp.getString("name");
                GDriveHelper help = new GDriveHelper(gToken.get(i));
                JsonObject label = gapi.getMetadata(gToken.get(i), tmp.getString("id")).getJsonObject("labels");
                if (label.getBoolean("trashed")) continue;
                help.build(tmp.getString("id"));
                String path = help.getPath();
                HashMap<String, String> m = new HashMap<String, String>();
                m.put("cid", "1");
                m.put("email", email);
                m.put("name", title);
                m.put("path", path);
                resp.add(m);
            }
        }
        for (i = 0; i < dToken.size(); ++i) {
            email = dapi.getEmail(dToken.get(i));
            JsonObject obj = dapi.Search(dToken.get(i), query);
            arr = obj.getJsonArray("matches");
            for (j = 0; j < arr.size() - 1; ++j) {
                tmp = arr.getJsonObject(j).getJsonObject("metadata");
                HashMap<String, String> m = new HashMap<String, String>();
                m.put("cid", "0");
                m.put("email", email);
                m.put("name", tmp.getString("name"));
                m.put("path", tmp.getString("path_display"));
                resp.add(m);
            }
        }
        for (i = 0; i < oToken.size(); ++i) {
            email = oapi.getEmail(oToken.get(i));
            JsonObject res = oapi.Search(oToken.get(i), query);
            arr = res.getJsonArray("value");
            for (j = 0; j < arr.size(); ++j) {
                tmp = arr.getJsonObject(j);
                JsonObject p = tmp.getJsonObject("parentReference");
                String path = p.getString("path");
                HashMap<String, String> m = new HashMap<String, String>();
                m.put("cid", "2");
                m.put("email", email);
                m.put("name", tmp.getString("name"));
                m.put("path", path);
                resp.add(m);
            }
        }
        return resp;
    }

    public static void Upload(String token, String trgCloud, String filename, byte[] data) {
        String resp;
        WebResource.Builder wb;
        WebResource wr;
        ClientResponse cr;
        Client c = new Client();
        if (trgCloud.equalsIgnoreCase("dropbox")) {
            JSONObject jb = new JSONObject();
            jb.put((Object)"path", (Object)("/" + filename));
            wr = c.resource("https://content.dropboxapi.com/2/files/upload");
            wb = (WebResource.Builder)((WebResource.Builder)wr.header("Authorization", (Object)("Bearer " + token)).header("Content-Type", (Object)"application/octet-stream")).header("Dropbox-API-Arg", (Object)jb.toJSONString());
            cr = (ClientResponse)wb.post(ClientResponse.class, (Object)data);
            resp = (String)cr.getEntity(String.class);
            System.out.println(resp);
        }
        if (trgCloud.equalsIgnoreCase("googledrive")) {
            JsonObject jObj = Json.createObjectBuilder().add("title", filename).build();
            wr = c.resource("https://www.googleapis.com/drive/v2/files");
            wb = (WebResource.Builder)wr.header("Authorization", (Object)("Bearer " + token)).header("Content-Type", (Object)"application/json");
            cr = (ClientResponse)wb.post(ClientResponse.class, (Object)jObj.toString());
            resp = (String)cr.getEntity(String.class);
            System.out.println(resp);
            JsonReader jr = Json.createReader((Reader)new StringReader(resp));
            JsonObject response = jr.readObject();
            WebResource wbr = c.resource("https://www.googleapis.com/upload/drive/v2/files/" + response.getString("id")).queryParam("uploadType", "media");
            WebResource.Builder wrb = wbr.header("Authorization", (Object)("Bearer " + token));
            ClientResponse ctr = (ClientResponse)wrb.put(ClientResponse.class, (Object)data);
            String res = (String)ctr.getEntity(String.class);
            System.out.println(res);
        }
        if (trgCloud.equalsIgnoreCase("onedrive")) {
            WebResource wr2 = c.resource("https://api.onedrive.com/v1.0/drive/root:/" + filename + ":/content");
            WebResource.Builder wb2 = (WebResource.Builder)wr2.header("Authorization", (Object)("Bearer " + token)).header("Content-Type", (Object)"text/plain");
            ClientResponse cr2 = (ClientResponse)wb2.put(ClientResponse.class, (Object)data);
            String res = (String)cr2.getEntity(String.class);
            System.out.println(res);
        }
    }
}

