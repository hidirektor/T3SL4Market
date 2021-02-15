package me.t3sl4.market.util;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class SettingsManager {
   private SettingsManager() {}
   static SettingsManager instance = new SettingsManager();
   private Plugin p;
   private FileConfiguration config;
   private File cfile;
   private FileConfiguration data;
   private File dfile;
   private FileConfiguration item;
   private File itemfile;

   public void setup(Plugin p) {
      cfile = new File(p.getDataFolder(), "config.yml");

      if(!p.getDataFolder().exists()) {
         p.getDataFolder().mkdir();
      }
      if(!cfile.exists()) {
         p.saveDefaultConfig();
      }
      config = p.getConfig();

      this.dfile = new File(p.getDataFolder(), "data.yml");
      if (!this.dfile.exists()) {
         try {
            this.dfile.createNewFile();
         } catch (IOException var3) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create data.yml!");
         }
      }
      this.data = YamlConfiguration.loadConfiguration(this.dfile);

      this.itemfile = new File(p.getDataFolder(), "esyalar.yml");
      if(!itemfile.exists()) {
         p.saveResource("esyalar.yml", false);
      }
      item = YamlConfiguration.loadConfiguration(itemfile);
   }

   public FileConfiguration getConfig() {
      return config;
   }

   public void saveConfig() {
      try {
         config.save(cfile);
      } catch (IOException e) {
         Bukkit.getServer().getLogger().severe(ChatColor.RED + "Config.yml kaydedilemedi!");
      }
   }

   public void reloadConfig() {
      config = YamlConfiguration.loadConfiguration(cfile);
   }

   public FileConfiguration getData() {
      return this.data;
   }

   public void saveData() {
      try {
         this.data.save(this.dfile);
      } catch (IOException var2) {
         Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save data.yml!");
      }

   }

   public void reloadData() {
      this.data = YamlConfiguration.loadConfiguration(this.dfile);
   }

   public FileConfiguration getItemConfig() {
      return item;
   }

   public void saveItemConfig() {
      try {
         item.save(itemfile);
      } catch (IOException e) {
         Bukkit.getServer().getLogger().severe(ChatColor.RED + "esyalar.yml kaydedilemedi!");
      }
   }

   public void reloadItemConfig() {
      item = YamlConfiguration.loadConfiguration(itemfile);
   }

   public PluginDescriptionFile getDesc() {
      return p.getDescription();
   }

   public static SettingsManager getInstance() {
      return instance;
   }
}
