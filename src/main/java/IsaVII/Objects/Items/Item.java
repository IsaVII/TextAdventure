package IsaVII.Objects.Items;

public class Item {

    private final ItemType itemType;
    private final String name;
    private final String description;

    public Item(ItemType itemType, String name, String description) {
        this.itemType = itemType;
        this.name = name;
        this.description = description;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
