package impl;

import java.io.IOException;

import impl.command.MessageCommand;
import impl.connection.SimplePacketManager;
import impl.packet.IPacketManager;
import impl.util.*;
import impl.util.handlers.BaseCommandLineHandler;
import impl.util.handlers.CUnsupportedHandler;
import me.earth.earthhack.impl.modules.client.server.protocol.handlers.MessageHandler;

public class ClientMain {
    public static void main(String[] args) throws IOException {
        if (args.length < 4) {
            throw new IllegalArgumentException("Ip and port and name are missing!");
        } else {
            String ip = args[1];
            int port = Integer.parseInt(args[2]);
            ILogger log = new SystemLogger();
            log.log("Attempting to connect to: " + ip + ", " + port);
            IPacketManager manager = new SimplePacketManager();
            manager.add(2, new MessageHandler(log));
            manager.add(4, new MessageHandler(log, (s) -> {
                return "global: " + s;
            }));
            manager.add(6, new MessageHandler(log, (s) -> {
                return "error: " + s;
            }));
            manager.add(1, new MessageHandler(log, (s) -> {
                return "command: " + s;
            }));
            int[] var5 = Protocol.ids();
            int var6 = var5.length;

            for(int var7 = 0; var7 < var6; ++var7) {
                int id = var5[var7];
                if (manager.getHandlerFor(id) == null) {
                    manager.add(id, new CUnsupportedHandler(log, id));
                }
            }

            IServerList serverList = new SimpleServerList();
            Client client = new Client(manager, serverList, ip, port);
            GlobalExecutor.EXECUTOR.submit(client);
            log.log("Client connected. Enter \"exit\" or \"stop\" to exit.");
            log.log("Setting name to " + args[3] + "...");
            client.setName(args[3]);
            client.send(ProtocolUtil.writeString(0, args[3]));
            BaseCommandLineHandler commands = new BaseCommandLineHandler(client);
            commands.add("msg", new MessageCommand(client, 2));
            commands.add("message", new MessageCommand(client, 2));
            commands.add("name", new MessageCommand(client, 0));
            commands.add("global", new MessageCommand(client, 4));
            commands.startListening();
        }
    }
}
