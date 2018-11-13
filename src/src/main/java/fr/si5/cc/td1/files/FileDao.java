package fr.si5.cc.td1.files;

import com.google.appengine.api.datastore.*;

import java.util.List;


public class FileDao {

    private String userId;
    private String fileName;
    private String BlobStoreId;

    public static final String USER_ID = "user_id";
    public static final String FILE_NAME = "file_name";
    public static final String BLOB_STORE = "blob_store";

    private final String FILE = "file";

    private final DatastoreService datastore;

    public FileDao() {
        datastore = DatastoreServiceFactory.getDatastoreService();
    }

    public Entity save(File file) {
        Key userKey = KeyFactory.createKey(FILE, file.getFileName());

        // Place greeting in the same entity group as guestbook.
        Entity fileEntity = new Entity(FILE, userKey);
        fileEntity.setProperty(USER_ID, file.getUserId());
        fileEntity.setProperty(FILE_NAME, file.getFileName());
        fileEntity.setProperty(BLOB_STORE, file.getBlobStoreId());

        datastore.put(fileEntity);
        return fileEntity;
    }

    public File findByFilename(String filename) {
        List<File> files = findAll();
        System.out.println("Number of file : " + files.size());
        for (File file: files) {
            System.out.println(file.getFileName());
            if (file.getFileName().equals(filename)) {
                return file;
            }
        }
        return null;
    }

    public List<File> findAll() {
        Query query = new Query(FILE);
        FileEntityTranslator translator = new FileEntityTranslator();
        return translator.translate(datastore.prepare(query).asList(FetchOptions.Builder.withDefaults()));
    }

}
