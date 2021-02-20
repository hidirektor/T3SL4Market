package me.t3sl4.market;

import me.t3sl4.market.commands.MarketCommand;
import me.t3sl4.market.listeners.InventoryClickListener;
import me.t3sl4.market.listeners.JoinListener;
import me.t3sl4.market.util.MessageUtil;
import me.t3sl4.market.util.SettingsManager;
import me.t3sl4.market.util.TranslationMapping;
import me.t3sl4.market.util.UpdateChecker;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class T3SL4Market extends JavaPlugin {
   public static int count = 0;
   static Plugin plugin;
   static SettingsManager manager = SettingsManager.getInstance();
   public static Economy economy = null;

   public void onEnable() {
      Bukkit.getConsoleSender().sendMessage("   ");
      Bukkit.getConsoleSender().sendMessage("  ____   __   __  _   _   _____   _____   ____    _       _  _   ");
      Bukkit.getConsoleSender().sendMessage(" / ___|  \\ \\ / / | \\ | | |_   _| |___ /  / ___|  | |     | || |  ");
      Bukkit.getConsoleSender().sendMessage(" \\___ \\   \\ V /  |  \\| |   | |     |_ \\  \\___ \\  | |     | || |_ ");
      Bukkit.getConsoleSender().sendMessage("  ___) |   | |   | |\\  |   | |    ___) |  ___) | | |___  |__   _|");
      Bukkit.getConsoleSender().sendMessage(" |____/    |_|   |_| \\_|   |_|   |____/  |____/  |_____|    |_|  ");
      Bukkit.getConsoleSender().sendMessage("    ");
      Bukkit.getConsoleSender().sendMessage("T3SL4 Series: T3SL4Market");
      initialize();
   }

   public void onDisable() {
      plugin = null;
      manager.save("data");
   }

   public void initialize() {
      plugin = this;
      manager.setup(plugin);
      if (!this.setupEconomy()) {
         Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[HATA] Market Eklentisi Icin Vault Eklentisinin Kurulu Olmasi Lazim. ");
      } else {
         new TranslationMapping(this);
         this.registerListeners();
         this.registerCommands();
         count = manager.get("data").getInt("market_sayisi");
         manager.reload("data");
         MessageUtil.loadMessages();
      }
   }

   private boolean setupEconomy() {
      RegisteredServiceProvider<Economy> economyProvider = this.getServer().getServicesManager().getRegistration(Economy.class);
      if (economyProvider != null) {
         economy = (Economy)economyProvider.getProvider();
      }

      return economy != null;
   }

   public void registerListeners() {
      Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
      Bukkit.getPluginManager().registerEvents(new JoinListener(), this);
   }

   public void registerCommands() {
      Bukkit.getPluginCommand("market").setExecutor(new MarketCommand());
   }

   public static Economy getEconomy() {
      return economy;
   }

   public static JavaPlugin getPlugin() {
      return (JavaPlugin)plugin;
   }
}
