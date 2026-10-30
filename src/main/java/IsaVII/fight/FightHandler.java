package IsaVII.fight;

import IsaVII.character.MainCharacter;
import IsaVII.character.Skill;
import IsaVII.character.Skills;
import IsaVII.enemies.Enemy;
import IsaVII.localization.LocalizationManager;
import IsaVII.skills.Skills_Base;
import IsaVII.ui.IO;
import IsaVII.ui.UiHelpers;
import IsaVII.ui.UserInputHelpers;

import java.util.ArrayList;

public class FightHandler {

    ArrayList<Enemy> enemies;
    boolean isFighting = true;

    MainCharacter mainCharacter;

    public FightHandler(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
        this.mainCharacter = MainCharacter.getInstance();
    }

    public void startFight() {
        IO.println(LocalizationManager.getText("fight.being_attacked"));

        //Chance that it's a surprise encounter
        if (Math.random() < 0.9) {
            IO.println(UiHelpers.color(LocalizationManager.getText("fight.surprise_encounter"), UiHelpers.RED));
            printEnemies();
            enemyAttacks();
            printPlayerHealth();
        }
        
        while (isFighting) {
            //Player's turn
            handleFightMenu();
            
            //Enemies' turn
            if (isFighting) {
                enemyAttacks();
                printPlayerHealth();
            }
        }
    }

    private void printPlayerHealth() {
        IO.println(LocalizationManager.getText("fight.your_health", mainCharacter.printHealth()));
    }

    private void enemyAttacks() {

        for (Enemy enemy : enemies) {
            Skill skill = enemy.getRandomAttackSkill();

            if (skill == null) {
                IO.println(UiHelpers.color(LocalizationManager.getText("fight.enemy_no_skills", enemy.getName()), UiHelpers.RED));
                continue;
            }

            Skills_Base skillBase = skill.getSkill();
            mainCharacter.receiveDamage(skillBase.getDamage());
            IO.println(UiHelpers.color(LocalizationManager.getText("fight.enemy_attack", enemy.getName(), skillBase.getName(), skillBase.getDamage()), UiHelpers.RED));
        }
    }

  

    private void handleFightMenu() {

        printEnemies();
        printFightMenu();
        int choice = UserInputHelpers.getMenuNumberFromUserInRange(LocalizationManager.getText("fight.menu.prompt"), 1, 3);

        switch (choice) {
            case 1:
                chooseSkill();

                break;
            case 2:
                //Todo:: handle items
                break;
            case 3:
                IO.println(UiHelpers.color(LocalizationManager.getText("fight.fled"), UiHelpers.YELLOW));
                isFighting = false;
                break;
        }

        mainCharacter.roundEnded();

        if (enemies.isEmpty()) {
            IO.println(LocalizationManager.getText("fight.victory"));
            isFighting = false;
        }


    }

    private void printFightMenu() {
        IO.println(String.format("%-20s\t | %s", LocalizationManager.getText("fight.menu.title"), LocalizationManager.getText("fight.your_health", mainCharacter.printHealth())));
        IO.println(String.format("\t%-20s |", LocalizationManager.getText("fight.menu.attack")));
        IO.println(String.format("\t%-20s |", LocalizationManager.getText("fight.menu.use_item")));
        IO.println(String.format("\t%-20s |", LocalizationManager.getText("fight.menu.flee")));
    }

    public void chooseSkill() {
        UiHelpers.printLine(true);
        IO.println(LocalizationManager.getText("fight.chose_attack"));

        Skills skills = mainCharacter.getSkills();

        //print skill list and get user input for skill choice
        skills.printSkillList();
        int skillCount = skills.getSkillCount();

        while (true) {
            int choice = UserInputHelpers.getMenuNumberFromUserInRange(LocalizationManager.getText("fight.skill.prompt"), 1, skillCount);
            Skill selectedSkill = skills.getSkillByIndex(choice - 1);

            if (selectedSkill.isOnCooldown()) {
                IO.println(LocalizationManager.getText("fight.skill.cooldown", selectedSkill.getCurrentCooldown()) + "\n");
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
        UiHelpers.printLine(true);
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
        IO.println(UiHelpers.color(LocalizationManager.getText("fight.player_attack_single", skillBase.getName(), skillBase.getDamage(), enemy.getName()), UiHelpers.BLUE));
    }

    private void attackSeveralEnemies(Skills_Base skillBase) {
        int maxTargets = Math.min(skillBase.getTargetCount(), enemies.size());

        for (int i = 0; i < maxTargets; i++) {
            Enemy enemy = enemies.get(i);
            enemy.receiveDamage(skillBase.getDamage());
        }

        IO.println(UiHelpers.color(LocalizationManager.getText("fight.player_attack_aoe", skillBase.getName(), skillBase.getDamage(), maxTargets), UiHelpers.BLUE));
    }


    private Enemy chooseEnemyToAttack(Skills_Base skillBase) {
        IO.println(LocalizationManager.getText("fight.select_enemy_label"));
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            IO.println("\t" + LocalizationManager.getText("fight.enemy_list_item_numbered", i + 1, enemy.getName(), enemy.getHealth()));
        }
        int choice = UserInputHelpers.getMenuNumberFromUserInRange(LocalizationManager.getText("fight.select_enemy_prompt"), 1, enemies.size());
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
            IO.println(UiHelpers.color(LocalizationManager.getText("fight.enemy_defeated", deadEnemy.getName()), UiHelpers.GREEN));
            enemies.remove(deadEnemy);
        }
    }

    private void printEnemies() {
        UiHelpers.printLine(true);
        IO.println(LocalizationManager.getText("fight.enemies_label"));
        for (Enemy enemy : enemies) {
            IO.println("\t" + LocalizationManager.getText("fight.enemy_list_item", enemy.getName(), enemy.getHealth()));
        }
        UiHelpers.printLine(true);

    }

}
