package IsaVII.Objects;

import IsaVII.Objects.Items.Item;
import IsaVII.Objects.Items.ItemType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class ItemManagerTest {

    @BeforeAll
    static void initItemManager() {
        new ItemManager();
    }

    @Test
    @DisplayName("Getting HEALTH_POTION returns an item with the expected type, name and description")
    void getItemReturnsHealthPotionWithExpectedDetails() {
        Item item = ItemManager.getItem(ItemType.HEALTH_POTION);

        assertNotNull(item);
        assertEquals(ItemType.HEALTH_POTION, item.getItemType());
        assertEquals("Health Potion", item.getName());
        assertFalse(item.getDescription().isEmpty());
    }

    @Test
    @DisplayName("Getting the same item type repeatedly returns the same instance")
    void getItemIsConsistentAcrossCalls() {
        Item first = ItemManager.getItem(ItemType.HEALTH_POTION);
        Item second = ItemManager.getItem(ItemType.HEALTH_POTION);

        assertSame(first, second);
    }
}
