package me.t3sl4.market.listeners;

import me.t3sl4.market.T3SL4Market;
import me.t3sl4.market.util.MessageUtil;
import me.t3sl4.market.util.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if(e.getPlayer().isOp()) {
            new UpdateChecker(T3SL4Market.getPlugin(), 89057).getVersion(version -> {
                if (!T3SL4Market.getPlugin().getDescription().getVersion().equalsIgnoreCase(version)) {
                    e.getPlayer().sendMessage(MessageUtil.colorize(MessageUtil.PREFIX + "&eThere is a new update available: "));
                    e.getPlayer().sendMessage(MessageUtil.colorize(MessageUtil.PREFIX + "&eDownload Link: &chttps://www.spigotmc.org/resources/t3sl4market-tax-partnership-onepage.89057/"));
                }
            });
        }
    }
}
