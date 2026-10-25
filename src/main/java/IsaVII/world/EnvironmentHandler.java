package IsaVII.world;

import IsaVII.fight.FightManager;
import IsaVII.ui.UiHelpers;
import IsaVII.ui.UserInputHelpers;

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

            Direction direction = UserInputHelpers.getDirectionFromUser(node.getExits().keySet());
            currentNodeId = node.getExits().get(direction);
        }
    }

    private void showNode(EnvironmentNode node) {
        UiHelpers.clearConsole();
        IO.println(environmentManager.loadArt(node.getArtPath()));
        IO.println();
        UiHelpers.printLine(true);

        if (node.hasBoss()) {
            IO.println(UiHelpers.color("A powerful presence blocks your path: " + node.getBoss(), UiHelpers.MAGENTA));
        } else {
            IO.println("You can go: " + describeExits(node));
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
}
