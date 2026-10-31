package IsaVII.Objects.Items;

public class ItemStack {

    private ItemType itemType;
    private int quantity;

    public ItemStack(ItemType itemType, int quantity) {
        this.itemType = itemType;
        this.quantity = quantity;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public int getQuantity() {
        return quantity;
    }
}
