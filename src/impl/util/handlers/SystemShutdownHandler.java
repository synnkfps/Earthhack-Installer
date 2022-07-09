package impl.util.handlers;

public class SystemShutdownHandler implements IShutDownHandler {
    public void disable(String message) {
        System.out.println(message);
        System.exit(0);
    }
}
