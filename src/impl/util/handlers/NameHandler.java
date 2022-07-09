package impl.util.handlers;

import java.nio.charset.StandardCharsets;

import impl.connection.IConnection;
import impl.packet.IPacketHandler;
import impl.util.ILogger;
public class NameHandler implements IPacketHandler {
    private final ILogger logger;

    public NameHandler(ILogger logger) {
        this.logger = logger;
    }

    public void handle(IConnection connection, byte[] bytes) {
        String name = new String(bytes, StandardCharsets.UTF_8);
        this.logger.log("Connection: " + connection.getId() + " previously (" + connection.getName() + ") set it's name to: " + name + ".");
        connection.setName(name);
    }
}
