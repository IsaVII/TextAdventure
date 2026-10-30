package IsaVII.Objects;

import IsaVII.Objects.Items.Item;
import IsaVII.Objects.Items.ItemStack;
import IsaVII.localization.LocalizationManager;

import java.util.List;

public class WorldObject {

    private ObjectType objectType;
    private List<ItemStack> contents;

    private transient boolean opened = false;

    public ObjectType getObjectType() {
        return objectType;
    }

    public List<ItemStack> getContents() {
        return contents;
    }

    public boolean isOpened() {
        return opened;
    }

    public String getName() {
        return LocalizationManager.getText("object." + objectType.name().toLowerCase() + ".name");
    }

    public String getActionVerb() {
        return LocalizationManager.getText("object." + objectType.name().toLowerCase() + ".action");
    }

    public String getCommand() {
        return getActionVerb() + " " + getName();
    }

    public String interact(Inventory inventory) {
        if (opened) {
            return LocalizationManager.getText("worldobject.already_empty", getName());
        }

        opened = true;

        if (contents == null || contents.isEmpty()) {
            return LocalizationManager.getText("worldobject.opened_empty", getName());
        }

        StringBuilder loot = new StringBuilder();
        for (int i = 0; i < contents.size(); i++) {
            ItemStack stack = contents.get(i);
            Item item = ItemManager.getItem(stack.getItemType());
            inventory.addItem(item, stack.getQuantity());
            if (i > 0) {
                loot.append(", ");
            }
            loot.append(stack.getQuantity()).append("x ").append(item.getName());
        }
        return LocalizationManager.getText("worldobject.opened_found", getName(), loot);
    }
}
