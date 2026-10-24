package IsaVII.enemies;

import IsaVII.character.Skills;
import IsaVII.enemies.spawn.AreaSpawnConfig;
import IsaVII.enemies.spawn.EnemySpawnEntry;
import IsaVII.skills.SkillManager;
import IsaVII.skills.SkillName;
import IsaVII.world.AreaType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnemyManager {

    private static final String SPAWN_CONFIG_PATH = "enemies/enemy_spawns.json";

    private static HashMap<EnemyType, Enemy> enemies;
    private static Map<AreaType, AreaSpawnConfig> spawnConfig;

    public EnemyManager() {
        enemies = new HashMap<>();
        createWolf();
        createBoar();
        spawnConfig = loadSpawnConfig();
    }

    public static Enemy getEnemyCopy(EnemyType type) {
        Enemy enemy = enemies.get(type);
        if (enemy != null) {
            return new Enemy(enemy);
        }
        return null;
    }


    private void createWolf() {
        Enemy wolf = new Enemy("Wolf", 10);
        wolf.getSkills().addSkill(SkillManager.getSkill(SkillName.BITE));
        enemies.put(EnemyType.Wolf, wolf);
    }

    private void createBoar() {
        Enemy boar = new Enemy("Boar", 30);
        Skills skills = boar.getSkills();
        skills.addSkill(SkillManager.getSkill(SkillName.HEAVY_STRIKE));
        skills.addSkill(SkillManager.getSkill(SkillName.BITE));

        enemies.put(EnemyType.Boar, boar);
    }

    private Map<AreaType, AreaSpawnConfig> loadSpawnConfig() {
        try (Reader reader = new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream(SPAWN_CONFIG_PATH),
                StandardCharsets.UTF_8)) {
            Type mapType = new TypeToken<Map<AreaType, AreaSpawnConfig>>() {}.getType();
            return new Gson().fromJson(reader, mapType);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load enemy spawn config: " + SPAWN_CONFIG_PATH, e);
        }
    }

    public ArrayList<Enemy> spawnEnemies(AreaType areaType) {
        ArrayList<Enemy> spawnedEnemies = new ArrayList<>();
        AreaSpawnConfig config = spawnConfig.get(areaType);
        if (config == null || config.getEnemies() == null || config.getEnemies().isEmpty()) {
            return spawnedEnemies;
        }

        int groupSize = config.getMinGroupSize()
                + (int) (Math.random() * (config.getMaxGroupSize() - config.getMinGroupSize() + 1));

        for (int i = 0; i < groupSize; i++) {
            EnemyType type = rollEnemyType(config.getEnemies());
            if (type == null) {
                continue;
            }
            Enemy copy = getEnemyCopy(type);
            if (copy != null) {
                spawnedEnemies.add(new Enemy(copy, config.getHealthMultiplier()));
            }
        }

        return spawnedEnemies;
    }

    private EnemyType rollEnemyType(List<EnemySpawnEntry> candidates) {
        Map<EnemyType, Double> weights = new HashMap<>();
        double totalWeight = 0.0;

        for (EnemySpawnEntry entry : candidates) {
            double weight = entry.getMinSpawnChance()
                    + Math.random() * (entry.getMaxSpawnChance() - entry.getMinSpawnChance());
            weights.put(EnemyType.valueOf(entry.getType()), weight);
            totalWeight += weight;
        }

        if (totalWeight <= 0.0) {
            return null;
        }

        double roll = Math.random() * totalWeight;
        double cumulative = 0.0;
        for (Map.Entry<EnemyType, Double> entry : weights.entrySet()) {
            cumulative += entry.getValue();
            if (roll <= cumulative) {
                return entry.getKey();
            }
        }
        return null;
    }
}
