/*
 * Decompiled with CFR 0_118.
 */
package Helper;

public class MetaData {
    private String fileName;
    private int cloud;
    private String path;
    private int size;
    private int downloaded;

    public MetaData() {
        this.size = 0;
        this.downloaded = 0;
        this.cloud = 0;
        this.path = "";
        this.fileName = "";
    }

    public MetaData(String fileName, int cloud, String path, int size, int downloaded) {
        this.fileName = fileName;
        this.cloud = cloud;
        this.path = path;
        this.size = size;
        this.downloaded = downloaded;
    }

    public int getCloud() {
        return this.cloud;
    }

    public int getDownloaded() {
        return this.downloaded;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getPath() {
        return this.path;
    }

    public int getSize() {
        return this.size;
    }

    public void setCloud(int cloud) {
        this.cloud = cloud;
    }

    public void setDownloaded(int downloaded) {
        this.downloaded = downloaded;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setSize(int size) {
        this.size = size;
    }
}

