package IsaVII.enemies;

import IsaVII.skills.Skill_Melee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EnemyTest {

    @Test
    @DisplayName("New enemy starts at full health")
    void newEnemyStartsAtFullHealth() {
        Enemy enemy = new Enemy("Wolf", 10);
        assertEquals(10, enemy.getHealth());
    }

    @Test
    @DisplayName("Receiving damage reduces enemy health")
    void receiveDamageReducesHealth() {
        Enemy enemy = new Enemy("Wolf", 10);
        enemy.receiveDamage(4);
        assertEquals(6, enemy.getHealth());
    }

    @Test
    @DisplayName("Enemy is dead when health reaches zero")
    void isDeadWhenHealthReachesZero() {
        Enemy enemy = new Enemy("Wolf", 10);
        enemy.receiveDamage(10);
        assertTrue(enemy.isDead());
    }

    @Test
    @DisplayName("Enemy is not dead while health remains")
    void isNotDeadWhileHealthRemains() {
        Enemy enemy = new Enemy("Wolf", 10);
        enemy.receiveDamage(9);
        assertFalse(enemy.isDead());
    }

    @Test
    @DisplayName("Copy constructor duplicates name and health")
    void copyConstructorDuplicatesNameAndHealth() {
        Enemy original = new Enemy("Wolf", 10);
        Enemy copy = new Enemy(original);

        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getHealth(), copy.getHealth());
    }

    @Test
    @DisplayName("Copy constructor with multiplier scales health")
    void copyConstructorWithMultiplierScalesHealth() {
        Enemy original = new Enemy("Boar", 30);
        Enemy copy = new Enemy(original, 2.0);

        assertEquals(60, copy.getHealth());
    }

    @Test
    @DisplayName("Copy constructor with multiplier rounds to the nearest whole health value")
    void copyConstructorWithMultiplierRoundsToNearestInt() {
        Enemy original = new Enemy("Boar", 10);
        Enemy copy = new Enemy(original, 1.5);

        assertEquals(15, copy.getHealth());
    }

    @Test
    @DisplayName("Copy is independent from the original's health")
    void copyIsIndependentFromOriginalHealth() {
        Enemy original = new Enemy("Wolf", 10);
        Enemy copy = new Enemy(original);

        copy.receiveDamage(5);

        assertEquals(10, original.getHealth());
        assertEquals(5, copy.getHealth());
    }

    @Test
    @DisplayName("Copy is independent from the original's skill cooldowns")
    void copyIsIndependentFromOriginalSkills() {
        Enemy original = new Enemy("Wolf", 10);
        original.getSkills().addSkill(new Skill_Melee(1, "Bite", "desc", 3, 2, 1));
        Enemy copy = new Enemy(original);

        copy.getSkills().getSkillByIndex(0).startCooldown();

        assertFalse(original.getSkills().getSkillByIndex(0).isOnCooldown());
        assertTrue(copy.getSkills().getSkillByIndex(0).isOnCooldown());
    }
}
