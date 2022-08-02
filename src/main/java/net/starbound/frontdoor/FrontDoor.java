package net.starbound.frontdoor;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;

public final class FrontDoor extends JavaPlugin {

    FileConfiguration config;
    public static boolean logPlayers;
    public static PlayerLogger logger;
    public static ArrayList<Player> matchedPlayers;
    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getLogger().log(Level.INFO, "Starting Front Door...");
        matchedPlayers = new ArrayList<Player>();
        logger = new PlayerLogger();

        this.getLogger().log(Level.INFO, "Fetching Config..."); //load config file and default options check
        config = getConfig();
        config.addDefault("isDevBuild", true);
        config.addDefault("logPlayers", false);
        config.options().copyDefaults(true);
        saveConfig();
        this.getLogger().log(Level.INFO, "Fetched Config.");

        if(config.getBoolean("logPlayers"))
            logPlayers = true;
        else logPlayers = false;

        if(config.getBoolean("isDevBuild")) //Devbuild check
            this.getLogger().log(Level.WARNING, "This is a Dev build of Front Door. Not for production use.");

        this.getLogger().log(Level.INFO, "Registering Event Listener.");
        getServer().getPluginManager().registerEvents(new Events(), this);

        this.getLogger().log(Level.INFO, "Front Door has started successfully.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.getLogger().log(Level.INFO, "Stopping Front Door");
        logger.save();
        this.getLogger().log(Level.INFO, "Front Door stopped gracefully.");
    }
}
