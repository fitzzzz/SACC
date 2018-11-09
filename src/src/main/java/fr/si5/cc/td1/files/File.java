package fr.si5.cc.td1.files;

public class File {
    private String userId;
    private String fileName;
    private String BlobStoreId;

    public File() {
    }

    public File(String userId, String fileName, String blobStoreId) {
        this.userId = userId;
        this.fileName = fileName;
        BlobStoreId = blobStoreId;
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

    public String getBlobStoreId() {
        return BlobStoreId;
    }

    public void setBlobStoreId(String blobStoreId) {
        BlobStoreId = blobStoreId;
    }
}
