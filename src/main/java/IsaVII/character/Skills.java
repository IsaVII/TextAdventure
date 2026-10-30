package IsaVII.character;

import IsaVII.localization.LocalizationManager;
import IsaVII.skills.Skills_Base;

import java.util.ArrayList;

public class Skills {
    
    //skill + cooldown
    ArrayList<Skill> skills = new ArrayList<>();

    public Skills() {
    }

    public Skills(Skills skills) {
        for (Skill skill : skills.skills) {
            this.skills.add(new Skill(skill.getSkill()));
        }
    }

    public void addSkill(Skills_Base skill) {
        skills.add(new Skill(skill));
    }
    
    public void removeSkill(int id) {
        skills.removeIf(skill -> skill.getSkill().getId() == id);
    }

    public void printSkillList() {
        IO.println(LocalizationManager.getText("skills.list.title"));
        IO.println(String.format("\t\t%-17s %-14s %-17s",
                LocalizationManager.getText("skills.list.column.skill"),
                LocalizationManager.getText("skills.list.column.damage"),
                LocalizationManager.getText("skills.list.column.cooldown")));
        
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

    public Skill getRandomSkill() {
         //check if cooldown is 0 and return a random skill that is not on cooldown
        ArrayList<Skill> availableSkills = new ArrayList<>();
        for (Skill skill : skills) {
            if (!skill.isOnCooldown()) {
                availableSkills.add(skill);
            }
        }
        
        if (availableSkills.isEmpty()) {
            return null; // No available skills
        }
        
        int randomIndex = (int) (Math.random() * availableSkills.size());
        return availableSkills.get(randomIndex);
    }
}
