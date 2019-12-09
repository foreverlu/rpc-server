public class RpcServerApp {

    public static void main(String[] args) {
        RpcServerProxy proxy = new RpcServerProxy();
        IUserService userService = new UserServiceImpl();
        proxy.publish(userService,9001);
    }

}
