/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  javax.json.Json
 *  javax.json.JsonNumber
 *  javax.json.JsonObject
 *  javax.json.JsonObjectBuilder
 */
package Helper;

import AccessAPI.AccessAPI;
import Helper.MetaData;
import Helper.PauseResume;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import javax.json.Json;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class Downloader
extends Thread
implements PauseResume {
    public static final int STEP = 3145728;
    private static JsonObject tmpInfo;
    private static String tmpFile;
    private JsonObject job;
    private AccessAPI dwnld;
    private String token;
    private byte[] array;
    private ArrayList<byte[]> Stream;
    private static boolean flag;
    private boolean tFlag = true;

    public Downloader(MetaData mt, AccessAPI obj, String token) {
        this.dwnld = obj;
        this.token = token;
        this.job = Json.createObjectBuilder().add("fileName", mt.getFileName()).add("cloud", mt.getCloud()).add("path", mt.getPath()).add("size", mt.getSize()).add("downloaded", mt.getDownloaded()).build();
        tmpInfo.put((Object)(this.job.getString("path") + "@" + this.job.getString("cloud")), (Object)this.job);
        this.Stream = new ArrayList();
        this.array = new byte[3145728];
        System.out.println((Object)tmpInfo);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void run() {
        block23 : {
            DataOutputStream dop;
            int downloaded = this.job.getInt("downloaded");
            int size = this.job.getInt("size");
            int i = downloaded;
            do {
                try {
                    if (this.tFlag) {
                        System.out.println("******\nDownloaded: " + downloaded + " out of " + size);
                        while (i < size && flag) {
                            if (i - size <= 3145728) {
                                this.array = this.dwnld.Download(this.token, this.job.getString("path"), i, i - size);
                                if (this.dwnld.getStatusCode() != 206) {
                                    return;
                                }
                                i += i - size;
                                break;
                            }
                            this.array = this.dwnld.Download(this.token, this.job.getString("path"), i, i + 3145728);
                            this.Stream.add(this.array);
                            i += 3145728;
                        }
                        if (i == size) {
                            tmpInfo.remove((Object)(this.job.getString("path") + "@" + this.job.getString("cloud")));
                            return;
                        }
                        JsonObject tmp = Json.createObjectBuilder().add("tmp", i).build();
                        this.job.replace((Object)"downloaded", (Object)tmp.getJsonNumber("tmp"));
                        tmpInfo.replace((Object)(this.job.getString("path") + "@" + this.job.getString("cloud")), (Object)this.job);
                        dop = new DataOutputStream(new FileOutputStream(tmpFile, true));
                        dop.writeUTF(tmpInfo.toString());
                        this.wait();
                        continue;
                    }
                    break block23;
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            } while (true);
            finally {
                try {
                    JsonObject tmp = Json.createObjectBuilder().add("tmp", i).build();
                    this.job.replace((Object)"downloaded", (Object)tmp.getJsonNumber("tmp"));
                    tmpInfo.replace((Object)(this.job.getString("path") + "@" + this.job.getString("cloud")), (Object)this.job);
                    dop = new DataOutputStream(new FileOutputStream(tmpFile, true));
                    dop.writeUTF(tmpInfo.toString());
                }
                catch (Exception ex) {
                    ex.fillInStackTrace();
                    throw new Error();
                }
            }
        }
    }

    @Override
    public void onResume() {
        Thread.State st = this.getState();
        if (Thread.State.WAITING == st) {
            flag = true;
            this.notify();
        }
    }

    @Override
    public void onPause() {
        flag = false;
    }

    static {
        block6 : {
            try {
                flag = true;
                tmpFile = System.getProperty("java.io.tmpdir") + File.pathSeparator + "tmpFile";
                File f = new File(tmpFile);
                if (f.exists()) {
                    if (f.length() == 0) {
                        tmpInfo = Json.createObjectBuilder().build();
                        System.out.println((Object)tmpInfo);
                    } else {
                        DataInputStream dis = new DataInputStream(new FileInputStream(f));
                        String s = dis.readUTF();
                        tmpInfo = Json.createReader((Reader)new StringReader(s)).readObject();
                        System.out.println((Object)tmpInfo);
                    }
                    break block6;
                }
                if (f.createNewFile()) {
                    System.out.println("tmpFile created successfully !!");
                    tmpInfo = Json.createObjectBuilder().build();
                    System.out.println((Object)tmpInfo);
                    break block6;
                }
                tmpInfo = null;
                System.out.println("Unable to create tmp file !!");
                throw new Exception("Unable to create file");
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}

