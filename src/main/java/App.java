import java.io.IOException;

import com.beust.jcommander.JCommander;

public class App
{
    public static void main(String[] args) throws IOException, InterruptedException
    {
        CommandLineArgs commandLineArgs = new CommandLineArgs();
        
        JCommander.newBuilder()
            .addObject(commandLineArgs)
            .build()
            .parse(args);
        
        DBConnectionData noSqlDBConnectionData = new DBConnectionData();
        noSqlDBConnectionData.setHost(commandLineArgs.noSqlDBHost);
        noSqlDBConnectionData.setPort(commandLineArgs.noSqlDBPort);
        noSqlDBConnectionData.setDbName(commandLineArgs.noSqlDBName);

        MongoDatabaseManager.getInstance().connect(noSqlDBConnectionData);
        
        final RpcServer server = new RpcServer();
        server.start();
        server.blockUntilShutdown();
    }
}
