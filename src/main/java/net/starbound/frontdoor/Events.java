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
        if(event.getHostname().equalsIgnoreCase("york.inizicraft.net:25565")){
            FrontDoor.matchedPlayers.add(event.getPlayer());
            FrontDoor.getPlugin(FrontDoor.class).getLogger().log(Level.INFO, "Detected login attempt from "+event.getPlayer().getName() + " though york.inizicraft.net:25565");
        }
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void playerJoinEvent(PlayerJoinEvent event){
        for(Player p : FrontDoor.matchedPlayers){ //if the player is using the york ip
            if(p.getUniqueId() == event.getPlayer().getUniqueId()){
                if(FrontDoor.logPlayers) { //if players are being logged
                    for (String s : FrontDoor.logger.masterList) { //if the player has not already joined
                        if (!s.equalsIgnoreCase(p.getName())) {
                            FrontDoor.getPlugin(FrontDoor.class).getLogger().log(Level.INFO, "Moving player to York Spawn");
                            ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                            String command = "/cmi warp york " + p.getName();
                            Bukkit.dispatchCommand(console, command);

                            FrontDoor.logger.masterList.add(p.getName());
                            return;
                        }
                    }
                } else{ //players are not being logged and so always put them at spawn.
                    FrontDoor.getPlugin(FrontDoor.class).getLogger().log(Level.INFO, "Moving player to York Spawn");
                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    String command = "/cmi warp york " + p.getName();
                    Bukkit.dispatchCommand(console, command);
                }

            }

        }
    }
}
