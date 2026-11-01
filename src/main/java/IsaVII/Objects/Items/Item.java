package IsaVII.Objects.Items;

public class Item {

    private final ItemType itemType;
    private final ItemTag itemTag;
    private final String name;
    private final String description;

    public Item(ItemType itemType, ItemTag itemTag, String name, String description) {
        this.itemType = itemType;
        this.itemTag = itemTag;
        this.name = name;
        this.description = description;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public ItemTag getItemTag() {
        return itemTag;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
