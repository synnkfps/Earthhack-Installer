package impl.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public abstract class AbstractConnection implements IConnection {
    protected final Socket socket;
    protected String name;

    public AbstractConnection(String ip, int port) throws IOException {
        this(new Socket(ip, port));
    }

    public AbstractConnection(Socket socket) {
        this.socket = socket;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void send(byte[] packet) throws IOException {
        this.socket.getOutputStream().write(packet);
    }

    public void close() {
        try {
            this.socket.close();
        } catch (IOException var2) {
            var2.printStackTrace();
        }

    }

    public InputStream getInputStream() throws IOException {
        return this.socket.getInputStream();
    }

    public OutputStream getOutputStream() throws IOException {
        return this.socket.getOutputStream();
    }

    public boolean isOpen() {
        return !this.socket.isClosed();
    }
}
