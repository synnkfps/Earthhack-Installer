package me.earthhack.installer.main;

import impl.ClientMain;
import impl.ServerMain;

import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class Main
{
   public static void main(String[] args) throws Throwable
   {
      if (args.length != 0)
      {
         if (args[0].equalsIgnoreCase("server"))
         {
            ServerMain.main(args);
            return;
         }
         else if (args[0].equalsIgnoreCase("client"))
         {
            ClientMain.main(args);
            return;
         }
      }

      URL[] urls;
      ClassLoader bootCl = Main.class.getClassLoader();
      if (bootCl instanceof URLClassLoader)
      {
         URLClassLoader ucl = (URLClassLoader) bootCl;
         urls = ucl.getURLs();
      }
      else
      {
         urls = new URL[] { Main.class.getProtectionDomain()
                 .getCodeSource()
                 .getLocation() };
      }

      LibraryClassLoader cl = new LibraryClassLoader(bootCl, urls);
      Thread.currentThread().setContextClassLoader(cl);

      String installer = "me.earthhack.installer.EarthhackInstaller";
      Class<?> c = cl.findClass_public(installer);
      Method   m = c.getMethod("launch", cl.getClass(), String[].class);
      Object   o = c.newInstance();
      m.invoke(o, cl, args);
   }

}