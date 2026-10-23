package IsaVII;

import IsaVII.character.MainCharacter;
import IsaVII.fight.FightHandler;
import IsaVII.skills.SkillManager;
import IsaVII.ui.UserInputHelpers;

public class AdventureApp {
    static void main() {

        SkillManager skillManager = new SkillManager();

        MainCharacter mainCharacter = new MainCharacter("Xemnas");
        FightHandler.startFight(mainCharacter);
        
        
        
    }
}
