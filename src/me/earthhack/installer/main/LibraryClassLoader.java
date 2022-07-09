package me.earthhack.installer.main;

import me.earthhack.installer.util.StreamUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class LibraryClassLoader extends URLClassLoader {
   public LibraryClassLoader(ClassLoader parent, URL... urls) {
      super(urls, parent);
   }

   public void installLibrary(Library library) throws Exception {
      if (library.needsDownload()) {
         (new File(library.getUrl().getFile())).getParentFile().mkdirs();
         ReadableByteChannel rbc = Channels.newChannel(library.getWeb().openStream());
         Throwable var3 = null;

         try {
            FileOutputStream fos = new FileOutputStream(library.getUrl().getFile());
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

      this.addURL(library.getUrl());
   }

   public Class findClass_public(String name) throws ClassNotFoundException {
      return this.findClass(name);
   }

   protected Class loadClass(String name, boolean resolve) throws ClassNotFoundException {
      if (!name.startsWith("java.") && !name.startsWith("javax.") && !name.startsWith("sun.") && !name.startsWith("me.earthhack.installer.main") && !name.startsWith("jdk.")) {
         Class alreadyLoaded = this.findLoadedClass(name);
         if (alreadyLoaded != null) {
            return alreadyLoaded;
         } else {
            try {
               InputStream is = this.getResourceAsStream(name.replaceAll("\\.", "/") + ".class");
               Throwable var5 = null;

               Class var8;
               try {
                  if (is == null) {
                     throw new ClassNotFoundException("Could not find " + name);
                  }

                  byte[] bytes = StreamUtil.toByteArray(is);
                  Class clazz = this.defineClass(name, bytes, 0, bytes.length);
                  if (resolve) {
                     this.resolveClass(clazz);
                  }

                  var8 = clazz;
               } catch (Throwable var18) {
                  var5 = var18;
                  throw var18;
               } finally {
                  if (is != null) {
                     if (var5 != null) {
                        try {
                           is.close();
                        } catch (Throwable var17) {
                           var5.addSuppressed(var17);
                        }
                     } else {
                        is.close();
                     }
                  }

               }

               return var8;
            } catch (IOException var20) {
               throw new ClassNotFoundException("Could not load " + name, var20);
            }
         }
      } else {
         return super.loadClass(name, resolve);
      }
   }
}
