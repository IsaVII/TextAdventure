package IsaVII.Objects;

import IsaVII.Objects.Items.Item;

import java.util.HashMap;
import java.util.Map;

public class Inventory {

    private final Map<Item, Integer> items = new HashMap<>();

    public void addItem(Item item, int quantity) {
        items.merge(item, quantity, Integer::sum);
    }

    public boolean removeItem(Item item, int quantity) {
        Integer currentQuantity = items.get(item);
        if (currentQuantity == null || currentQuantity < quantity) {
            return false;
        }

        if (currentQuantity == quantity) {
            items.remove(item);
        } else {
            items.put(item, currentQuantity - quantity);
        }
        return true;
    }

    public int getQuantity(Item item) {
        return items.getOrDefault(item, 0);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public Map<Item, Integer> getItems() {
        return items;
    }
}
