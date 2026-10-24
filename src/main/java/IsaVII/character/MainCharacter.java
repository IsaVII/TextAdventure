package IsaVII.character;


import IsaVII.AdventureApp;
import IsaVII.fight.Health;
import IsaVII.fight.Health_Interface;
import IsaVII.skills.SkillManager;
import IsaVII.skills.SkillName;

public class MainCharacter implements Health_Interface {
    
    private final String name; 
    private Skills skills;
    private Health health;
   
    private int coins = 0;
    
    public MainCharacter(String name) {
        this.name = name;
        health = new Health(100);
        InitSkills();
    }

    public static MainCharacter getInstance() {
        return AdventureApp.getMainCharacter();
    }

    private void InitSkills(){
        skills = new Skills();
        skills.addSkill(SkillManager.getSkill(SkillName.SLASH));
        skills.addSkill(SkillManager.getSkill(SkillName.CLEAVE));
    }

    public void roundEnded() {
         skills.roundEnded();
    }
    
    // **** Health_Interface methods **** 
    @Override
    public void addHealth(int amount) {
        health.addHealth(amount);
    }
    
    @Override
    public void receiveDamage(int damage) {
        health.receiveDamage(damage);
    }

    @Override
    public boolean isDead() {
        return health.isDead();
    }

    @Override
    public int getHealth() {
        return health.getHealth();
    }


    
    //****  Getters **** 

    public String getName() {
        return name;
    }

    public Skills getSkills() {
        return skills;
    }

    public int getCoins() {
        return coins;
    }
}
