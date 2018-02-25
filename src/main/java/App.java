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
        
        MongoDatabase.getInstance().connect(commandLineArgs.noSqlDBHost, commandLineArgs.noSqlDBPort);
        
        final RpcServer server = new RpcServer();
        server.start();
        server.blockUntilShutdown();
    }
}
