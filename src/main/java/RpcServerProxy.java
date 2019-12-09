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
        Socket socket = null;
        try {

            serverSocket = new ServerSocket(port);
            System.out.println("等待client连接。。");
            while (true){
                socket = serverSocket.accept();
                System.out.println("client 已连接。");
                executorService.execute(new ProcessHandler(socket,service));
                System.out.println("请求结束");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != socket){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
