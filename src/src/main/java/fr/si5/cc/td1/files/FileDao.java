package fr.si5.cc.td1.files;

import com.google.appengine.api.datastore.*;
import fr.si5.cc.td1.users.User;
import fr.si5.cc.td1.users.UserEntityTranslator;

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

    public List<User> findAll() {
        Query query = new Query(FILE);
        UserEntityTranslator translator = new UserEntityTranslator();
        return translator.translate(datastore.prepare(query).asList(FetchOptions.Builder.withDefaults()));
    }

}
