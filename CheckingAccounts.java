public class CheckingAccounts extends Account {
    private double overDraftLimit;
    private double monthlyFee;
    private String debitCardNumber;
    private double transactionFee;

    public boolean applyMonthlyFee(double newLimit){
        if(balance >= monthlyFee){
            balance -= monthlyFee;
            return true;
        }
        return false;
    }
    public boolean updateOverdraftLimit(double newLimit){
        if(newLimit>=0){
            overDraftLimit = newLimit;
            return true;
        }
        return false;
    }
    public double getAvaliableBalance(){
        return balance + overDraftLimit;
    }
    public double getTransactionFee(){
        return transactionFee;
    }
    private void deductTransactionFee(){
            balance -= transactionFee;

    }
};
