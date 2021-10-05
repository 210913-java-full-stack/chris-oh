package SBAmodels;

public class Junc {
    private int account_id;
    private int junc_id;
    private int money_id;

    public Junc(int account_id, int junc_id, int money_id) {
        this.account_id = account_id;
        this.junc_id = junc_id;
        this.money_id = money_id;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getJunc_id() {
        return junc_id;
    }

    public void setJunc_id(int junc_id) {
        this.junc_id = junc_id;
    }

    public int getMoney_id() {
        return money_id;
    }

    public void setMoney_id(int money_id) {
        this.money_id = money_id;
    }
}
