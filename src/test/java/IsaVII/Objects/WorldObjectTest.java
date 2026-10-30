package IsaVII.Objects;

import IsaVII.Objects.Items.Item;
import IsaVII.Objects.Items.ItemType;
import IsaVII.localization.LocalizationManager;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WorldObjectTest {

    private static final Gson GSON = new Gson();

    @BeforeAll
    static void initItemManager() {
        new LocalizationManager();
        new ItemManager();
    }

    private WorldObject chestWithPotions() {
        String json = """
                {
                  "objectType": "CHEST",
                  "contents": [
                    { "itemType": "HEALTH_POTION", "quantity": 2 }
                  ]
                }
                """;
        return GSON.fromJson(json, WorldObject.class);
    }

    private WorldObject emptyChest() {
        String json = """
                {
                  "objectType": "CHEST"
                }
                """;
        return GSON.fromJson(json, WorldObject.class);
    }

    @Test
    @DisplayName("A newly loaded world object is not opened")
    void newWorldObjectStartsUnopened() {
        WorldObject chest = chestWithPotions();

        assertFalse(chest.isOpened());
    }

    @Test
    @DisplayName("getCommand combines the action verb and the object name")
    void getCommandCombinesVerbAndName() {
        WorldObject chest = chestWithPotions();

        assertEquals("open chest", chest.getCommand());
    }

    @Test
    @DisplayName("Opening a chest grants its contents to the given inventory")
    void interactGrantsContentsToInventory() {
        WorldObject chest = chestWithPotions();
        Inventory inventory = new Inventory();

        chest.interact(inventory);

        Item healthPotion = ItemManager.getItem(ItemType.HEALTH_POTION);
        assertEquals(2, inventory.getQuantity(healthPotion));
    }

    @Test
    @DisplayName("Opening a chest marks it as opened and describes what was found")
    void interactMarksOpenedAndDescribesLoot() {
        WorldObject chest = chestWithPotions();
        Inventory inventory = new Inventory();

        String result = chest.interact(inventory);

        assertTrue(chest.isOpened());
        assertTrue(result.contains("2x Health Potion"));
    }

    @Test
    @DisplayName("Opening an already-opened chest grants no further items")
    void interactTwiceDoesNotGrantItemsAgain() {
        WorldObject chest = chestWithPotions();
        Inventory inventory = new Inventory();

        chest.interact(inventory);
        chest.interact(inventory);

        Item healthPotion = ItemManager.getItem(ItemType.HEALTH_POTION);
        assertEquals(2, inventory.getQuantity(healthPotion));
    }

    @Test
    @DisplayName("Opening an already-opened chest reports that it is empty")
    void interactTwiceReportsAlreadyEmpty() {
        WorldObject chest = chestWithPotions();
        Inventory inventory = new Inventory();

        chest.interact(inventory);
        String secondResult = chest.interact(inventory);

        assertTrue(secondResult.contains("already empty"));
    }

    @Test
    @DisplayName("Opening a chest with no contents grants nothing and reports it is empty")
    void interactWithNoContentsGrantsNothing() {
        WorldObject chest = emptyChest();
        Inventory inventory = new Inventory();

        String result = chest.interact(inventory);

        assertTrue(chest.isOpened());
        assertTrue(inventory.getItems().isEmpty());
        assertTrue(result.contains("empty"));
    }
}
