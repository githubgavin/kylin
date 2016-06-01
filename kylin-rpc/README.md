## E.X.
```JAVA
@RemoteService(serviceType = ServiceType.HESSIAN, serviceInterface = HelloService.class, exportPath = "/hello.service")
public class HelloServiceImp implements HelloService {
    public String hello(String username) {
        return username + " hello...";
    }
}
```