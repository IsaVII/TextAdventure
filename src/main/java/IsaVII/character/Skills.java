package IsaVII.character;

import IsaVII.skills.Skills_Base;

import java.util.ArrayList;
import java.util.HashMap;

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
        
        int counter = 1;
        for (Skill skill :  skills) {
            skill.printSkill(true, counter++);
        }
    }
}
