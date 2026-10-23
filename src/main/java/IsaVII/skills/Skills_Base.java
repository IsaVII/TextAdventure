package IsaVII.skills;

public class Skills_Base {
    
    private final int id;
    private final SkillType skillType;
    private final String name;
    private final String description;
    
    private final int cooldown;
    
    public Skills_Base(int id, SkillType skillType, String name, String description, int cooldown) {
        this.id = id;
        this.skillType = skillType;
        this.name = name;
        this.description = description;
        this.cooldown = cooldown;  
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
