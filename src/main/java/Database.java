import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

public class Database
{
    private Logger logger = Logger.getLogger(Database.class.getName());
    private static volatile Connection connection;
    private static volatile Database instance;
    
    private Database() {
        try {
            connection = DriverManager.getConnection(Consts.LINK_DB, Consts.USER_DB, Consts.PASSWORD_DB);
        } catch(SQLException e) {
            logger.log(Level.SEVERE, "Connection to the Database was not successful", e);
        }
        createInitialTables();
    }
    
    /* Singleton */
    public static Database getInstance() {
        Database localInstance = instance;
        if(localInstance == null) {
            synchronized (Database.class) {
                localInstance = instance;
                if(localInstance == null) {
                    instance = localInstance = new Database();
                }
            }
        }
        return instance;
    }
    
    private void createInitialTables() {
        try (Statement statement = connection.createStatement()) {
            connection.setAutoCommit(false); // TODO check
            logger.log(Level.INFO, "Creating (if not already) initial user table");
            statement.executeUpdate(CreateTablesStrings.CREATE_USERS_TABLE);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Creation of initial tables was not successful", e);
        }
    }
}
