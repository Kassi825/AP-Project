import java.util.ArrayList;

public class Customer extends Account{

    private Integer balance;
    private Cart cart;
    public Customer(String username, String password, String role) {
        super(username, password, role);
        balance = 0;
        cart = new Cart(this);
    }

    public void setBalance (Integer balance) {
        this.balance += balance;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Integer getBalance() {
        return balance;
    }

    public Cart getCart() {
        return cart;
    }

}
