import java.time.LocalDate;
public class SavingAccount extends Account {
    private double InterestRate;
    private String minimumBalance;
    private LocalDate lastInterestDate;// should I make it javaDate
    private String nickName;
    public SavingAccount() {}
    public void applyInterest(){
    balance += balance*InterestRate;
    }

    public void updateInterestRate(double newRate){
        InterestRate = newRate;
    }//?

    public double getProjectedInterest(){
        return balance*InterestRate;
    }
}
