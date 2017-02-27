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
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;
import org.apache.http.client.utils.URIBuilder;

public class OneDriveAccess
extends AccessAPI {
    @Override
    public int getStatusCode() {
        return this.statusCode;
    }

    @Override
    public int size(String token, String path) {
        Client c = new Client();
        WebResource wr = c.resource("https://api.onedrive.com/v1.0/drive/root:/").path(path);
        WebResource.Builder wb = wr.header("Authorization", (Object)("Bearer " + token));
        ClientResponse cr = (ClientResponse)wb.get(ClientResponse.class);
        String resp = (String)cr.getEntity(String.class);
        JsonObject jobj = Json.createReader((Reader)new StringReader(resp)).readObject();
        int size = jobj.getInt("size");
        return size;
    }

    @Override
    public Map<String, Object> Authenticate(String codeLink) throws Exception {
        int strt = codeLink.indexOf("=");
        int last = codeLink.indexOf("&");
        String s = "";
        for (int i = strt + 1; i < last; ++i) {
            s = s + codeLink.charAt(i);
        }
        Form f = new Form();
        f.add("Content-type", "application/x-www-form-urlencoded");
        f.add("client_id", "000000004417E648");
        f.add("redirect_uri", "https://login.live.com/oauth20_desktop.srf");
        f.add("client_secret", "XoxFSDeKBMojOD2dtiZ9lMVNhOBVrK6Z");
        f.add("code", s);
        f.add("grant_type", "authorization_code");
        Client ct = new Client();
        WebResource wt = ct.resource("https://login.live.com/oauth20_token.srf");
        ClientResponse rs = (ClientResponse)wt.post(ClientResponse.class, (Object)f);
        this.statusCode = rs.getStatus();
        String resp = (String)rs.getEntity(String.class);
        Map map = Handle.toMap((String)resp);
        return map;
    }

    @Override
    public Map<String, Object> getAccessToken(String refToken) throws IOException {
        Form f = new Form();
        f.add("Content-type", "application/x-www-form-urlencoded");
        f.add("client_id", "000000004417E648");
        f.add("redirect_uri", "https://login.live.com/oauth20_desktop.srf");
        f.add("client_secret", "XoxFSDeKBMojOD2dtiZ9lMVNhOBVrK6Z");
        f.add("refresh_token", refToken);
        f.add("grant_type", "refresh_token");
        Client c = new Client();
        WebResource wt = c.resource("https://login.live.com/oauth20_token.srf");
        ClientResponse rs = (ClientResponse)wt.post(ClientResponse.class, (Object)f);
        this.statusCode = rs.getStatus();
        String response = (String)rs.getEntity(String.class);
        Map map = Handle.toMap((String)response);
        return map;
    }

    public Map<String, Object> getDetails(String token) throws IOException {
        Client c = new Client();
        WebResource wr = c.resource("https://api.onedrive.com").path("/v1.0/drive/");
        WebResource.Builder wb = wr.header("Authorization", (Object)("Bearer " + token));
        ClientResponse cr = (ClientResponse)wb.get(ClientResponse.class);
        this.statusCode = cr.getStatus();
        String response = (String)cr.getEntity(String.class);
        Map map = Handle.toMap((String)response);
        return map;
    }

    @Override
    public String UnLink(String token) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getURL() throws URISyntaxException {
        URIBuilder ub = new URIBuilder();
        ub.setScheme("https");
        ub.setHost("login.live.com");
        ub.setPath("/oauth20_authorize.srf");
        ub.addParameter("client_id", "000000004417E648");
        ub.addParameter("scope", "wl.signin wl.offline_access wl.basic wl.emails onedrive.readwrite");
        ub.addParameter("response_type", "code");
        ub.addParameter("redirect_uri", "https://login.live.com/oauth20_desktop.srf");
        URI u = ub.build();
        return u.toString();
    }

    @Override
    public String getEmail(String token) {
        Client c = new Client();
        WebResource wr = c.resource("https://apis.live.net/v5.0/me").queryParam("access_token", token);
        ClientResponse cr = (ClientResponse)wr.get(ClientResponse.class);
        String str = (String)cr.getEntity(String.class);
        this.statusCode = cr.getStatus();
        JsonReader jr = Json.createReader((Reader)new StringReader(str));
        JsonObject jObj = jr.readObject();
        JsonObject emails = jObj.getJsonObject("emails");
        String mail = emails.getString("account");
        return mail;
    }

    @Override
    public boolean isFolder(String token, String path) {
        Client c = new Client();
        WebResource wr = c.resource("https://api.onedrive.com/v1.0/drive/root:/").path(path);
        WebResource.Builder wb = wr.header("Authorization", (Object)("Bearer " + token));
        ClientResponse cr = (ClientResponse)wb.get(ClientResponse.class);
        String str = (String)cr.getEntity(String.class);
        JsonReader jr = Json.createReader((Reader)new StringReader(str));
        JsonObject respo = jr.readObject();
        if (respo.containsKey((Object)"folder")) {
            return true;
        }
        return false;
    }

    @Override
    public JsonObject getMetadata(String token, String path) {
        Client c = new Client();
        WebResource wr = c.resource("https://api.onedrive.com/v1.0/drive/root:/").path(path);
        WebResource.Builder wb = wr.header("Authorization", (Object)("Bearer " + token));
        ClientResponse cr = (ClientResponse)wb.get(ClientResponse.class);
        this.statusCode = cr.getStatus();
        String str = (String)cr.getEntity(String.class);
        JsonReader jr = Json.createReader((Reader)new StringReader(str));
        return jr.readObject();
    }

    @Override
    public JsonObject getFilesFromPath(String token, String path) {
        Client c = new Client();
        WebResource wr = c.resource("https://api.onedrive.com/v1.0/drive/root:/").path(path).path(":/children");
        WebResource.Builder wb = wr.header("Authorization", (Object)("Bearer " + token));
        ClientResponse cr = (ClientResponse)wb.get(ClientResponse.class);
        this.statusCode = cr.getStatus();
        String str = (String)cr.getEntity(String.class);
        System.out.println("" + str);
        JsonReader jr = Json.createReader((Reader)new StringReader(str));
        return jr.readObject();
    }

    @Override
    public byte[] Download(String token, String path) {
        Client c = new Client();
        WebResource wr = c.resource("https://api.onedrive.com/v1.0/drive/root:/").path(path).path(":/content");
        WebResource.Builder wb = wr.header("Authorization", (Object)("Bearer " + token));
        ClientResponse cr = (ClientResponse)wb.get(ClientResponse.class);
        this.statusCode = cr.getStatus();
        byte[] data = (byte[])cr.getEntity(byte[].class);
        return data;
    }

    @Override
    public byte[] Download(String token, String path, int ini, int fin) {
        Client c = new Client();
        WebResource wr = c.resource("https://api.onedrive.com/v1.0/drive/root:/").path(path).path(":/content");
        WebResource.Builder wb = (WebResource.Builder)wr.header("Authorization", (Object)("Bearer " + token)).header("Range", (Object)("bytes=" + ini + "-" + fin));
        ClientResponse cr = (ClientResponse)wb.get(ClientResponse.class);
        this.statusCode = cr.getStatus();
        System.out.println(cr.getStatus());
        byte[] data = (byte[])cr.getEntity(byte[].class);
        return data;
    }

    @Override
    public boolean Delete(String token, String path) {
        Client c = new Client();
        WebResource wr = c.resource("https://api.onedrive.com/v1.0/drive/root:/").path(path);
        WebResource.Builder wb = wr.header("Authorization", (Object)("Bearer " + token));
        ClientResponse cr = (ClientResponse)wb.delete(ClientResponse.class);
        this.statusCode = cr.getStatus();
        return cr.getStatus() == 204;
    }

    @Override
    public JsonObject Search(String token, String query) {
        Client c = new Client();
        WebResource wr = c.resource("https://api.onedrive.com/v1.0/drive/root/view.search").queryParam("q", query);
        WebResource.Builder wb = wr.header("Authorization", (Object)("Bearer " + token));
        ClientResponse cr = (ClientResponse)wb.get(ClientResponse.class);
        String str = (String)cr.getEntity(String.class);
        this.statusCode = cr.getStatus();
        JsonReader jr = Json.createReader((Reader)new StringReader(str));
        return jr.readObject();
    }

    @Override
    public void Upload(String target, File f, String token) {
        Client c = new Client();
        WebResource wr = c.resource("https://api.onedrive.com/v1.0/drive/root:" + target + "/" + f.getName() + ":/content");
        WebResource.Builder wb = (WebResource.Builder)wr.header("Authorization", (Object)("Bearer " + token)).header("Content-Type", (Object)"text/plain");
        ClientResponse cr = (ClientResponse)wb.put(ClientResponse.class, (Object)f);
        this.statusCode = cr.getStatus();
        String res = (String)cr.getEntity(String.class);
        System.out.println(res);
    }

    @Override
    public String shareLink(String token, String path) {
        JsonObject job = Json.createObjectBuilder().add("type", "view").build();
        Client c = new Client();
        WebResource wr = c.resource("https://api.onedrive.com/v1.0/drive/root:/" + path + ":/action.createLink");
        WebResource.Builder wb = (WebResource.Builder)wr.header("Authorization", (Object)("Bearer " + token)).header("Content-Type", (Object)"application/json");
        ClientResponse cr = (ClientResponse)wb.post(ClientResponse.class, (Object)job.toString());
        String resp = (String)cr.getEntity(String.class);
        this.statusCode = cr.getStatus();
        JsonReader jr = Json.createReader((Reader)new StringReader(resp));
        JsonObject jObj = jr.readObject();
        return jObj.getJsonObject("link").getString("webUrl");
    }

    @Override
    public JsonObject createFolder(String token, String parent, String folderName) {
        JsonObject jObj = Json.createObjectBuilder().add("name", folderName).add("folder", (JsonValue)Json.createObjectBuilder().build()).add("@name.conflictBehavior", "rename").build();
        Client c = new Client();
        WebResource wr = c.resource("https://api.onedrive.com/v1.0/drive/items/" + parent + "/children");
        WebResource.Builder wb = (WebResource.Builder)wr.header("Authorization", (Object)("Bearer " + token)).header("Content-Type", (Object)"application/json");
        ClientResponse cr = (ClientResponse)wb.post(ClientResponse.class, (Object)jObj.toString());
        String resp = (String)cr.getEntity(String.class);
        this.statusCode = cr.getStatus();
        System.out.println(resp);
        JsonReader jr = Json.createReader((Reader)new StringReader(resp));
        return jr.readObject();
    }
}

