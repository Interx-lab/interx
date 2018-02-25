import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.mongodb.MongoClient;

public class MongoDatabase
{   
    Logger logger = Logger.getLogger(MongoClient.class.getName());
    private static volatile MongoDatabase instance;
    private static volatile MongoClient connection;
    
    private MongoDatabase() {}
    
    /* Singleton */
    public static MongoDatabase getInstance() {
        MongoDatabase localInstance = instance;
        if(localInstance == null) {
            synchronized (MongoDatabase.class) {
                localInstance = instance;
                if(localInstance == null) {
                    instance = localInstance = new MongoDatabase();
                }
            }
        }
        return instance;
    }

    /* Connects to a database with arguments in parameter list. Should be called only once. */
    public void connect(String host, int port) {
        if(connection != null) {
            throw new AssertionError("The connection was already established");
        }
        try {
            connection = new MongoClient(host, port);
        } catch (UnknownHostException e) {
            logger.log(Level.SEVERE, "The connection to NoSQL Database was not successful.\n"
                    + "Is your connection data correct?");
            throw new RuntimeException(e);
        }
    }
    
}
