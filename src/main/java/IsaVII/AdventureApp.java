package IsaVII;

import IsaVII.character.MainCharacter;
import IsaVII.skills.SkillManager;

public class AdventureApp {
    static void main() {

        SkillManager skillManager = new SkillManager();

        MainCharacter mainCharacter = new MainCharacter("Xemnas");
        mainCharacter.printTurnData();
        
        
        
    }
}
