package impl.connection;

import java.io.IOException;

import impl.ProtocolUtil;
import impl.packet.IPacketHandler;

public class SUnsupportedHandler
        implements IPacketHandler {
    private final String message;

    public SUnsupportedHandler(String message) {
        this.message = message;
    }

    public void handle(IConnection connection, byte[] bytes) throws IOException {
        ProtocolUtil.sendMessage((IConnection)connection, (int)2, (String)this.message);
    }
}
