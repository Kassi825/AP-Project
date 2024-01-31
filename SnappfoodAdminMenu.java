import java.util.Scanner;
import java.util.regex.Matcher;

public class SnappfoodAdminMenu {

    public String run(Scanner scanner, Controller controller) {
        String command;
        Matcher matcher;
        while (true) {
            command = scanner.nextLine();
            if ((matcher = controller.getMatcher(command, "\\s*add\\s+restaurant\\s+(?<name>\\S+)\\s+(?<username>\\S+)\\s+(?<password>\\S+)\\s*")) != null) {
                System.out.println(controller.addRestaurant(matcher.group("name"), matcher.group("username"), matcher.group("password")));
            }
            else if ((matcher = controller.getMatcher(command, "\\s*remove\\s+restaurant\\s+(?<name>\\S+)\\s*")) != null) {
                System.out.println(controller.removeRestaurant(matcher.group("name")));
            }
            else if ((matcher = controller.getMatcher(command, "\\s*show\\s+list\\s+of\\s+restaurants\\s*")) != null) {
                controller.showListOfRestaurants();
            }
            else if (command.equals("logout"))
                return "logout";
            else System.out.println("invalid command!");
        }
    }
}
