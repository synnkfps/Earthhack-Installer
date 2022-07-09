package impl.util;

public class SystemLogger
        implements ILogger {
    public void log(String message) {
        System.out.println(message);
    }
}
