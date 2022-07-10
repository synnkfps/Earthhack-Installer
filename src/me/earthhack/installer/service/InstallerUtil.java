package me.earthhack.installer.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Iterator;

import me.earthhack.installer.util.Jsonable;
import me.earthhack.installer.version.VersionUtil;

public class InstallerUtil {
   public static final String ASM = "org.ow2.asm:asm-debug-all:5.2";
   public static final String LAUNCH = "net.minecraft:launchwrapper:1.12";
   public static final String NAME = "earthhack:forge:1.12.2";
   public static final String VNAME = "earthhack:vanilla:1.12.2";

   public static void installEarthhack(JsonObject o, boolean forge) {
      JsonElement args = VersionUtil.getOrElse("minecraftArguments", o, "");
      String newArgs = args.getAsString() + " " + "--tweakClass me.earthhack.tweaker.EarthhackTweaker";
      JsonElement element = Jsonable.parse(newArgs);
      o.add("minecraftArguments", element);
      JsonElement libs = VersionUtil.getOrElse("libraries", o, "[]");
      JsonArray array = libs.getAsJsonArray();
      JsonObject object = new JsonObject();
      object.add("name", Jsonable.parse(forge ? "earthhack:forge:1.12.2" : "earthhack:vanilla:1.12.2"));
      array.add(object);
      o.add("libraries", libs);
   }

   public static void installLibs(JsonObject o) {
      JsonElement libs = VersionUtil.getOrElse("libraries", o, "[]");
      JsonArray array = libs.getAsJsonArray();
      boolean hasAsm = false;
      boolean hasLaunch = false;

      for (JsonElement element : array) {
         JsonElement name = element.getAsJsonObject().get("name");
         if (name != null) {
            if (name.getAsString().equals("org.ow2.asm:asm-debug-all:5.2")) {
               hasAsm = true;
            } else if (name.getAsString().equals("net.minecraft:launchwrapper:1.12")) {
               hasLaunch = true;
            }
         }
      }

      JsonObject launchLib;
      if (!hasAsm) {
         launchLib = new JsonObject();
         launchLib.add("name", Jsonable.parse("org.ow2.asm:asm-debug-all:5.2"));
         JsonObject downloads = new JsonObject();
         JsonObject artifact = new JsonObject();
         artifact.add("path", Jsonable.parse("org/ow2/asm/asm-debug-all/5.2/asm-debug-all-5.2.jar"));
         artifact.add("url", Jsonable.parse("https://files.minecraftforge.net/maven/org/ow2/asm/asm-debug-all/5.2/asm-debug-all-5.2.jar"));
         artifact.add("sha1", Jsonable.parse("3354e11e2b34215f06dab629ab88e06aca477c19"));
         artifact.add("size", Jsonable.parse("387903", false));
         downloads.add("artifact", artifact);
         launchLib.add("downloads", downloads);
         launchLib.add("earthhlib", Jsonable.parse("true", false));
         array.add(launchLib);
      }

      if (!hasLaunch) {
         launchLib = new JsonObject();
         launchLib.add("name", Jsonable.parse("net.minecraft:launchwrapper:1.12"));
         launchLib.add("serverreq", Jsonable.parse("true", false));
         launchLib.add("earthhlib", Jsonable.parse("true", false));
         array.add(launchLib);
      }

   }

   public static void uninstallEarthhack(JsonObject o) {
      JsonElement args = o.get("minecraftArguments");
      if (args != null) {
         String removed = args.getAsString().replace(" --tweakClass me.earthhack.tweaker.EarthhackTweaker", "");
         JsonElement element = Jsonable.parse(removed);
         o.add("minecraftArguments", element);
      }

      JsonElement libs = o.get("libraries");
      if (libs != null) {
         JsonArray array = libs.getAsJsonArray();
         Iterator<JsonElement> itr = array.iterator();

         while(true) {
            JsonElement name;
            do {
               do {
                  if (!itr.hasNext()) {
                     return;
                  }

                  JsonObject library = ((JsonElement)itr.next()).getAsJsonObject();
                  name = library.get("name");
               } while(name == null);
            } while(!name.getAsString().equals("earthhack:forge:1.12.2") && !name.getAsString().equals("earthhack:vanilla:1.12.2"));

            itr.remove();
         }
      }
   }

   public static void uninstallLibs(JsonObject o) {
      JsonElement libs = o.get("libraries");
      if (libs != null) {
         JsonArray array = libs.getAsJsonArray();
         Iterator<JsonElement> itr = array.iterator();

         while(itr.hasNext()) {
            JsonObject library = ((JsonElement)itr.next()).getAsJsonObject();
            JsonElement name = library.get("earthhlib");
            if (name != null) {
               itr.remove();
            }
         }
      }

   }
}
