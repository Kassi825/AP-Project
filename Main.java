import java.io.*;
import java.util.regex.Matcher;

public class Main {

    public static void main(String[] args) throws IOException {
        Database database = new Database();
        Controller controller = new Controller();
        controller.setDatabase(database);
        System.out.println("Please enter your username and password:");
        String command = controller.getScanner().nextLine();
        Matcher matcher;
        if ((matcher = controller.getMatcher(command, "\\s*(?<username>\\S+)\\s+(?<password>\\S+)\\s*")) != null) {
            controller.register(matcher.group("username"), matcher.group("password"), "SnappfoodAdmin");
        }
        controller.setDatabase(database);
        controller.run();
    }
}

