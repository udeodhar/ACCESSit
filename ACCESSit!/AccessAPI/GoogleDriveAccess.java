/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  Handler.Handle
 *  com.sun.jersey.api.client.Client
 *  com.sun.jersey.api.client.ClientResponse
 *  com.sun.jersey.api.client.RequestBuilder
 *  com.sun.jersey.api.client.WebResource
 *  com.sun.jersey.api.client.WebResource$Builder
 *  com.sun.jersey.api.representation.Form
 *  javax.json.Json
 *  javax.json.JsonArray
 *  javax.json.JsonArrayBuilder
 *  javax.json.JsonObject
 *  javax.json.JsonObjectBuilder
 *  javax.json.JsonReader
 *  javax.json.JsonValue
 *  org.apache.http.client.utils.URIBuilder
 */
package AccessAPI;

import AccessAPI.AccessAPI;
import Handler.Handle;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.RequestBuilder;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;
import org.apache.http.client.utils.URIBuilder;

public class GoogleDriveAccess
extends AccessAPI {
    @Override
    public int getStatusCode() {
        return this.statusCode;
    }

    @Override
    public String UnLink(String token) {
        Client c = new Client();
        Form f = new Form();
        f.add("token", token);
        WebResource wbr = c.resource("https://accounts.google.com/o/oauth2/revoke");
        ClientResponse cr = (ClientResponse)wbr.post(ClientResponse.class, (Object)f);
        this.statusCode = cr.getStatus();
        String response = (String)cr.getEntity(String.class);
        System.out.println(response);
        return response;
    }

    @Override
    public Map<String, Object> Authenticate(String codeLink) throws URISyntaxException, IOException {
        int start = codeLink.indexOf("code=");
        int last = codeLink.length();
        String code = "";
        for (int i = start + 5; i < last - 1; ++i) {
            code = code + codeLink.charAt(i);
        }
        Form f = new Form();
        f.add("code", code);
        f.add("client_id", "1092268829927-6g07lpvsl3tbr4bggi2anq7kbestpiei.apps.googleusercontent.com");
        f.add("client_secret", "rYB2I2OKBaGFQ8wffmjMgtty");
        f.add("redirect_uri", "https://accessitblog.wordpress.com");
        f.add("grant_type", "authorization_code");
        Client c = new Client();
        WebResource webr = c.resource("https://www.googleapis.com/oauth2/v4/token");
        ClientResponse res = (ClientResponse)webr.post(ClientResponse.class, (Object)f);
        this.statusCode = res.getStatus();
        String response = (String)res.getEntity(String.class);
        Map map = Handle.toMap((String)response);
        return map;
    }

    @Override
    public Map<String, Object> getAccessToken(String refToken) throws IOException {
        Client c = new Client();
        WebResource webr = c.resource("https://www.googleapis.com/oauth2/v4/token");
        Form f = new Form();
        f.add("refresh_token", refToken);
        f.add("client_id", "1092268829927-6g07lpvsl3tbr4bggi2anq7kbestpiei.apps.googleusercontent.com");
        f.add("client_secret", "rYB2I2OKBaGFQ8wffmjMgtty");
        f.add("grant_type", "refresh_token");
        ClientResponse res = (ClientResponse)webr.post(ClientResponse.class, (Object)f);
        this.statusCode = res.getStatus();
        String response = (String)res.getEntity(String.class);
        Map map = Handle.toMap((String)response);
        return map;
    }

    @Override
    public String getURL() throws URISyntaxException {
        URIBuilder ub = new URIBuilder();
        ub.setScheme("https");
        ub.setHost("accounts.google.com");
        ub.setPath("/o/oauth2/v2/auth");
        ub.addParameter("response_type", "code");
        ub.addParameter("client_id", "1092268829927-6g07lpvsl3tbr4bggi2anq7kbestpiei.apps.googleusercontent.com");
        ub.addParameter("state", "sample");
        ub.addParameter("access_type", "offline");
        ub.addParameter("redirect_uri", "https://accessitblog.wordpress.com");
        ub.addParameter("scope", "https://www.googleapis.com/auth/drive profile email");
        ub.addParameter("prompt", "consent");
        String code = ub.build().toString();
        return code;
    }

    @Override
    public boolean Delete(String token, String fileId) {
        Client c = new Client();
        WebResource webr = c.resource("https://www.googleapis.com/drive/v2/files/" + fileId);
        WebResource.Builder wb = webr.header("Authorization", (Object)("Bearer " + token));
        ClientResponse cr = (ClientResponse)wb.delete(ClientResponse.class);
        this.statusCode = cr.getStatus();
        return cr.getStatus() == 204;
    }

    public JsonObject getNextPage(String token, String nextPageToken) {
        Client c = new Client();
        WebResource wr = c.resource("https://www.googleapis.com/drive/v3/files").queryParam("pageToken", nextPageToken);
        WebResource.Builder wb = wr.header("Authorization", (Object)("Bearer " + token));
        ClientResponse cr = (ClientResponse)wb.get(ClientResponse.class);
        String resp = (String)cr.getEntity(String.class);
        this.statusCode = cr.getStatus();
        JsonReader jr = Json.createReader((Reader)new StringReader(resp));
        return jr.readObject();
    }

    @Override
    public byte[] Download(String token, String path) {
        Client c = new Client();
        WebResource wr = c.resource("https://www.googleapis.com/drive/v2/files/" + path).queryParam("alt", "media");
        WebResource.Builder wb = wr.header("Authorization", (Object)("Bearer " + token));
        ClientResponse cr = (ClientResponse)wb.get(ClientResponse.class);
        byte[] data = (byte[])cr.getEntity(byte[].class);
        return data;
    }

    @Override
    public JsonObject Search(String token, String query) {
        Client c = new Client();
        WebResource wr = c.resource("https://www.googleapis.com/drive/v3/files").queryParam("q", "name contains '" + query + "'").queryParam("trashed", "false");
        WebResource.Builder wb = wr.header("Authorization", (Object)("Bearer " + token));
        ClientResponse cr = (ClientResponse)wb.get(ClientResponse.class);
        this.statusCode = cr.getStatus();
        String resp = (String)cr.getEntity(String.class);
        JsonReader jr = Json.createReader((Reader)new StringReader(resp));
        return jr.readObject();
    }

    private JsonObject createFile(String token, String name, String parent) {
        JsonArray jarr;
        if (parent.equalsIgnoreCase("root")) {
            jarr = Json.createArrayBuilder().build();
        } else {
            JsonObject par = Json.createObjectBuilder().add("id", parent).build();
            jarr = Json.createArrayBuilder().add((JsonValue)par).build();
        }
        JsonObject jObj = Json.createObjectBuilder().add("title", name).add("parents", (JsonValue)jarr).build();
        System.out.println((Object)jObj);
        Client c = new Client();
        WebResource wr = c.resource("https://www.googleapis.com/drive/v2/files");
        WebResource.Builder wb = (WebResource.Builder)wr.header("Authorization", (Object)("Bearer " + token)).header("Content-Type", (Object)"application/json");
        ClientResponse cr = (ClientResponse)wb.post(ClientResponse.class, (Object)jObj.toString());
        this.statusCode = cr.getStatus();
        String resp = (String)cr.getEntity(String.class);
        System.out.println(resp);
        JsonReader jr = Json.createReader((Reader)new StringReader(resp));
        JsonObject response = jr.readObject();
        return response;
    }

    private void putData(String token, String id, File f) {
        Client c = new Client();
        WebResource wr = c.resource("https://www.googleapis.com/upload/drive/v2/files/" + id).queryParam("uploadType", "media");
        WebResource.Builder wb = wr.header("Authorization", (Object)("Bearer " + token));
        ClientResponse cr = (ClientResponse)wb.put(ClientResponse.class, (Object)f);
        this.statusCode = cr.getStatus();
        String resp = (String)cr.getEntity(String.class);
        System.out.println(resp);
    }

    @Override
    public String getEmail(String token) {
        Client c = new Client();
        WebResource wr = c.resource("https://www.googleapis.com/plus/v1/people/me");
        WebResource.Builder wb = wr.header("Authorization", (Object)("Bearer " + token));
        ClientResponse cr = (ClientResponse)wb.get(ClientResponse.class);
        this.statusCode = cr.getStatus();
        String res = (String)cr.getEntity(String.class);
        JsonReader jr = Json.createReader((Reader)new StringReader(res));
        JsonObject jObj = jr.readObject();
        JsonArray jArr = jObj.getJsonArray("emails");
        JsonObject email = jArr.getJsonObject(0);
        return email.getString("value");
    }

    @Override
    public boolean isFolder(String token, String path) {
        Client c = new Client();
        WebResource wr = c.resource("https://www.googleapis.com/drive/v2/files/" + path);
        WebResource.Builder wb = wr.header("Authorization", (Object)("Bearer " + token));
        ClientResponse cr = (ClientResponse)wb.get(ClientResponse.class);
        String res = (String)cr.getEntity(String.class);
        System.out.println(cr.getStatus());
        System.out.println(res);
        JsonReader jr = Json.createReader((Reader)new StringReader(res));
        JsonObject respo = jr.readObject();
        if (respo.getString("mimeType").equalsIgnoreCase("application/vnd.google-apps.folder")) {
            return true;
        }
        return false;
    }

    @Override
    public JsonObject getMetadata(String token, String path) {
        Client c = new Client();
        WebResource wr = c.resource("https://www.googleapis.com/drive/v2/files/" + path);
        WebResource.Builder wb = wr.header("Authorization", (Object)("Bearer " + token));
        ClientResponse cr = (ClientResponse)wb.get(ClientResponse.class);
        this.statusCode = cr.getStatus();
        String res = (String)cr.getEntity(String.class);
        JsonReader jr = Json.createReader((Reader)new StringReader(res));
        return jr.readObject();
    }

    @Override
    public byte[] Download(String token, String path, int ini, int fin) {
        Client c = new Client();
        WebResource wr = c.resource("https://www.googleapis.com/drive/v2/files/" + path).queryParam("alt", "media");
        WebResource.Builder wb = (WebResource.Builder)wr.header("Authorization", (Object)("Bearer " + token)).header("Range", (Object)("bytes=" + ini + "-" + fin));
        ClientResponse cr = (ClientResponse)wb.get(ClientResponse.class);
        this.statusCode = cr.getStatus();
        System.out.println(cr.getStatus());
        byte[] data = (byte[])cr.getEntity(byte[].class);
        return data;
    }

    @Override
    public JsonObject getFilesFromPath(String token, String fileId) {
        Client c = new Client();
        WebResource wr = c.resource("https://www.googleapis.com/drive/v2/files/" + fileId + "/children");
        WebResource.Builder wb = wr.header("Authorization", (Object)("Bearer " + token));
        ClientResponse cr = (ClientResponse)wb.get(ClientResponse.class);
        this.statusCode = cr.getStatus();
        String res = (String)cr.getEntity(String.class);
        JsonReader jr = Json.createReader((Reader)new StringReader(res));
        JsonArray parentResponse = jr.readObject().getJsonArray("items");
        JsonArrayBuilder jab = Json.createArrayBuilder();
        for (int i = 0; i < parentResponse.size(); ++i) {
            JsonObject tmp = parentResponse.getJsonObject(i);
            JsonObject response = this.getMetadata(token, tmp.getString("id"));
            jab.add((JsonValue)response);
        }
        JsonArray items = jab.build();
        return Json.createObjectBuilder().add("items", (JsonValue)items).build();
    }

    @Override
    public void Upload(String token, File f, String parentId) {
        JsonObject jObj = this.createFile(token, f.getName(), parentId);
        this.putData(token, jObj.getString("id"), f);
    }

    @Override
    public String shareLink(String token, String id) {
        Client c = new Client();
        WebResource wr = c.resource("https://www.googleapis.com/drive/v2/files/" + id);
        WebResource.Builder wb = wr.header("Authorization", (Object)("Bearer " + token));
        ClientResponse cr = (ClientResponse)wb.get(ClientResponse.class);
        this.statusCode = cr.getStatus();
        String resp = (String)cr.getEntity(String.class);
        JsonReader jr = Json.createReader((Reader)new StringReader(resp));
        JsonObject jObj = jr.readObject();
        System.out.println();
        return jObj.getString("alternateLink");
    }

    @Override
    public int size(String token, String path) {
        Client c = new Client();
        WebResource wr = c.resource("https://www.googleapis.com/drive/v2/files/" + path);
        WebResource.Builder wb = wr.header("Authorization", (Object)("Bearer " + token));
        ClientResponse cr = (ClientResponse)wb.get(ClientResponse.class);
        String res = (String)cr.getEntity(String.class);
        JsonObject jObj = Json.createReader((Reader)new StringReader(res)).readObject();
        int size = Integer.parseInt(jObj.getString("fileSize"));
        System.out.println(size);
        return size;
    }

    @Override
    public JsonObject createFolder(String token, String parent, String folderName) {
        JsonArray jarr;
        if (parent.equalsIgnoreCase("root")) {
            jarr = Json.createArrayBuilder().build();
        } else {
            JsonObject par = Json.createObjectBuilder().add("id", parent).build();
            jarr = Json.createArrayBuilder().add((JsonValue)par).build();
        }
        JsonObject jObj = Json.createObjectBuilder().add("title", folderName).add("mimeType", "application/vnd.google-apps.folder").add("parents", (JsonValue)jarr).build();
        Client c = new Client();
        WebResource wr = c.resource("https://www.googleapis.com/drive/v2/files");
        WebResource.Builder wb = (WebResource.Builder)wr.header("Authorization", (Object)("Bearer " + token)).header("Content-Type", (Object)"application/json");
        ClientResponse cr = (ClientResponse)wb.post(ClientResponse.class, (Object)jObj.toString());
        this.statusCode = cr.getStatus();
        String resp = (String)cr.getEntity(String.class);
        System.out.println(resp);
        JsonReader jr = Json.createReader((Reader)new StringReader(resp));
        JsonObject response = jr.readObject();
        return response;
    }
}

