package impl;

import java.io.IOException;

import impl.connection.SUnsupportedHandler;
import impl.connection.SimpleConnectionManager;
import impl.connection.SimplePacketManager;
import impl.util.SystemLogger;
import impl.util.handlers.*;
import impl.util.host.Host;

public class ServerMain {
    public static void main(String[] args) throws IOException {
        int port = 0;
        if (args.length > 1) {
            port = Integer.parseInt(args[1]);
        }
        int t = (int)((double)Runtime.getRuntime().availableProcessors() / 1.5) - 1;
        SystemLogger logger = new SystemLogger();
        SimplePacketManager pManager = new SimplePacketManager();
        SimpleConnectionManager cManager = new SimpleConnectionManager(pManager, t);
        pManager.add(0, new NameHandler(logger));
        pManager.add(2, new me.earth.earthhack.impl.modules.client.server.protocol.handlers.MessageHandler(logger));
        pManager.add(4, new GlobalMessageHandler(logger, cManager));
        for (int id : Protocol.ids()) {
            if (pManager.getHandlerFor(id) != null) continue;
            pManager.add(id, new SUnsupportedHandler("This is a command-line server. This type of packet is not supported!"));
        }
        Host host = Host.createAndStart(GlobalExecutor.FIXED_EXECUTOR, cManager, new SystemShutdownHandler(), port, true);
        System.out.println("Listening on port: " + host.getPort() + ". Enter \"exit\" or \"stop\" to exit.");
        BaseCommandLineHandler commandLine = new BaseCommandLineHandler(host);
        commandLine.startListening();
    }
}