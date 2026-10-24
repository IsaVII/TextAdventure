package IsaVII;

import IsaVII.character.MainCharacter;
import IsaVII.fight.FightManager;
import IsaVII.skills.SkillManager;
import IsaVII.world.AreaType;


public class AdventureApp {
    static FightManager fightManager;
    static SkillManager skillManager;
    private static MainCharacter mainCharacter;
    
    void main() {
        skillManager = new SkillManager();
        fightManager = new FightManager();
        mainCharacter = new MainCharacter("Xemnas");
        
        fightManager.initializeFight(AreaType.FOREST);
        
        
    }
    
    public static MainCharacter getMainCharacter() {
        return mainCharacter;
    }
}
