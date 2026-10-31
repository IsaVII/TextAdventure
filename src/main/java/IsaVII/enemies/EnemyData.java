package IsaVII.enemies;

import IsaVII.enemies.loot.LootEntry;
import IsaVII.skills.SkillName;

import java.util.List;

public class EnemyData {
    private int health;
    private Element weakness;
    private List<SkillName> skills;
    private List<LootEntry> drops;

    public int getHealth() {
        return health;
    }

    public Element getWeakness() {
        return weakness;
    }

    public List<SkillName> getSkills() {
        return skills;
    }

    public List<LootEntry> getDrops() {
        return drops;
    }
}
