package IsaVII.Objects.Items;

import IsaVII.character.MainCharacter;

public class ItemEffects {

    private static final int HEALTH_POTION_HEAL_AMOUNT = 20;

    public static void apply(Item item, MainCharacter mainCharacter) {
        switch (item.getItemType()) {
            case HEALTH_POTION -> mainCharacter.addHealth(HEALTH_POTION_HEAL_AMOUNT);
        }
    }
}
