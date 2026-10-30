package IsaVII.world;

import IsaVII.Objects.WorldObject;
import IsaVII.character.MainCharacter;
import IsaVII.fight.FightManager;
import IsaVII.inventory.InventoryHandler;
import IsaVII.localization.LocalizationManager;
import IsaVII.ui.UiHelpers;
import IsaVII.ui.UserInputHelpers;

import java.util.ArrayList;
import java.util.List;

public class EnvironmentHandler {

    private final EnvironmentManager environmentManager;
    private final FightManager fightManager;
    private String currentNodeId;
    private boolean exploring = true;

    public EnvironmentHandler(EnvironmentManager environmentManager, FightManager fightManager, String startNodeId) {
        this.environmentManager = environmentManager;
        this.fightManager = fightManager;
        this.currentNodeId = startNodeId;
    }

    public void explore() {
        while (exploring) {
            EnvironmentNode node = environmentManager.getNode(currentNodeId);
            if (node == null) {
                throw new IllegalStateException("Unknown environment node: " + currentNodeId);
            }

            showNode(node);

            //check for boss
            if (node.hasBoss()) {
                fightManager.initializeFight(node.getAreaType());
                exploring = false;
                continue;
            }

            //check for random monster attacks
            if (node.isMonsterAttacksPossible() && Math.random() < 0.01) { //todo:: make this chance configurable
                IO.println(UiHelpers.color(LocalizationManager.getText("environment.danger_sensed"), UiHelpers.RED));
                fightManager.initializeFight(node.getAreaType());
            }

            //interactable object actions, plus the player actions always available (e.g. checking the inventory)
            List<WorldObject> interactables = node.getWorldObjects().stream().filter(o -> !o.isOpened()).toList();
            List<String> actions = new ArrayList<>(interactables.stream().map(WorldObject::getCommand).toList());
            actions.add(inventoryAction());

            String command = UserInputHelpers.getDirectionOrActionFromUser(node.getExits().keySet(), actions);

            Direction direction = asDirection(command);
            if (direction != null) {
                currentNodeId = node.getExits().get(direction);
            } else if (command.equalsIgnoreCase(inventoryAction())) {
                new InventoryHandler(MainCharacter.getInstance()).open();
            } else {
                performAction(interactables, command);
            }
        }
    }

    private String inventoryAction() {
        return LocalizationManager.getText("action.inventory");
    }

    private Direction asDirection(String command) {
        try {
            return Direction.valueOf(command);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private void performAction(List<WorldObject> interactables, String command) {
        for (WorldObject worldObject : interactables) {
            if (worldObject.getCommand().equalsIgnoreCase(command)) {
                String result = worldObject.interact(MainCharacter.getInstance().getInventory());
                IO.println(UiHelpers.color(result, UiHelpers.GREEN));
                return;
            }
        }
    }

    private void showNode(EnvironmentNode node) {
        UiHelpers.clearConsole();
        IO.println(environmentManager.loadArt(node.getArtPath()));
        IO.println();

        if (node.getDescription() != null && !node.getDescription().isEmpty()) {
            IO.println(node.getDescription());
            IO.println();
        }

        UiHelpers.printLine(true);

        if (node.hasBoss()) {
            IO.println(UiHelpers.color(LocalizationManager.getText("environment.boss_blocks_path", node.getBoss()), UiHelpers.MAGENTA));
        } else {
            IO.println(String.format("%-20s", LocalizationManager.getText("environment.can_go", describeExits(node))));
            IO.println(String.format("%-20s", LocalizationManager.getText("environment.can_also", describeActions(node))));
        }

        UiHelpers.printLine(true);
    }

    private String describeExits(EnvironmentNode node) {
        StringBuilder sb = new StringBuilder();
        for (Direction direction : node.getExits().keySet()) {
            if (!sb.isEmpty()) {
                sb.append(" - ");
            }
            sb.append(direction.name().toLowerCase());
        }
        return sb.toString();
    }

    private String describeActions(EnvironmentNode node) {
        StringBuilder sb = new StringBuilder();
        for (WorldObject worldObject : node.getWorldObjects()) {
            if (worldObject.isOpened()) {
                continue;
            }
            if (!sb.isEmpty()) {
                sb.append(" - ");
            }
            sb.append(worldObject.getCommand());
        }
        if (!sb.isEmpty()) {
            sb.append(" - ");
        }
        sb.append(inventoryAction());
        return sb.toString();
    }
}
