package IsaVII.character;


import IsaVII.skills.SkillManager;

public class MainCharacter {
    
    private String name; 
    private Skills skills;
    
    private int coins = 0;
    
    public MainCharacter(String name) {
        this.name = name;
        InitSkills();
    }
    
    private void InitSkills(){
        skills = new Skills();
        skills.addSkill(SkillManager.getSkillById(1));
        skills.addSkill(SkillManager.getSkillById(4));
    }
    

    public void printTurnData(){
        
        IO.println("Skills:");
        IO.println(String.format("\t\t%-17s %-14s %-17s", "Skill", "Damage", "Cooldown"));
        skills.printSkillList();
    }
    
}
