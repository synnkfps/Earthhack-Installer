package me.earthhack.installer.version;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.earthhack.installer.util.Jsonable;


public class VersionUtil {
   public static final String MAIN = "net.minecraft.launchwrapper.Launch";
   public static final String EARTH = "--tweakClass me.earthhack.tweaker.EarthhackTweaker";
   public static final String FORGE = "--tweakClass net.minecraftforge.fml.common.launcher.FMLTweaker";
   public static final String ARGS = "minecraftArguments";
   public static final String LIBS = "libraries";

   public static boolean hasEarthhack(Version version) {
      return containsArgument(version, "--tweakClass me.earthhack.tweaker.EarthhackTweaker");
   }

   public static boolean hasForge(Version version) {
      return containsArgument(version, "--tweakClass net.minecraftforge.fml.common.launcher.FMLTweaker");
   }

   public static boolean hasLaunchWrapper(Version version) {
      JsonElement element = version.getJson().get("mainClass");
      return element != null && element.getAsString().equals("net.minecraft.launchwrapper.Launch");
   }

   public static boolean containsArgument(Version version, String tweaker) {
      JsonElement element = version.getJson().get("minecraftArguments");
      return element != null && element.getAsString().contains(tweaker);
   }

   public static JsonElement getOrElse(String name, JsonObject object, String returnElse) {
      JsonElement element = object.get(name);
      return element == null ? Jsonable.PARSER.parse(returnElse) : element;
   }
}
