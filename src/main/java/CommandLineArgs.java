import com.beust.jcommander.Parameter;

public class CommandLineArgs
{
    @Parameter(names = "--nosqldbhost", description = "Host name of NoSQL database you wish to connect")
    public String noSqlDBHost = "localhost";
    
    @Parameter(names = "--nosqldbport", description = "Port number of NoSQL database you wish to connect")
    public Integer noSqlDBPort = 27017;
}
