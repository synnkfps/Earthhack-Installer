package me.earthhack.installer;

import me.earthhack.installer.version.Version;

public interface Installer {
   boolean refreshVersions();

   boolean install(Version var1);

   boolean uninstall(Version var1);

   boolean update(boolean var1);
}
