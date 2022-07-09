package me.earthhack.installer.util;

import com.google.gson.stream.*;
import java.io.*;
import com.google.gson.*;

public interface Jsonable
{
    public static final JsonParser PARSER = new JsonParser();
    public static final Gson GSON = new GsonBuilder().setLenient().setPrettyPrinting().create();

    void fromJson(final JsonElement p0);

    String toJson();

    static JsonElement parse(final String string) {
        return parse(string, true);
    }

    static JsonElement parse(final String string, final boolean addQuotation) {
        JsonElement element = null;
        try (final JsonReader reader = new JsonReader(new StringReader(addQuotation ? ("\"" + string + "\"") : string))) {
            reader.setLenient(true);
            element = Jsonable.PARSER.parse(reader);
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
        return element;
    }
}
