package fr.si5.cc.td1.users;

import com.google.appengine.api.datastore.Entity;

import java.util.ArrayList;
import java.util.List;

public class UserEntityTranslator {


    public Entity translate(User user) {
        return null;
    }

    public User translate(Entity entity) {
        User user = new User();
        user.setLogin((String)entity.getProperty(UserDao.LOGIN_FIELD));
        user.setCurrentUsage((long)entity.getProperty(UserDao.CURRENT_USAGE_FIELD));
        user.setLevel((long)entity.getProperty(UserDao.LEVEL_FIELD));
        user.setDataUploaded((long)entity.getProperty(UserDao.DATA_UPLOADED_FIELD));
        user.setPassword((String)entity.getProperty(UserDao.PASSWORD_FIELD));
        return user;
    }

    public List<User> translate(List<Entity> entities) {
        List<User> users = new ArrayList<>();
        for (Entity entity: entities) {
            users.add(translate(entity));
        }
        return users;
    }

}
