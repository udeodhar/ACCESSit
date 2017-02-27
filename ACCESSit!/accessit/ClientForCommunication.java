/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.sun.jersey.api.client.Client
 *  com.sun.jersey.api.client.ClientResponse
 *  com.sun.jersey.api.client.WebResource
 *  com.sun.jersey.api.representation.Form
 */
package accessit;

import ServerPackage.UserInfo;
import accessit.FileSystem;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;
import java.io.PrintStream;
import java.util.Map;

public class ClientForCommunication {
    static String URL = "http://sampledeploy-gguaproject.rhcloud.com";

    public static void recv(String rtoken, String rc, String sender, String rec, String otp) {
        Form f = new Form();
        Client c = new Client();
        f.add("send", sender);
        f.add("recv", rec);
        f.add("otp", otp);
        f.add("mode", "recv");
        f.add("rCloud", rc);
        f.add("rToken", rtoken);
        ClientResponse cr = (ClientResponse)c.resource("http://sampledeploy-gguaproject.rhcloud.com").path("/Authenticate").post(ClientResponse.class, (Object)f);
        System.out.println((String)cr.getEntity(String.class));
    }

    public static void send(String stoken, String sc, String sender, String receiver, String otp, String path, String file) {
        Form f = new Form();
        Client c = new Client();
        f.add("send", sender);
        f.add("recv", receiver);
        f.add("otp", otp);
        f.add("mode", "send");
        f.add("sCloud", sc);
        f.add("sToken", stoken);
        f.add("filename", file);
        f.add("sPath", path);
        ClientResponse cr = (ClientResponse)c.resource("http://sampledeploy-gguaproject.rhcloud.com").path("/Authenticate").post(ClientResponse.class, (Object)f);
        System.out.println((String)cr.getEntity(String.class));
    }

    public static void storeDropboxInfo(Map<String, Object> m, String email) {
        String map = new Gson().toJson(m);
        Form f = new Form();
        f.add("email", email);
        f.add("map", map);
        f.add("uName", FileSystem.readFile().getUsername());
        Client c = new Client();
        WebResource wr = c.resource(URL).path("/storeDropBox");
        wr.post(ClientResponse.class, (Object)f);
    }

    public static void storeGDriveInfo(Map<String, Object> m, String email) {
        String map = new Gson().toJson(m);
        Form f = new Form();
        f.add("email", email);
        f.add("map", map);
        f.add("uName", FileSystem.readFile().getUsername());
        Client c = new Client();
        WebResource wr = c.resource(URL).path("/storeGDrive");
        wr.post(ClientResponse.class, (Object)f);
    }

    public static void storeOneDriveInfo(Map<String, Object> m, String email) {
        String map = new Gson().toJson(m);
        Form f = new Form();
        f.add("email", email);
        f.add("map", map);
        f.add("uName", FileSystem.readFile().getUsername());
        Client c = new Client();
        WebResource wr = c.resource(URL).path("/storeODrive");
        wr.post(ClientResponse.class, (Object)f);
    }

    public static boolean sendOTP(String uName) {
        Client c = new Client();
        WebResource wr = c.resource(URL).path("/otpReset").queryParam("uName", uName);
        ClientResponse cr = (ClientResponse)wr.get(ClientResponse.class);
        String s = (String)cr.getEntity(String.class);
        System.out.println(s);
        return Boolean.parseBoolean(s);
    }

    public static boolean editInfo(UserInfo ui) {
        Gson gs = new Gson();
        String gString = gs.toJson((Object)ui);
        Client c = new Client();
        WebResource wr = c.resource(URL).path("/editInfo");
        Form f = new Form();
        f.add("object", gString);
        ClientResponse cr = (ClientResponse)wr.post(ClientResponse.class, (Object)f);
        String s = (String)cr.getEntity(String.class);
        System.out.println(s);
        return Boolean.parseBoolean(s);
    }

