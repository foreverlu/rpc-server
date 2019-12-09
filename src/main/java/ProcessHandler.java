import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class ProcessHandler implements Runnable {

    private Socket socket;

    private Object service;

    public ProcessHandler(Socket socket, Object service) {
        this.socket = socket;
        this.service = service;
    }

    public ProcessHandler() {
    }

    public void run() {
        ObjectInputStream objectInputStream;
        ObjectOutputStream objectOutputStream;
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            RPCRequest request = (RPCRequest) objectInputStream.readObject();

            Object result = invoke(request);

            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(result);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private Object invoke(RPCRequest request) {
        String methodName = request.getMethod();
        Object[] parameters = request.getParameters();
        Class<?> types[] = new Class[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            types[i] = parameters[i].getClass();
        }

        try {
            Method method = service.getClass().getMethod(methodName, types);
            Object result = method.invoke(service, parameters);
            return result;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}
