package org.crafted.craftedmage.magic;

import org.crafted.craftedmage.Element;

import java.util.HashSet;
import java.util.Set;

public class PlayerData {
    private Element assignedElement;
    private int magicLevel;
    private Set<String> unlockedSpells;

    public PlayerData(Element assignedElement) {
        this.assignedElement = assignedElement;
        this.magicLevel = 1; // Initial magic level
        this.unlockedSpells = new HashSet<>();
    }

    public Element getAssignedElement() {
        return assignedElement;
    }

    public int getMagicLevel() {
        return magicLevel;
    }

    public Set<String> getUnlockedSpells() {
        return unlockedSpells;
    }
}
