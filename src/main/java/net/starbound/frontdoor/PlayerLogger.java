package net.starbound.frontdoor;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.Yaml;

import javax.lang.model.type.ArrayType;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class PlayerLogger {

    public static ArrayList<String> masterList;
    void generateSaveFile(){
        File savepoint = new File(FrontDoor.getPlugin(FrontDoor.class).getDataFolder(), "players.yml");
        FileConfiguration data = YamlConfiguration.loadConfiguration(savepoint);

        List<String> defaultList = Arrays.asList("Notch", "Jeb");
        data.set("players", defaultList);

        try {
            data.save(savepoint);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public PlayerLogger(){

        File savepoint = new File(FrontDoor.getPlugin(FrontDoor.class).getDataFolder(), "playres.yml");
        if(!savepoint.exists())
            generateSaveFile();

        masterList = new ArrayList<String>();
        FileConfiguration data = YamlConfiguration.loadConfiguration(savepoint);

        List<String> tempList = data.getStringList("players");
        for(String s : tempList)
            masterList.add(s);
    }

    public void save(){
        File savepoint = new File(FrontDoor.getPlugin(FrontDoor.class).getDataFolder(), "players.yml");
        FileConfiguration data = YamlConfiguration.loadConfiguration(savepoint);

        data.set("players", masterList);
        try {
            data.save(savepoint);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
