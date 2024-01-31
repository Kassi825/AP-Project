import java.util.Scanner;
import java.util.regex.Matcher;

public class RestaurantAdminMenu {

    public String run(Scanner scanner, Controller controller) {
        String command;
        Matcher matcher;
        while (true) {
            command = scanner.nextLine();
            if ((matcher = controller.getMatcher(command, "\\s*show\\s+budget\\s*")) != null) {
                controller.showBudget();
            }
            else if ((matcher = controller.getMatcher(command, "\\s*increase\\s+budget\\s+(?<amount>\\S+)\\s*")) != null) {
                controller.increaseBudget(matcher.group("amount"));
            }
            else if ((matcher = controller.getMatcher(command, "\\s*show\\s+restaurant\\s+menu\\s*")) != null) {
                controller.showRestaurantMenu(null);
            }
            else if ((matcher = controller.getMatcher(command, "\\s*add\\s+food\\s+to\\s+menu\\s+(?<foodName>\\S+)\\s+(?<price>\\S+)\\s+(?<cost>\\S+)\\s*")) != null) {
                System.out.println(controller.addFoodToMenu(matcher.group("foodName"), matcher.group("price"), matcher.group("cost")));
            }
            else if ((matcher = controller.getMatcher(command, "\\s*remove\\s+food\\s+from\\s+menu\\s+(?<foodName>\\S+)\\s*")) != null) {
                System.out.println(controller.removeFoodFromMenu(matcher.group("foodName")));
            }
            else if (command.equals("logout"))
                return "logout";
            else System.out.println("invalid command!");
        }
    }
}

