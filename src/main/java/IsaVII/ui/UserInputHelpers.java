package IsaVII.ui;

import IsaVII.world.Direction;

import java.util.List;
import java.util.Set;

public class UserInputHelpers {

    // **** USER INPUTS ****
    public static String getYesNoChoiceFromUser(String message) {
        String choice = "";
        boolean validInput = false;

        while (!validInput) {
            String userInput = IO.readln(message + " (yes/no): ");

            if (userInput.equalsIgnoreCase("yes") ||
                    userInput.equalsIgnoreCase("y") ||
                    userInput.equalsIgnoreCase("no" ) ||
                    userInput.equalsIgnoreCase("n")) {
                choice = userInput.toLowerCase();
                validInput = true;
            } else {
                IO.println("Invalid input. Please enter 'yes' or 'no'.");
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
                IO.println("Invalid choice. Please enter a number between " + minIncl + " and " + maxIncl + ".");
            }
        }
        return menuNumber;
    }

    public static Direction getDirectionFromUser(Set<Direction> allowedDirections) {
        while (true) {
            String userInput = IO.readln("Where do you want to go?: ");

            try {
                Direction direction = Direction.valueOf(userInput.trim().toUpperCase());
                if (allowedDirections.contains(direction)) {
                    return direction;
                }
            } catch (IllegalArgumentException e) {
            }

            IO.println("Invalid direction. Please choose one of: " + describeDirections(allowedDirections));
        }
    }

    // Returns either a Direction enum name (uppercase) or one of the given action commands (as-is),
    // so the caller can tell the two apart without a second lookup.
    public static String getDirectionOrActionFromUser(Set<Direction> allowedDirections, List<String> availableActions) {
        while (true) {
            String userInput = IO.readln("What do you want to do?: ").trim();

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

            IO.println("Invalid input. Please choose one of: " + describeOptions(allowedDirections, availableActions));
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
