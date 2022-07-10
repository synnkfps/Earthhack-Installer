package me.earthhack.installer;

import java.util.Iterator;
import java.util.List;
import javax.swing.SwingUtilities;

import me.earthhack.installer.gui.ErrorPanel;
import me.earthhack.installer.gui.InstallerFrame;
import me.earthhack.installer.gui.VersionPanel;
import me.earthhack.installer.main.Library;
import me.earthhack.installer.main.LibraryClassLoader;
import me.earthhack.installer.main.LibraryFinder;
import me.earthhack.installer.main.MinecraftFiles;
import me.earthhack.installer.service.InstallerService;
import me.earthhack.installer.util.*;
import me.earthhack.installer.version.Version;
import me.earthhack.installer.version.VersionFinder;

@SuppressWarnings("unused")
public class EarthhackInstaller implements Installer {
   private final MinecraftFiles files = new MinecraftFiles();
   private final InstallerFrame gui = new InstallerFrame();
   private InstallerService service;

   public void launch(LibraryClassLoader classLoader, String[] args) {
      SwingUtilities.invokeLater(this.gui::display);
      this.wrapErrorGui(() -> {
         this.files.findFiles(args);
         LibraryFinder libraryFinder = new LibraryFinder();

         for (Object o : libraryFinder.findLibraries(this.files)) {
            Library library = (Library) o;
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
