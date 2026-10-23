package IsaVII.fight;

import IsaVII.character.MainCharacter;
import IsaVII.character.Skill;
import IsaVII.character.Skills;
import IsaVII.ui.UiHelpers;
import IsaVII.ui.UserInputHelpers;

public class FightHandler {
    
    
    public static void startFight(MainCharacter mainCharacter) {
        IO.println("Fight started!");
        
        //Todo:: show enemy 
        
        printFightMenu();
        int choice = UserInputHelpers.getMenuNumberFromUserInRange("What do you want to do?", 1, 3);
        
        switch (choice) {
            case 1:
                chooseSkill(mainCharacter);
                break;
            case 2:
                //Todo:: handle items
                break;
            case 3:
                //Todo:: handle flee
                break;
        }
    }

    private static void printFightMenu() {
        IO.println("Fight Menu:");
        IO.println("1. Attack");
        IO.println("2. Use Item");
        IO.println("3. Flee");
    }

    public static void chooseSkill(MainCharacter mainCharacter) {
        UiHelpers.printLine();
        IO.println("You chose to attack!");

        Skills skills = mainCharacter.getSkills();
        skills.printSkillList();
        int choice = UserInputHelpers.getMenuNumberFromUserInRange("Which skill do you want to use?", 1, 3);

        Skill selectedSkill = skills.getSkillByIndex(choice - 1);
        
    }
    
    //Todo:: add enemy as reference to handle skill usage
    public static void handleSkillUsage(Skill skill) {

    }
}
