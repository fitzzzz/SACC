package fr.si5.cc.td1.files;

import com.google.appengine.api.datastore.Entity;

import java.util.ArrayList;
import java.util.List;

public class FileEntityTranslator {


    public Entity translate(File file) {
        return null;
    }

    public File translate(Entity entity) {
        File file = new File();
        file.setUserId((String) entity.getProperty(FileDao.USER_ID));
        file.setBlobStoreId((String) entity.getProperty(FileDao.BLOB_STORE));
        file.setFileName((String) entity.getProperty(FileDao.FILE_NAME));
        return file;
    }

    public List<File> translate(List<Entity> entities) {
        List<File> files = new ArrayList<>();
        for (Entity entity : entities) {
            files.add(translate(entity));
        }
        return files;
    }

}
