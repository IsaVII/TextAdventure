package IsaVII.fight;

import IsaVII.enemies.Enemy;
import IsaVII.enemies.EnemyManager;
import IsaVII.world.AreaType;

import java.util.ArrayList;

public class FightManager {
    
    private static EnemyManager enemyManager;
    
    public FightManager() {
        enemyManager = new EnemyManager();
        
    }
    
    public void initializeFight(AreaType areaType) {
        ArrayList<Enemy> enemies = enemyManager.spawnEnemies(areaType);
        
        FightHandler fightHandler = new FightHandler(enemies);
        fightHandler.startFight();
    }
}
