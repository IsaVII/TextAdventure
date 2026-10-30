package IsaVII.ui;

import IsaVII.localization.LocalizationManager;
import IsaVII.world.Direction;

import java.util.List;
import java.util.Set;

public class UserInputHelpers {

    // **** USER INPUTS ****
    public static String getYesNoChoiceFromUser(String message) {
        String choice = "";
        boolean validInput = false;

        while (!validInput) {
            String userInput = IO.readln(LocalizationManager.getText("input.yes_no_prompt", message));

            if (userInput.equalsIgnoreCase("yes") ||
                    userInput.equalsIgnoreCase("y") ||
                    userInput.equalsIgnoreCase("no" ) ||
                    userInput.equalsIgnoreCase("n")) {
                choice = userInput.toLowerCase();
                validInput = true;
            } else {
                IO.println(LocalizationManager.getText("input.yes_no_invalid"));
            }

        }
        return choice;
    }

    //getting a number from the user from min to max, includes validation checks so that the program continues only 
    //when a valid number is being entered by the user
    public static int getMenuNumberFromUserInRange(String message, int minIncl, int maxIncl) {
        int menuNumber = 0;
        boolean validInput = false;

        while (!validInput) {
            String userInput = IO.readln(message + " (" + minIncl + "-" + maxIncl + "): ");

            try {
                menuNumber = Integer.parseInt(userInput);
                validInput = menuNumber >= minIncl && menuNumber <= maxIncl;

            } catch (NumberFormatException e) {
            }

            if (!validInput) {
                IO.println(LocalizationManager.getText("input.menu_number_invalid", minIncl, maxIncl));
            }
        }
        return menuNumber;
    }

    public static Direction getDirectionFromUser(Set<Direction> allowedDirections) {
        while (true) {
            String userInput = IO.readln(LocalizationManager.getText("input.direction_prompt"));

            try {
                Direction direction = Direction.valueOf(userInput.trim().toUpperCase());
                if (allowedDirections.contains(direction)) {
                    return direction;
                }
            } catch (IllegalArgumentException e) {
            }

            IO.println(LocalizationManager.getText("input.direction_invalid", describeDirections(allowedDirections)));
        }
    }

    // Returns either a Direction enum name (uppercase) or one of the given action commands (as-is),
    // so the caller can tell the two apart without a second lookup.
    public static String getDirectionOrActionFromUser(Set<Direction> allowedDirections, List<String> availableActions) {
        while (true) {
            String userInput = IO.readln(LocalizationManager.getText("input.action_prompt")).trim();

            for (String action : availableActions) {
                if (userInput.equalsIgnoreCase(action)) {
                    return action;
                }
            }

            try {
                Direction direction = Direction.valueOf(userInput.toUpperCase());
                if (allowedDirections.contains(direction)) {
                    return direction.name();
                }
            } catch (IllegalArgumentException e) {
            }

            IO.println(LocalizationManager.getText("input.action_invalid", describeOptions(allowedDirections, availableActions)));
        }
    }

    private static String describeDirections(Set<Direction> directions) {
        StringBuilder sb = new StringBuilder();
        for (Direction direction : directions) {
            if (!sb.isEmpty()) {
                sb.append(", ");
            }
            sb.append(direction.name().toLowerCase());
        }
        return sb.toString();
    }

    private static String describeOptions(Set<Direction> directions, List<String> availableActions) {
        StringBuilder sb = new StringBuilder(describeDirections(directions));
        for (String action : availableActions) {
            if (!sb.isEmpty()) {
                sb.append(", ");
            }
            sb.append(action);
        }
        return sb.toString();
    }

}
