package SBAmodels;

public class Money {
    private int money_id;
    private float balance;

    public int getMoney_id() {
        return money_id;
    }

    public void setMoney_id(int money_id) {
        this.money_id = money_id;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public Money(int money_id, float balance) {
        this.money_id = money_id;
        this.balance = balance;
    }
}
