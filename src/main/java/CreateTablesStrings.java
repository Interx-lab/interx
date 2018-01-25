public class CreateTablesStrings
{
    public final static String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS users ("
            + "user_id INTEGER PRIMARY KEY,"
            + "last_activity TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,"
            + "reg_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
            + "email VARCHAR(255) NOT NULL,"
            + "first_name VARCHAR(255),"
            + "last_name VARCHAR(255),"
            + "password VARCHAR(255),"
            + "CONSTRAINT constr1 UNIQUE(user_id))";
}