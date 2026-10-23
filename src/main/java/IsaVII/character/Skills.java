package IsaVII.character;

import IsaVII.skills.Skills_Base;

import java.util.ArrayList;

public class Skills {
    
    //skill + cooldown
    ArrayList<Skill> skills = new ArrayList<>();
    
    public void addSkill(Skills_Base skill) {
        skills.add(new Skill(skill));
    }
    
    public void removeSkill(int id) {
        skills.removeIf(skill -> skill.getSkill().getId() == id);
    }

    public void printSkillList() {
        IO.println("Skills:");
        IO.println(String.format("\t\t%-17s %-14s %-17s", "Skill", "Damage", "Cooldown"));
        
        int counter = 1;
        for (Skill skill :  skills) {
            skill.getSkill().printSkill(true, skill.getCurrentCooldown(), counter++);
        }
        
    }

    public Skill getSkillByIndex(int i) {
        return skills.get(i);
    }

    public void roundEnded() {
        for (Skill skill : skills) {
            skill.roundEnded();
        }
    }

    public int getSkillCount() {
        return skills.size();
    }
}
