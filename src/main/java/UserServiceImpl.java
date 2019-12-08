public class UserServiceImpl implements IUserService {
    public String sayHello(String name) {
        return "hello " + name;
    }

    public String saveUser(User user) {
        System.out.println(user);
        return "success";
    }
}
