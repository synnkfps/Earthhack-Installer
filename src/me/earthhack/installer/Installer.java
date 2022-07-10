package me.earthhack.installer;

import me.earthhack.installer.version.Version;

public interface Installer {
   boolean refreshVersions();

   boolean install(Version version);

   boolean uninstall(Version version);

   boolean update(boolean forge);
}
