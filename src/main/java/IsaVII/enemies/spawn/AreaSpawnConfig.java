package IsaVII.enemies.spawn;

import java.util.List;

public class AreaSpawnConfig {
    private double healthMultiplier;
    private int minGroupSize;
    private int maxGroupSize;
    private List<EnemySpawnEntry> enemies;

    public double getHealthMultiplier() {
        return healthMultiplier;
    }

    public int getMinGroupSize() {
        return minGroupSize;
    }

    public int getMaxGroupSize() {
        return maxGroupSize;
    }

    public List<EnemySpawnEntry> getEnemies() {
        return enemies;
    }
}
