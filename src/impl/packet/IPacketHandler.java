package impl.packet;

import impl.connection.IConnection;

import java.io.IOException;

public interface IPacketHandler {
    void handle(IConnection var1, byte[] var2) throws IOException;
}
