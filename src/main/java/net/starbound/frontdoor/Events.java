package net.starbound.frontdoor;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.logging.Level;

public class Events implements Listener {

    @EventHandler (priority = EventPriority.MONITOR)
    public void playLoginEvent(PlayerLoginEvent event){
        //FrontDoor.getPlugin(FrontDoor.class).getLogger().log(Level.INFO, "Player joining through " + event.getHostname());
        if(event.getHostname().equalsIgnoreCase("york.inizicraft.net:25565")){
            FrontDoor.matchedPlayers.add(event.getPlayer());
            FrontDoor.getPlugin(FrontDoor.class).getLogger().log(Level.INFO, "Detected login attempt from "+event.getPlayer().getName() + " though york.inizicraft.net:25565");
        }
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void playerJoinEvent(PlayerJoinEvent event){
        if(FrontDoor.matchedPlayers.contains(event.getPlayer())){
            FrontDoor.matchedPlayers.remove(event.getPlayer());

            if(FrontDoor.logPlayers && FrontDoor.logger.masterList.contains(event.getPlayer().getName())){
                FrontDoor.getPlugin(FrontDoor.class).getLogger().log(Level.INFO, "Joining player is already logged. They will not be teleported.");
            } else if(FrontDoor.logPlayers && !(FrontDoor.logger.masterList.contains(event.getPlayer().getName()))){
                FrontDoor.getPlugin(FrontDoor.class).getLogger().log(Level.INFO, "Moving player to York Spawn");
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                String command = "cmi warp york " + event.getPlayer().getName();
                Bukkit.dispatchCommand(console, command);

                FrontDoor.logger.masterList.add(event.getPlayer().getName());
            }
            else{
                FrontDoor.getPlugin(FrontDoor.class).getLogger().log(Level.INFO, "No logging. Moving player to York Spawn");
                ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                String command = "cmi warp york " + event.getPlayer().getName();
                Bukkit.dispatchCommand(console, command);
            }
        }
    }
}
