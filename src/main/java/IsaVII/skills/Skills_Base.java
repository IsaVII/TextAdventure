package IsaVII.skills;

public abstract class Skills_Base {
    
    protected final int id;
    protected final SkillType skillType;
    protected final String name;
    protected final String description;
    
    protected final int cooldown;
    
    public Skills_Base(int id, SkillType skillType, String name, String description, int cooldown) {
        this.id = id;
        this.skillType = skillType;
        this.name = name;
        this.description = description;
        this.cooldown = cooldown;  
    }


    public void printSkill(boolean fightMode, int nr) {
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

    public int getCooldown() {
        return cooldown;
    }

 
}
