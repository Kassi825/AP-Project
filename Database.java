import java.util.ArrayList;

public class Database {

    private ArrayList<Account> accounts;
    private ArrayList<Customer> customers;
    private ArrayList<RestaurantAdmin> restaurantAdmins;
    private SnappfoodAdmin snappfoodAdmin;
    private ArrayList<Restaurant> restaurants;
    public Database() {
        accounts = new ArrayList<>();
        customers = new ArrayList<>();
        restaurantAdmins = new ArrayList<>();
        snappfoodAdmin = null;
        restaurants = new ArrayList<>();
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    public void setRestaurantAdmins(ArrayList<RestaurantAdmin> restaurantAdmins) {
        this.restaurantAdmins = restaurantAdmins;
    }

    public void setSnappfoodAdmin(SnappfoodAdmin snappfoodAdmin) {
        this.snappfoodAdmin = snappfoodAdmin;
    }

    public void setRestaurants(ArrayList<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public ArrayList<RestaurantAdmin> getRestaurantAdmins() {
        return restaurantAdmins;
    }

    public SnappfoodAdmin getSnappfoodAdmin() {
        return snappfoodAdmin;
    }

    public ArrayList<Restaurant> getRestaurants() {
        return restaurants;
    }

    public Account getAccountByUsername(String username) {
        for (Account account : accounts) {
            if (account.getUsername().equals(username)) {
                return account;
            }
        }
        return null;
    }


    public Customer getCustomerByUsername(String username) {
        for (Customer customer : customers) {
            if (customer.getUsername().equals(username)) {
                return customer;
            }
        }
        return null;
    }

    public RestaurantAdmin getRestaurantAdminByUsername(String username) {
        for (RestaurantAdmin restaurantAdmin :restaurantAdmins) {
            if (restaurantAdmin.getUsername().equals(username))
                return restaurantAdmin;
        }
        return null;
    }

    public Restaurant getRestaurantByName(String name) {
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getName().equals(name))
                return restaurant;
        }
        return null;
    }

}
