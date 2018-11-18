package fr.si5.cc.td1.users;

import com.google.appengine.api.datastore.*;
import fr.si5.cc.td1.utils.DataStoreFactory;

import java.util.List;


public class UserDao {

    private String login;
    private String password;
    private int level;
    private int dataUploaded;
    private int currentUsage;

    public static final String LOGIN_FIELD = "login";
    public static final String PASSWORD_FIELD = "password";
    public static final String LEVEL_FIELD = "level";
    public static final String DATA_UPLOADED_FIELD = "data_uploaded";
    public static final String CURRENT_USAGE_FIELD = "current_usage";

    private final String USER_KIND = "User";

    private final DatastoreService datastore;

    public UserDao() {
        datastore = DataStoreFactory.constructConsistentDataStore();
    }

    public Entity persistUser(User user) {
        Key userKey = KeyFactory.createKey(USER_KIND, user.getLogin());

        // Place greeting in the same entity group as guestbook.
        Entity userEntity = new Entity(USER_KIND, userKey);
        userEntity.setProperty(LOGIN_FIELD, user.getLogin());
        userEntity.setProperty(PASSWORD_FIELD, user.getPassword());
        userEntity.setProperty(LEVEL_FIELD, user.getLevel());
        userEntity.setProperty(DATA_UPLOADED_FIELD, user.getDataUploaded());
        userEntity.setProperty(CURRENT_USAGE_FIELD, user.getCurrentUsage());

        datastore.put(userEntity);
        return userEntity;
    }


    public void updateEntity(User user) {
        Entity userEntity = getUseEntityByLogin(user.getLogin());

        userEntity.setProperty(LOGIN_FIELD, user.getLogin());
        userEntity.setProperty(PASSWORD_FIELD, user.getPassword());
        userEntity.setProperty(LEVEL_FIELD, user.getLevel());
        userEntity.setProperty(DATA_UPLOADED_FIELD, user.getDataUploaded());
        userEntity.setProperty(CURRENT_USAGE_FIELD, user.getCurrentUsage());
        datastore.put(userEntity);
    }

    private Entity getUseEntityByLogin(String login) {
        Query query = new Query(USER_KIND);
        List<Entity> entities = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());

        for (Entity entity : entities) {
            if ((entity.getProperty(LOGIN_FIELD)).equals(login)) {
                return entity;
            }
        }
        return null;
    }


    public List<User> getUsers() {
        Query query = new Query(USER_KIND);
        UserEntityTranslator translator = new UserEntityTranslator();
        return translator.translate(datastore.prepare(query).asList(FetchOptions.Builder.withDefaults()));
    }

    public User getUserByLogin(String login) {
        Query query = new Query(USER_KIND);
        UserEntityTranslator translator = new UserEntityTranslator();
        List<User> users = translator.translate(datastore.prepare(query).asList(FetchOptions.Builder.withDefaults()));
        for (User user : users) {
            if (user.getLogin().equals(login)) return user;
        }
        return null;
    }

    public List<User> getUsersOrderByScore() {
        Query query = new Query(USER_KIND);
        query.addSort(DATA_UPLOADED_FIELD, Query.SortDirection.DESCENDING);
        UserEntityTranslator translator = new UserEntityTranslator();
        return translator.translate(datastore.prepare(query).asList(FetchOptions.Builder.withDefaults()));
    }

    public void clear() {
        Query query = new Query(USER_KIND);
        datastore.prepare(query).asList(FetchOptions.Builder.withDefaults())
                .forEach(entity -> datastore.delete(entity.getKey()));
    }

}
