package impl;

import com.google.common.base.Charsets;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.AbstractMap;

import impl.connection.IConnection;
import impl.connection.IConnectionEntry;
import impl.connection.IConnectionManager;
import impl.util.SimpleEntry;

public class ProtocolUtil {
    public static void sendMessage(IConnection connection, int id, String s) throws IOException {
        connection.send(ProtocolUtil.writeString(id, s));
    }

    public static byte[] writeString(int id, String message) {
        byte[] bytes = new byte[message.length() + 8];
        ProtocolUtil.addInt(id, bytes);
        ProtocolUtil.addInt(message.length(), bytes, 4);
        ProtocolUtil.addAllBytes(message.getBytes(StandardCharsets.UTF_8), bytes, 8);
        return bytes;
    }

    public static void addAllBytes(byte[] from, byte[] to, int start) {
        System.arraycopy((Object)from, (int)0, (Object)to, (int)start, (int)from.length);
    }

    public static void addInt(int integer, byte[] bytes) {
        ProtocolUtil.addInt(integer, bytes, 0);
    }

    public static void addInt(int integer, byte[] bytes, int offset) {
        bytes[offset] = (byte)((integer & 0xFF000000) >>> 24);
        bytes[offset + 1] = (byte)((integer & 0xFF0000) >>> 16);
        bytes[offset + 2] = (byte)((integer & 0xFF00) >>> 8);
        bytes[offset + 3] = (byte)(integer & 0xFF);
    }

    public static void copy(byte[] from, byte[] to, int offset) {
        if (to.length - offset < 0) {
            throw new IndexOutOfBoundsException(from.length + " : " + to.length + " : " + offset);
        }
        System.arraycopy((Object)from, (int)0, (Object)to, (int)offset, (int)from.length);
    }

    public static SimplePacket readPacket(DataInputStream in) throws IOException {
        int id = in.readInt();
        int size = in.readInt();
        byte[] buffer = new byte[size];
        int read = in.read(buffer);
        if (read != size) {
            throw new IOException("Expected " + size + " bytes, but found: " + read);
        }
        return new SimplePacket(id, buffer);
    }

    public static byte[] serializeServerList(IConnectionManager manager) {
        IConnectionEntry[] entries = (IConnectionEntry[])manager.getConnections().toArray((Object[])new IConnection[0]);
        int size = 0;
        int count = 0;
        for (IConnectionEntry entry : entries) {
            if (entry == null) continue;
            ++count;
            size += 8 + entry.getName().length();
        }
        ByteBuffer buffer = ByteBuffer.allocate((int)(size + 12));
        buffer.putInt(8);
        buffer.putInt(size);
        buffer.putInt(count);
        for (IConnectionEntry entry : entries) {
            if (entry == null) continue;
            buffer.putInt(entry.getId());
            buffer.putInt(entry.getName().length());
            buffer.put(entry.getName().getBytes(StandardCharsets.UTF_8));
        }
        return buffer.array();
    }

    public static IConnectionEntry[] deserializeServerList(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap((byte[])bytes);
        int count = buffer.getInt();
        IConnectionEntry[] entries = new IConnectionEntry[count];
        for (int i = 0; i < entries.length; ++i) {
            int id = buffer.getInt();
            int size = buffer.getInt();
            byte[] name = new byte[size];
            buffer.get(name);
            entries[i] = new SimpleEntry(new String(name, Charsets.UTF_8), id);
        }
        return entries;
    }
}
