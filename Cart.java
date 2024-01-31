import java.util.ArrayList;

public class Cart {
    private ArrayList<Food> foods;
    private Customer customer;

    public Cart(Customer customer) {
        this.customer = customer;
        foods = new ArrayList<>();
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Integer getTotalPrice() {
        Integer price = 0;
        for (Food food : foods) {
            price += food.getPrice();
        }
        return price;
    }
}

