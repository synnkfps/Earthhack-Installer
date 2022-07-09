package me.earthhack.installer.util;

import com.google.gson.*;
import java.nio.file.*;
import java.io.*;

public class JsonPathWriter
{
    public static void write(final Path path, final JsonObject object) throws IOException {
        final String json = Jsonable.GSON.toJson(object);
        try (final BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(path, new OpenOption[0])))) {
            writer.write(json);
        }
    }
}
