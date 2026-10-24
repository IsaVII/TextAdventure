package IsaVII.enemies;

import IsaVII.character.Skills;
import IsaVII.fight.Health;
import IsaVII.fight.Health_Interface;

public class Enemy implements Health_Interface {
    private Health health;
    
    private String name;
    private Skills skills;
    
    public Enemy(String name, int health) {
        this.name = name;
        this.health = new Health(health);
        skills = new Skills();
    }

    // **** Health_Interface methods **** 
    @Override
    public void addHealth(int amount) {
        health.addHealth(amount);
    }

    @Override
    public void receiveDamage(int damage) {
        health.receiveDamage(damage);
    }

    @Override
    public boolean isDead() {
        return health.isDead();
    }

    @Override
    public int getHealth() {
        return health.getHealth();
    }
    
    public String getName() {
        return name;
    }
    
    public Skills getSkills() {
        return skills;
    }
}
