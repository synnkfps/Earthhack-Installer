package impl.util;

import impl.connection.IConnectionEntry;

public interface IServerList {
    IConnectionEntry[] get();

    void set(IConnectionEntry[] var1);
}
