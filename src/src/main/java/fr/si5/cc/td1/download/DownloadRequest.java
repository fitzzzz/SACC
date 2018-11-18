package fr.si5.cc.td1.download;

public class DownloadRequest {
    private String userMail;
    private String fileName;

    public DownloadRequest() {
    }

    public DownloadRequest(String userMail, String fileName) {
        this();
        this.userMail = userMail;
        this.fileName = fileName;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
