package IsaVII.world;

import IsaVII.Objects.WorldObject;
import IsaVII.localization.LocalizationManager;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EnvironmentNodeTest {

    private static final Gson GSON = new Gson();

    @BeforeAll
    static void initLocalizationManager() {
        new LocalizationManager();
    }

    @Test
    @DisplayName("A node deserializes its area type, art path, description and monster flag")
    void deserializesBasicFields() {
        String json = """
                {
                  "areaType": "FOREST",
                  "artPath": "ascii/forest-1.txt",
                  "description": "A dark forest.",
                  "monsterAttacksPossible": true,
                  "boss": "NONE",
                  "exits": {}
                }
                """;
        EnvironmentNode node = GSON.fromJson(json, EnvironmentNode.class);

        assertEquals(AreaType.FOREST, node.getAreaType());
        assertEquals("ascii/forest-1.txt", node.getArtPath());
        assertEquals("A dark forest.", node.getDescription());
        assertTrue(node.isMonsterAttacksPossible());
    }

    @Test
    @DisplayName("Exits deserialize into a Direction-keyed map")
    void deserializesExits() {
        String json = """
                {
                  "areaType": "FOREST",
                  "artPath": "a.txt",
                  "boss": "NONE",
                  "exits": { "FORWARD": "next-node", "LEFT": "other-node" }
                }
                """;
        EnvironmentNode node = GSON.fromJson(json, EnvironmentNode.class);

        assertEquals("next-node", node.getExits().get(Direction.FORWARD));
        assertEquals("other-node", node.getExits().get(Direction.LEFT));
    }

    @Test
    @DisplayName("A node with a real boss reports hasBoss as true")
    void hasBossTrueWhenBossPresent() {
        String json = """
                {
                  "areaType": "FOREST",
                  "artPath": "a.txt",
                  "boss": "FOREST_GUARDIAN",
                  "exits": {}
                }
                """;
        EnvironmentNode node = GSON.fromJson(json, EnvironmentNode.class);

        assertTrue(node.hasBoss());
        assertEquals(BossType.FOREST_GUARDIAN, node.getBoss());
    }

    @Test
    @DisplayName("A node with boss NONE reports hasBoss as false")
    void hasBossFalseWhenBossIsNone() {
        String json = """
                {
                  "areaType": "FOREST",
                  "artPath": "a.txt",
                  "boss": "NONE",
                  "exits": {}
                }
                """;
        EnvironmentNode node = GSON.fromJson(json, EnvironmentNode.class);

        assertFalse(node.hasBoss());
    }

    @Test
    @DisplayName("getWorldObjects returns an empty list when the node has none, never null")
    void getWorldObjectsReturnsEmptyListWhenAbsent() {
        String json = """
                {
                  "areaType": "FOREST",
                  "artPath": "a.txt",
                  "boss": "NONE",
                  "exits": {}
                }
                """;
        EnvironmentNode node = GSON.fromJson(json, EnvironmentNode.class);

        assertTrue(node.getWorldObjects().isEmpty());
        assertFalse(node.hasInteractableObjects());
    }

    @Test
    @DisplayName("World objects deserialize from the 'worldObject' JSON key")
    void deserializesWorldObjectsFromSingularJsonKey() {
        String json = """
                {
                  "areaType": "FOREST",
                  "artPath": "a.txt",
                  "boss": "NONE",
                  "exits": {},
                  "worldObject": [
                    {
                      "objectType": "CHEST",
                      "contents": [ { "itemType": "HEALTH_POTION", "quantity": 2 } ]
                    }
                  ]
                }
                """;
        EnvironmentNode node = GSON.fromJson(json, EnvironmentNode.class);

        assertEquals(1, node.getWorldObjects().size());
        WorldObject chest = node.getWorldObjects().get(0);
        assertEquals("open chest", chest.getCommand());
    }

    @Test
    @DisplayName("hasInteractableObjects is true only while an unopened world object remains")
    void hasInteractableObjectsReflectsOpenedState() {
        String json = """
                {
                  "areaType": "FOREST",
                  "artPath": "a.txt",
                  "boss": "NONE",
                  "exits": {},
                  "worldObject": [ { "objectType": "CHEST" } ]
                }
                """;
        EnvironmentNode node = GSON.fromJson(json, EnvironmentNode.class);

        assertTrue(node.hasInteractableObjects());

        node.getWorldObjects().get(0).interact(new IsaVII.Objects.Inventory());

        assertFalse(node.hasInteractableObjects());
    }
}
