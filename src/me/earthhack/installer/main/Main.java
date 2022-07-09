package me.earthhack.installer.main;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import impl.ClientMain;
import impl.ServerMain;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class Main {
   public static void main(String[] args) throws Throwable {
      /**
       * @TODO: Add ServerMain and ClientMain System...
       */
        if (args.length != 0) {
          if (args[0].equalsIgnoreCase("server")) {
             ServerMain.main(args);
             return;
          }
          if (args[0].equalsIgnoreCase("client")) {
             ClientMain.main(args);
             return;
      }
      }
      ClassLoader bootCl = Main.class.getClassLoader();
      URL[] urls;
      if (bootCl instanceof URLClassLoader) {
         URLClassLoader ucl = (URLClassLoader)bootCl;
         urls = ucl.getURLs();
      } else {
         urls = new URL[]{Main.class.getProtectionDomain().getCodeSource().getLocation()};
      }

      LibraryClassLoader cl = new LibraryClassLoader(bootCl, urls);
      Thread.currentThread().setContextClassLoader(cl);
      String installer = "me.earthhack.installer.EarthhackInstaller";
      Class c = cl.findClass_public(installer);
      Method m = c.getMethod("launch", cl.getClass(), String[].class);
      Object o = c.newInstance();
      m.invoke(o, cl, args);
   }
}
