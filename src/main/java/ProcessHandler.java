import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ProcessHandler implements Runnable {

    private Socket socket;

    private int port;

    public ProcessHandler(Socket socket, int port) {
        this.socket = socket;
        this.port = port;
    }

    public ProcessHandler(){}

    public void run() {
        ObjectInputStream objectInputStream;
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            RPCRequest request = (RPCRequest) objectInputStream.readObject();

            invoke(request);
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void invoke(RPCRequest request) {
        String className = request.getClassName();
        String method = request.getMethod();
        Object[] parameters = request.getParameters();


    }
}
