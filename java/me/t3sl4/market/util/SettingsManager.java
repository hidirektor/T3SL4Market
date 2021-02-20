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
   private File configfile;
   private FileConfiguration data;
   private File datafile;
   private FileConfiguration depo;
   private File depofile;
   private FileConfiguration item;
   private File itemfile;

   public void setup(Plugin p) {
      if(!p.getDataFolder().exists()) {
         p.getDataFolder().mkdir();
      }
      create("config", p);
      create("data", p);
      create("item", p);
      create("depo", p);
   }

   public FileConfiguration get(String file) {
      if(file.equalsIgnoreCase("config")) {
         return config;
      } else if(file.equalsIgnoreCase("data")) {
         return data;
      } else if(file.equalsIgnoreCase("item")) {
         return item;
      } else if(file.equalsIgnoreCase("depo")) {
         return depo;
      }
      return null;
   }

   public void save(String file) {
      if(file.equalsIgnoreCase("config")) {
         try {
            config.save(configfile);
         } catch (IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Config.yml kaydedilemedi!");
         }
      } else if(file.equalsIgnoreCase("data")) {
         try {
            this.data.save(this.datafile);
         } catch (IOException var2) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save data.yml!");
         }
      } else if(file.equalsIgnoreCase("item")) {
         try {
            item.save(itemfile);
         } catch (IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "esyalar.yml kaydedilemedi!");
         }
      } else if(file.equalsIgnoreCase("depo")) {
         try {
            depo.save(depofile);
         } catch (IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "depo.yml kaydedilemedi!");
         }
      }
   }

   public void reload(String file) {
      if(file.equalsIgnoreCase("config")) {
         config = YamlConfiguration.loadConfiguration(configfile);
      } else if(file.equalsIgnoreCase("data")) {
         data = YamlConfiguration.loadConfiguration(datafile);
      } else if(file.equalsIgnoreCase("item")) {
         item = YamlConfiguration.loadConfiguration(itemfile);
      } else if(file.equalsIgnoreCase("depo")) {
         depo = YamlConfiguration.loadConfiguration(depofile);
      }
   }

   public void create(String file, Plugin p) {
      if(file.equalsIgnoreCase("config")) {
         configfile = new File(p.getDataFolder(), "config.yml");
         if(!configfile.exists()) {
            p.saveDefaultConfig();
         }
         config = p.getConfig();
      } else if(file.equalsIgnoreCase("data")) {
         datafile = new File(p.getDataFolder(), "data.yml");
         if (!datafile.exists()) {
            try {
               datafile.createNewFile();
            } catch (IOException var3) {
               Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create data.yml!");
            }
         }
         data = YamlConfiguration.loadConfiguration(datafile);
      } else if(file.equalsIgnoreCase("item")) {
         itemfile = new File(p.getDataFolder(), "esyalar.yml");
         if(!itemfile.exists()) {
            p.saveResource("esyalar.yml", false);
         }
         item = YamlConfiguration.loadConfiguration(itemfile);
      } else if(file.equalsIgnoreCase("depo")) {
         depofile = new File(p.getDataFolder(), "depo.yml");
         if (!this.depofile.exists()) {
            try {
               this.depofile.createNewFile();
            } catch (IOException var3) {
               Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create depo.yml!");
            }
         }
         depo = YamlConfiguration.loadConfiguration(this.depofile);
      }
   }

   public PluginDescriptionFile getDesc() {
      return p.getDescription();
   }

   public static SettingsManager getInstance() {
      return instance;
   }
}
