package IsaVII.world;

import IsaVII.Objects.Items.ItemType;
import IsaVII.Objects.WorldObject;
import IsaVII.localization.LocalizationManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EnvironmentManagerTest {

    private static EnvironmentManager environmentManager;

    @BeforeAll
    static void loadEnvironmentManager() {
        new LocalizationManager();
        environmentManager = new EnvironmentManager();
    }

    @Test
    @DisplayName("The forest entrance node loads with its expected area, description and exit")
    void forestEntranceLoadsWithExpectedData() {
        EnvironmentNode node = environmentManager.getNode("forest_entrance");

        assertNotNull(node);
        assertEquals(AreaType.FOREST, node.getAreaType());
        assertNotNull(node.getDescription());
        assertFalse(node.getDescription().isEmpty());
        assertEquals("forest-t-path", node.getExits().get(Direction.FORWARD));
    }

    @Test
    @DisplayName("An unknown node id returns null instead of throwing")
    void unknownNodeIdReturnsNull() {
        assertNull(environmentManager.getNode("does-not-exist"));
    }

    @Test
    @DisplayName("The forest clearing node has a chest containing health potions")
    void forestClearingHasChestWithHealthPotions() {
        EnvironmentNode node = environmentManager.getNode("forest-clearing");

        assertNotNull(node);
        assertTrue(node.hasInteractableObjects());

        WorldObject chest = node.getWorldObjects().get(0);
        assertEquals("open chest", chest.getCommand());
        assertEquals(ItemType.HEALTH_POTION, chest.getContents().get(0).getItemType());
        assertEquals(2, chest.getContents().get(0).getQuantity());
    }

    @Test
    @DisplayName("The boss lair node has the forest guardian boss and no exits")
    void bossLairHasBossAndNoExits() {
        EnvironmentNode node = environmentManager.getNode("boss-lair");

        assertNotNull(node);
        assertTrue(node.hasBoss());
        assertEquals(BossType.FOREST_GUARDIAN, node.getBoss());
        assertTrue(node.getExits().isEmpty());
    }

    @Test
    @DisplayName("Every exit target referenced by a node points at a node that actually exists")
    void everyExitTargetResolvesToAnExistingNode() {
        for (String nodeId : new String[] {
                "forest_entrance", "forest-t-path", "forest-clearing", "deep-forest", "boss-lair"
        }) {
            EnvironmentNode node = environmentManager.getNode(nodeId);
            assertNotNull(node, "Expected node to exist: " + nodeId);

            for (Map.Entry<Direction, String> exit : node.getExits().entrySet()) {
                assertNotNull(
                        environmentManager.getNode(exit.getValue()),
                        "Node '" + nodeId + "' has a " + exit.getKey() + " exit to unknown node '" + exit.getValue() + "'");
            }
        }
    }

    @Test
    @DisplayName("loadArt reads the ascii art file referenced by a node's artPath")
    void loadArtReadsReferencedAsciiFile() {
        EnvironmentNode node = environmentManager.getNode("forest_entrance");

        String art = environmentManager.loadArt(node.getArtPath());

        assertNotNull(art);
        assertFalse(art.isEmpty());
    }

    @Test
    @DisplayName("loadArt throws when the referenced art file does not exist")
    void loadArtThrowsForMissingFile() {
        org.junit.jupiter.api.Assertions.assertThrows(
                RuntimeException.class,
                () -> environmentManager.loadArt("ascii/does-not-exist.txt"));
    }
}
