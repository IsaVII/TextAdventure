package IsaVII.character;

import IsaVII.skills.Skills_Base;

public class Skill {

    private Skills_Base skill;
    private int currentCooldown;

    public Skill(Skills_Base skill) {
        this.skill = skill;
        this.currentCooldown = 0;
    }

    public Skills_Base getSkill() {
        return skill;
    }

    public int getCurrentCooldown() {
        return currentCooldown;
    }

    public void setCurrentCooldown(int currentCooldown) {
        this.currentCooldown = currentCooldown;
    }


    public void startCooldown() {
        this.currentCooldown = skill.getCooldown();
    }

    public void roundEnded() {
        
    }

    public boolean isOnCooldown() {
        return  currentCooldown > 0;
    }
}