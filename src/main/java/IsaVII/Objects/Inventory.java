package IsaVII.Objects;

import IsaVII.Objects.Items.Item;

import java.util.HashMap;
import java.util.Map;

public class Inventory {

    private final Map<Item, Integer> items = new HashMap<>();

    public void addItem(Item item, int quantity) {
        items.merge(item, quantity, Integer::sum);
    }

    public int getQuantity(Item item) {
        return items.getOrDefault(item, 0);
    }

    public Map<Item, Integer> getItems() {
        return items;
    }
}
