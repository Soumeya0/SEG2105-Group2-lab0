public class LoanAccount extends Account {
    private double interestRate;
    private double loanAmount;
    private double remainingBalance;
    private int loanTerm;
    private double monthlyPayment;

public void makePayment(double amount){
    if(amount < 0 && remainingBalance >= amount) {
        remainingBalance -= amount;
    }
}


}
