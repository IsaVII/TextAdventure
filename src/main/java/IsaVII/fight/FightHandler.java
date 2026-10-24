package IsaVII.fight;

import IsaVII.character.MainCharacter;
import IsaVII.character.Skill;
import IsaVII.character.Skills;
import IsaVII.enemies.Enemy;
import IsaVII.skills.Skills_Base;
import IsaVII.ui.UiHelpers;
import IsaVII.ui.UserInputHelpers;

import java.util.ArrayList;

public class FightHandler {

    ArrayList<Enemy> enemies;

    public FightHandler(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }

    public void startFight() {
        IO.println("You are being attacked");

        handleFightMenu();
    }

    private void printEnemies() {
        UiHelpers.printLine();
        for (Enemy enemy : enemies) {
            IO.println("\t" + enemy.getName() + " with health: " + enemy.getHealth());
        }
        UiHelpers.printLine();

    }

    private void handleFightMenu() {

        printEnemies();
        printFightMenu();
        int choice = UserInputHelpers.getMenuNumberFromUserInRange("What do you want to do?", 1, 3);

        MainCharacter mainCharacter = MainCharacter.getInstance();
        switch (choice) {
            case 1:
                chooseSkill(mainCharacter);

                break;
            case 2:
                //Todo:: handle items
                break;
            case 3:
                //Todo:: handle flee

                return;
        }

        mainCharacter.roundEnded();

        if (enemies.isEmpty()) {
            IO.println("You have defeated all enemies!");
            return;
        }

        handleFightMenu();

    }

    private void printFightMenu() {
        IO.println("Fight Menu:");
        IO.println("\t1. Attack");
        IO.println("\t2. Use Item");
        IO.println("\t3. Flee");
    }

    public void chooseSkill(MainCharacter mainCharacter) {
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
            } else {
                handleSkillUsage(selectedSkill);
                return;
            }
        }
    }

    public void handleSkillUsage(Skill skill) {

        Skills_Base skillBase = skill.getSkill();
        boolean hasMultipleTargets = skillBase.getTargetCount() > 1;
        if (hasMultipleTargets) {
            attackSeveralEnemies(skillBase);
        } else {
            attackSingleEnemy(skillBase);
        }

        checkForDeadEnemies();
        UiHelpers.printLine();
        IO.println();

        skill.startCooldown();
    }

    private void attackSingleEnemy(Skills_Base skillBase) {
        Enemy enemy = null;
        if (enemies.size() > 1) {
            enemy = chooseEnemyToAttack(skillBase);

        } else {
            enemy = enemies.getFirst();
        }

        dealDamageToSingleEnemy(skillBase, enemy);
    }

    private void dealDamageToSingleEnemy(Skills_Base skillBase, Enemy enemy) {
        enemy.receiveDamage(skillBase.getDamage());
        IO.println(UiHelpers.color("You used " + skillBase.getName() + " and dealt " + skillBase.getDamage() + " damage to " + enemy.getName() + "!", UiHelpers.BLUE));
    }

    private void attackSeveralEnemies(Skills_Base skillBase) {
        int maxTargets = Math.min(skillBase.getTargetCount(), enemies.size());

        for (int i = 0; i < maxTargets; i++) {
            Enemy enemy = enemies.get(i);
            enemy.receiveDamage(skillBase.getDamage());
        }

        IO.println(UiHelpers.color("You used " + skillBase.getName() + " and dealt " + skillBase.getDamage() + " damage to " + maxTargets + " targets!", UiHelpers.BLUE));
    }


    private Enemy chooseEnemyToAttack(Skills_Base skillBase) {
        IO.println("Select an enemy to attack:");
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            IO.println("\t" + (i + 1) + ". " + enemy.getName() + " with health: " + enemy.getHealth());
        }
        int choice = UserInputHelpers.getMenuNumberFromUserInRange("Which enemy do you want to attack?", 1, enemies.size());
        return enemies.get(choice - 1);
    }

    private void checkForDeadEnemies() {

        ArrayList<Enemy> deadEnemies = new ArrayList<>();
        for (Enemy enemy : enemies) {
            if (enemy.isDead()) {
                deadEnemies.add(enemy);
            }
        }

        for (Enemy deadEnemy : deadEnemies) {
            IO.println(UiHelpers.color(deadEnemy.getName() + " has been defeated!", UiHelpers.GREEN));
            enemies.remove(deadEnemy);
        }
    }

}
