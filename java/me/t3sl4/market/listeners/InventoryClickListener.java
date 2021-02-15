package me.t3sl4.market.listeners;

import java.util.Arrays;
import java.util.UUID;

import me.t3sl4.market.T3SL4Market;
import me.t3sl4.market.gui.Gui;
import me.t3sl4.market.util.MarketUtil;
import me.t3sl4.market.util.MessageUtil;
import me.t3sl4.market.util.SettingsManager;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryClickListener implements Listener {
   @EventHandler
   public void onInventoryClick(InventoryClickEvent e) {
      SettingsManager manager = SettingsManager.getInstance();
      Player p = (Player)e.getWhoClicked();
      String uuid = "";
      double bal = 0.0D;
      int amount = 0;
      Inventory inv = e.getInventory();
      ItemStack item = e.getCurrentItem();
      int price = 0;
      Economy econ = T3SL4Market.getEconomy();
      if (e.getInventory() != null && Gui.getInventory() != null &&
              e.getInventory().getName().equalsIgnoreCase(Gui.getInventory().getName())) {
         if (e.getCurrentItem() == null)
            return;
         if (e.getCurrentItem() == new ItemStack(Material.AIR))
            return;
         e.setCancelled(true);
         if (!e.getCurrentItem().hasItemMeta())
            return;
         if(MessageUtil.ORTAKLIK) {
            if(MarketUtil.checkAllOrtaks(Bukkit.getPlayer(e.getWhoClicked().getName()))) {
               int marketNumber = MarketUtil.marketsNumber;
               Player marketSahibi = Bukkit.getPlayer(manager.getData().getString("markets.market" + marketNumber + ".name"));
               if(e.getInventory().getName().contains(marketSahibi.getName())) {
                  p.sendMessage(MessageUtil.KENDI_MARKETINDEN_ITEM_ALAMAZSIN);
                  return;
               }
            } else {
               if (e.getInventory().getName().contains(e.getWhoClicked().getName())) {
                  p.sendMessage(MessageUtil.KENDI_MARKETINDEN_ITEM_ALAMAZSIN);
                  return;
               }
            }
         } else {
            if (e.getInventory().getName().contains(e.getWhoClicked().getName())) {
               p.sendMessage(MessageUtil.KENDI_MARKETINDEN_ITEM_ALAMAZSIN);
               return;
            }
            return;
         }
         if (e.getCurrentItem().hasItemMeta()) {
            amount = item.getAmount();
            String priceText = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', manager.getConfig().getString("FIYAT")));
            String priceLore = ChatColor.stripColor(item.getItemMeta().getLore().get(2));
            String step1 = priceLore.substring(priceText.indexOf('%') - 1);
            int lastDigit = 0;
            for (int i = 0; i < step1.length(); i++) {
               if (Character.isDigit(step1.charAt(step1.length() - i + 1))) {
                  lastDigit = step1.length() - i;
                  break;
               }
            }
            String step2 = step1.substring(1, lastDigit);
            price = Integer.parseInt(step2);
            bal = econ.getBalance((OfflinePlayer)p);
            if (bal >= price) {
               EconomyResponse r = econ.withdrawPlayer((OfflinePlayer)p, price);
               if (r.transactionSuccess()) {
                  OfflinePlayer op;
                  OfflinePlayer paraYatirilacakOyuncu = null;
                  p.sendMessage(MessageUtil.SATIN_ALMA_BASARILI);
                  PlayerInventory playerInventory = p.getInventory();
                  ItemMeta meta = item.getItemMeta();
                  meta.setLore(Arrays.asList(new String[] { "" }));
                  ItemStack mitem = new ItemStack(item.getType(), item.getAmount(), item.getData().getData());
                  ItemMeta x = mitem.getItemMeta();
                  meta.setDisplayName(x.getDisplayName());
                  item.setItemMeta(meta);
                  playerInventory.addItem(new ItemStack[] { item });
                  Gui.getInventory().setItem(e.getSlot(), null);
                  for (int j = 0; j <= T3SL4Market.count; j++) {
                     if (manager.getData().isString("markets.market" + j + ".name") &&
                             manager.getData().getString("markets.market" + j + ".name").equalsIgnoreCase(Gui.getString())) {
                        paraYatirilacakOyuncu = Bukkit.getOfflinePlayer(manager.getData().getString("markets.market" + j + ".slot" + e.getSlot() + ".ekleyen"));
                        uuid = manager.getData().getString("markets.market" + j + ".uuid");
                        manager.getData().set("markets.market" + j + ".slot" + e.getSlot(), null);
                        manager.saveData();
                     }
                  }
                  try {
                     op = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
                  } catch (IllegalArgumentException exception) {
                     p.sendMessage(MessageUtil.HATA);
                     return;
                  }
                  if(MessageUtil.VERGI_YUZDESI == 0) {
                     econ.depositPlayer(paraYatirilacakOyuncu, price);
                  } else {
                     float yatirilacakMiktar = price - ((price / 100) * MessageUtil.VERGI_YUZDESI);
                     econ.depositPlayer(paraYatirilacakOyuncu, yatirilacakMiktar);
                  }
                  e.setCancelled(true);
                  return;
               }
               p.sendMessage(MessageUtil.YETERSIZ_BAKIYE);
               e.setCancelled(true);
               return;
            }
            p.sendMessage(MessageUtil.YETERSIZ_BAKIYE);
            e.setCancelled(true);
            return;
         }
      }
   }
}
