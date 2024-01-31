import java.util.ArrayList;

public class Restaurant {
    private String name;
    private Integer budget;
    private ArrayList<Food> foods;
    private RestaurantAdmin owner;

    public Restaurant(String name, Integer budget, RestaurantAdmin owner) {
        this.name = name;
        this.budget = budget;
        foods = new ArrayList<>();
        this.owner = owner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBudget(Integer budget) {
        this.budget += budget;
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
    }

    public void setOwner(RestaurantAdmin owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public Integer getBudget() {
        return budget;
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public RestaurantAdmin getOwner() {
        return owner;
    }

    public Food getFoodByName(String name) {
        for (Food food : foods) {
            if(food.getName().equals(name))
                return food;
        }
        return null;
    }

}

