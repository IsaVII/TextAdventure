package IsaVII.enemies.loot;

import IsaVII.Objects.Items.ItemType;

public class LootEntry {
    private ItemType item;
    private int minCount;
    private int maxCount;
    private double minChance;
    private double maxChance;
    private Double minDifficulty;
    private Double maxDifficulty;

    public boolean isMoney() {
        return item == null;
    }

    public boolean appliesAtDifficulty(double difficulty) {
        if (minDifficulty != null && difficulty < minDifficulty) {
            return false;
        }
        if (maxDifficulty != null && difficulty > maxDifficulty) {
            return false;
        }
        return true;
    }

    public ItemType getItem() {
        return item;
    }

    public int getMinCount() {
        return minCount;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public double getMinChance() {
        return minChance;
    }

    public double getMaxChance() {
        return maxChance;
    }
}
