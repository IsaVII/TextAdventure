package IsaVII.Objects;

import IsaVII.Objects.Items.Item;
import IsaVII.Objects.Items.ItemType;

import java.util.HashMap;
import java.util.Map;

public class ItemManager {

    private static Map<ItemType, Item> itemMap;

    public ItemManager() {
        itemMap = new HashMap<>();
        itemMap.put(ItemType.HEALTH_POTION, new Item(ItemType.HEALTH_POTION, "Health Potion", "Restores a small amount of health."));
    }

    public static Item getItem(ItemType itemType) {
        return itemMap.get(itemType);
    }
}
