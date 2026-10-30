package IsaVII.Objects;

import IsaVII.Objects.Items.Item;
import IsaVII.Objects.Items.ItemType;
import IsaVII.localization.LocalizationManager;

import java.util.HashMap;
import java.util.Map;

public class ItemManager {

    private static Map<ItemType, Item> itemMap;

    public ItemManager() {
        itemMap = new HashMap<>();
        itemMap.put(ItemType.HEALTH_POTION, new Item(ItemType.HEALTH_POTION,
                LocalizationManager.getText("item.health_potion.name"),
                LocalizationManager.getText("item.health_potion.description")));
    }

    public static Item getItem(ItemType itemType) {
        return itemMap.get(itemType);
    }
}
