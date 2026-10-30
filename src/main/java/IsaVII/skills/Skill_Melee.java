package IsaVII.skills;

import IsaVII.ui.IO;
import IsaVII.ui.UiHelpers;
import IsaVII.ui.UserInputHelpers;

public class Skill_Melee extends Skills_Base {
    
    private final int damage;
    private final int targetCount; // Default hit count for melee attacks
    
    
    public Skill_Melee(int id, String name, String description, int damage, int cooldown, int targetCount) {
        super(id, SkillType.MELEE_ATTACK, name, description, cooldown);
        this.damage = damage;
        this.targetCount = targetCount;
    }
    
    @Override
    public void printSkill(boolean fightMode, int currentCooldown, int nr)  {
        if (fightMode) {
            //if cooldown is not 0 -> italic font 
            IO.println(String.format( currentCooldown > 0 ? UiHelpers.color(UiHelpers.italic("\t\t%-20s %-15d %-15d"), UiHelpers.GREY): "\t"+nr+".\t%-20s %-15d %-15d", name, damage, currentCooldown));
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
