package impl.connection;

import impl.connection.IConnectionListener;
import impl.packet.IPacketManager;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SimpleConnectionManager implements IConnectionManager {
    private final IPacketManager packetManager;
    private final List listeners;
    private final List connections;
    private final int maxConnections;

    public SimpleConnectionManager(IPacketManager packetManager, int maxConnections) {
        this.packetManager = packetManager;
        this.maxConnections = maxConnections;
        this.connections = new CopyOnWriteArrayList();
        this.listeners = new CopyOnWriteArrayList();
    }

    public IPacketManager getHandler() {
        return this.packetManager;
    }

    public boolean accept(IConnection client) {
        if (this.connections.size() >= this.maxConnections) {
            return false;
        } else {
            this.connections.add(client);
            Iterator var2 = this.listeners.iterator();

            while(var2.hasNext()) {
                IConnectionListener listener = (IConnectionListener)var2.next();
                if (listener != null) {
                    listener.onJoin(this, client);
                }
            }

            return true;
        }
    }

    public void remove(IConnection connection) {
        if (connection.isOpen()) {
            ICloseable.close();
        }

        this.connections.remove(connection);
        Iterator var2 = this.listeners.iterator();

        while(var2.hasNext()) {
            IConnectionListener listener = (IConnectionListener)var2.next();
            if (listener != null) {
                listener.onLeave(this, connection);
            }
        }

    }

    public List getConnections() {
        return this.connections;
    }

    public void addListener(IConnectionListener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(IConnectionListener listener) {
        this.listeners.remove(listener);
    }

    public void send(byte[] packet) throws IOException {
        Iterator var2 = this.connections.iterator();

        while(var2.hasNext()) {
            IConnection connection = (IConnection)var2.next();

            try {
                connection.send(packet);
            } catch (IOException var5) {
                this.remove(connection);
                var5.printStackTrace();
            }
        }

    }
}
