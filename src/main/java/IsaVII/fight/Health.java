package IsaVII.fight;

import IsaVII.ui.UiHelpers;
import IsaVII.ui.UserInputHelpers;

public class Health {

    private int maxHealth = 50;
    private int health = 50;
    
    public Health(int maxHealth) {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }
    
    public void addHealth(int amount) {
        health += amount;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }
    
    public void receiveDamage(int damage) {
        health -= damage;
        if (health < 0) {
            health = 0;
        }
    }
    
    public boolean isDead() {
        return health <= 0;
    }
    
    public int getHealth() {
        return health;
    }

    public String printHealth() {
        String color = health > maxHealth * 0.5 ? UiHelpers.GREEN : (health > maxHealth * 0.2 ? UiHelpers.YELLOW : UiHelpers.RED);
        return UiHelpers.color(String.format("%d/%d", health, maxHealth), color);
    }
}