    public static boolean resetPassword(String pass, int otp, String username) {
        Client c = new Client();
        WebResource wr = c.resource(URL).path("/passwordReset");
        Form f = new Form();
        f.add("uPass", pass);
        f.add("uName", username);
        f.add("otp", "" + otp + "");
        ClientResponse cr = (ClientResponse)wr.post(ClientResponse.class, (Object)f);
        String s = (String)cr.getEntity(String.class);
        System.out.println(s);
        return Boolean.parseBoolean(s);
    }

    public static UserInfo getUserInfo(String username) {
        Client c = new Client();
        WebResource wr = c.resource(URL).path("/getUserInfo").queryParam("uName", username);
        ClientResponse cr = (ClientResponse)wr.get(ClientResponse.class);
        String s = (String)cr.getEntity(String.class);
        System.out.println("In getUserInfo printing " + s + " string...");
        UserInfo ui = (UserInfo)new Gson().fromJson(s, UserInfo.class);
        System.out.println("UserInfo:\n" + ui);
        return (UserInfo)new Gson().fromJson(s, UserInfo.class);
    }

    public static boolean sendMail(String mail) {
        Client c = new Client();
        WebResource wr = c.resource(URL).path("/mailer");
        Form f = new Form();
        f.add("email", mail);
        ClientResponse cr = (ClientResponse)wr.post(ClientResponse.class, (Object)f);
        String resp = (String)cr.getEntity(String.class);
        System.out.println(resp);
        return Boolean.parseBoolean(resp);
    }

    public static boolean signUpCommunication(String firstName, String lastName, String email, String username, String password, int otp) {
        Client c = new Client();
        WebResource wr = c.resource(URL).path("/signUp");
        Form f = new Form();
        f.add("fName", firstName);
        f.add("lName", lastName);
        f.add("email", email);
        f.add("uName", username);
        f.add("pass", password);
        f.add("otp", (Object)otp);
        ClientResponse cr = (ClientResponse)wr.post(ClientResponse.class, (Object)f);
        String res = (String)cr.getEntity(String.class);
        System.out.println(res);
        return Boolean.parseBoolean(res);
    }

    public static boolean checkUsernameCommunication(String username) {
        Client c = new Client();
        WebResource wr = c.resource(URL).path("/checkUserName").queryParam("uName", username);
        ClientResponse cr = (ClientResponse)wr.get(ClientResponse.class);
        String resp = (String)cr.getEntity(String.class);
        System.out.println(resp);
        return Boolean.parseBoolean(resp);
    }

    public static int logInCommunication(String username, String password) {
        Client c = new Client();
        WebResource wr = c.resource(URL).path("/login");
        Form f = new Form();
        f.add("uName", username);
        f.add("uPass", password);
        ClientResponse cr = (ClientResponse)wr.post(ClientResponse.class, (Object)f);
        String res = (String)cr.getEntity(String.class);
        System.out.println(res);
        return Integer.parseInt(res);
    }

    public static String getToken(int cNo, String email, String username) {
        Client c = new Client();
        WebResource wr = c.resource(URL).path("/getToken");
        Form f = new Form();
        f.add("email", email);
        f.add("uName", username);
        f.add("cNo", (Object)cNo);
        ClientResponse cr = (ClientResponse)wr.post(ClientResponse.class, (Object)f);
        String resp = (String)cr.getEntity(String.class);
        System.out.println(resp);
        return resp;
    }

    public static boolean unlink(int cno, String uname, String email) {
        Client c = new Client();
        WebResource wr = c.resource(URL).path("/unLink");
        Form f = new Form();
        f.add("email", email);
        f.add("uName", uname);
        f.add("cNo", (Object)cno);
        ClientResponse cr = (ClientResponse)wr.post(ClientResponse.class, (Object)f);
        String resp = (String)cr.getEntity(String.class);
        System.out.println(resp);
        return Boolean.parseBoolean(resp);
    }
}

