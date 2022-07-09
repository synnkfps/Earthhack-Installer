package impl.util;

import java.io.DataInputStream;
import java.io.IOException;

import impl.ProtocolUtil;
import impl.connection.AbstractConnection;
import impl.packet.IPacket;
import impl.packet.IPacketManager;
import me.earthhack.installer.util.SafeRunnable;

public final class Client extends AbstractConnection implements SafeRunnable, IClient {
    private final IPacketManager manager;
    private final IServerList serverList;

    public Client(IPacketManager manager, IServerList serverList, String ip, int port) throws IOException {
        super(ip, port);
        this.manager = manager;
        this.serverList = serverList;
    }

    public void runSafely() throws Throwable {
        DataInputStream in = new DataInputStream(this.getInputStream());
        Throwable var2 = null;

        try {
            while(this.isOpen()) {
                IPacket packet = ProtocolUtil.readPacket(in);
                this.manager.handle(this, packet.getId(), packet.getBuffer());
            }
        } catch (Throwable var11) {
            var2 = var11;
            throw var11;
        } finally {
            if (in != null) {
                if (var2 != null) {
                    try {
                        in.close();
                    } catch (Throwable var10) {
                        var2.addSuppressed(var10);
                    }
                } else {
                    in.close();
                }
            }

        }

    }

    public void handle(Throwable t) {
        t.printStackTrace();
        this.close();
    }

    public IServerList getServerList() {
        return this.serverList;
    }
}
