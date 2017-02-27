/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  javax.json.JsonArray
 *  javax.json.JsonObject
 */
package accessit;

import java.util.ArrayList;
import java.util.HashMap;
import javax.json.JsonArray;
import javax.json.JsonObject;

public class JSONHandler {
    public static Object extractFiles(JsonObject jObject, int cloud) {
        ArrayList<String> al = new ArrayList<String>();
        HashMap<String, String> map = new HashMap<String, String>();
        if (cloud == 0) {
            JsonArray jArr = jObject.getJsonArray("entries");
            for (int i = 0; i < jArr.size(); ++i) {
                JsonObject tmp = jArr.getJsonObject(i);
                String tag = tmp.getString(".tag");
                if (!tag.equalsIgnoreCase("file")) continue;
                al.add(tmp.getString("name"));
            }
            return al;
        }
        if (cloud == 1) {
            JsonArray jArr = jObject.getJsonArray("items");
            for (int i = 0; i < jArr.size(); ++i) {
                JsonObject tmp = jArr.getJsonObject(i);
                String type = tmp.getString("mimeType");
                JsonObject labels = tmp.getJsonObject("labels");
                if (type.equalsIgnoreCase("application/vnd.google-apps.folder") || labels.getBoolean("trashed")) continue;
                map.put(tmp.getString("id"), tmp.getString("title"));
            }
            return map;
        }
        if (cloud == 2) {
            JsonArray jArr = jObject.getJsonArray("value");
            for (int i = 0; i < jArr.size(); ++i) {
                JsonObject tmp = jArr.getJsonObject(i);
                if (!tmp.containsKey((Object)"file")) continue;
                al.add(tmp.getString("name"));
            }
            return al;
        }
        return null;
    }

    public static Object extractFolder(JsonObject jObject, int cloud) {
        ArrayList<String> al = new ArrayList<String>();
        HashMap<String, String> map = new HashMap<String, String>();
        if (cloud == 0) {
            JsonArray jArr = jObject.getJsonArray("entries");
            for (int i = 0; i < jArr.size(); ++i) {
                JsonObject tmp = jArr.getJsonObject(i);
                String tag = tmp.getString(".tag");
                if (!tag.equalsIgnoreCase("folder")) continue;
                al.add(tmp.getString("name"));
            }
            return al;
        }
        if (cloud == 1) {
            JsonArray jArr = jObject.getJsonArray("items");
            for (int i = 0; i < jArr.size(); ++i) {
                JsonObject tmp = jArr.getJsonObject(i);
                JsonObject labels = tmp.getJsonObject("labels");
                String type = tmp.getString("mimeType");
                if (!type.equalsIgnoreCase("application/vnd.google-apps.folder") || labels.getBoolean("trashed")) continue;
                map.put(tmp.getString("id"), tmp.getString("title"));
            }
            return map;
        }
        if (cloud == 2) {
            JsonArray jArr = jObject.getJsonArray("value");
            for (int i = 0; i < jArr.size(); ++i) {
                JsonObject tmp = jArr.getJsonObject(i);
                if (!tmp.containsKey((Object)"folder")) continue;
                al.add(tmp.getString("name"));
            }
            return al;
        }
        return null;
    }
}

