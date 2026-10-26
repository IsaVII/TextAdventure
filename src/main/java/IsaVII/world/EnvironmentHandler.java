package IsaVII.world;

import IsaVII.Objects.WorldObject;
import IsaVII.character.MainCharacter;
import IsaVII.fight.FightManager;
import IsaVII.ui.UiHelpers;
import IsaVII.ui.UserInputHelpers;

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

            if (node.hasBoss()) {
                fightManager.initializeFight(node.getAreaType());
                exploring = false;
                continue;
            }

            if (node.isMonsterAttacksPossible() && Math.random() < 0.01) {
                IO.println(UiHelpers.color("You sense danger nearby...", UiHelpers.RED));
                fightManager.initializeFight(node.getAreaType());
            }

            List<WorldObject> interactables = node.getWorldObjects().stream().filter(o -> !o.isOpened()).toList();
            List<String> actions = interactables.stream().map(WorldObject::getCommand).toList();

            String command = UserInputHelpers.getDirectionOrActionFromUser(node.getExits().keySet(), actions);

            Direction direction = asDirection(command);
            if (direction != null) {
                currentNodeId = node.getExits().get(direction);
            } else {
                performAction(interactables, command);
            }
        }
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
            IO.println(UiHelpers.color("A powerful presence blocks your path: " + node.getBoss(), UiHelpers.MAGENTA));
        } else {
            IO.println("You can go: " + describeExits(node));
            if (node.hasInteractableObjects()) {
                IO.println("You can also: " + describeActions(node));
            }
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
        return sb.toString();
    }
}
