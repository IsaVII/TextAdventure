package IsaVII.fight;

import IsaVII.character.MainCharacter;
import IsaVII.character.Skill;
import IsaVII.character.Skills;
import IsaVII.skills.SkillManager;
import IsaVII.skills.Skills_Base;
import IsaVII.ui.UiHelpers;
import IsaVII.ui.UserInputHelpers;

public class FightHandler {
    
    //for testing here, will be removed later
    void main(){
        SkillManager skillManager = new SkillManager();
        MainCharacter mainCharacter = new MainCharacter("Xemnas");
        startFight(mainCharacter);
    }
    
    
    public static void startFight(MainCharacter mainCharacter) {
        IO.println("Fight started!");
        
        //Todo:: show enemy 
         handleFightMenu(mainCharacter);
        
    
    }
    
    private static void handleFightMenu(MainCharacter mainCharacter){

        printFightMenu();
        int choice = UserInputHelpers.getMenuNumberFromUserInRange("What do you want to do?", 1, 3);

        switch (choice) {
            case 1:
                chooseSkill(mainCharacter);
                handleFightMenu(mainCharacter);
                break;
            case 2:
                //Todo:: handle items
                break;
            case 3:
                //Todo:: handle flee
                break;
        }

        mainCharacter.roundEnded();
    }

    private static void printFightMenu() {
        IO.println("Fight Menu:");
        IO.println("\t1. Attack");
        IO.println("\t2. Use Item");
        IO.println("\t3. Flee");
    }

    public static void chooseSkill(MainCharacter mainCharacter) {
        UiHelpers.printLine();
        IO.println("You chose to attack!");

        Skills skills = mainCharacter.getSkills();
        
        //print skill list and get user input for skill choice
        skills.printSkillList();
        int skillCount = skills.getSkillCount();

        while (true) {
            int choice = UserInputHelpers.getMenuNumberFromUserInRange("Which skill do you want to use?", 1, skillCount);
            Skill selectedSkill = skills.getSkillByIndex(choice - 1);

            if (selectedSkill.isOnCooldown()) {
                IO.println("This skill is on cooldown for " + selectedSkill.getCurrentCooldown() + " more rounds.\n");
            }
            else {
                handleSkillUsage(selectedSkill);
                return;
            }

        }
        
    }
    
    //Todo:: add enemy as reference to handle skill usage
    public static void handleSkillUsage(Skill skill) {

        Skills_Base skillBase = skill.getSkill();
        boolean hasMultipleTargets = skillBase.getTargetCount() > 1;
        if (hasMultipleTargets) {
            IO.println("You used " + skillBase.getName() + " and dealt " + skillBase.getDamage() + " damage to "+ skillBase.getTargetCount() + " targets!");
        }
        else {
            IO.println("You used " + skillBase.getName() + " and dealt " + skillBase.getDamage() + " damage!");
        }
        
        UiHelpers.printLine();
        IO.println();
        
        skill.startCooldown();
    }
}
