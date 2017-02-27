/*
 * Decompiled with CFR 0_118.
 */
package accessit;

import ServerPackage.UserInfo;
import accessit.HomeDirectory;
import accessit.LogInWindowController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class FileSystem {
    static final String dirname = System.getProperty("java.io.tmpdir") + File.separatorChar;

    public static String getHomeDirectory() {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(dirname + "HomeDir.ser"));
            HomeDirectory homeDirectory = (HomeDirectory)ois.readObject();
            return homeDirectory.getDir();
        }
        catch (IOException ex) {
            Logger.getLogger(FileSystem.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        catch (ClassNotFoundException ex) {
            Logger.getLogger(FileSystem.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static void setHomeDirectory(String dir) {
        ObjectOutputStream oos = null;
        HomeDirectory homeDirectory = new HomeDirectory(dir);
        File f = new File(dirname + "HomeDir.ser");
        try {
            f.createNewFile();
            oos = new ObjectOutputStream(new FileOutputStream(dirname + "HomeDir.ser"));
            oos.writeObject(homeDirectory);
        }
        catch (IOException ex) {
            Logger.getLogger(FileSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void create(UserInfo ui) {
        Scanner reader = new Scanner(System.in);
        boolean success = false;
        File directory = new File(dirname + "ACCESSit");
        if (directory.exists()) {
            System.out.println("Directory already exists ...");
        } else {
            System.out.println("Directory not exists, creating now");
            success = directory.mkdir();
            if (success) {
                System.out.printf("Successfully created new directory : %s%n", dirname);
            } else {
                System.out.printf("Failed to create new directory: %s%n", dirname);
            }
        }
        File f = new File(dirname + "ACCESSit" + File.separatorChar + "UserInfo.ser");
        if (f.exists()) {
            System.out.println("File already exists!\nDeleting Previous file.");
            FileSystem.deleteFile(f);
            System.out.println("File successfully deleted!");
        } else {
            System.out.println("Creating file now...");
            try {
                success = f.createNewFile();
            }
            catch (IOException ex) {
                Logger.getLogger(FileSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (success) {
                System.out.printf("Successfully created new file: %s%n", f);
            } else {
                System.out.printf("Failed to create new file: %s%n", f);
            }
        }
        String dir1 = null;
        File directory1 = new File(LogInWindowController.getHomeDirectory() + File.separatorChar + "ACCESSit");
        if (directory1.exists()) {
            System.out.println("Directory already exists ...");
        } else {
            System.out.println("Directory not exists, creating now");
            success = directory1.mkdir();
            if (success) {
                System.out.printf("Successfully created new directory : %s%n", dirname);
            } else {
                System.out.printf("Failed to create new directory: %s%n", dirname);
            }
        }
        for (int i = 0; i < 4; ++i) {
            switch (i) {
                case 0: {
                    dir1 = directory1.toString() + File.separatorChar + "One Drive";
                    break;
                }
                case 1: {
                    dir1 = directory1.toString() + File.separatorChar + "Google Drive";
                    break;
                }
                case 2: {
                    dir1 = directory1.toString() + File.separatorChar + "Dropbox";
                    break;
                }
            }
            File directoryName = new File(dir1);
            if (directoryName.exists()) {
                System.out.println("Directory already exists ...");
                continue;
            }
            System.out.println("Directory not exists, creating now");
            success = directoryName.mkdir();
            if (success) {
                System.out.printf("Successfully created new directory : %s%n", dir1);
                continue;
            }
            System.out.printf("Failed to create new directory: %s%n", dir1);
        }
        reader.close();
        try {
            System.out.println("Loading data into the file...");
            FileOutputStream fout = new FileOutputStream(dirname + "ACCESSit" + File.separatorChar + "UserInfo.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(ui);
            System.out.println("Data successfully written...");
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(FileSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(FileSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static UserInfo readFile() {
        UserInfo ui = null;
        try {
            System.out.println("UserInfo location: " + dirname + "ACCESSit" + File.separatorChar + "UserInfo.ser");
            FileInputStream fin = new FileInputStream(dirname + "ACCESSit" + File.separatorChar + "UserInfo.ser");
            ObjectInputStream ois = new ObjectInputStream(fin);
            ui = (UserInfo)ois.readObject();
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(FileSystem.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("File not found exception.");
        }
        catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(FileSystem.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Class not found exception.");
        }
        System.out.println("Returned " + ui + " from readFile()");
        return ui;
    }

    public static void deleteFile(File f1) {
        File f = new File(dirname + "ACCESSit" + File.separatorChar + "UserInfo.ser");
        System.out.println("File.canWrite(): " + f.canWrite());
        System.out.println("File.canExecute(): " + f.canExecute());
        System.out.println("File.canRead(): " + f.canRead());
        boolean b = f.delete();
        System.out.println("File deleted: " + b);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void copyFileUsingFileStreams(File source, File dest) {
        FileInputStream input = null;
        OutputStream output = null;
        try {
            int bytesRead;
            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        }
        catch (FileNotFoundException ex) {
            Logger.getLogger(FileSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex) {
            Logger.getLogger(FileSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            try {
                input.close();
                output.close();
            }
            catch (IOException ex) {
                Logger.getLogger(FileSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void createFile(UserInfo ui) {
        boolean success1 = false;
        File f = new File(dirname + "ACCESSit" + File.separatorChar + "UserInfo.ser");
        if (f.exists()) {
            System.out.println("File already exists!\nDeleting Previous file.");
            FileSystem.deleteFile(f);
            System.out.println("File successfully deleted!");
        } else {
            System.out.println("Creating file now...");
            try {
                success1 = f.createNewFile();
            }
            catch (IOException ex) {
                Logger.getLogger(FileSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (success1) {
                System.out.printf("Successfully created new file: %s%n", f);
            } else {
                System.out.printf("Failed to create new file: %s%n", f);
            }
        }
    }

    public static void writeMap(Map<String, Object> map) {
        boolean success1 = false;
        File f = new File(dirname + "ACCESSit" + File.separatorChar + "abc.ser");
        if (f.exists()) {
            System.out.println("File already exists!\nDeleting Previous file.");
            FileSystem.deleteFile(f);
            System.out.println("File successfully deleted!");
        } else {
            System.out.println("Creating file now...");
            try {
                success1 = f.createNewFile();
            }
            catch (IOException ex) {
                Logger.getLogger(FileSystem.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (success1) {
                System.out.printf("Successfully created new file: %s%n", f);
            } else {
                System.out.printf("Failed to create new file: %s%n", f);
            }
        }
    }
}

