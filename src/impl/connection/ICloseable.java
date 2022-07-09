package impl.connection;

import java.util.function.Consumer;

public interface ICloseable {
    static Consumer close() {

        return null;
    }

    boolean isOpen();
}
