package me.t3sl4.market.commands;

import me.t3sl4.market.T3SL4Market;
import me.t3sl4.market.gui.Gui;
import me.t3sl4.market.util.MarketUtil;
import me.t3sl4.market.util.MessageUtil;
import me.t3sl4.market.util.SettingsManager;
import me.t3sl4.market.util.TranslationMapping;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MarketCommand implements CommandExecutor {
   SettingsManager manager = SettingsManager.getInstance();
   static int slot;
   static int fiyat;
   static int adet;
   static boolean selfInv = false;
   static byte data;
   static int q;
   static int k = 0;
   static Material material;
   static ItemStack item;
   static int amount;
   static int aData;
   static byte aDataByte;
   private static ItemMeta oldItem;

   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
      int i;
      int n;
      Player p = (Player) sender;
      String uuid = p.getUniqueId().toString();
      if (cmd.getName().equalsIgnoreCase("market")) {
         if (args.length == 0) {
            if (p instanceof Player) {
               if (p.isOp() || p.hasPermission("t3sl4market.general")) {
                  for (String s : MessageUtil.INFO) {
                     p.sendMessage(s);
                  }
               } else {
                  for (int k = 0; k < MessageUtil.INFO.size() - MessageUtil.SADECE_YETKILI_INFO_SATIRI_SAYISI; k++) {
                     p.sendMessage(MessageUtil.INFO.get(k));
                  }
               }
            } else {
               sender.sendMessage(MessageUtil.CONSOLE);
            }
         } else {
            if (args[0].equalsIgnoreCase("kur")) {
               if (sender instanceof Player) {
                  if (p.isOp() || p.hasPermission("t3sl4market.kur")) {
                     if (args.length != 2) {
                        p.sendMessage(MessageUtil.KUR_KULLANIM);
                        return true;
                     }

                     if (MarketUtil.hasMarket(p)) {
                        p.sendMessage(MessageUtil.ZATEN_MARKET_VAR);
                        return true;
                     }

                     if(MarketUtil.checkAllOrtaks(p)) {
                        int marketNumber = MarketUtil.marketsNumber;
                        Player marketSahibi = Bukkit.getPlayer(this.manager.getData().getString("markets.market" + marketNumber + ".name"));
                        p.sendMessage(MessageUtil.ZATEN_ORTAKSIN.replaceAll("%player%", marketSahibi.getName()));
                        return true;
                     }

                     boolean durum = false;
                     if (args[1].equalsIgnoreCase("true")) {
                        durum = true;
                     } else if (args[1].equalsIgnoreCase("false")) {
                        durum = false;
                     } else {
                        p.sendMessage(MessageUtil.KUR_KULLANIM);
                     }

                     ++T3SL4Market.count;
                     this.manager.getData().set("market_sayisi", T3SL4Market.count);
                     this.manager.getData().set("markets.market" + T3SL4Market.count + ".uuid", uuid);
                     this.manager.getData().set("markets.market" + T3SL4Market.count + ".name", p.getName());
                     this.manager.getData().set("markets.market" + T3SL4Market.count + ".ortaklik.enable", durum);
                     this.manager.getData().set("markets.market" + T3SL4Market.count + ".ortaklik.sonSayi", 0);
                     this.manager.saveData();
                     p.sendMessage(MessageUtil.MARKET_YARATILDI);
                     new Gui(p.getName());
                     return true;
                  } else {
                     p.sendMessage(MessageUtil.PERMERROR);
                  }
               } else {
                  sender.sendMessage(MessageUtil.CONSOLE);
               }
            } else if (args[0].equalsIgnoreCase("aç")) {
               if (sender instanceof Player) {
                  if (p.isOp() || p.hasPermission("t3sl4market.ac")) {
                     if (args.length == 1) {
                        if (MarketUtil.hasMarket(p)) {
                           new Gui(p.getName());
                           MarketUtil.loadItemsString(p.getName());
                           if (Gui.getInventory() == null) {
                              p.sendMessage(MessageUtil.HATA);
                              return true;
                           }

                           p.openInventory(Gui.getInventory());
                           return true;
                        } else {
                           if(MarketUtil.checkAllOrtaks(p)) {
                              int marketNumber = MarketUtil.marketsNumber;
                              Player ortak = p;
                              Player marketSahibi = Bukkit.getPlayer(this.manager.getData().getString("markets.market" + marketNumber + ".name"));
                              new Gui(marketSahibi.getName());
                              MarketUtil.loadItemsString(marketSahibi.getName());
                              if (Gui.getInventory() == null) {
                                 p.sendMessage(MessageUtil.HATA);
                                 return true;
                              }

                              p.openInventory(Gui.getInventory());
                              return true;
                           } else {
                              p.sendMessage(MessageUtil.MARKET_BULUNAMADI);
                              return true;
                           }
                        }
                     } else if (args.length == 2) {
                        i = 0;
                        n = 0;
                        if (p.getName().equalsIgnoreCase(args[1])) {
                           selfInv = true;
                        }

                        do {
                           if (i > T3SL4Market.count) {
                              return true;
                           }

                           if (this.manager.getData().isString("markets.market" + i + ".name") && this.manager.getData().getString("markets.market" + i + ".name").equalsIgnoreCase(args[1])) {
                              new Gui(args[1]);
                              MarketUtil.loadItemsString(args[1]);
                              p.openInventory(Gui.getInventory());
                              return true;
                           }

                           ++i;
                           ++n;
                        } while (n <= T3SL4Market.count);

                        p.sendMessage(MessageUtil.OYUNCU_BULUNAMADI);
                        return true;
                     }
                  } else {
                     p.sendMessage(MessageUtil.PERMERROR);
                  }
               } else {
                  sender.sendMessage(MessageUtil.CONSOLE);
               }
            } else if (args[0].equalsIgnoreCase("ekle")) {
               if (sender instanceof Player) {
                  if (p.isOp() || p.hasPermission("t3sl4market.ekle")) {
                     if (args.length != 3) {
                        p.sendMessage(MessageUtil.EKLE_KULLANIM);
                        return true;
                     }

                     if(MarketUtil.checkAllOrtaks(p)) {
                        int marketNumber = MarketUtil.marketsNumber;
                        Player marketSahibi = Bukkit.getPlayer(this.manager.getData().getString("markets.market" + marketNumber + ".name"));
                        if (p.getInventory().getItemInHand().getType().equals(Material.AIR)) {
                           p.sendMessage(MessageUtil.ELINIZ_BOS);
                           return true;
                        }

                        ItemStack m = p.getInventory().getItemInHand();
                        oldItem = m.getItemMeta();
                        n = p.getInventory().getHeldItemSlot();
                        data = m.getData().getData();
                        adet = m.getAmount();

                        try {
                           slot = Integer.parseInt(args[1]);
                        } catch (Exception var11) {
                           p.sendMessage(MessageUtil.HATA);
                           return true;
                        }

                        try {
                           fiyat = Integer.parseInt(args[2]);
                        } catch (Exception var10) {
                           p.sendMessage(MessageUtil.HATA);
                           return true;
                        }

                        if (slot > 53) {
                           p.sendMessage(MessageUtil.SLOT_FAZLA);
                           return true;
                        }

                        for (int j = 0; j <= T3SL4Market.count; ++j) {
                           if (this.manager.getData().isString("markets.market" + j + ".uuid") && this.manager.getData().getString("markets.market" + j + ".uuid").equalsIgnoreCase(String.valueOf(marketSahibi.getUniqueId()))) {
                              if (this.manager.getData().isConfigurationSection("markets.market" + j + ".slot" + slot)) {
                                 p.sendMessage(MessageUtil.SLOT_ZATEN_DOLU);
                                 return true;
                              }

                              this.manager.getData().set("markets.market" + j + ".slot" + slot + ".item", m);
                              this.manager.getData().set("markets.market" + j + ".slot" + slot + ".fiyat", fiyat);
                              this.manager.getData().set("markets.market" + j + ".slot" + slot + ".ekleyen", p.getName());
                              this.manager.saveData();
                           }
                        }

                        p.sendMessage(MessageUtil.ITEM_EKLENDI);
                        p.getInventory().setItem(n, new ItemStack(Material.AIR));
                        return true;
                     } else {
                        if (!MarketUtil.hasMarket(p)) {
                           p.sendMessage(MessageUtil.MARKET_BULUNAMADI);
                           return true;
                        }

                        if (p.getInventory().getItemInHand().getType().equals(Material.AIR)) {
                           p.sendMessage(MessageUtil.ELINIZ_BOS);
                           return true;
                        }

                        ItemStack m = p.getInventory().getItemInHand();
                        oldItem = m.getItemMeta();
                        n = p.getInventory().getHeldItemSlot();
                        data = m.getData().getData();
                        adet = m.getAmount();

                        try {
                           slot = Integer.parseInt(args[1]);
                        } catch (Exception var11) {
                           p.sendMessage(MessageUtil.HATA);
                           return true;
                        }

                        try {
                           fiyat = Integer.parseInt(args[2]);
                        } catch (Exception var10) {
                           p.sendMessage(MessageUtil.HATA);
                           return true;
                        }

                        if (slot > 53) {
                           p.sendMessage(MessageUtil.SLOT_FAZLA);
                           return true;
                        }

                        for (int j = 0; j <= T3SL4Market.count; ++j) {
                           if (this.manager.getData().isString("markets.market" + j + ".uuid") && this.manager.getData().getString("markets.market" + j + ".uuid").equalsIgnoreCase(uuid)) {
                              if (this.manager.getData().isConfigurationSection("markets.market" + j + ".slot" + slot)) {
                                 p.sendMessage(MessageUtil.SLOT_ZATEN_DOLU);
                                 return true;
                              }

                              this.manager.getData().set("markets.market" + j + ".slot" + slot + ".item", m);
                              this.manager.getData().set("markets.market" + j + ".slot" + slot + ".fiyat", fiyat);
                              this.manager.getData().set("markets.market" + j + ".slot" + slot + ".ekleyen", p.getName());
                              this.manager.saveData();
                           }
                        }

                        p.sendMessage(MessageUtil.ITEM_EKLENDI);
                        p.getInventory().setItem(n, new ItemStack(Material.AIR));
                        return true;
                     }
                  } else {
                     p.sendMessage(MessageUtil.PERMERROR);
                  }
               } else {
                  sender.sendMessage(MessageUtil.CONSOLE);
               }
            } else if (args[0].equalsIgnoreCase("kaldır")) {
               if (sender instanceof Player) {
                  if (p.isOp() || p.hasPermission("t3sl4market.kaldir")) {
                     if (args.length != 2) {
                        p.sendMessage(MessageUtil.KALDIRMA_KULLANIM);
                        return true;
                     }

                     if(MarketUtil.checkAllOrtaks(p)) {
                        int marketNumber = MarketUtil.marketsNumber;
                        Player marketSahibi = Bukkit.getPlayer(this.manager.getData().getString("markets.market" + marketNumber + ".name"));
                        for (i = 0; i <= T3SL4Market.count; ++i) {
                           if (this.manager.getData().isString("markets.market" + i + ".name") && this.manager.getData().getString("markets.market" + i + ".name").equalsIgnoreCase(marketSahibi.getName()) && this.manager.getData().isConfigurationSection("markets.market" + i + ".slot" + args[1])) {
                              try {
                                 slot = Integer.parseInt(args[1]);
                              } catch (Exception var12) {
                                 p.sendMessage(MessageUtil.HATA);
                                 return true;
                              }

                              if (this.manager.getData().isItemStack("markets.market" + i + ".slot" + slot + ".item")) {
                                 if(this.manager.getData().getString("markets.market" + i + ".slot" + slot + ".ekleyen") == p.getName()) {
                                    item = this.manager.getData().getItemStack("markets.market" + i + ".slot" + slot + ".item");
                                    item.setItemMeta(oldItem);
                                    p.getInventory().addItem(new ItemStack[]{item});
                                    this.manager.getData().set("markets.market" + i + ".slot" + slot, (Object) null);
                                    this.manager.saveData();
                                 } else {
                                    p.sendMessage(MessageUtil.ESYAYI_SEN_EKLEMEDIN);
                                 }
                              }
                           }
                        }

                        if (args[1].equalsIgnoreCase("satır")) {

                        }

                        if (args[1].equalsIgnoreCase("hepsi")) {
                           for (i = 0; i <= T3SL4Market.count; ++i) {
                              if (this.manager.getData().isString("markets.market" + i + ".name") && this.manager.getData().getString("markets.market" + i + ".name").equalsIgnoreCase(p.getName())) {
                                 n = 0;

                                 while (n <= 54) {
                                    if (this.manager.getData().isConfigurationSection("markets.market" + i + ".slot" + n)) {
                                       slot = n;
                                       if (this.manager.getData().isItemStack("markets.market" + i + ".slot" + slot + ".item")) {
                                          if(this.manager.getData().getString("markets.market" + i + ".slot" + slot + ".ekleyen") == p.getName()) {
                                             item = this.manager.getData().getItemStack("markets.market" + i + ".slot" + slot + ".item");
                                             item.setItemMeta(oldItem);
                                             p.getInventory().addItem(new ItemStack[]{item});
                                             this.manager.getData().set("markets.market" + i + ".slot" + slot, (Object) null);
                                             this.manager.saveData();
                                          }
                                       }
                                    } else {
                                       ++n;
                                    }
                                 }
                              }
                           }
                           p.sendMessage(MessageUtil.MARKET_TEMIZLENDI);
                           return true;
                        }
                     } else {
                        if (!MarketUtil.hasMarket(p)) {
                           p.sendMessage(MessageUtil.MARKET_BULUNAMADI);
                           return true;
                        }

                        for (i = 0; i <= T3SL4Market.count; ++i) {
                           if (this.manager.getData().isString("markets.market" + i + ".name") && this.manager.getData().getString("markets.market" + i + ".name").equalsIgnoreCase(p.getName()) && this.manager.getData().isConfigurationSection("markets.market" + i + ".slot" + args[1])) {
                              try {
                                 slot = Integer.parseInt(args[1]);
                              } catch (Exception var12) {
                                 p.sendMessage(MessageUtil.HATA);
                                 return true;
                              }

                              if (this.manager.getData().isItemStack("markets.market" + i + ".slot" + slot + ".item")) {
                                 if(this.manager.getData().getString("markets.market" + i + ".slot" + slot + ".ekleyen") == p.getName()) {
                                    item = this.manager.getData().getItemStack("markets.market" + i + ".slot" + slot + ".item");
                                    item.setItemMeta(oldItem);
                                    p.getInventory().addItem(new ItemStack[]{item});
                                    this.manager.getData().set("markets.market" + i + ".slot" + slot, (Object) null);
                                    this.manager.saveData();
                                 }
                              }
                           }
                        }

                        if (args[1].equalsIgnoreCase("satır")) {

                        }

                        if (args[1].equalsIgnoreCase("hepsi")) {
                           for (i = 0; i <= T3SL4Market.count; ++i) {
                              if (this.manager.getData().isString("markets.market" + i + ".name") && this.manager.getData().getString("markets.market" + i + ".name").equalsIgnoreCase(p.getName())) {
                                 n = 0;

                                 while (n <= 54) {
                                    if (this.manager.getData().isConfigurationSection("markets.market" + i + ".slot" + n)) {
                                       slot = n;
                                       if (this.manager.getData().isItemStack("markets.market" + i + ".slot" + slot + ".item")) {
                                          if(this.manager.getData().getString("markets.market" + i + ".slot" + slot + ".ekleyen") == p.getName()) {
                                             item = this.manager.getData().getItemStack("markets.market" + i + ".slot" + slot + ".item");
                                             item.setItemMeta(oldItem);
                                             p.getInventory().addItem(new ItemStack[]{item});
                                             this.manager.getData().set("markets.market" + i + ".slot" + slot, (Object) null);
                                             this.manager.saveData();
                                          }
                                       }
                                    } else {
                                       ++n;
                                    }
                                 }
                              }
                           }

                           p.sendMessage(MessageUtil.MARKET_TEMIZLENDI);
                           return true;
                        }
                     }
                  } else {
                     p.sendMessage(MessageUtil.PERMERROR);
                  }
               } else {
                  sender.sendMessage(MessageUtil.CONSOLE);
               }
            } else if (args[0].equalsIgnoreCase("ortaklik")) {
               if (sender instanceof Player) {
                  if (p.isOp() || p.hasPermission("t3sl4market.ortaklik")) {
                     if (MessageUtil.ORTAKLIK) {
                        if (!MarketUtil.hasMarket(p)) {
                           p.sendMessage(MessageUtil.MARKET_BULUNAMADI_ORTAKLIK);
                           return true;
                        }
                        if(args.length != 1) {
                           p.sendMessage(MessageUtil.ORTAKLIK_KULLANIM);
                           return true;
                        }

                        int marketNumber = MarketUtil.getMarketNumber(p);
                        if (this.manager.getData().getBoolean("markets.market" + marketNumber + ".ortaklik.enable") == false) {
                           this.manager.getData().set("markets.market" + marketNumber + ".ortaklik.enable", true);
                           this.manager.saveData();
                           p.sendMessage(MessageUtil.ORTAKLIK_AKTIF);
                        } else {
                           this.manager.getData().set("markets.market" + marketNumber + ".ortaklik.enable", false);
                           this.manager.saveData();
                           p.sendMessage(MessageUtil.ORTAKLIK_DEAKTIF);
                        }
                     } else {
                        p.sendMessage(MessageUtil.ORTAKLIK_DEVREDISI);
                     }
                  } else {
                     p.sendMessage(MessageUtil.PERMERROR);
                  }
               } else {
                  sender.sendMessage(MessageUtil.CONSOLE);
               }
            } else if (args[0].equalsIgnoreCase("ortakekle")) {
               if (sender instanceof Player) {
                  if (p.isOp() || p.hasPermission("t3sl4market.ortakekle")) {
                     if (MessageUtil.ORTAKLIK) {
                        if (!MarketUtil.hasMarket(p)) {
                           p.sendMessage(MessageUtil.MARKET_BULUNAMADI_ORTAKLIK);
                           return true;
                        }

                        if (this.manager.getData().getBoolean("markets.market" + MarketUtil.getMarketNumber(p) + ".ortaklik.enable") == false) {
                           p.sendMessage(MessageUtil.ORTAKLIK_KAPALI);
                           return true;
                        }

                        //if(args.length != 1) {
                           //p.sendMessage(MessageUtil.ORTAKEKLE_KULLANIM);
                           //return true;
                        //}

                        Player eklenecek = Bukkit.getPlayer(args[1]);
                        if (eklenecek != null) {
                           if (eklenecek.getName() != p.getName()) {
                              if (eklenecek.isOnline()) {
                                 if (MarketUtil.checkOrtak(p, eklenecek)) {
                                    String eklenecekIsim = eklenecek.getName();
                                    int marketNumber = MarketUtil.getMarketNumber(p);
                                    int sonSayi = 0;
                                    this.manager.getData().set("markets.market" + marketNumber + ".ortaklik.sonSayi", sonSayi);
                                    if (this.manager.getData().getInt("markets.market" + marketNumber + ".ortaklik.sonSayi") == 0) {
                                       this.manager.getData().set("markets.market" + marketNumber + ".ortaklik.ortaklar." + sonSayi, eklenecekIsim);
                                       sonSayi++;
                                       this.manager.getData().set("markets.market" + marketNumber + ".ortaklik.sonSayi", sonSayi);
                                       this.manager.saveData();
                                    } else {
                                       int ortakSira = (int) this.manager.getData().get("markets.market" + marketNumber + ".ortaklik.sonSayi");
                                       this.manager.getData().set("markets.market" + marketNumber + ".ortaklik.ortaklar." + ortakSira, eklenecekIsim);
                                       this.manager.saveData();
                                    }
                                    p.sendMessage(MessageUtil.ORTAK_EKLENDI.replaceAll("%player%", eklenecekIsim));
                                    eklenecek.sendMessage(MessageUtil.ORTAK_OLARAK_EKLENDINIZ.replaceAll("%player%", p.getName()));
                                 } else {
                                    p.sendMessage(MessageUtil.ZATEN_ORTAK.replaceAll("%player%", eklenecek.getName()));
                                 }
                              } else {
                                 p.sendMessage(MessageUtil.ONLINE);
                              }
                           } else {
                              p.sendMessage(MessageUtil.KENDINI_ORTAK_EKLEYEMEZSIN);
                           }
                        } else {
                           p.sendMessage(MessageUtil.OYUNCU_BULUNAMADI);
                        }
                     } else {
                        p.sendMessage(MessageUtil.ORTAKLIK_DEVREDISI);
                     }
                  } else {
                     p.sendMessage(MessageUtil.PERMERROR);
                  }
               } else {
                  sender.sendMessage(MessageUtil.CONSOLE);
               }
            } else if (args[0].equalsIgnoreCase("ortaksil")) {
               if (sender instanceof Player) {
                  if (p.isOp() || p.hasPermission("t3sl4market.ortaksil")) {
                     if (MessageUtil.ORTAKLIK) {
                        if (!MarketUtil.hasMarket(p)) {
                           p.sendMessage(MessageUtil.MARKET_BULUNAMADI_ORTAKLIK);
                           return true;
                        }

                        if (this.manager.getData().getBoolean("markets.market" + MarketUtil.getMarketNumber(p) + ".ortaklik.enable") == false) {
                           p.sendMessage(MessageUtil.ORTAKLIK_KAPALI);
                           return true;
                        }

                        //if(args.length != 1) {
                           //p.sendMessage(MessageUtil.ORTAKSIL_KULLANIM);
                           //return true;
                        //}

                        Player silinecek = Bukkit.getPlayer(args[1]);
                        int marketNumber = MarketUtil.getMarketNumber(p);
                        int sonSayi = (int) this.manager.getData().get("markets.market" + marketNumber + ".ortaklik.sonSayi");
                        List<String> ortaklar = new ArrayList<>();
                        for (int j = 0; j < sonSayi; j++) {
                           ortaklar.add(this.manager.getData().getString("markets.market" + marketNumber + ".ortaklik.ortaklar." + j));
                        }
                        int silinecekSira = 0;
                        int maks = ortaklar.size();
                        if (ortaklar.contains(silinecek.getName())) {
                           for (int k = 0; k < ortaklar.size(); k++) {
                              if (this.manager.getData().getString("markets.market" + marketNumber + ".ortaklik.ortaklar." + k).equalsIgnoreCase(silinecek.getName())) {
                                 silinecekSira = k;
                              }
                           }
                           if (this.manager.getData().getInt("markets.market" + marketNumber + ".ortaklik.sonSayi") != 1) {
                              this.manager.getData().set("markets.market" + marketNumber + ".ortaklik.ortaklar." + silinecekSira, this.manager.getData().getString("markets.market" + marketNumber + ".ortaklik.ortaklar." + maks));
                              this.manager.getData().set("markets.market" + marketNumber + ".ortaklik.sonSayi", sonSayi - 1);
                              this.manager.saveData();
                           } else {
                              this.manager.getData().set("markets.market" + marketNumber + ".ortaklik.ortaklar", null);
                              this.manager.getData().set("markets.market" + marketNumber + ".ortaklik.sonSayi", 0);
                              manager.saveData();
                           }
                           p.sendMessage(MessageUtil.ORTAK_SILINDI.replaceAll("%player%", silinecek.getName()));
                           silinecek.sendMessage(MessageUtil.ORTAKLIKTAN_CIKARILDINIZ.replaceAll("%player%", p.getName()));
                        } else {
                           p.sendMessage(MessageUtil.ORTAK_BULUNAMADI);
                        }
                     } else {
                        p.sendMessage(MessageUtil.ORTAKLIK_DEVREDISI);
                     }
                  } else {
                     p.sendMessage(MessageUtil.PERMERROR);
                  }
               } else {
                  sender.sendMessage(MessageUtil.CONSOLE);
               }
            } else if (args[0].equalsIgnoreCase("ortaklar")) {
               if (sender instanceof Player) {
                  if (p.isOp() || p.hasPermission("t3sl4market.ortaklar")) {
                     if (MessageUtil.ORTAKLIK) {
                        //if(args.length != 0) {
                           //p.sendMessage(MessageUtil.ORTAKLAR_KULLANIM);
                           //return true;
                        //}

                        if(MarketUtil.checkAllOrtaks(p)) {
                           int marketNumber = MarketUtil.marketsNumber;
                           Player marketSahibi = Bukkit.getPlayer(this.manager.getData().getString("markets.market" + marketNumber + ".name"));
                           p.sendMessage(MessageUtil.MARKET_SAHIBI);
                           p.sendMessage(MessageUtil.MARKET_SAHIP.replaceAll("%player%", marketSahibi.getName()));
                        } else {
                           p.sendMessage(MessageUtil.MEVCUT_ORTAKLARIN);
                           int marketNumber = MarketUtil.getMarketNumber(p);
                           int sonSayi = this.manager.getData().getInt("markets.market" + marketNumber + ".ortaklik.sonSayi");
                           List<String> ortaklar = new ArrayList<>();
                           for (int z = 0; z < sonSayi; z++) {
                              ortaklar.add(this.manager.getData().getString("markets.market" + marketNumber + ".ortaklik.ortaklar." + z));
                           }
                           for (int k = 0; k < ortaklar.size(); k++) {
                              p.sendMessage(MessageUtil.ORTAK_SIRALAMASI.replaceAll("%number%", String.valueOf(k)).replaceAll("%player%", ortaklar.get(k)));
                           }
                        }
                     } else {
                        p.sendMessage(MessageUtil.ORTAKLIK_DEVREDISI);
                     }
                  } else {
                     p.sendMessage(MessageUtil.PERMERROR);
                  }
               } else {
                  sender.sendMessage(MessageUtil.CONSOLE);
               }
            } else if (args[0].equalsIgnoreCase("ayrıl")) {
               if (sender instanceof Player) {
                  if (p.isOp() || p.hasPermission("t3sl4market.ayril")) {
                     if (MessageUtil.ORTAKLIK) {
                        if(args.length != 0) {
                           p.sendMessage(MessageUtil.AYRIL_KULLANIM);
                           return true;
                        }
                        if (MarketUtil.checkAllOrtaks(p)) {
                           int marketNumber = MarketUtil.marketsNumber;
                           Player silinecek = p;
                           Player marketSahibi = Bukkit.getPlayer(this.manager.getData().getString("markets.market" + marketNumber + ".name"));
                           int sonSayi = (int) this.manager.getData().get("markets.market" + marketNumber + ".ortaklik.sonSayi");
                           List<String> ortaklar = new ArrayList<>();
                           for (int j = 0; j < sonSayi; j++) {
                              ortaklar.add(this.manager.getData().getString("markets.market" + marketNumber + ".ortaklik.ortaklar." + j));
                           }
                           int silinecekSira = 0;
                           int maks = ortaklar.size();
                           if (ortaklar.contains(silinecek.getName())) {
                              for (int k = 0; k < ortaklar.size(); k++) {
                                 if (this.manager.getData().getString("markets.market" + marketNumber + ".ortaklik.ortaklar." + k).equalsIgnoreCase(silinecek.getName())) {
                                    silinecekSira = k;
                                 }
                              }
                              if (this.manager.getData().getInt("markets.market" + marketNumber + ".ortaklik.sonSayi") != 1) {
                                 this.manager.getData().set("markets.market" + marketNumber + ".ortaklik.ortaklar." + silinecekSira, this.manager.getData().getString("markets.market" + marketNumber + ".ortaklik.ortaklar." + maks));
                                 this.manager.getData().set("markets.market" + marketNumber + ".ortaklik.sonSayi", sonSayi - 1);
                                 this.manager.saveData();
                              } else {
                                 this.manager.getData().set("markets.market" + marketNumber + ".ortaklik.ortaklar", null);
                                 this.manager.getData().set("markets.market" + marketNumber + ".ortaklik.sonSayi", 0);
                                 manager.saveData();
                              }
                              silinecek.sendMessage(MessageUtil.AYRILDINIZ.replaceAll("%player%", marketSahibi.getName()));
                              marketSahibi.sendMessage(MessageUtil.AYRILDI.replaceAll("%player%", silinecek.getName()));
                           }
                        } else {
                           p.sendMessage(MessageUtil.ORTAK_DEGILSIN);
                        }
                     } else {
                        p.sendMessage(MessageUtil.ORTAKLIK_DEVREDISI);
                     }
                  } else {
                     p.sendMessage(MessageUtil.PERMERROR);
                  }
               } else {
                  p.sendMessage(MessageUtil.CONSOLE);
               }
            } else if (args[0].equalsIgnoreCase("ortaklikd")) {
               if(sender instanceof Player) {
                  if(p.isOp() || p.hasPermission("t3sl4market.ortaklikd")) {
                     if(args.length !=2) {
                        p.sendMessage(MessageUtil.ORTAKLIKD_KULLANIM);
                     } else {
                        if(args[1].equalsIgnoreCase("true")) {
                           if(MessageUtil.ORTAKLIK) {
                              p.sendMessage(MessageUtil.ORTAKLIK_ZATEN_AKTIF);
                           } else {
                              this.manager.getConfig().set("Settings.Ortaklik", true);
                              this.manager.saveConfig();
                              p.sendMessage(MessageUtil.ORTAKLIKD_AKTIF);
                           }
                        } else if(args[1].equalsIgnoreCase("false")) {
                           if(!MessageUtil.ORTAKLIK) {
                              p.sendMessage(MessageUtil.ORTAKLIK_ZATEN_KAPALI);
                           } else {
                              this.manager.getConfig().set("Settings.Ortaklik", false);
                              this.manager.saveConfig();
                              p.sendMessage(MessageUtil.ORTAKLIKD_PASIF);
                           }
                        } else {
                           p.sendMessage(MessageUtil.ORTAKLIKD_KULLANIM);
                        }
                     }
                  } else {
                     p.sendMessage(MessageUtil.PERMERROR);
                  }
               } else {
                  sender.sendMessage(MessageUtil.CONSOLE);
               }
            } else if (args[0].equalsIgnoreCase("reload")) {
               if (sender.isOp() || sender.hasPermission("t3sl4market.reload")) {
                  if(args.length != 0) {
                     p.sendMessage(MessageUtil.RELOAD_KULLANIM);
                     return true;
                  }
                  this.manager.reloadConfig();
                  this.manager.reloadItemConfig();
                  MessageUtil.loadMessages();
                  new TranslationMapping(T3SL4Market.getPlugin());
                  p.sendMessage(MessageUtil.RELOAD);
                  return true;
               } else {
                  sender.sendMessage(MessageUtil.PERMERROR);
               }
            }
         }
      }
      return true;
   }
}