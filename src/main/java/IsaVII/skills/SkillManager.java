package IsaVII.skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SkillManager {
    
    private static HashMap<SkillType, ArrayList<Skills_Base>> skillMap;
    
    public SkillManager() {
        skillMap = new HashMap<>();
        for (SkillType type : SkillType.values()) {
            skillMap.put(type, new ArrayList<>());
        }
        
        fillMapWithSkills();
    }

    private void fillMapWithSkills() {
         
        ArrayList<Skills_Base> meleeSkills = new ArrayList<>(List.of(
            new Skill_Melee(1, "Slash", "A quick melee attack.", 2, 0,1),
            new Skill_Melee(2, "Stab", "A precise melee attack.", 5, 1,1),
            new Skill_Melee(3, "Heavy Strike", "A powerful melee attack.", 10, 2,1),
            new Skill_Melee(4, "Cleave", "A wide melee attack hitting 3 enemies.", 4, 3,3),
            new Skill_Melee(5, "Bite", "A vicious melee attack.", 3, 0,1)
        ));
        
        skillMap.put(SkillType.MELEE_ATTACK, meleeSkills);
    }
    
    
    public static Skills_Base getSkillById(int id) {
        for (ArrayList<Skills_Base> skills : skillMap.values()) {
            for (Skills_Base skill : skills) {
                if (skill.getId() == id) {
                    return skill;
                }
            }
        }
        return null;
    }
}
