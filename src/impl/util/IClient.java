package impl.util;

import impl.connection.IConnection;
import impl.util.IServerList;

public interface IClient extends IConnection {
    IServerList getServerList();
}
