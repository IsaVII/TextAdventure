package IsaVII.skills;

public abstract class Skills_Base  implements Skill_Interface {
    
    protected final int id;
    protected final SkillType skillType;
    protected final String name;
    protected final String description;
    
    protected final int cooldown; //the number is the round when the player can use the skill again (3 -> 2 rounds no usage)
    
    public Skills_Base(int id, SkillType skillType, String name, String description, int cooldown) {
        this.id = id;
        this.skillType = skillType;
        this.name = name;
        this.description = description;
        this.cooldown = cooldown;  
    }


    public void printSkill(boolean fightMode, int currentCooldown, int nr) {
    }

    public int getId() {
        return id;
    }

    public SkillType getSkillType() {
        return skillType;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public int getCooldown() {
        return cooldown;
    }

 
}
