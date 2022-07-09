package impl.util.host;

import impl.connection.ICloseable;
import impl.connection.IConnectionManager;

public interface IHost extends ICloseable {
    IConnectionManager getConnectionManager();

    int getPort();
}
