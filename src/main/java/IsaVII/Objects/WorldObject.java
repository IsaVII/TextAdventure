package IsaVII.Objects;

import IsaVII.Objects.Items.Item;
import IsaVII.Objects.Items.ItemStack;

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
        return objectType.name().toLowerCase();
    }

    public String getActionVerb() {
        return switch (objectType) {
            case CHEST -> "open";
        };
    }

    public String getCommand() {
        return getActionVerb() + " " + getName();
    }

    public String interact(Inventory inventory) {
        if (opened) {
            return "The " + getName() + " is already empty.";
        }

        opened = true;

        if (contents == null || contents.isEmpty()) {
            return "You " + getActionVerb() + " the " + getName() + ", but it's empty.";
        }

        StringBuilder sb = new StringBuilder("You " + getActionVerb() + " the " + getName() + " and find: ");
        for (int i = 0; i < contents.size(); i++) {
            ItemStack stack = contents.get(i);
            Item item = ItemManager.getItem(stack.getItemType());
            inventory.addItem(item, stack.getQuantity());
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(stack.getQuantity()).append("x ").append(item.getName());
        }
        return sb.toString();
    }
}
