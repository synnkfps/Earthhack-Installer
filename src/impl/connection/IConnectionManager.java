package impl.connection;

import impl.ISender;
import impl.packet.IPacketManager;

import java.util.List;

public interface IConnectionManager extends ISender {
    IPacketManager getHandler();

    boolean accept(IConnection var1);

    void remove(IConnection var1);

    List getConnections();

    void addListener(IConnectionListener var1);

    void removeListener(IConnectionListener var1);
}
