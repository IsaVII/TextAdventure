package IsaVII.fight;

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
    
}
