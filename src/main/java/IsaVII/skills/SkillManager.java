package IsaVII.skills;

import java.util.HashMap;

public class SkillManager {
    
    private static HashMap<SkillType, HashMap<SkillName, Skills_Base>> skillMap;
    
    public SkillManager() {
        skillMap = new HashMap<>();
        for (SkillType type : SkillType.values()) {
            skillMap.put(type,  new HashMap<>());
        }
        
        fillMapWithSkills();
    }

    private void fillMapWithSkills() {

        HashMap<SkillName, Skills_Base> meleeSkills = new HashMap<>();
        meleeSkills.put(SkillName.SLASH, new Skill_Melee(1, "Slash", "A quick melee attack.", 2, 0,1));
        meleeSkills.put(SkillName.STAB, new Skill_Melee(2, "Stab", "A precise melee attack.", 5, 1,1));
        meleeSkills.put(SkillName.HEAVY_STRIKE, new Skill_Melee(3, "Heavy Strike", "A powerful melee attack.", 10, 2,1));
        meleeSkills.put(SkillName.CLEAVE, new Skill_Melee(4, "Cleave", "A wide melee attack hitting 3 enemies.", 4, 3,3));
        meleeSkills.put(SkillName.BITE, new Skill_Melee(5, "Bite", "A vicious melee attack.", 3, 0,1));
        
        skillMap.put(SkillType.MELEE_ATTACK, meleeSkills);
    }
    
    
    public static Skills_Base getSkill(SkillName name) {
        for (HashMap<SkillName, Skills_Base> skills : skillMap.values()) {
            if (skills.containsKey(name)) {
                return skills.get(name);
            }
        }
        return null;
    }
}
