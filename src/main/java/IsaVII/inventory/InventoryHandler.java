package IsaVII.inventory;

import IsaVII.Objects.Inventory;
import IsaVII.Objects.Items.Item;
import IsaVII.character.MainCharacter;
import IsaVII.ui.UiHelpers;
import IsaVII.ui.UserInputHelpers;

import java.util.ArrayList;
import java.util.List;

// Entered from exploration via the always-available "inventory" action. Never triggers a fight.
public class InventoryHandler {

    private static final int HEALTH_POTION_HEAL_AMOUNT = 20;

    private static final String BACKPACK_ART = """
                ______________
               /             /|
              /_____________/ |
             |  __________  | |
             | |          | | |
             | |  POCKETS | | |
             | |__________| | |
             |_______________|/
            """;

    private final MainCharacter mainCharacter;

    public InventoryHandler(MainCharacter mainCharacter) {
        this.mainCharacter = mainCharacter;
    }

    public void open() {
        boolean browsing = true;

        while (browsing) {
            Inventory inventory = mainCharacter.getInventory();
            List<Item> items = new ArrayList<>(inventory.getItems().keySet());

            showInventory(inventory, items);

            int choice = UserInputHelpers.getMenuNumberFromUserInRange("What do you want to do?", 1, 3);

            switch (choice) {
                case 1 -> useItem(inventory, items);
                case 2 -> discardItem(inventory, items);
                case 3 -> browsing = false;
            }
        }
    }

    private void showInventory(Inventory inventory, List<Item> items) {
        UiHelpers.clearConsole();
        IO.println(BACKPACK_ART);
        IO.println();
        UiHelpers.printLine(true);
        IO.println("Inventory:");

        if (items.isEmpty()) {
            IO.println("\t(empty)");
        } else {
            for (int i = 0; i < items.size(); i++) {
                Item item = items.get(i);
                IO.println(String.format("\t%d. %-20s x%-3d %s", i + 1, item.getName(), inventory.getQuantity(item), item.getDescription()));
            }
        }

        UiHelpers.printLine(true);
        IO.println("1. Use an item");
        IO.println("2. Remove an item");
        IO.println("3. Back");
    }

    private void useItem(Inventory inventory, List<Item> items) {
        if (items.isEmpty()) {
            IO.println(UiHelpers.color("You have no items to use.", UiHelpers.YELLOW));
            return;
        }

        Item item = chooseItem("Which item do you want to use?", items);

        applyItemEffect(item);
        inventory.removeItem(item, 1);
        IO.println(UiHelpers.color("You used " + item.getName() + ".", UiHelpers.GREEN));
    }

    private void discardItem(Inventory inventory, List<Item> items) {
        if (items.isEmpty()) {
            IO.println(UiHelpers.color("You have no items to remove.", UiHelpers.YELLOW));
            return;
        }

        Item item = chooseItem("Which item do you want to remove?", items);

        inventory.removeItem(item, 1);
        IO.println(UiHelpers.color("You discarded " + item.getName() + ".", UiHelpers.YELLOW));
    }

    private Item chooseItem(String prompt, List<Item> items) {
        int choice = UserInputHelpers.getMenuNumberFromUserInRange(prompt, 1, items.size());
        return items.get(choice - 1);
    }

    private void applyItemEffect(Item item) {
        switch (item.getItemType()) {
            case HEALTH_POTION -> mainCharacter.addHealth(HEALTH_POTION_HEAL_AMOUNT);
        }
    }
}
