package IsaVII.fight;

import IsaVII.character.Skill;
import IsaVII.enemies.Element;
import IsaVII.enemies.Enemy;
import IsaVII.skills.Skill_Melee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FightHandlerTest {

    private Enemy enemy(String name, int health) {
        return new Enemy(name, health, Element.NONE, List.of());
    }

    private Skill_Melee singleTargetSkill(int damage, int cooldown) {
        return new Skill_Melee(1, "Slash", "desc", damage, cooldown, 1);
    }

    private Skill_Melee multiTargetSkill(int damage, int cooldown, int targetCount) {
        return new Skill_Melee(2, "Cleave", "desc", damage, cooldown, targetCount);
    }

    @Test
    @DisplayName("Using a single-target skill damages the only enemy present")
    void handleSkillUsageDamagesSoleEnemy() {
        Enemy enemy = enemy("Wolf", 10);
        ArrayList<Enemy> enemies = new ArrayList<>(List.of(enemy));
        FightHandler fightHandler = new FightHandler(enemies);

        fightHandler.handleSkillUsage(new Skill(singleTargetSkill(4, 0)));

        assertEquals(6, enemy.getHealth());
    }

    @Test
    @DisplayName("An enemy killed by the attack is removed from the fight")
    void handleSkillUsageRemovesEnemyKilledByDamage() {
        Enemy enemy = enemy("Wolf", 10);
        ArrayList<Enemy> enemies = new ArrayList<>(List.of(enemy));
        FightHandler fightHandler = new FightHandler(enemies);

        fightHandler.handleSkillUsage(new Skill(singleTargetSkill(10, 0)));

        assertTrue(enemies.isEmpty());
    }

    @Test
    @DisplayName("A surviving enemy remains in the fight's enemy list")
    void handleSkillUsageKeepsSurvivingEnemyInList() {
        Enemy enemy = enemy("Wolf", 10);
        ArrayList<Enemy> enemies = new ArrayList<>(List.of(enemy));
        FightHandler fightHandler = new FightHandler(enemies);

        fightHandler.handleSkillUsage(new Skill(singleTargetSkill(4, 0)));

        assertEquals(1, enemies.size());
    }

    @Test
    @DisplayName("Using a skill starts its cooldown")
    void handleSkillUsageStartsCooldownOnSkill() {
        Enemy enemy = enemy("Wolf", 10);
        ArrayList<Enemy> enemies = new ArrayList<>(List.of(enemy));
        FightHandler fightHandler = new FightHandler(enemies);
        Skill skill = new Skill(singleTargetSkill(4, 3));

        fightHandler.handleSkillUsage(skill);

        assertTrue(skill.isOnCooldown());
        assertEquals(3, skill.getCurrentCooldown());
    }

    @Test
    @DisplayName("An AOE skill damages all enemies when the target count covers them")
    void handleSkillUsageWithAoeSkillDamagesAllEnemiesUpToTargetCount() {
        Enemy first = enemy("Wolf", 10);
        Enemy second = enemy("Wolf", 10);
        Enemy third = enemy("Wolf", 10);
        ArrayList<Enemy> enemies = new ArrayList<>(List.of(first, second, third));
        FightHandler fightHandler = new FightHandler(enemies);

        fightHandler.handleSkillUsage(new Skill(multiTargetSkill(4, 0, 3)));

        assertEquals(6, first.getHealth());
        assertEquals(6, second.getHealth());
        assertEquals(6, third.getHealth());
    }

    @Test
    @DisplayName("An AOE skill leaves excess enemies beyond its target count untouched")
    void handleSkillUsageWithAoeSkillLeavesExcessEnemiesUntouched() {
        Enemy first = enemy("Wolf", 10);
        Enemy second = enemy("Wolf", 10);
        Enemy third = enemy("Wolf", 10);
        ArrayList<Enemy> enemies = new ArrayList<>(List.of(first, second, third));
        FightHandler fightHandler = new FightHandler(enemies);

        fightHandler.handleSkillUsage(new Skill(multiTargetSkill(4, 0, 2)));

        assertEquals(6, first.getHealth());
        assertEquals(6, second.getHealth());
        assertEquals(10, third.getHealth());
    }

    @Test
    @DisplayName("An AOE skill that kills every target removes them all from the fight")
    void handleSkillUsageWithAoeSkillRemovesAllKilledEnemies() {
        Enemy first = enemy("Wolf", 5);
        Enemy second = enemy("Wolf", 5);
        ArrayList<Enemy> enemies = new ArrayList<>(List.of(first, second));
        FightHandler fightHandler = new FightHandler(enemies);

        fightHandler.handleSkillUsage(new Skill(multiTargetSkill(10, 0, 2)));

        assertFalse(enemies.contains(first));
        assertFalse(enemies.contains(second));
        assertTrue(enemies.isEmpty());
    }
}
