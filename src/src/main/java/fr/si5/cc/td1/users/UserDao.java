package fr.si5.cc.td1.users;

import com.google.appengine.api.datastore.*;

import java.util.List;


public class UserDao {

    private String login;
    private String password;
    private int    level;
    private int    dataUploaded;
    private int    currentUsage;

    public static final String LOGIN_FIELD = "login";
    public static final String PASSWORD_FIELD = "password";
    public static final String LEVEL_FIELD = "level";
    public static final String DATA_UPLOADED_FIELD = "data_uploaded";
    public static final String CURRENT_USAGE_FIELD = "current_usage";

    private final String USER_KIND = "User";

    private final DatastoreService datastore;

    public UserDao() {
        datastore =  DatastoreServiceFactory.getDatastoreService();
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

    public List<User> getUsers() {
        Query query = new Query(USER_KIND);
        UserEntityTranslator translator = new UserEntityTranslator();
        return translator.translate(datastore.prepare(query).asList(FetchOptions.Builder.withDefaults()));
    }

}
