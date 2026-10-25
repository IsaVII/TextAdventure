package IsaVII.world;

import java.util.Map;

public class EnvironmentNode {
    private AreaType areaType;
    private String artPath;
    private boolean monsterAttacksPossible;
    private BossType boss;
    private Map<Direction, String> exits;

    public AreaType getAreaType() {
        return areaType;
    }

    public String getArtPath() {
        return artPath;
    }

    public boolean isMonsterAttacksPossible() {
        return monsterAttacksPossible;
    }

    public BossType getBoss() {
        return boss;
    }

    public Map<Direction, String> getExits() {
        return exits;
    }

    public boolean hasBoss() {
        return boss != null && boss != BossType.NONE;
    }
}
