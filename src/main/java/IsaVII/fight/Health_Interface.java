package IsaVII.fight;

public interface Health_Interface {

    void addHealth(int amount);
    void receiveDamage(int damage);
    boolean isDead();
    int getHealth();
    String printHealth();   
}
