public interface NoSqlDatabaseManager
{
    public void connect(DBConnectionData connectionData);
    public void putDataInCollection(String json, String collectionName);
}
