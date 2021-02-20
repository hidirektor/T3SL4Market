package me.t3sl4.market.gui;

import java.util.ArrayList;
import java.util.Arrays;

import me.t3sl4.market.T3SL4Market;
import me.t3sl4.market.util.MessageUtil;
import me.t3sl4.market.util.SettingsManager;
import me.t3sl4.market.util.TranslationMapping;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class GUI {
   static int price;
   static Inventory inv;
   static Player p;
   static String s;
   static SettingsManager manager = SettingsManager.getInstance();
   static ArrayList<Material> problems;

   static {
      problems = new ArrayList(Arrays.asList(Material.ENCHANTED_BOOK, Material.MONSTER_EGG, Material.POTION, Material.MOB_SPAWNER));
   }

   public GUI(String s) {
      GUI.s = s;
      String message = MessageUtil.MARKET_ISMI.replace("%player%", s);
      inv = Bukkit.createInventory((InventoryHolder)null, 54, message);
   }

   public static String getString() {
      return s == null ? p.getName() : s;
   }

   public static Inventory getInventory() {
      return inv;
   }

   public static void addItem(int slot, ItemStack item) {
      String ekleyenIsim = null;
      if (item != null) {
         for(int i = 0; i <= T3SL4Market.count; ++i) {
            ekleyenIsim = manager.get("data").getString("markets.market" + i + ".slot" + slot + ".ekleyen");
            if (manager.get("data").isString("markets.market" + i + ".name") && manager.get("data").getString("markets.market" + i + ".name").contains(s) && manager.get("data").isSet("markets.market" + i + ".slot" + slot + ".fiyat")) {
               try {
                  price = Integer.parseInt(manager.get("data").getString("markets.market" + i + ".slot" + slot + ".fiyat"));
               } catch (Exception var10) {
                  p.sendMessage(MessageUtil.HATA);
                  return;
               }
            }
         }

         ItemMeta meta = item.getItemMeta();
         meta.setDisplayName(ChatColor.GREEN.toString() + TranslationMapping.getKey(item));
         meta.setLore(Arrays.asList(ChatColor.DARK_PURPLE.toString() + " ", MessageUtil.ADET.replace("%p", String.valueOf(item.getAmount())), MessageUtil.FIYAT.replace("%p", String.valueOf(price)), MessageUtil.ESYA_SAHIBI.replace("%player%", ekleyenIsim)));
         item.setItemMeta(meta);

         for(int i = 0; i <= T3SL4Market.count; ++i) {
            if (manager.get("data").isString("markets.market" + i + ".name") && manager.get("data").getString("markets.market" + i + ".name").contains(s) && manager.get("data").isConfigurationSection("markets.market" + i + ".slot" + slot) && manager.get("data").isConfigurationSection("markets.market" + i + ".slot" + slot + ".enchants")) {
               int q = manager.get("data").getInt("markets.market" + i + ".slot" + slot + ".enchant_sayisi");

               for(int l = 0; l < q; ++l) {
                  if (manager.get("data").isString("markets.market" + i + ".slot" + slot + ".enchants.enchant" + l + ".name")) {
                     String enchant_name = manager.get("data").getString("markets.market" + i + ".slot" + slot + ".enchants.enchant" + l + ".name");
                     int enchant_level = manager.get("data").getInt("markets.market" + i + ".slot" + slot + ".enchants.enchant" + l + ".level");
                     Enchantment enchant = Enchantment.getByName(enchant_name);
                     if (item.getType() == Material.ENCHANTED_BOOK) {
                        EnchantmentStorageMeta emeta = (EnchantmentStorageMeta)item.getItemMeta();
                        emeta.addStoredEnchant(enchant, enchant_level, true);
                        item.setItemMeta(emeta);
                     } else {
                        item.addUnsafeEnchantment(enchant, enchant_level);
                     }
                  }
               }
            }
         }

         inv.setItem(slot, item);
      }
   }

   public static ItemStack getItem(int s) {
      return inv.getItem(s);
   }
}
