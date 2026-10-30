package IsaVII.skills;

import IsaVII.localization.LocalizationManager;

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
        meleeSkills.put(SkillName.SLASH, new Skill_Melee(1, LocalizationManager.getText("skill.slash.name"), LocalizationManager.getText("skill.slash.description"), 2, 0,1));
        meleeSkills.put(SkillName.STAB, new Skill_Melee(2, LocalizationManager.getText("skill.stab.name"), LocalizationManager.getText("skill.stab.description"), 5, 1,1));
        meleeSkills.put(SkillName.HEAVY_STRIKE, new Skill_Melee(3, LocalizationManager.getText("skill.heavy_strike.name"), LocalizationManager.getText("skill.heavy_strike.description"), 10, 2,1));
        meleeSkills.put(SkillName.CLEAVE, new Skill_Melee(4, LocalizationManager.getText("skill.cleave.name"), LocalizationManager.getText("skill.cleave.description"), 4, 3,3));
        meleeSkills.put(SkillName.BITE, new Skill_Melee(5, LocalizationManager.getText("skill.bite.name"), LocalizationManager.getText("skill.bite.description"), 3, 0,1));

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
