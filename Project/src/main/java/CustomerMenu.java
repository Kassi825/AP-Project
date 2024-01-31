import java.util.Scanner;
import java.util.regex.Matcher;

public class CustomerMenu {

    public String run(Scanner scanner, Controller controller) {
        String command;
        Matcher matcher;
        while (true) {
            command = scanner.nextLine();
            if ((matcher = controller.getMatcher(command, "\\s*show\\s+balance\\s*")) != null) {
                controller.showBalance();
            }
            else if ((matcher = controller.getMatcher(command, "\\s*increase\\s+balance\\s+(?<amount>\\S+)\\s*")) != null) {
                controller.increaseBalance(matcher.group("amount"));
            }
            else if ((matcher = controller.getMatcher(command, "\\s*show\\s+list\\s+of\\s+restaurants\\s*")) != null) {
                controller.showListOfRestaurants();
            }
            else if ((matcher = controller.getMatcher(command, "\\s*show\\s+restaurant\\s+menu\\s+(?<restaurantName>)\\s*")) != null) {
                controller.showRestaurantMenu(matcher.group("restaurantName"));
            }
            else if ((matcher = controller.getMatcher(command, "\\s*show\\s+cart\\s*")) != null) {
                controller.showCart();
            }
            else if ((matcher = controller.getMatcher(command, "\\s*add\\s+food\\s+to\\s+cart\\s+(?<restaurantName>\\S+)\\s+(?<foodName>\\S+)\\s*")) != null) {
                controller.addFoodToCart(matcher.group("restaurantName"), matcher.group("foodName"));
            }
            else if ((matcher = controller.getMatcher(command, "\\s*remove\\s+food\\s+from\\s+cart\\s+(?<restaurantName>\\S+)\\s+(?<foodName>\\S+)\\s*")) != null) {
                controller.removeFoodFromCart(matcher.group("restaurantName"), matcher.group("foodName"));
            }
            else if ((matcher = controller.getMatcher(command, "\\s*payment\\s*")) != null) {
                controller.payment();
            }
            else if (command.equals("logout"))
                return "logout";
            else System.out.println("invalid command!");
        }
    }
}
