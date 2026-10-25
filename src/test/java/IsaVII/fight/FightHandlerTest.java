package IsaVII.fight;

import IsaVII.character.Skill;
import IsaVII.enemies.Enemy;
import IsaVII.skills.Skill_Melee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FightHandlerTest {

    private Skill_Melee singleTargetSkill(int damage, int cooldown) {
        return new Skill_Melee(1, "Slash", "desc", damage, cooldown, 1);
    }

    private Skill_Melee multiTargetSkill(int damage, int cooldown, int targetCount) {
        return new Skill_Melee(2, "Cleave", "desc", damage, cooldown, targetCount);
    }

    @Test
    @DisplayName("Using a single-target skill damages the only enemy present")
    void handleSkillUsageDamagesSoleEnemy() {
        Enemy enemy = new Enemy("Wolf", 10);
        ArrayList<Enemy> enemies = new ArrayList<>(java.util.List.of(enemy));
        FightHandler fightHandler = new FightHandler(enemies);

        fightHandler.handleSkillUsage(new Skill(singleTargetSkill(4, 0)));

        assertEquals(6, enemy.getHealth());
    }

    @Test
    @DisplayName("An enemy killed by the attack is removed from the fight")
    void handleSkillUsageRemovesEnemyKilledByDamage() {
        Enemy enemy = new Enemy("Wolf", 10);
        ArrayList<Enemy> enemies = new ArrayList<>(java.util.List.of(enemy));
        FightHandler fightHandler = new FightHandler(enemies);

        fightHandler.handleSkillUsage(new Skill(singleTargetSkill(10, 0)));

        assertTrue(enemies.isEmpty());
    }

    @Test
    @DisplayName("A surviving enemy remains in the fight's enemy list")
    void handleSkillUsageKeepsSurvivingEnemyInList() {
        Enemy enemy = new Enemy("Wolf", 10);
        ArrayList<Enemy> enemies = new ArrayList<>(java.util.List.of(enemy));
        FightHandler fightHandler = new FightHandler(enemies);

        fightHandler.handleSkillUsage(new Skill(singleTargetSkill(4, 0)));

        assertEquals(1, enemies.size());
    }

    @Test
    @DisplayName("Using a skill starts its cooldown")
    void handleSkillUsageStartsCooldownOnSkill() {
        Enemy enemy = new Enemy("Wolf", 10);
        ArrayList<Enemy> enemies = new ArrayList<>(java.util.List.of(enemy));
        FightHandler fightHandler = new FightHandler(enemies);
        Skill skill = new Skill(singleTargetSkill(4, 3));

        fightHandler.handleSkillUsage(skill);

        assertTrue(skill.isOnCooldown());
        assertEquals(3, skill.getCurrentCooldown());
    }

    @Test
    @DisplayName("An AOE skill damages all enemies when the target count covers them")
    void handleSkillUsageWithAoeSkillDamagesAllEnemiesUpToTargetCount() {
        Enemy first = new Enemy("Wolf", 10);
        Enemy second = new Enemy("Wolf", 10);
        Enemy third = new Enemy("Wolf", 10);
        ArrayList<Enemy> enemies = new ArrayList<>(java.util.List.of(first, second, third));
        FightHandler fightHandler = new FightHandler(enemies);

        fightHandler.handleSkillUsage(new Skill(multiTargetSkill(4, 0, 3)));

        assertEquals(6, first.getHealth());
        assertEquals(6, second.getHealth());
        assertEquals(6, third.getHealth());
    }

    @Test
    @DisplayName("An AOE skill leaves excess enemies beyond its target count untouched")
    void handleSkillUsageWithAoeSkillLeavesExcessEnemiesUntouched() {
        Enemy first = new Enemy("Wolf", 10);
        Enemy second = new Enemy("Wolf", 10);
        Enemy third = new Enemy("Wolf", 10);
        ArrayList<Enemy> enemies = new ArrayList<>(java.util.List.of(first, second, third));
        FightHandler fightHandler = new FightHandler(enemies);

        fightHandler.handleSkillUsage(new Skill(multiTargetSkill(4, 0, 2)));

        assertEquals(6, first.getHealth());
        assertEquals(6, second.getHealth());
        assertEquals(10, third.getHealth());
    }

    @Test
    @DisplayName("An AOE skill that kills every target removes them all from the fight")
    void handleSkillUsageWithAoeSkillRemovesAllKilledEnemies() {
        Enemy first = new Enemy("Wolf", 5);
        Enemy second = new Enemy("Wolf", 5);
        ArrayList<Enemy> enemies = new ArrayList<>(java.util.List.of(first, second));
        FightHandler fightHandler = new FightHandler(enemies);

        fightHandler.handleSkillUsage(new Skill(multiTargetSkill(10, 0, 2)));

        assertFalse(enemies.contains(first));
        assertFalse(enemies.contains(second));
        assertTrue(enemies.isEmpty());
    }
}
