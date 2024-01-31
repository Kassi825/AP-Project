public class RestaurantAdmin extends Account{

    private Restaurant ownedRestaurant;

    public RestaurantAdmin(String username, String password, String role, Restaurant ownedRestaurant) {
        super(username, password, role);
        this.ownedRestaurant = ownedRestaurant;
    }

    public void setOwnedRestaurant(Restaurant ownedRestaurant) {
        this.ownedRestaurant = ownedRestaurant;
    }

    public Restaurant getOwnedRestaurant() {
        return ownedRestaurant;
    }

}
