package IsaVII.skills;

public class Skill_Melee extends Skills_Base {
    
    private final int damage;
    private final int hitCount; // Default hit count for melee attacks
    
    
    public Skill_Melee(int id, String name, String description, int damage, int cooldown, int hitCount) {
        super(id, SkillType.MELEE_ATTACK, name, description, cooldown);
        this.damage = damage;
        this.hitCount = hitCount;
    }
    
    @Override
    public void printSkill(boolean fightMode, int nr) {
        if (fightMode) {
            IO.println(String.format("\t"+nr+".\t%-20s %-15d %-15d", name, damage, cooldown));
        }
    }
    
    public int getDamage() {
        return damage;
    }
    
    public int getHitCount() {
        return hitCount;
    }
}
