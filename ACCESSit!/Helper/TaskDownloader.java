/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  javafx.concurrent.Task
 */
package Helper;

import AccessAPI.AccessAPI;
import Helper.MetaData;
import Helper.PauseResume;
import Helper.TemperoryDataStore;
import accessit.LogInWindowController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Task;

public class TaskDownloader
extends Task
implements PauseResume {
    public static final int STEP = 131072;
    private static String tmpFile;
    private AccessAPI dwnld;
    private String token;
    private byte[] array;
    private ArrayList<byte[]> Stream;
    private static boolean flag;
    private boolean tFlag = true;
    private static TemperoryDataStore tmpInfo;
    private Map<String, Object> job;
    File f;
    FileOutputStream fout;

    public TaskDownloader(MetaData mt, AccessAPI obj, String token) {
        String temp = null;
        switch (mt.getCloud()) {
            case 0: {
                temp = "Dropbox";
                break;
            }
            case 1: {
                temp = "Google Drive";
                break;
            }
            case 2: {
                temp = "One Drive";
            }
        }
        System.out.println("mt.getCloud(): " + mt.getCloud() + "\ntemp = " + temp);
        this.f = new File(LogInWindowController.getHomeDirectory() + File.separatorChar + "ACCESSit" + File.separatorChar + temp + File.separatorChar + mt.getFileName());
        try {
            this.fout = new FileOutputStream(this.f);
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(TaskDownloader.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dwnld = obj;
        this.token = token;
        this.job = new HashMap<String, Object>();
        this.job.put("fileName", mt.getFileName());
        this.job.put("cloud", mt.getCloud());
        this.job.put("path", mt.getPath());
        this.job.put("size", mt.getSize());
        this.job.put("downloaded", mt.getDownloaded());
        tmpInfo.getMap().put("a", "b");
        tmpInfo.getMap().put((String)this.job.get("path") + "@" + (Integer)this.job.get("cloud"), this.job);
        this.Stream = new ArrayList();
        this.array = new byte[131072];
        System.out.println(tmpInfo);
    }

    /*
     * Exception decompiling
     */
    protected Integer call() throws Exception {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 23[SIMPLE_IF_TAKEN]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:397)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:449)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:2877)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:825)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:217)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:162)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:95)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:355)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:768)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:700)
        // org.benf.cfr.reader.Main.doJar(Main.java:134)
        // org.benf.cfr.reader.Main.main(Main.java:189)
        throw new IllegalStateException("Decompilation failed");
    }

    @Override
    public void onPause() {
        flag = false;
    }

    @Override
    public void onResume() {
        flag = true;
    }

    static {
        block6 : {
            try {
                flag = true;
                tmpFile = System.getProperty("java.io.tmpdir") + File.separatorChar + "tmpFile";
                System.out.println("tmpFile: " + tmpFile);
                File f = new File(tmpFile);
                if (f.exists()) {
                    if (f.length() == 0) {
                        tmpInfo = new TemperoryDataStore();
                        System.out.println(tmpInfo);
                    } else {
                        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
                        tmpInfo = (TemperoryDataStore)ois.readObject();
                        System.out.println(tmpInfo);
                    }
                    break block6;
                }
                if (f.createNewFile()) {
                    System.out.println("tmpFile created successfully !!");
                    tmpInfo = new TemperoryDataStore();
                    System.out.println(tmpInfo);
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

