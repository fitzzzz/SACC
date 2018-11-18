package fr.si5.cc.td1.files;

public class File {
    private String userId;
    private String fileName;
    private String blobLink;
    private String blobName;

    public File() {
    }

    public File(String userId, String fileName, String blobLink, String blobName) {
        this.userId = userId;
        this.fileName = fileName;
        this.blobLink = blobLink;
        this.blobName = blobName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getBlobLink() {
        return blobLink;
    }

    public void setBlobLink(String blobLink) {
        this.blobLink = blobLink;
    }

    public String getBlobName() {
        return blobName;
    }

    public void setBlobName(String blobName) {
        this.blobName = blobName;
    }
}
