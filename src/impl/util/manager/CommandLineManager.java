package impl.util.manager;

import java.util.HashMap;
import java.util.Map;

public class CommandLineManager implements ICommandLineHandler {
    private final Map handlers = new HashMap();

    public void handle(String line) {
        String[] command = line.split(" ", 2);
        if (command.length < 1) {
            System.out.println("err");
        } else {
            ICommandHandler handler = (ICommandHandler)this.handlers.get(command[0].toLowerCase());
            if (handler == null) {
                System.out.println("err");
            } else {
                handler.handle(command.length == 1 ? "" : command[1]);
            }
        }
    }

    public void add(String command, ICommandHandler handler) {
        this.handlers.put(command.toLowerCase(), handler);
    }
}
