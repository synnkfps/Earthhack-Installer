package me.earthhack.installer.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import me.earthhack.installer.srg2notch.ASMRemapper;
import me.earthhack.installer.srg2notch.Mapping;
import me.earthhack.installer.util.StreamUtil;

public class Srg2NotchService {
   private final ASMRemapper remapper = new ASMRemapper();

   public void remap(URL from, URL to) throws IOException {
      Mapping mapping = Mapping.fromResource("mappings/mappings.csv");
      JarFile jar = new JarFile(from.getFile());
      FileOutputStream fos = new FileOutputStream(to.getFile());
      Throwable var6 = null;

      try {
         JarOutputStream jos = new JarOutputStream(fos);
         Throwable var8 = null;

         try {
            Enumeration e = jar.entries();

            while(e.hasMoreElements()) {
               JarEntry next = (JarEntry)e.nextElement();
               this.handleEntry(next, jos, jar, mapping);
            }
         } catch (Throwable var32) {
            var8 = var32;
            throw var32;
         } finally {
            if (jos != null) {
               if (var8 != null) {
                  try {
                     jos.close();
                  } catch (Throwable var31) {
                     var8.addSuppressed(var31);
                  }
               } else {
                  jos.close();
               }
            }

         }
      } catch (Throwable var34) {
         var6 = var34;
         throw var34;
      } finally {
         if (fos != null) {
            if (var6 != null) {
               try {
                  fos.close();
               } catch (Throwable var30) {
                  var6.addSuppressed(var30);
               }
            } else {
               fos.close();
            }
         }

      }

   }

   protected void handleEntry(JarEntry entry, JarOutputStream jos, JarFile jar, Mapping mapping) throws IOException {
      InputStream is = jar.getInputStream(entry);
      Throwable var6 = null;

      try {
         jos.putNextEntry(new JarEntry(entry.getName()));
         if (entry.getName().endsWith(".class")) {
            byte[] bytes = StreamUtil.toByteArray(is);
            jos.write(this.remapper.transform(bytes, mapping));
         } else {
            StreamUtil.copy(is, jos);
         }

         jos.flush();
         jos.closeEntry();
      } catch (Throwable var15) {
         var6 = var15;
         throw var15;
      } finally {
         if (is != null) {
            if (var6 != null) {
               try {
                  is.close();
               } catch (Throwable var14) {
                  var6.addSuppressed(var14);
               }
            } else {
               is.close();
            }
         }

      }

   }
}
