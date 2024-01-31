public class Food {
    private String name;
    private Integer cost;
    private Integer price;
    private Restaurant designatedRestaurant;

    public Food(String name, Integer cost, Integer price, Restaurant designatedRestaurant) {
        this.name = name;
        this.cost = cost;
        this.price = price;
        this.designatedRestaurant = designatedRestaurant;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setDesignatedRestaurant(Restaurant designatedRestaurant) {
        this.designatedRestaurant = designatedRestaurant;
    }

    public String getName() {
        return name;
    }

    public Integer getCost() {
        return cost;
    }

    public Integer getPrice() {
        return price;
    }

    public Restaurant getDesignatedRestaurant() {
        return designatedRestaurant;
    }

}
