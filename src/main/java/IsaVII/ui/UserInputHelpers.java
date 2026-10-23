package IsaVII.ui;

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
    
}
