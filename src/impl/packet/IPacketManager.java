package impl.packet;

import impl.UnknownProtocolException;
import impl.connection.IConnection;

import java.io.IOException;

public interface IPacketManager {
    void handle(IConnection var1, int var2, byte[] var3) throws UnknownProtocolException, IOException;

    void add(int var1, IPacketHandler var2);

    IPacketHandler getHandlerFor(int var1);
}
