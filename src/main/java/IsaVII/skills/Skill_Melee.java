package IsaVII.skills;

public class Skill_Melee extends Skills_Base {
    
    private final int damage;
    private final int targetCount; // Default hit count for melee attacks
    
    
    public Skill_Melee(int id, String name, String description, int damage, int cooldown, int targetCount) {
        super(id, SkillType.MELEE_ATTACK, name, description, cooldown);
        this.damage = damage;
        this.targetCount = targetCount;
    }
    
    @Override
    public void printSkill(boolean fightMode, int nr) {
        if (fightMode) {
            IO.println(String.format("\t"+nr+".\t%-20s %-15d %-15d", name, damage, cooldown));
        }
    }
    
    @Override
    public int getDamage() {
        return damage;
    }

    @Override
    public int getTargetCount() {
        return targetCount;
    }
}
