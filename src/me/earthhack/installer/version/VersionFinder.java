package me.earthhack.installer.version;

import com.google.gson.JsonObject;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import me.earthhack.installer.main.MinecraftFiles;
import me.earthhack.installer.util.Jsonable;

public class VersionFinder {
   public List findVersions(MinecraftFiles files) throws IOException {
      File[] versionFolders = (new File(files.getVersions())).listFiles();
      if (versionFolders == null) {
         throw new IllegalStateException("Version folder was empty!");
      } else {
         List result = new ArrayList();
         File[] var4 = versionFolders;
         int var5 = versionFolders.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            File file = var4[var6];
            if (file.getName().startsWith("1.12.2") && file.isDirectory()) {
               File[] contained = file.listFiles();
               if (contained != null) {
                  File[] var9 = contained;
                  int var10 = contained.length;

                  for(int var11 = 0; var11 < var10; ++var11) {
                     File json = var9[var11];
                     if (json.getName().endsWith("json")) {
                        Version version = this.readJson(file.getName(), json);
                        result.add(version);
                     }
                  }
               }
            }
         }

         return result;
      }
   }

   private Version readJson(String name, File jsonFile) throws IOException {
      JsonObject object = Jsonable.PARSER.parse(new InputStreamReader(jsonFile.toURI().toURL().openStream())).getAsJsonObject();
      return new Version(name, jsonFile, object);
   }
}
