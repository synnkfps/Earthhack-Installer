package impl.connection;

import impl.ISender;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface IConnection extends IConnectionEntry, ICloseable, ISender {
    void setName(String var1);

    InputStream getInputStream() throws IOException;

    OutputStream getOutputStream() throws IOException;
}
