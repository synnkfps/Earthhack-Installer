package me.earthhack.installer;

import me.earthhack.installer.gui.ErrorPanel;
import me.earthhack.installer.gui.InstallerFrame;
import me.earthhack.installer.gui.VersionPanel;
import me.earthhack.installer.main.Library;
import me.earthhack.installer.main.LibraryClassLoader;
import me.earthhack.installer.main.LibraryFinder;
import me.earthhack.installer.main.MinecraftFiles;
import me.earthhack.installer.service.InstallerService;
import me.earthhack.installer.util.SafeRunnable;
import me.earthhack.installer.version.Version;
import me.earthhack.installer.version.VersionFinder;

import javax.swing.*;
import java.util.Iterator;
import java.util.List;
public class EarthhackInstaller implements Installer {
   private final MinecraftFiles files = new MinecraftFiles();
   private final InstallerFrame gui = new InstallerFrame();
   private InstallerService service;

   public void launch(LibraryClassLoader classLoader, String[] args) {
      InstallerFrame var10000 = this.gui;
      SwingUtilities.invokeLater(var10000::display);
      this.wrapErrorGui(() -> {
         this.files.findFiles(args);
         LibraryFinder libraryFinder = new LibraryFinder();
         Iterator var4 = libraryFinder.findLibraries(this.files).iterator();

         while(var4.hasNext()) {
            Library library = (Library)var4.next();
            classLoader.installLibrary(library);
         }

         this.service = new InstallerService();
         this.refreshVersions();
      });
   }
   public boolean refreshVersions() {
      return this.wrapErrorGui(() -> {
         VersionFinder versionFinder = new VersionFinder();
         List versions = versionFinder.findVersions(this.files);
         this.gui.schedule(new VersionPanel(this, versions));
      });
   }
   public boolean install(Version version) {
      return this.wrapErrorGui(() -> {
         this.service.install(this.files, version);
         this.refreshVersions();
      });
   }
   public boolean uninstall(Version version) {
      return this.wrapErrorGui(() -> {
         this.service.uninstall(version);
         this.refreshVersions();
      });
   }
   public boolean update(boolean forge) {
      return this.wrapErrorGui(() -> {
         this.service.update(this.files, forge);
         this.refreshVersions();
      });
   }
   private boolean wrapErrorGui(SafeRunnable runnable) {
      try {
         runnable.runSafely();
         return false;
      } catch (Throwable var3) {
         this.gui.schedule(new ErrorPanel(var3));
         var3.printStackTrace();
         return true;
      }
   }
}