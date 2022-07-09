package impl.util;

import impl.connection.IConnectionEntry;

public class SimpleEntry implements IConnectionEntry {
    private final String name;
    private final int id;

    public SimpleEntry(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }
}
