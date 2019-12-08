import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcServerProxy {

    public void publish(Object service, int port){

        ServerSocket serverSocket;
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        try {
            Socket socket;
            serverSocket = new ServerSocket(port);
            while (true){
                socket = serverSocket.accept();
                executorService.execute(new ProcessHandler(socket,port));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
