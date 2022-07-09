package impl.util.handlers;

import java.io.IOException;

import impl.connection.IConnection;
import impl.packet.IPacketHandler;
import impl.util.ILogger;

public class CUnsupportedHandler implements IPacketHandler {
    private final ILogger logger;
    private final int id;

    public CUnsupportedHandler(ILogger logger, int id) {
        this.logger = logger;
        this.id = id;
    }

    public void handle(IConnection connection, byte[] bytes) throws IOException {
        this.logger.log("Received packet with unsupported id: " + this.id);
    }
}
