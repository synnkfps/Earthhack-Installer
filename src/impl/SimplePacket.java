package impl;


import impl.packet.IPacket;

public class SimplePacket
        implements IPacket {
    private final byte[] buffer;
    private final int id;

    public SimplePacket(int id, byte[] buffer) {
        this.id = id;
        this.buffer = buffer;
    }

    public int getId() {
        return this.id;
    }

    public byte[] getBuffer() {
        return this.buffer;
    }
}
