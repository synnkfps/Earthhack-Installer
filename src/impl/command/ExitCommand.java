package impl.command;

import impl.connection.ICloseable;
import impl.util.manager.ICommandHandler;

public class ExitCommand implements ICommandHandler {
    private final ICloseable closeable;

    public ExitCommand(ICloseable closeable) {
        this.closeable = closeable;
    }

    public void handle(String command) {
        System.out.println("Bye!");
        ICloseable.close();
        System.exit(0);
    }
}
