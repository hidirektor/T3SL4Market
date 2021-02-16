package me.t3sl4.market.util;

import me.t3sl4.market.gui.Gui;
import me.t3sl4.market.T3SL4Market;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MarketUtil {
   private static int q = -1;
   static SettingsManager manager = SettingsManager.getInstance();
   private static int price;
   private static int amount;
   private static int slot;
   private static Material material;
   private static int data;
   private static byte dataByte;
   public static int marketsNumber;


   public static boolean hasMarket(Player p) {
      for(int i = 0; i <= T3SL4Market.count; ++i) {
         if (manager.getData().isString("markets.market" + i + ".uuid") && manager.getData().getString("markets.market" + i + ".uuid").equalsIgnoreCase(p.getUniqueId().toString())) {
            return true;
         }
      }

      return false;
   }

   public static boolean hasMarketString(String s) {
      for(int i = 0; i <= T3SL4Market.count; ++i) {
         if (manager.getData().isString("markets.market" + i + ".uuid") && manager.getData().getString("markets.market" + i + ".name").equalsIgnoreCase(s)) {
            return true;
         }
      }

      return false;
   }

   public static int getMarketNumber(Player p) {
      int z = 1;
      for(int i=1; i<= T3SL4Market.count; i++) {
         if (manager.getData().isString("markets.market" + i + ".uuid") && manager.getData().getString("markets.market" + i + ".name").equalsIgnoreCase(p.getName())) {
            z = i;
            return z;
         }
      }
      return z;
   }

   public static boolean checkOrtak(Player owner, Player control) {
      int marketNumber = getMarketNumber(owner);
      int sonSayi = manager.getData().getInt("markets.market" + marketNumber + ".ortaklik.sonSayi");
      for(int i=0; i<sonSayi; i++) {
         if(manager.getData().get("markets.market" + marketNumber + ".ortaklik.ortaklar." + i) == control.getName()) {
            return false;
         }
      }
      return true;
   }

   public static boolean checkAllOrtaks(Player p) {
      for(int i=1; i<=T3SL4Market.count; i++) {
         for(int j=0; j<manager.getData().getInt("markets.market" + i + ".ortaklik.sonSayi"); j++) {
            if(manager.getData().getString("markets.market" + i + ".ortaklik.ortaklar." + j).equalsIgnoreCase(p.getName())) {
               marketsNumber = i;
               return true;
            }
         }
      }
      return false;
   }

   public static void loadItemsString(String s) {
      int market = -1;
      if (hasMarketString(s)) {
         for(int g = 0; g <= T3SL4Market.count; ++g) {
            if (manager.getData().isString("markets.market" + g + ".name") && manager.getData().getString("markets.market" + g + ".name").equalsIgnoreCase(s)) {
               market = g;
               break;
            }
         }

         if (market != -1) {
            int k = 0;

            while(k <= 54) {
               if (manager.getData().isConfigurationSection("markets.market" + market + ".slot" + k)) {
                  slot = k;
                  Gui.addItem(k, manager.getData().getItemStack("markets.market" + market + ".slot" + slot + ".item"));
                  ++k;
               } else {
                  ++k;
               }
            }

         }
      }
   }
}
