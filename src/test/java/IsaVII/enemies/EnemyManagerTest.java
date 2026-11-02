package IsaVII.enemies;

import IsaVII.localization.LocalizationManager;
import IsaVII.skills.SkillManager;
import IsaVII.world.AreaType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EnemyManagerTest {

    @BeforeAll
    static void initEnemyManager() {
        new LocalizationManager();
        new SkillManager();
        new EnemyManager();
    }

    @Test
    @DisplayName("Getting a Wolf copy returns expected name, health and skill count")
    void getEnemyCopyReturnsWolfWithExpectedStats() {
        Enemy wolf = EnemyManager.getEnemyCopy(EnemyType.WOLF);

        assertNotNull(wolf);
        assertEquals("Wolf", wolf.getName());
        assertEquals(10, wolf.getHealth());
        assertEquals(1, wolf.getSkills().getSkillCount());
    }

    @Test
    @DisplayName("Getting a Boar copy returns expected name, health and skill count")
    void getEnemyCopyReturnsBoarWithExpectedStats() {
        Enemy boar = EnemyManager.getEnemyCopy(EnemyType.BOAR);

        assertNotNull(boar);
        assertEquals("Boar", boar.getName());
        assertEquals(30, boar.getHealth());
        assertEquals(2, boar.getSkills().getSkillCount());
    }

    @Test
    @DisplayName("Getting enemy copies returns independent instances")
    void getEnemyCopyReturnsIndependentInstances() {
        Enemy first = EnemyManager.getEnemyCopy(EnemyType.WOLF);
        Enemy second = EnemyManager.getEnemyCopy(EnemyType.WOLF);

        assertNotSame(first, second);

        first.receiveDamage(5);

        assertEquals(5, first.getHealth());
        assertEquals(10, second.getHealth());
    }

    @Test
    @DisplayName("Spawning enemies for a known area returns enemies with positive health")
    void spawnEnemiesForKnownAreaReturnsEnemiesWithPositiveHealth() {
        EnemyManager enemyManager = new EnemyManager();

        for (int i = 0; i < 20; i++) {
            var enemies = enemyManager.spawnEnemies(AreaType.FOREST);
            for (Enemy enemy : enemies) {
                assertTrue(enemy.getHealth() > 0);
                assertNotNull(enemy.getName());
            }
        }
    }
}
