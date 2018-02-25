import interx.protos.Protocol.Message;
import interx.protos.Protocol.EmptyMessage;
import interx.protos.ServerGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.util.logging.Logger;

public class RpcServer
{
    private static final Logger logger = Logger.getLogger(RpcServer.class.getName());
    private Server server;

    public void start() throws IOException
    {
        /* The port on which the server should run */
        int port = 50051;
        server = ServerBuilder.forPort(port)
                               .addService(new ServerImpl())
                               .build()
                               .start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            @Override
            public void run()
            {
                // Use stderr here since the logger may have been reset by its
                // JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                RpcServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    public void stop()
    {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon
     * threads.
     */
    public void blockUntilShutdown() throws InterruptedException
    {
        if (server != null) {
            server.awaitTermination();
        }
    }

    static class ServerImpl extends ServerGrpc.ServerImplBase
    {
        @Override
        public void send(Message req, StreamObserver<EmptyMessage> responseObserver)
        {
            System.out.println(req.getJson());
            MongoDatabaseManager.getInstance().putDataInCollection(req.getJson(), "test123");
            MongoDatabaseManager.getInstance().printDataInCollection("test123");
            EmptyMessage reply = EmptyMessage.newBuilder().build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }
}
