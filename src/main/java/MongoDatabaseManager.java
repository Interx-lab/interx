import java.util.logging.Logger;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public class MongoDatabaseManager implements NoSqlDatabaseManager
{   
    Logger logger = Logger.getLogger(MongoClient.class.getName());
    private static volatile MongoDatabaseManager instance;
    private static volatile MongoClient connection;
    private static volatile MongoDatabase database;
    
    private MongoDatabaseManager() {}
    
    /* Singleton */
    public static MongoDatabaseManager getInstance() {
        MongoDatabaseManager localInstance = instance;
        if(localInstance == null) {
            synchronized (MongoDatabaseManager.class) {
                localInstance = instance;
                if(localInstance == null) {
                    instance = localInstance = new MongoDatabaseManager();
                }
            }
        }
        return instance;
    }

    /* Connects to a database. Should be called only once. */
    @Override
    public void connect(DBConnectionData connectionData) {
        if(connection != null) {
            throw new AssertionError("The connection was already established");
        }
        logger.info("Connecting to MongoDB..");
        connection = new MongoClient(connectionData.getHost(), connectionData.getPort());
        logger.fine("Connected to MongoDB");
        database = connection.getDatabase(connectionData.getDbName());
        logger.fine("Database" + connectionData.getDbName() + " was selected");
    }
    
    @Override
    public void putDataInCollection(String json, String collectionName) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document document = Document.parse(json);
        collection.insertOne(document);
    }
    
    public long countDataInCollection(String collectionName) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        return collection.count();
    }
    
    public void printDataInCollection(String collectionName) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        MongoCursor<Document> cursor = collection.find().iterator();
        try {
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        } finally {
            cursor.close();
        }   
    }
}
