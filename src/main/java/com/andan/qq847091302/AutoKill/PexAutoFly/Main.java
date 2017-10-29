package com.andan.qq847091302.AutoKill.PexAutoFly;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin implements Listener{

    public double height = getConfig().getDouble("Height");
    public double multiply = getConfig().getDouble("Multiply");

    public void onEnable(){
        getServer().getPluginManager().registerEvents(this,this);
        ConfigLoad();
        getLogger().info("权限自动飞行插件开启!");
    }
    public void onDisable(){
        getLogger().info("插件关闭QwQ");
    }


    public void ConfigLoad() {
        File f = new File(getDataFolder() + "/config.yml");
        if (f.exists()) {
            getLogger().info("检测到config.yml,开始加载配置!");
        } else {
            getLogger().info("未检测到config.yml,正在创建默认配置文件!");
            saveDefaultConfig();
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if (p.hasPermission("zhubo.prefix")){
            Bukkit.broadcastMessage("§b[§b§l主播✔§b] "+p.getDisplayName()+" §e进入了服务器!");
            p.setAllowFlight(true);
            p.setFlying(true);
        }
    }
    @EventHandler
    public void onDoubleJump(PlayerToggleFlightEvent e)
    {
        Player p = e.getPlayer();
        if (!p.hasPermission("zhubo.prefix")){
        if (p.getGameMode() != GameMode.CREATIVE)
        {
            e.setCancelled(true);
            p.setAllowFlight(false);
            p.setFlying(false);
            p.setVelocity(p.getLocation().getDirection().multiply(1.0D * this.multiply).setY(1.0D * this.height));
            p.setFallDistance(0.0F);
        }
    }
    }
    @EventHandler
    public void onMove(PlayerMoveEvent e)
    {
        Player p = e.getPlayer();
        if (!p.hasPermission("zhubo.prefix")){
        if ((p.getGameMode() != GameMode.CREATIVE) &&
                (p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR)) {
            p.setAllowFlight(true);
        }
    }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e)
    {
        Player player = e.getPlayer();
        if (e.getMessage().equalsIgnoreCase("/dj reload")) {
            if (player.hasPermission("doublejump.reload"))
            {
                reloadConfig();
                player.sendMessage("§b插件重载成功!");
                e.setCancelled(true);
            }
            else
            {
                player.sendMessage("§c你没有权限执行该命令!!");
            }
        }
    }
}
