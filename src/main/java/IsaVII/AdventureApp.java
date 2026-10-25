package IsaVII;

import IsaVII.character.MainCharacter;
import IsaVII.fight.FightManager;
import IsaVII.skills.SkillManager;
import IsaVII.world.EnvironmentHandler;
import IsaVII.world.EnvironmentManager;


public class AdventureApp {
    static FightManager fightManager;
    static SkillManager skillManager;
    private static MainCharacter mainCharacter;

    void main() {
        skillManager = new SkillManager();
        fightManager = new FightManager();
        mainCharacter = new MainCharacter("Xemnas");

        EnvironmentManager environmentManager = new EnvironmentManager();
        EnvironmentHandler environmentHandler = new EnvironmentHandler(environmentManager, fightManager, "forest_entrance");
        environmentHandler.explore();
    }
    
    public static MainCharacter getMainCharacter() {
        return mainCharacter;
    }
}
