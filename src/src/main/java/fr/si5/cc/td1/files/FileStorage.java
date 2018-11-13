package fr.si5.cc.td1.files;

import com.google.cloud.storage.*;
import org.joda.time.DateTime;

import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileStorage {

    private static FileStorage instance;

    private final Storage storage;
    private static final String BUCKET_NAME = "projet-sacc-bucket";

    private FileStorage() {
        storage = StorageOptions.getDefaultInstance().getService();
    }

    public static FileStorage getInstance() {
        if (instance == null) {
            instance = new FileStorage();
        }
        return instance;
    }

    /**
     * Uploads a file to Google Cloud Storage to the bucket specified in the BUCKET_NAME
     * environment variable, appending a timestamp to end of the uploaded filename.
     */
    @SuppressWarnings("deprecation")
    public String uploadFile(Part filePart) throws IOException {
        final String fileName = filePart.getSubmittedFileName() + DateTime.now().toString();

        // the inputstream is closed by default, so we don't need to close it here
        BlobInfo blobInfo =
                storage.create(
                        BlobInfo
                                .newBuilder(FileStorage.BUCKET_NAME, fileName)
                                // Modify access list to allow all users with link to read file
                                .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER))))
                                .build(),
                        filePart.getInputStream());
        // return the public download link
        return blobInfo.getMediaLink();
    }

    public void deleteFile(String blobName){
        BlobId blobId = BlobId.of(BUCKET_NAME, blobName);
        storage.delete(blobId);
    }

    public void clear(List<String> blobsNames) {

        blobsNames.forEach(this::deleteFile);
    }
}
