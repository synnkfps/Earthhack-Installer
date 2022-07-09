package impl.util.host;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import impl.connection.ICloseable;
import impl.connection.IConnectionManager;
import impl.util.Connection;
import impl.util.handlers.IShutDownHandler;
import me.earthhack.installer.util.SafeRunnable;

public final class Host implements SafeRunnable, IHost {
    private final IConnectionManager manager;
    private final ExecutorService service;
    private final IShutDownHandler module;
    private final ServerSocket socket;
    private final boolean receive;
    private Future future;

    private Host(IConnectionManager connectionManager, ExecutorService service, IShutDownHandler module, int port, boolean receive) throws IOException {
        this.socket = new ServerSocket(port);
        this.service = service;
        this.manager = connectionManager;
        this.module = module;
        this.receive = receive;
    }

    public void runSafely() throws Throwable {
        while(!this.future.isCancelled()) {
            Socket client = this.socket.accept();
            Connection connection = new Connection(this.manager, client);
            if (!this.manager.accept(connection)) {
                client.close();
            } else if (this.receive) {
                this.service.submit(connection);
            }
        }

    }

    public void handle(Throwable t) {
        this.module.disable(t.getMessage());
    }

    public int getPort() {
        return this.socket.getLocalPort();
    }

    public IConnectionManager getConnectionManager() {
        return this.manager;
    }

    public void close() {
        if (this.future != null) {
            this.future.cancel(true);
        }

        if (this.isOpen()) {
            try {
                this.socket.close();
            } catch (IOException var2) {
                var2.printStackTrace();
            }
        }

        this.manager.getConnections().forEach(ICloseable.close());
        this.manager.getConnections().clear();
    }

    public boolean isOpen() {
        return !this.socket.isClosed();
    }

    public void setFuture(Future future) {
        this.future = future;
    }

    public static Host createAndStart(ExecutorService service, IConnectionManager manager, IShutDownHandler module, int port, boolean receive) throws IOException {
        Host host = new Host(manager, service, module, port, receive);
        host.setFuture(service.submit(host));
        return host;
    }
}
