package IsaVII.enemies;

import IsaVII.Objects.Items.ItemStack;
import IsaVII.character.Skill;
import IsaVII.character.Skills;
import IsaVII.enemies.loot.LootEntry;
import IsaVII.enemies.loot.LootResult;
import IsaVII.fight.Health;
import IsaVII.fight.Health_Interface;

import java.util.List;

public class Enemy implements Health_Interface {
    private Health health;

    private String name;
    private Skills skills;
    private Element weakness;
    private List<LootEntry> drops;
    private double difficulty;

    public Enemy(String name, int health, Element weakness, List<LootEntry> drops) {
        this.name = name;
        this.health = new Health(health);
        this.skills = new Skills();
        this.weakness = weakness;
        this.drops = drops;
        this.difficulty = 1.0;
    }

    public Enemy(Enemy enemy) {
        this(enemy, 1.0);
    }

    public Enemy(Enemy enemy, double difficultyMultiplier) {
        this.name = enemy.name;
        this.health = new Health((int) Math.round(enemy.health.getHealth() * difficultyMultiplier));
        this.skills = new Skills(enemy.skills);
        this.weakness = enemy.weakness;
        this.drops = enemy.drops;
        this.difficulty = difficultyMultiplier;
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

    @Override
    public String printHealth() {
        return health.printHealth();
    }

    public String getName() {
        return name;
    }

    public Skills getSkills() {
        return skills;
    }

    public Element getWeakness() {
        return weakness;
    }

    public Skill getRandomAttackSkill() {
        return skills.getRandomSkill();
    }

    public LootResult rollLoot() {
        LootResult result = new LootResult();
        if (drops == null) {
            return result;
        }

        for (LootEntry entry : drops) {
            if (!entry.appliesAtDifficulty(difficulty)) {
                continue;
            }

            double chance = entry.getMinChance() + Math.random() * (entry.getMaxChance() - entry.getMinChance());
            if (Math.random() > chance) {
                continue;
            }

            int count = entry.getMinCount() + (int) (Math.random() * (entry.getMaxCount() - entry.getMinCount() + 1));
            if (entry.isMoney()) {
                result.addCoins(count);
            } else {
                result.addItem(new ItemStack(entry.getItem(), count));
            }
        }

        return result;
    }
}
