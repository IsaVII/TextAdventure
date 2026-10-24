package IsaVII.character;


import IsaVII.skills.SkillManager;
import IsaVII.skills.SkillName;

public class MainCharacter {
    
    private final String name; 
    private Skills skills;
    
    private int coins = 0;
    
    public MainCharacter(String name) {
        this.name = name;
        InitSkills();
    }
    
    private void InitSkills(){
        skills = new Skills();
        skills.addSkill(SkillManager.getSkill(SkillName.SLASH));
        skills.addSkill(SkillManager.getSkill(SkillName.CLEAVE));
    }

  

    public String getName() {
        return name;
    }

    public Skills getSkills() {
        return skills;
    }

    public int getCoins() {
        return coins;
    }

    public void roundEnded() {
         skills.roundEnded();
    }
}
