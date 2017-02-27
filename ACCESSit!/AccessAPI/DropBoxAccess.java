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
 *  javax.json.Json
 *  javax.json.JsonObject
 *  javax.json.JsonObjectBuilder
 *  javax.json.JsonReader
 *  javax.ws.rs.core.MultivaluedMap
 *  org.apache.http.client.utils.URIBuilder
 *  org.json.simple.JSONObject
 */
package AccessAPI;

import AccessAPI.AccessAPI;
import Handler.Handle;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.RequestBuilder;
import com.sun.jersey.api.client.WebResource;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.ws.rs.core.MultivaluedMap;
import org.apache.http.client.utils.URIBuilder;
import org.json.simple.JSONObject;

public class DropBoxAccess
extends AccessAPI {
    @Override
    public int getStatusCode() {
        return this.statusCode;
    }

    @Override
    public Map<String, Object> Authenticate(String codeLink) throws Exception {
        int start = codeLink.indexOf("code=") + 5;
        int last = codeLink.length();
        String code = "";
        for (int i = start; i < last; ++i) {
            code = code + codeLink.charAt(i);
        }
        Client ct = new Client();
        WebResource wr = ct.resource("https://api.dropboxapi.com/1/oauth2/token").queryParam("code", code).queryParam("grant_type", "authorization_code").queryParam("client_id", "zkmjm8qhr2c8rk8").queryParam("client_secret", "c05dl2d27pk86yg").queryParam("redirect_uri", "https://accessitblog.wordpress.com");
        ClientResponse rs = (ClientResponse)wr.post(ClientResponse.class);
        this.statusCode = rs.getStatus();
        String resp = (String)rs.getEntity(String.class);
        Map map = Handle.toMap((String)resp);
        return map;
    }

    @Override
    public int size(String token, String path) {
        Client c = new Client();
        WebResource wr = c.resource("https://content.dropboxapi.com/1/files/auto" + path);
        WebResource.Builder wb = (WebResource.Builder)wr.header("Authorization", (Object)("Bearer " + token)).header("Range", (Object)"bytes=0-1");
        ClientResponse resp = (ClientResponse)wb.get(ClientResponse.class);
        MultivaluedMap map = resp.getHeaders();
        int size = Integer.parseInt("45");
        String obj = (String)((List)map.get((Object)"x-dropbox-metadata")).get(0);
        JsonObject jobj = Json.createReader((Reader)new StringReader(obj)).readObject();
        size = jobj.getInt("bytes");
        System.out.println(size);
        return size;
    }

    @Override
    public byte[] Download(String token, String path, int ini, int fin) {
        try {
            URIBuilder ub = new URIBuilder();
            ub.setScheme("https");
            ub.setHost("content.dropboxapi.com/1/files/auto");
            ub.setPath(path);
            Client c = new Client();
            WebResource wr = c.resource(ub.build().toString());
            WebResource.Builder wb = (WebResource.Builder)wr.header("Authorization", (Object)("Bearer " + token)).header("Range", (Object)("bytes=" + ini + "-" + fin));
            ClientResponse resp = (ClientResponse)wb.get(ClientResponse.class);
            this.statusCode = resp.getStatus();
            System.out.println(resp.getStatus());
            byte[] data = (byte[])resp.getEntity(byte[].class);
            return data;
        }
        catch (URISyntaxException ex) {
            Logger.getLogger(DropBoxAccess.class.getName()).log(Level.SEVERE, null, ex);
            return new byte[1];
        }
    }

    @Override
    public byte[] Download(String token, String path) {
        Client c = new Client();
        WebResource wr = c.resource("https://content.dropboxapi.com/2/files/download");
        JSONObject jb = new JSONObject();
        jb.put((Object)"path", (Object)path);
        WebResource.Builder wb = (WebResource.Builder)wr.header("Authorization", (Object)("Bearer " + token)).header("Dropbox-API-Arg", (Object)jb.toJSONString());
        ClientResponse resp = (ClientResponse)wb.post(ClientResponse.class);
        this.statusCode = resp.getStatus();
        byte[] data = (byte[])resp.getEntity(byte[].class);
        return data;
    }

    @Override
    public boolean Delete(String token, String path) {
        JSONObject jb = new JSONObject();
        jb.put((Object)"path", (Object)path);
        Client c = new Client();
        WebResource web = c.resource("https://api.dropboxapi.com/2/files/delete");
        WebResource.Builder build = (WebResource.Builder)web.header("Authorization", (Object)("Bearer " + token)).header("Content-Type", (Object)"application/json");
        ClientResponse cr = (ClientResponse)build.post(ClientResponse.class, (Object)jb.toJSONString());
        this.statusCode = cr.getStatus();
        String resp = (String)cr.getEntity(String.class);
        System.out.println(resp);
        return true;
    }

    @Override
    public boolean isFolder(String token, String path) {
        Client c = new Client();
        WebResource wr = c.resource("https://api.dropboxapi.com/2/files/get_metadata");
        JSONObject jb = new JSONObject();
        jb.put((Object)"path", (Object)path);
        WebResource.Builder wb = (WebResource.Builder)wr.header("Authorization", (Object)("Bearer " + token)).header("Content-Type", (Object)"application/json");
        ClientResponse ct = (ClientResponse)wb.post(ClientResponse.class, (Object)jb.toJSONString());
        String re = (String)ct.getEntity(String.class);
        JsonObject respo = Json.createReader((Reader)new StringReader(re)).readObject();
        String s = respo.getString(".tag");
        System.out.println(s);
        if (s.equalsIgnoreCase("folder")) {
            return true;
        }
        return false;
    }

    @Override
    public JsonObject getMetadata(String token, String path) {
        Client c = new Client();
        WebResource wr = c.resource("https://api.dropboxapi.com/2/files/get_metadata");
        JSONObject jb = new JSONObject();
        jb.put((Object)"path", (Object)path);
        WebResource.Builder wb = (WebResource.Builder)wr.header("Authorization", (Object)("Bearer " + token)).header("Content-Type", (Object)"application/json");
        ClientResponse ct = (ClientResponse)wb.post(ClientResponse.class, (Object)jb.toJSONString());
        this.statusCode = ct.getStatus();
        String re = (String)ct.getEntity(String.class);
        return Json.createReader((Reader)new StringReader(re)).readObject();
    }

    @Override
    public String UnLink(String token) {
        Client c = new Client();
        WebResource wr = c.resource("https://api.dropboxapi.com/1/disable_access_token");
        WebResource.Builder wb = wr.header("Authorization", (Object)("Bearer " + token));
        ClientResponse cr = (ClientResponse)wb.post(ClientResponse.class);
        this.statusCode = cr.getStatus();
        String res = (String)cr.getEntity(String.class);
        return res;
    }

    @Override
    public JsonObject Search(String token, String query) {
        JSONObject jb = new JSONObject();
        jb.put((Object)"path", (Object)"");
        jb.put((Object)"query", (Object)query);
        Client c = new Client();
        WebResource wr = c.resource("https://api.dropboxapi.com/2/files/search");
        WebResource.Builder wb = (WebResource.Builder)wr.header("Authorization", (Object)("Bearer " + token)).header("Content-Type", (Object)"application/json");
        ClientResponse cr = (ClientResponse)wb.post(ClientResponse.class, (Object)jb.toJSONString());
        this.statusCode = cr.getStatus();
        String resp = (String)cr.getEntity(String.class);
        JsonReader jr = Json.createReader((Reader)new StringReader(resp));
        JsonObject jObj = jr.readObject();
        return jObj;
    }

    @Override
    public String getURL() throws URISyntaxException {
        URIBuilder ub = new URIBuilder();
        ub.setScheme("https");
        ub.setHost("www.dropbox.com");
        ub.setPath("/1/oauth2/authorize");
        ub.addParameter("response_type", "code");
        ub.addParameter("client_id", "zkmjm8qhr2c8rk8");
        ub.addParameter("redirect_uri", "https://accessitblog.wordpress.com");
        ub.addParameter("state", "369");
        URI u = ub.build();
        return u.toString();
    }

    @Override
    public void Upload(String target, File f, String token) {
        JSONObject jb = new JSONObject();
        jb.put((Object)"path", (Object)(target + "/" + f.getName()));
        Client c = new Client();
        WebResource wr = c.resource("https://content.dropboxapi.com/2/files/upload");
        WebResource.Builder wb = (WebResource.Builder)((WebResource.Builder)wr.header("Authorization", (Object)("Bearer " + token)).header("Content-Type", (Object)"application/octet-stream")).header("Dropbox-API-Arg", (Object)jb.toJSONString());
        ClientResponse cr = (ClientResponse)wb.post(ClientResponse.class, (Object)f);
        this.statusCode = cr.getStatus();
        String resp = (String)cr.getEntity(String.class);
        System.out.println(resp);
    }

    @Override
    public JsonObject getFilesFromPath(String token, String path) {
        if (path.equalsIgnoreCase("root")) {
            path = "";
        }
        JsonObject jObj = Json.createObjectBuilder().add("path", path).build();
        Client c = new Client();
        WebResource wr = c.resource("https://api.dropboxapi.com/2/files/list_folder");
        WebResource.Builder wb = (WebResource.Builder)wr.header("Authorization", (Object)("Bearer " + token)).header("Content-Type", (Object)"application/json");
        ClientResponse cr = (ClientResponse)wb.post(ClientResponse.class, (Object)jObj.toString());
        this.statusCode = cr.getStatus();
        String resp = (String)cr.getEntity(String.class);
        JsonReader jp = Json.createReader((Reader)new StringReader(resp));
        JsonObject response = jp.readObject();
        return response;
    }

    @Override
    public JsonObject createFolder(String token, String path, String folderName) {
        JSONObject jObj = new JSONObject();
        jObj.put((Object)"path", (Object)(path + "/" + folderName));
        Client c = new Client();
        WebResource wr = c.resource("https://api.dropboxapi.com/2/files/create_folder");
        WebResource.Builder wb = (WebResource.Builder)wr.header("Authorization", (Object)("Bearer " + token)).header("Content-Type", (Object)"application/json");
        ClientResponse cr = (ClientResponse)wb.post(ClientResponse.class, (Object)jObj.toString());
        this.statusCode = cr.getStatus();
        String resp = (String)cr.getEntity(String.class);
        JsonReader jp = Json.createReader((Reader)new StringReader(resp));
        JsonObject response = jp.readObject();
        return response;
    }

    @Override
    public String getEmail(String token) {
        Client c = new Client();
        WebResource web = c.resource("https://api.dropboxapi.com/2/users/get_current_account");
        WebResource.Builder build = web.header("Authorization", (Object)("Bearer " + token));
        ClientResponse response = (ClientResponse)build.post(ClientResponse.class);
        this.statusCode = response.getStatus();
        String resp = (String)response.getEntity(String.class);
        JsonReader jr = Json.createReader((Reader)new StringReader(resp));
        JsonObject jObj = jr.readObject();
        String email = jObj.getString("email");
        return email;
    }

    @Override
    public Map<String, Object> getAccessToken(String refToken) throws IOException {
        HashMap<String, Object> m = new HashMap<String, Object>();
        m.put("access_token", refToken);
        return m;
    }

    @Override
    public String shareLink(String token, String path) {
        JsonObject job = Json.createObjectBuilder().add("path", path).build();
        Client c = new Client();
        WebResource wr = c.resource("https://api.dropboxapi.com/2/sharing/create_shared_link");
        WebResource.Builder wb = (WebResource.Builder)wr.header("Authorization", (Object)("Bearer " + token)).header("Content-Type", (Object)"application/json");
        ClientResponse cr = (ClientResponse)wb.post(ClientResponse.class, (Object)job.toString());
        String resp = (String)cr.getEntity(String.class);
        this.statusCode = cr.getStatus();
        JsonReader jr = Json.createReader((Reader)new StringReader(resp));
        JsonObject jObj = jr.readObject();
        return jObj.getString("url");
    }
}

