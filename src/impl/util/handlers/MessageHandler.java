package me.earth.earthhack.impl.modules.client.server.protocol.handlers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

import impl.connection.IConnection;
import impl.packet.IPacketHandler;
import impl.util.ILogger;

public class MessageHandler implements IPacketHandler {
    private final Function format;
    private final ILogger logger;

    public MessageHandler(ILogger logger) {
        this(logger, (Function)null);
    }

    public MessageHandler(ILogger logger, Function format) {
        this.logger = logger;
        this.format = format;
    }

    public void handle(IConnection connection, byte[] bytes) throws IOException {
        String message = new String(bytes, StandardCharsets.UTF_8);
        if (this.format != null) {
            this.logger.log((String)this.format.apply(message));
        } else {
            this.logger.log(new String(bytes, StandardCharsets.UTF_8));
        }

    }
}
