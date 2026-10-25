package IsaVII.fight;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HealthTest {

    @Test
    @DisplayName("New health starts at max value")
    void startsAtMaxHealth() {
        Health health = new Health(50);
        assertEquals(50, health.getHealth());
    }

    @Test
    @DisplayName("Receiving damage reduces current health")
    void receiveDamageReducesHealth() {
        Health health = new Health(50);
        health.receiveDamage(20);
        assertEquals(30, health.getHealth());
    }

    @Test
    @DisplayName("Receiving damage clamps health at zero")
    void receiveDamageClampsAtZero() {
        Health health = new Health(10);
        health.receiveDamage(1000);
        assertEquals(0, health.getHealth());
    }

    @Test
    @DisplayName("Health at zero is considered dead")
    void isDeadWhenHealthReachesZero() {
        Health health = new Health(10);
        health.receiveDamage(10);
        assertTrue(health.isDead());
    }

    @Test
    @DisplayName("Health above zero is not considered dead")
    void isNotDeadWhileHealthRemains() {
        Health health = new Health(10);
        health.receiveDamage(9);
        assertFalse(health.isDead());
    }

    @Test
    @DisplayName("Adding health increases current health")
    void addHealthIncreasesHealth() {
        Health health = new Health(50);
        health.receiveDamage(30);
        health.addHealth(10);
        assertEquals(30, health.getHealth());
    }

    @Test
    @DisplayName("Adding health clamps at max health")
    void addHealthClampsAtMaxHealth() {
        Health health = new Health(50);
        health.receiveDamage(10);
        health.addHealth(1000);
        assertEquals(50, health.getHealth());
    }
}
