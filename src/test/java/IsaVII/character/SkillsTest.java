package IsaVII.character;

import IsaVII.skills.Skill_Melee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SkillsTest {

    private Skill_Melee newBase(int id, int cooldown) {
        return new Skill_Melee(id, "Skill " + id, "desc", 5, cooldown, 1);
    }

    @Test
    @DisplayName("New skills collection has no entries")
    void newSkillsHasNoEntries() {
        Skills skills = new Skills();
        assertEquals(0, skills.getSkillCount());
    }

    @Test
    @DisplayName("Adding a skill increases the skill count")
    void addSkillIncreasesCount() {
        Skills skills = new Skills();
        skills.addSkill(newBase(1, 0));
        assertEquals(1, skills.getSkillCount());
    }

    @Test
    @DisplayName("Getting a skill by index returns the added skill")
    void getSkillByIndexReturnsAddedSkill() {
        Skills skills = new Skills();
        Skill_Melee base = newBase(1, 0);
        skills.addSkill(base);
        assertEquals(base, skills.getSkillByIndex(0).getSkill());
    }

    @Test
    @DisplayName("Removing a skill by id removes only the matching skill")
    void removeSkillByIdRemovesMatchingSkill() {
        Skills skills = new Skills();
        skills.addSkill(newBase(1, 0));
        skills.addSkill(newBase(2, 0));

        skills.removeSkill(1);

        assertEquals(1, skills.getSkillCount());
        assertEquals(2, skills.getSkillByIndex(0).getSkill().getId());
    }

    @Test
    @DisplayName("Removing an unknown id leaves the collection unchanged")
    void removeSkillWithUnknownIdDoesNothing() {
        Skills skills = new Skills();
        skills.addSkill(newBase(1, 0));

        skills.removeSkill(999);

        assertEquals(1, skills.getSkillCount());
    }

    @Test
    @DisplayName("Ending a round decrements cooldown on every skill")
    void roundEndedDecrementsCooldownOnAllSkills() {
        Skills skills = new Skills();
        skills.addSkill(newBase(1, 3));
        skills.getSkillByIndex(0).startCooldown();

        skills.roundEnded();

        assertEquals(2, skills.getSkillByIndex(0).getCurrentCooldown());
    }

    @Test
    @DisplayName("Copy constructor creates independent cooldown state")
    void copyConstructorCreatesIndependentCooldownState() {
        Skills original = new Skills();
        original.addSkill(newBase(1, 3));

        Skills copy = new Skills(original);
        copy.getSkillByIndex(0).startCooldown();

        assertTrue(copy.getSkillByIndex(0).isOnCooldown());
        assertFalse(original.getSkillByIndex(0).isOnCooldown());
    }

    @Test
    @DisplayName("Copy constructor preserves the skill count")
    void copyConstructorPreservesSkillCount() {
        Skills original = new Skills();
        original.addSkill(newBase(1, 0));
        original.addSkill(newBase(2, 0));

        Skills copy = new Skills(original);

        assertEquals(original.getSkillCount(), copy.getSkillCount());
    }
}
