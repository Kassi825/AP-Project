import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Controller {
    // all of these fields below are used to as reference for when we require an instance of a specific object
    private Scanner scanner = new Scanner(System.in);
    private Database database;
    private LoginMenu loginMenu;
    private CustomerMenu customerMenu;
    private RestaurantAdminMenu restaurantAdminMenu;
    private SnappfoodAdminMenu snappfoodAdminMenu;
    private Account currentLoggedInAccount;
    private Customer currentCustomer;
    private RestaurantAdmin currentRestaurantAdmin;
    private SnappfoodAdmin currentSnappfoodAdmin;
    private Matcher matcher;
    public Controller() {
        loginMenu = new LoginMenu();
        customerMenu = new CustomerMenu();
        restaurantAdminMenu = new RestaurantAdminMenu();
        snappfoodAdminMenu = new SnappfoodAdminMenu();
    }
    public Matcher getMatcher(String command, String regex) {
        Matcher matcher = Pattern.compile(regex).matcher(command);
        return matcher.matches() ? matcher : null;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public Database getDatabase() {
        return database;
    }

    public Scanner getScanner() {
        return scanner;
    }


    public void run() {
        while (true) {
            switch (loginMenu.run(scanner, this)) {
                case "enter customer menu":
                    if (customerMenu.run(scanner, this).equals("logout"))
                        continue;
                case "enter restaurant admin menu":
                    if (restaurantAdminMenu.run(scanner, this).equals("logout"))
                        continue;
                case "enter Snappfood admin menu":
                    if (snappfoodAdminMenu.run(scanner, this).equals("logout"))
                        continue;
                case "exit" :
                    return;
            }
        }
    }
    // username must only have alphabet characters or digits or '_'
    private boolean usernameIsValid(String username) {
        matcher = getMatcher(username, "[\\w_]+");
        if (matcher != null) {
            return username.matches(".*[a-zA-Z].*");
        }
        return false;
    }
    // to check if username is used
    private boolean usernameExists(String username) {
        return database.getAccountByUsername(username) != null;
    }
    // password must only have alphabet characters or digits or '_'
    private boolean passwordIsValid(String password) {
        return password.matches("[\\w_]+");
    }
    // password is strong only if it has at least a digit, a small letter, a capital letter, and have at least 5 characters
    private boolean passwordIsStrong(String password) {
        if (!password.matches(".*[a-z].*") ||
                !password.matches(".*[A-Z].*") ||
                !password.matches(".*[0-9].*") ||
                password.length() < 5)
            return false;
        return true;
    }
    // to check when logging into account
    private boolean passwordIsCorrect(Account account, String password) {
        return account.getPassword().equals(password);
    }

    private boolean restaurantExists(String name) {
        return database.getRestaurantByName(name) != null;
    }
    // food name can only have small letters and '-'
    private boolean nameIsValid(String name) {
        return name.matches("[\\-a-z]+");
    }

    private boolean foodExists(Restaurant restaurant1, String name) {
        return restaurant1.getFoodByName(name) != null;
    }

    private boolean foodIsInCart(String restaurantName, String foodName) {
        for (Food food: currentCustomer.getCart().getFoods()) {
            if (food.getName().equals(foodName) && food.getDesignatedRestaurant().getName().equals(restaurantName))
                return true;
        }
        return false;
    }
    // for when we want to register a new account
    public String register(String username, String password, String role) {

        if (usernameExists(username))
            return "register failed: there is already an account with this username!";
        else if (!usernameIsValid(username))
            return "register failed: username is invalid!";
        else if (!passwordIsValid(password))
            return "register failed: password is invalid!";
        else if (!passwordIsStrong(password))
            return "register failed: password is weak!";

        if (role.equals("Customer")) {
            Customer customer = new Customer(username, password, role);
            database.getAccounts().add(customer);
            database.getCustomers().add(customer);
        }
        else if (role.equals("SnappfoodAdmin")) {
            SnappfoodAdmin snappfoodAdmin = new SnappfoodAdmin(username, password, role);
            database.getAccounts().add(snappfoodAdmin);
            database.setSnappfoodAdmin(snappfoodAdmin);
        }
        return "register successful!";
    }

    // for when we want to log into our account
    public String login(String username, String password) {

        if (!usernameExists(username))
            return "login failed: username not found!";
        if (!passwordIsCorrect(database.getAccountByUsername(username), password))
            return "login failed: password was incorrect!";

        currentLoggedInAccount = database.getAccountByUsername(username);
        System.out.println("login successful!");
        System.out.println("your role: " + currentLoggedInAccount.getRole());
        if (currentLoggedInAccount.getRole().equals("Customer")) {
            currentCustomer = (Customer) currentLoggedInAccount;
            return "enter customer menu";
        }
        else if (currentLoggedInAccount.getRole().equals("RestaurantAdmin")) {
            currentRestaurantAdmin = (RestaurantAdmin) currentLoggedInAccount;
            return "enter restaurant admin menu";
        }
        else if (currentLoggedInAccount.getRole().equals("SnappfoodAdmin")) {
            currentSnappfoodAdmin = (SnappfoodAdmin) currentLoggedInAccount;
            return "enter Snappfood admin menu";
        }
        return null;
    }
    public void showBalance() {
        System.out.println("Your balance is: " + currentCustomer.getBalance() + "$");
    }

    public void increaseBalance(String money) {
        Integer amount = Integer.parseInt(money);
        if (amount <= 0) {
            System.out.println("invalid number!");
            return;
        }
        currentCustomer.setBalance(amount);
        System.out.println("the balance is increased!");
    }

    public void showListOfRestaurants() {
        int index = 1;
        for (Restaurant restaurant : database.getRestaurants()) {
            System.out.println(index + ") " + restaurant.getName());
            index++;
        }
    }

    public void showRestaurantMenu(String name) {
        if (name != null && !restaurantExists(name)) {
            System.out.println("show menu failed: restaurant is not found!");
            return;
        }
        int index = 1;
        if (currentLoggedInAccount.getRole().equals("Customer")) {
            Restaurant restaurant = database.getRestaurantByName(name);
            for (Food food : restaurant.getFoods()) {
                System.out.println(index + ") " + food.getName() + ": " + food.getPrice());
                index++;
            }
        }
        else if (currentLoggedInAccount.getRole().equals("RestaurantAdmin")) {
            for (Food food : currentRestaurantAdmin.getOwnedRestaurant().getFoods()) {
                System.out.println(index + ") " + food.getName() + ", price:" + food.getPrice() + ", cost:" + food.getCost());
                index++;
            }
        }

    }

    public void showCart() {
        int index = 1;
        for (Food food : currentCustomer.getCart().getFoods()) {
            System.out.println(index + ") " + food.getName() + ", price" + food.getPrice() + " from restaurant: " + food.getDesignatedRestaurant().getName());
            index++;
        }
        System.out.println("the total price: " + currentCustomer.getCart().getTotalPrice());
    }

    public String addFoodToCart(String restaurantName, String foodName) {

        if (!restaurantExists(restaurantName))
            return "cannot add food to cart: restaurant is not found!";
        if (!foodExists(database.getRestaurantByName(restaurantName), foodName))
            return "cannot add food to cart: food is not found!";
        if (foodIsInCart(restaurantName, foodName))
            return "cannot add food to cart: food is not found!";

        Restaurant restaurant = database.getRestaurantByName(restaurantName);
        Food food = restaurant.getFoodByName(foodName);
        currentCustomer.getCart().getFoods().add(food);
        return "success: the food has been added to the cart!";
    }
    public String removeFoodFromCart(String restaurantName, String foodName) {

        if (!restaurantExists(restaurantName))
            return "cannot remove food from cart: restaurant is not found!";
        if (!foodExists(database.getRestaurantByName(restaurantName), foodName))
            return "cannot remove food from cart: food is not found!";
        if (!foodIsInCart(restaurantName, foodName))
            return "cannot remove food from cart: food is not in cart!";

        Restaurant restaurant = database.getRestaurantByName(restaurantName);
        Food food = restaurant.getFoodByName(foodName);
        currentCustomer.getCart().getFoods().remove(food);
        return "success: the food has been removed to the cart!";
    }

    public String payment() {
        Integer totalPrice = currentCustomer.getCart().getTotalPrice();
        if (totalPrice > currentCustomer.getBalance())
            return "cannot purchase: not enough balance!";
        for (Food food: currentCustomer.getCart().getFoods()) {
            Restaurant designatedRestaurant = food.getDesignatedRestaurant();
            designatedRestaurant.setBudget(((-1) * food.getCost()) + food.getPrice());
        }
        currentCustomer.setBalance((-1) * totalPrice);
        currentCustomer.setCart(null);
        Cart cart = new Cart(currentCustomer);
        currentCustomer.setCart(cart);
        return "Thank you for your purchase!";
    }

    public void showBudget() {
        System.out.println("the restaurant budget is :" + currentRestaurantAdmin.getOwnedRestaurant().getBudget() + "$");
    }

    public void increaseBudget(String money) {
        Integer amount = Integer.parseInt(money);
        if (amount <= 0) {
            System.out.println("invalid number");
            return;
        }
        currentRestaurantAdmin.getOwnedRestaurant().setBudget(amount);
        System.out.println("the restaurant budget has been increased");
    }

    public String addFoodToMenu(String foodName, String price, String cost) {
        Integer foodPrice = Integer.parseInt(price);
        Integer foodCost = Integer.parseInt(cost);

        if (!nameIsValid(foodName))
            return "cannot add food to menu: name is not valid!";
        if (foodExists(currentRestaurantAdmin.getOwnedRestaurant(), foodName))
            return "cannot add food to menu: food is already in the menu!";
        if (foodPrice <= 0 || foodCost <= 0)
            return "cannot add food to menu: price or cost is invalid!";

        Food newFood = new Food(foodName, foodPrice, foodCost, currentRestaurantAdmin.getOwnedRestaurant());
        currentRestaurantAdmin.getOwnedRestaurant().getFoods().add(newFood);
        return "success: the food has been added to the menu!";
    }

    public String removeFoodFromMenu(String foodName) {

        if (!foodExists(currentRestaurantAdmin.getOwnedRestaurant(), foodName))
            return "cannot remove food from menu: food is not found!";

        Food food = currentRestaurantAdmin.getOwnedRestaurant().getFoodByName(foodName);
        currentRestaurantAdmin.getOwnedRestaurant().getFoods().remove(food);
        return "success: the food has been removed from the menu!";
    }

    public String addRestaurant(String name, String username, String password) {

        if (usernameExists(username))
            return "add restaurant failed: there is already an account with this username!";
        else if (!usernameIsValid(username))
            return "add restaurant failed: admin username is invalid!";
        else if (!passwordIsValid(password))
            return "add restaurant failed: admin password is invalid!";
        else if (!passwordIsStrong(password))
            return "add restaurant failed: admin password is weak!";
        else if (restaurantExists(name))
            return "add restaurant failed: there is already a restaurant with this name!";

        RestaurantAdmin restaurantAdmin = new RestaurantAdmin(username, password, "RestaurantAdmin", null);
        Restaurant restaurant = new Restaurant(name, 0, null);
        restaurantAdmin.setOwnedRestaurant(restaurant);
        restaurant.setOwner(restaurantAdmin);
        database.getRestaurantAdmins().add(restaurantAdmin);
        database.getAccounts().add(restaurantAdmin);
        database.getRestaurants().add(restaurant);
        return "add restaurant successful!";
    }

    public String removeRestaurant(String name) {

        if (!restaurantExists(name))
            return "remove restaurant failed: the restaurant was not found!";

        Restaurant restaurant = database.getRestaurantByName(name);
        RestaurantAdmin restaurantAdmin = restaurant.getOwner();
        database.getAccounts().remove(restaurantAdmin);
        database.getRestaurantAdmins().remove(restaurantAdmin);
        database.getRestaurants().remove(restaurant);
        return "remove restaurant successful!";
    }
}
