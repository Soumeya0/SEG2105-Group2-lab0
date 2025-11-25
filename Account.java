import java.time.LocalDate;
public class Account {
    protected String typeOfClient;
    protected String accountNumber;
    protected double balance;
    protected LocalDate openingDate;//maybe make this date
    protected String status;
    protected String currency;
    protected Account() {}

    public boolean deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            return true;
        }
        return false;
    }
    public boolean withdraw(double amount) {
        if  (amount > 0&& amount <= this.balance) {
            this.balance -= amount;
            return true;
        }
        return false;
    }

    public double getBalance() {
        return balance;
    }
    public String getAccountInfo(){
        return "Account Number:" + this.accountNumber+"\nAccount Type:" + this.typeOfClient+"\nAccount Balance:" + this.balance;
    }
    public boolean closeAccount() {
        if (this.balance == 0) {
            return false;
        }
        return true;
    }
    public boolean transfer( Account toAccount, double amount) {
        if(this.withdraw(amount)) {
            toAccount.deposit(amount);
            return true ;
        }
        return false;
    }

    public String getAccountNumber() {
        return accountNumber;
    }


}
