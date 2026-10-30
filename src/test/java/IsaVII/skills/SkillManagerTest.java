package IsaVII.skills;

import IsaVII.localization.LocalizationManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class SkillManagerTest {

    @BeforeAll
    static void initSkillManager() {
        new LocalizationManager();
        new SkillManager();
    }

    @Test
    @DisplayName("Getting SLASH returns expected damage, cooldown and target count")
    void getSkillReturnsSlashWithExpectedStats() {
        Skills_Base slash = SkillManager.getSkill(SkillName.SLASH);

        assertNotNull(slash);
        assertEquals("Slash", slash.getName());
        assertEquals(2, slash.getDamage());
        assertEquals(0, slash.getCooldown());
        assertEquals(1, slash.getTargetCount());
    }

    @Test
    @DisplayName("Getting HEAVY_STRIKE returns expected high damage and cooldown")
    void getSkillReturnsHeavyStrikeWithExpectedStats() {
        Skills_Base heavyStrike = SkillManager.getSkill(SkillName.HEAVY_STRIKE);

        assertNotNull(heavyStrike);
        assertEquals("Heavy Strike", heavyStrike.getName());
        assertEquals(10, heavyStrike.getDamage());
        assertEquals(2, heavyStrike.getCooldown());
    }

    @Test
    @DisplayName("Getting CLEAVE returns a skill with multiple targets")
    void getSkillReturnsCleaveWithMultipleTargets() {
        Skills_Base cleave = SkillManager.getSkill(SkillName.CLEAVE);

        assertNotNull(cleave);
        assertEquals(3, cleave.getTargetCount());
        assertEquals(3, cleave.getCooldown());
    }

    @Test
    @DisplayName("Getting the same skill name repeatedly returns the same instance")
    void getSkillIsConsistentAcrossCalls() {
        Skills_Base first = SkillManager.getSkill(SkillName.BITE);
        Skills_Base second = SkillManager.getSkill(SkillName.BITE);

        assertSame(first, second);
    }
}
