package impl.connection;

import interfaces.Nameable;

public interface IConnectionEntry extends Nameable {
    default int getId() {
        return 0;
    }


}
