package IsaVII;

import IsaVII.Objects.ItemManager;
import IsaVII.character.MainCharacter;
import IsaVII.fight.FightManager;
import IsaVII.skills.SkillManager;
import IsaVII.world.EnvironmentHandler;
import IsaVII.world.EnvironmentManager;


public class AdventureApp {
    private static FightManager fightManager;
    private static SkillManager skillManager;
    private static ItemManager itemManager;
    private static EnvironmentManager environmentManager;
    private static MainCharacter mainCharacter;

    void main() {
        skillManager = new SkillManager();
        itemManager = new ItemManager();
        fightManager = new FightManager();
        environmentManager = new EnvironmentManager();
        
        mainCharacter = new MainCharacter("Xemnas");
        
        EnvironmentHandler environmentHandler = new EnvironmentHandler(environmentManager, fightManager, "forest_entrance");
        environmentHandler.explore();
    }
    
    public static MainCharacter getMainCharacter() {
        return mainCharacter;
    }
}
