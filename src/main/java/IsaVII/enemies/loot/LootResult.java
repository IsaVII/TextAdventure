package IsaVII.enemies.loot;

import IsaVII.Objects.Items.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class LootResult {
    private final List<ItemStack> items = new ArrayList<>();
    private int coins = 0;

    public void addItem(ItemStack itemStack) {
        items.add(itemStack);
    }

    public void addCoins(int amount) {
        coins += amount;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public int getCoins() {
        return coins;
    }

    public boolean isEmpty() {
        return items.isEmpty() && coins == 0;
    }
}
