package IsaVII.world;

import IsaVII.Objects.WorldObject;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class EnvironmentNode {
    private AreaType areaType;
    private String artPath;
    private String description;
    private boolean monsterAttacksPossible;
    private BossType boss;
    private Map<Direction, String> exits;

    @SerializedName("worldObject")
    private List<WorldObject> worldObjects;

    public AreaType getAreaType() {
        return areaType;
    }

    public String getArtPath() {
        return artPath;
    }

    public String getDescription() {
        return description;
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

    public List<WorldObject> getWorldObjects() {
        return worldObjects == null ? List.of() : worldObjects;
    }

    public boolean hasInteractableObjects() {
        return getWorldObjects().stream().anyMatch(worldObject -> !worldObject.isOpened());
    }
}
