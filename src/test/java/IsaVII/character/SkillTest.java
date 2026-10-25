package IsaVII.character;

import IsaVII.skills.Skill_Melee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SkillTest {

    private Skill_Melee newBase(int cooldown) {
        return new Skill_Melee(1, "Test Skill", "desc", 5, cooldown, 1);
    }

    @Test
    @DisplayName("New skill is not on cooldown")
    void newSkillIsNotOnCooldown() {
        Skill skill = new Skill(newBase(3));
        assertFalse(skill.isOnCooldown());
        assertEquals(0, skill.getCurrentCooldown());
    }

    @Test
    @DisplayName("Starting cooldown sets current cooldown to the skill's cooldown value")
    void startCooldownSetsCurrentCooldownToSkillCooldown() {
        Skill skill = new Skill(newBase(3));
        skill.startCooldown();
        assertEquals(3, skill.getCurrentCooldown());
        assertTrue(skill.isOnCooldown());
    }

    @Test
    @DisplayName("Starting cooldown on a zero-cooldown skill leaves it usable")
    void startCooldownWithZeroCooldownLeavesSkillUsable() {
        Skill skill = new Skill(newBase(0));
        skill.startCooldown();
        assertFalse(skill.isOnCooldown());
    }

    @Test
    @DisplayName("Ending a round decrements cooldown by one")
    void roundEndedDecrementsCooldownByOne() {
        Skill skill = new Skill(newBase(3));
        skill.startCooldown();
        skill.roundEnded();
        assertEquals(2, skill.getCurrentCooldown());
        assertTrue(skill.isOnCooldown());
    }

    @Test
    @DisplayName("Ending a round never decrements cooldown below zero")
    void roundEndedNeverGoesBelowZero() {
        Skill skill = new Skill(newBase(1));
        skill.startCooldown();
        skill.roundEnded();
        skill.roundEnded();
        skill.roundEnded();
        assertEquals(0, skill.getCurrentCooldown());
        assertFalse(skill.isOnCooldown());
    }

    @Test
    @DisplayName("Setting current cooldown directly overrides its value")
    void setCurrentCooldownOverridesValueDirectly() {
        Skill skill = new Skill(newBase(3));
        skill.setCurrentCooldown(5);
        assertEquals(5, skill.getCurrentCooldown());
        assertTrue(skill.isOnCooldown());
    }
}
