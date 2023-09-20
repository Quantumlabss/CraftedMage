package org.crafted.craftedmage.events;

import com.destroystokyo.paper.Title;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.crafted.craftedmage.Element;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class PlayerJoinManager implements Listener {
    private File dataFolder;
    private File playersFile;
    private FileConfiguration playersConfig;

    public void PlayerJoinListener(File dataFolder) {
        this.dataFolder = dataFolder;
        this.playersFile = new File(dataFolder, "org/crafted/craftedmage/data/players.yml");
        this.playersConfig = YamlConfiguration.loadConfiguration(playersFile);
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        // Check if the player has joined before
        if (!hasJoinedBefore(playerUUID)) {
            // This is the player's first time joining
            // Randomly assign an element here
            Element assignedElement = getRandomElement();

            // Save the assigned element and mark the player as having joined before
            playersConfig.set(playerUUID.toString() + ".hasJoinedBefore", true);
            playersConfig.set(playerUUID.toString() + ".assignedElement", assignedElement.toString());


            // Save the configuration
            savePlayersConfig();
        }
    }

    private boolean hasJoinedBefore(UUID playerUUID) {
        return playersConfig.getBoolean(playerUUID.toString() + ".hasJoinedBefore", false);
    }

    private Element getRandomElement() {
        Element[] elements = Element.values();
        int randomIndex = (int) (Math.random() * elements.length);
        return elements[randomIndex];
    }

    private void savePlayersConfig() {
        try {
            playersConfig.save(playersFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
