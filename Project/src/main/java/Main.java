import java.util.regex.Matcher;

public class Main {

    public static void main(String[] args) {
        // this is when program starts
        Database database = new Database();
        Controller controller = new Controller();
        controller.setDatabase(database);
        String result;
        // before we go to the menus we register the admin of the program
        System.out.println("welcome to the app! you are the admin of the program. please enter your username and password:");
        while (true) {
        String command = controller.getScanner().nextLine();
        Matcher matcher;
        if ((matcher = controller.getMatcher(command, "\\s*(?<username>\\S+)\\s+(?<password>\\S+)\\s*")) != null) {
            result = controller.register(matcher.group("username"), matcher.group("password"), "SnappfoodAdmin");
            System.out.println(result);
            if (result.equals("register successful!"))
                break;
        }
        else System.out.println("invalid command");
        }
        controller.setDatabase(database);
        controller.run();
    }
}

