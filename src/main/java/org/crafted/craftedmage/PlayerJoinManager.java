package org.crafted.craftedmage;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.crafted.craftedmage.magic.PlayerData;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerJoinManager implements Listener {

    private File dataFolder;
    private Map<UUID, PlayerData> playerDataMap;

    public PlayerJoinManager(File dataFolder) {
        this.dataFolder = dataFolder;
        this.playerDataMap = new HashMap<>();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if (!playerDataMap.containsKey(playerUUID)) {
            // This is the player's first time joining
            Element assignedElement = getRandomElement();
            PlayerData playerData = new PlayerData(assignedElement);

            // Save player data
            playerDataMap.put(playerUUID, playerData);
            savePlayerData(playerUUID, playerData);

            // Send a message to the player
            player.sendMessage("You have been assigned the element: " + assignedElement.toString());
        }
    }

    private void savePlayerData(UUID playerUUID, PlayerData playerData) {
        File playerFile = new File(dataFolder, playerUUID.toString() + ".yml");
        FileConfiguration playerConfig = YamlConfiguration.loadConfiguration(playerFile);

        // Save player data to the configuration file
        playerConfig.set("assignedElement", playerData.getAssignedElement().toString());
        playerConfig.set("magicLevel", playerData.getMagicLevel());
        playerConfig.set("unlockedSpells", playerData.getUnlockedSpells());

        // Save the configuration
        try {
            playerConfig.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Element getRandomElement() {
        Element[] elements = Element.values();
        int randomIndex = (int) (Math.random() * elements.length);
        return elements[randomIndex];
    }
}
