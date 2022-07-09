package impl.command;

import java.io.IOException;

import impl.ISender;
import impl.ProtocolUtil;
import impl.util.SystemLogger;
import impl.util.manager.ICommandHandler;

public class MessageCommand implements ICommandHandler {
    private final ISender sender;
    private final int id;

    public MessageCommand(ISender sender, int id) {
        this.sender = sender;
        this.id = id;
    }

    public void handle(String command){
        try {
            this.sender.send(ProtocolUtil.writeString(this.id, command));
        } catch (IOException var3) {
            new SystemLogger().log(var3.getMessage());
        }
    }
}
