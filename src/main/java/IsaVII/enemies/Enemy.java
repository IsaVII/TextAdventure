package IsaVII.enemies;

import IsaVII.character.Skills;

public class Enemy {
    
    private String name;
    private Skills skills;
    
    public Enemy(String name) {
        this.name = name;
       skills = new Skills();
    }
    
    public String getName() {
        return name;
    }
    
    public Skills getSkills() {
        return skills;
    }
}
