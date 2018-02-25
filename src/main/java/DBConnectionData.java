
public class DBConnectionData
{
    private String host;
    private Integer port;
    private String user;
    private String password;
    private String dbName;

    public String getHost()
    {
        return host;
    }

    public Integer getPort()
    {
        return port;
    }

    public String getUser()
    {
        return user;
    }

    public String getPassword()
    {
        return password;
    }
    
    public String getDbName()
    {
        return dbName;
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    public void setPort(Integer port)
    {
        this.port = port;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public void setDbName(String dbName)
    {
        this.dbName = dbName;
    }
}
