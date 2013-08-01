/*
 * Copyright (C) 2013 Dabo Ross <http://www.daboross.net/>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.daboross.bukkitdev.extrahealth;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.mcstats.MetricsLite;

/**
 *
 * @author daboross
 */
public class ExtraHealthPlugin extends JavaPlugin implements Listener {

    private ExtraHealthConfig config;

    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(this, this);
        MetricsLite metrics = null;
        try {
            metrics = new MetricsLite(this);
        } catch (IOException ex) {
            getLogger().warning("Unable to create Metrics");
        }
        if (metrics != null) {
            metrics.start();
        }
        config = new ExtraHealthConfig(this);
        config.reloadConfig();
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        sender.sendMessage("ExtraHealth doesn't know about the command /" + cmd.getName());
        return true;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent evt) {
        boostHealth(evt.getPlayer());
    }

    @EventHandler
    public void onWorldChange(final PlayerChangedWorldEvent evt) {
        // Do this one tick later so that permissions plugin can adjust.
        new BukkitRunnable() {
            @Override
            public void run() {
                Player p = evt.getPlayer();
                if (p.isOnline()) {// Make sure the player is still online.
                    boostHealth(p);
                }
            }
        }.runTask(this);
    }

    private void boostHealth(Player p) {
        double maxBoost = 0;
        for (Map.Entry<String, Double> permissionEntry : config.getPermissionsMap().entrySet()) {
            double boost = permissionEntry.getValue();
            if (boost > maxBoost && p.hasPermission(permissionEntry.getKey())) {
                maxBoost = boost;
            }
        }
        double newMaxHealth = 20 + maxBoost * 2;
        getLogger().log(Level.FINE, "Setting max health of player {0} to {1}", new Object[]{p.getName(), newMaxHealth});
        p.setMaxHealth(newMaxHealth);
    }
}
