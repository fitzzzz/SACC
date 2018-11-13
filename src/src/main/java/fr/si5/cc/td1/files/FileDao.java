package fr.si5.cc.td1.files;

import com.google.appengine.api.datastore.*;

import java.util.Arrays;
import java.util.List;

import static com.google.appengine.api.datastore.Query.*;


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

    public List<File> findAll() {
        Query query = new Query(FILE);
        FileEntityTranslator translator = new FileEntityTranslator();
        return translator.translate(datastore.prepare(query).asList(FetchOptions.Builder.withDefaults()));
    }

    public File findByUserIdAndFileName(String userId, String fileName) {
        Filter propertyFilter = new CompositeFilter(CompositeFilterOperator.AND,
                Arrays.asList(new FilterPredicate(USER_ID, FilterOperator.EQUAL, userId),
                        new FilterPredicate(FILE_NAME, FilterOperator.EQUAL, fileName))
        );
        Query query = new Query("Person").setFilter(propertyFilter);

        FileEntityTranslator translator = new FileEntityTranslator();
        return translator.translate(datastore.prepare(query).asSingleEntity());
    }

    public void clear() {
        Query query = new Query(FILE);
        datastore.prepare(query).asList(FetchOptions.Builder.withDefaults())
                .forEach(entity -> datastore.delete(entity.getKey()));
    }

}
