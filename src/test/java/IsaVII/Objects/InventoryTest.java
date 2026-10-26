package IsaVII.Objects;

import IsaVII.Objects.Items.Item;
import IsaVII.Objects.Items.ItemType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InventoryTest {

    private final Item healthPotion = new Item(ItemType.HEALTH_POTION, "Health Potion", "Restores health.");

    @Test
    @DisplayName("A new inventory has no quantity for an item it has never seen")
    void newInventoryHasZeroQuantityForUnknownItem() {
        Inventory inventory = new Inventory();

        assertEquals(0, inventory.getQuantity(healthPotion));
    }

    @Test
    @DisplayName("Adding an item sets its quantity")
    void addItemSetsQuantity() {
        Inventory inventory = new Inventory();

        inventory.addItem(healthPotion, 2);

        assertEquals(2, inventory.getQuantity(healthPotion));
    }

    @Test
    @DisplayName("Adding the same item twice accumulates the quantity")
    void addItemAccumulatesQuantity() {
        Inventory inventory = new Inventory();

        inventory.addItem(healthPotion, 2);
        inventory.addItem(healthPotion, 3);

        assertEquals(5, inventory.getQuantity(healthPotion));
    }

    @Test
    @DisplayName("Two distinct Item instances are tracked as separate inventory entries, even with the same ItemType")
    void distinctItemInstancesAreTrackedSeparately() {
        // Item has no overridden equals/hashCode, so inventory slots are keyed by instance identity.
        // This is why ItemManager must hand out a single shared instance per ItemType.
        Inventory inventory = new Inventory();
        Item anotherHealthPotionInstance = new Item(ItemType.HEALTH_POTION, "Health Potion", "Restores health.");

        inventory.addItem(healthPotion, 1);
        inventory.addItem(anotherHealthPotionInstance, 4);

        assertEquals(1, inventory.getQuantity(healthPotion));
        assertEquals(4, inventory.getQuantity(anotherHealthPotionInstance));
        assertEquals(2, inventory.getItems().size());
    }

    @Test
    @DisplayName("getItems exposes all stored items with their accumulated quantities")
    void getItemsExposesStoredItemsWithQuantities() {
        Inventory inventory = new Inventory();

        inventory.addItem(healthPotion, 2);

        assertEquals(1, inventory.getItems().size());
        assertEquals(2, inventory.getItems().get(healthPotion));
    }
}
