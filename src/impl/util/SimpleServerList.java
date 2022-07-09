package impl.util;

import impl.connection.IConnectionEntry;

public class SimpleServerList implements IServerList {
    private IConnectionEntry[] entries = new IConnectionEntry[0];

    public IConnectionEntry[] get() {
        return this.entries;
    }

    public void set(IConnectionEntry[] entries) {
        this.entries = entries;
    }
}
