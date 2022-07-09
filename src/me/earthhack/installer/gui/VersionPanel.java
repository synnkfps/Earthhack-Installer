package me.earthhack.installer.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import me.earthhack.installer.version.Version;
import me.earthhack.installer.version.VersionUtil;
import me.earthhack.installer.Installer;

public class VersionPanel extends JPanel {
   public VersionPanel(Installer handler, List versions) {
      String[] columns = new String[]{"name", "forge", "3arthh4ck", "valid"};
      List data = new ArrayList(versions.size());
      Iterator var5 = versions.iterator();

      while(var5.hasNext()) {
         Version version = (Version)var5.next();
         boolean earth = VersionUtil.hasEarthhack(version);
         boolean forge = VersionUtil.hasForge(version);
         boolean valid = VersionUtil.hasLaunchWrapper(version);
         data.add(new Object[]{version.getName(), forge, earth, valid});
      }

      JButton install = new JButton("Install");
      install.setEnabled(false);
      JButton uninstall = new JButton("Uninstall");
      uninstall.setEnabled(false);
      JButton refresh = new JButton("Refresh");
      refresh.addActionListener((e) -> {
         handler.refreshVersions();
      });
      Object[][] t = (Object[][])data.toArray(new Object[0][]);
      JTable jt = new JTable(t, columns);
      jt.setSelectionMode(0);
      jt.addMouseListener(new VersionMouseAdapter(jt, install, uninstall, t));
      install.addActionListener((e) -> {
         int row = jt.getSelectedRow();
         if (row >= 0) {
            Version version = (Version)versions.get(row);
            handler.install(version);
         }

      });
      uninstall.addActionListener((e) -> {
         int row = jt.getSelectedRow();
         if (row >= 0) {
            Version version = (Version)versions.get(row);
            handler.uninstall(version);
         }

      });
      JPanel buttonPanel = new JPanel();
      buttonPanel.add(install);
      buttonPanel.add(uninstall);
      buttonPanel.add(refresh);
      JButton installAll = new JButton("Install-All");
      installAll.addActionListener((e) -> {
         Iterator var3 = versions.iterator();

         Version version;
         do {
            if (!var3.hasNext()) {
               return;
            }

            version = (Version)var3.next();
         } while(!VersionUtil.hasLaunchWrapper(version) || VersionUtil.hasEarthhack(version) || !handler.install(version));

      });
      JButton uninstallAll = new JButton("Uninstall-All");
      uninstallAll.addActionListener((e) -> {
         Iterator var3 = versions.iterator();

         Version version;
         do {
            if (!var3.hasNext()) {
               return;
            }

            version = (Version)var3.next();
         } while(!VersionUtil.hasEarthhack(version) || !handler.uninstall(version));

      });
      JButton updateForge = new JButton("Update-Forge");
      updateForge.addActionListener((e) -> {
         handler.update(true);
      });
      JButton updateVanilla = new JButton("Update-Vanilla");
      updateVanilla.addActionListener((e) -> {
         handler.update(false);
      });
      JPanel allPanel = new JPanel();
      allPanel.add(installAll);
      allPanel.add(uninstallAll);
      allPanel.add(updateForge);
      allPanel.add(updateVanilla);
      JButton exit = new JButton("Exit");
      exit.addActionListener((e) -> {
         System.exit(0);
      });
      JPanel exitPanel = new JPanel();
      exitPanel.add(exit);
      this.setLayout(new BoxLayout(this, 1));
      this.add(new JScrollPane(jt));
      this.add(buttonPanel);
      this.add(allPanel);
      this.add(exitPanel);
   }
}
