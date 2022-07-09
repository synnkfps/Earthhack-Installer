package me.earthhack.installer.util;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class StreamUtil {
    public static void copy(URL from, URL to) throws IOException {
        ReadableByteChannel rbc = Channels.newChannel(from.openStream());
        Throwable var3 = null;

        try {
            FileOutputStream fos = new FileOutputStream(to.getFile());
            Throwable var5 = null;

            try {
                fos.getChannel().transferFrom(rbc, 0L, Long.MAX_VALUE);
            } catch (Throwable var28) {
                var5 = var28;
                throw var28;
            } finally {
                if (fos != null) {
                    if (var5 != null) {
                        try {
                            fos.close();
                        } catch (Throwable var27) {
                            var5.addSuppressed(var27);
                        }
                    } else {
                        fos.close();
                    }
                }

            }
        } catch (Throwable var30) {
            var3 = var30;
            throw var30;
        } finally {
            if (rbc != null) {
                if (var3 != null) {
                    try {
                        rbc.close();
                    } catch (Throwable var26) {
                        var3.addSuppressed(var26);
                    }
                } else {
                    rbc.close();
                }
            }

        }

    }

    public static byte[] toByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        copy((InputStream)is, (OutputStream)buffer);
        return buffer.toByteArray();
    }

    public static void copy(InputStream is, OutputStream os) throws IOException {
        byte[] bytes = new byte[1024];

        int length;
        while((length = is.read(bytes)) != -1) {
            os.write(bytes, 0, length);
        }

    }
}
