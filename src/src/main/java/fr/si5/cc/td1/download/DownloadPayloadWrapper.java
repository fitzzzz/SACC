package fr.si5.cc.td1.download;

public class DownloadPayloadWrapper {

    String mail;
    String blobLink;

    public DownloadPayloadWrapper() {
    }

    public DownloadPayloadWrapper(String mail, String blobLink) {
        this.mail = mail;
        this.blobLink = blobLink;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getBlobLink() {
        return blobLink;
    }

    public void setBlobLink(String blobLink) {
        this.blobLink = blobLink;
    }

}
