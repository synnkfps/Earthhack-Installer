package impl.util.handlers;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

import impl.connection.IConnection;
import impl.connection.IConnectionManager;
import impl.packet.IPacketHandler;
import impl.util.ILogger;

public class GlobalMessageHandler implements IPacketHandler {
    private final IConnectionManager manager;
    private final ILogger logger;

    public GlobalMessageHandler(ILogger logger, IConnectionManager manager) {
        this.logger = logger;
        this.manager = manager;
    }

    public void handle(IConnection connection, byte[] bytes) throws IOException {
        String message = new String(bytes, StandardCharsets.UTF_8);
        this.logger.log(message);
        ByteBuffer buffer = ByteBuffer.allocate(bytes.length + 8);
        buffer.putInt(2);
        buffer.putInt(bytes.length);
        buffer.put(bytes);
        byte[] globalMessage = buffer.array();
        Iterator var6 = this.manager.getConnections().iterator();

        while(var6.hasNext()) {
            IConnection conn = (IConnection)var6.next();
            if (conn != null && !conn.equals(connection)) {
                conn.send(globalMessage);
            }
        }

    }
}
