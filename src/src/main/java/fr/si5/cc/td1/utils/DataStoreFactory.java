package fr.si5.cc.td1.utils;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceConfig;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.ReadPolicy;

public class DataStoreFactory {

    public static DatastoreService constructConsistentDataStore() {
        // Construct a read policy for consistency
        ReadPolicy policy = new ReadPolicy(ReadPolicy.Consistency.STRONG);

        // Set the read policy
        DatastoreServiceConfig datastoreConfig=
                DatastoreServiceConfig.Builder.withReadPolicy(policy);

        return DatastoreServiceFactory.getDatastoreService(datastoreConfig);
    }
}
