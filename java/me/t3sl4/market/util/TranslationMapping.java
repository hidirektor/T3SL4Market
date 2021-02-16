package me.t3sl4.market.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class TranslationMapping {
   static Map<String, String> transmap = new HashMap();
   static SettingsManager manager = SettingsManager.getInstance();

   public TranslationMapping(JavaPlugin plugin) {
      plugin.getLogger().info("Loading TranslationMapping...");
      manager.saveItemConfig();
      manager.reloadItemConfig();
      ConfigurationSection blocksection = manager.getItemConfig().getConfigurationSection("mapping");
      Iterator var4 = blocksection.getKeys(false).iterator();

      while(var4.hasNext()) {
         String matname = (String)var4.next();

         try {
            String matkey = matname.toUpperCase();
            Material.valueOf(matkey);
            if (!blocksection.isConfigurationSection(matname)) {
               transmap.put(matkey, blocksection.getString(matname));
            } else {
               ConfigurationSection extrasection = blocksection.getConfigurationSection(matname);
               String general = extrasection.getString("general");
               if (general != null) {
                  transmap.put(matkey, general);
               }

               if (general.equalsIgnoreCase(".")) {
                  general = " ";
               }

               String template = extrasection.getString("template");
               ConfigurationSection damagesection;
               String damage;
               Iterator var11;
               String mckey;
               if (template != null) {
                  damagesection = manager.getItemConfig().getConfigurationSection("templates." + template);
                  if (damagesection != null) {
                     for(var11 = damagesection.getKeys(false).iterator(); var11.hasNext(); transmap.put(matkey + ":" + damage, mckey)) {
                        damage = (String)var11.next();
                        mckey = damagesection.getString(damage);
                        if (general != null) {
                           mckey = general + "." + mckey;
                        }
                     }
                  } else {
                     plugin.getLogger().warning("[TranslationMapping] The template " + template + " does not exist!");
                  }
               }

               damagesection = extrasection.getConfigurationSection("types");
               if (damagesection != null) {
                  for(var11 = damagesection.getKeys(false).iterator(); var11.hasNext(); transmap.put(matkey + ":" + damage, mckey)) {
                     damage = (String)var11.next();
                     mckey = damagesection.getString(damage);
                     if (general != null) {
                        mckey = general + "." + mckey;
                     }
                  }
               }
            }
         } catch (IllegalArgumentException var13) {
            plugin.getLogger().warning("[TranslationMapping] " + matname + " is not a valid Bukkit material name!");
         }
      }

      plugin.getLogger().info("TranslationMapping loaded.");
   }

   public static String getKey(ItemStack item) {
      manager.reloadItemConfig();
      Material mat = item.getType();
      if (mat == Material.SKULL_ITEM && item.getItemMeta() instanceof SkullMeta && ((SkullMeta)item.getItemMeta()).getOwner() != null) {
         return "item.skull.player.name";
      } else {
         String trans = "";
         int data = item.getDurability();
         if (item.hasItemMeta() && item.getItemMeta() instanceof BannerMeta) {
            BannerMeta bm = (BannerMeta)item.getItemMeta();
            DyeColor baseColor = bm.getBaseColor();
            if (baseColor != null) {
               data = baseColor.getDyeData();
            }
         }

         if (transmap.containsKey(mat.toString() + ":" + data)) {
            trans = (String)transmap.get(mat.toString() + ":" + data);
         } else if (transmap.containsKey(mat.toString())) {
            trans = (String)transmap.get(mat.toString());
         } else {
            trans = mat.toString().toLowerCase().replace("_block", "").replace("_item", "").replace("_", "");
         }

         return handle(trans);
      }
   }

   public static String handle(String s) {
      if (s.contains(".")) {
         int n = s.indexOf(".");
         String s1 = s.substring(n + 1);
         String s2 = s.replace(s1, "").replace(".", "");
         return s1 + " " + s2;
      } else {
         return s;
      }
   }
}
