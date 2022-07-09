package impl.util.handlers;

import impl.connection.ICloseable;
import impl.util.manager.CommandLineManager;
import impl.command.ExitCommand;

import java.io.IOError;
import java.util.Scanner;


public class BaseCommandLineHandler extends CommandLineManager {
    private final ICloseable closeable;

    public BaseCommandLineHandler(ICloseable closeable) {
        this.closeable = closeable;
        this.add("exit", new ExitCommand(closeable));
        this.add("stop", new ExitCommand(closeable));
        this.add("bye", new ExitCommand(closeable));
    }

    public void startListening() {
        Thread thread = Thread.currentThread();
        Scanner scanner = new Scanner(System.in);
        Throwable var3 = null;

        try {
            while(!thread.isInterrupted() && this.closeable.isOpen()) {
                String input = scanner.nextLine();

                try {
                    this.handle(input);
                } catch (IOError var14) {
                    System.out.println(var14.getMessage());
                }
            }
        } catch (Throwable var15) {
            var3 = var15;
            throw var15;
        } finally {
            if (scanner != null) {
                if (var3 != null) {
                    try {
                        scanner.close();
                    } catch (Throwable var13) {
                        var3.addSuppressed(var13);
                    }
                } else {
                    scanner.close();
                }
            }

        }

    }
}
