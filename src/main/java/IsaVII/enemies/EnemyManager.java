package IsaVII.enemies;

import IsaVII.character.Skills;
import IsaVII.skills.SkillManager;
import IsaVII.skills.SkillName;

import java.util.HashMap;

public class EnemyManager {
    
    HashMap<EnemyType, Enemy> enemies;
    
    public EnemyManager() {
        enemies = new HashMap<>();
        createWolf();
        createBoar();
    }
    
    private void createWolf() {
        Enemy wolf = new Enemy("Wolf", 10);
        wolf.getSkills().addSkill(SkillManager.getSkill(SkillName.BITE));
        enemies.put(EnemyType.Wolf, wolf);
    }
    
    private void createBoar() {
        Enemy boar = new Enemy("Boar",30);
        Skills skills = boar.getSkills();
        skills.addSkill(SkillManager.getSkill(SkillName.HEAVY_STRIKE));
        skills.addSkill(SkillManager.getSkill(SkillName.BITE));
        
        enemies.put(EnemyType.Boar, boar);
    }
}
