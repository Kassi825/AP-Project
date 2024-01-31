import java.util.Scanner;
import java.util.regex.Matcher;

public class LoginMenu {

    public String run(Scanner scanner , Controller controller) {
        String command;
        Matcher matcher;
        String result;
        while (true) {
            command = scanner.nextLine();
            if ((matcher = controller.getMatcher(command,  "\\s*register\\s+(?<username>\\S+)\\s+(?<password>\\S+)\\s*")) != null) {
                System.out.println();controller.register(matcher.group("username"), matcher.group("password"), "Customer");
            }
            else if ((matcher = controller.getMatcher(command, "\\s*login\\s+(?<username>\\S+)\\s+(?<password>\\S+)\\s*")) != null) {
                result = controller.login(matcher.group("username"), matcher.group("password"));
                if (!result.equals("login failed: password was incorrect") && !result.equals("login failed: username not found")) {
                    return result;
                }

            }
            else if (command.equals("exit")) {
                return "exit";
            }
            else {
                System.out.println("invalid command");
            }
        }
    }

}
