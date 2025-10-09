import java.util.Map;
//code by Soumaya
public interface Loyaltyprogram {
    String getClientId();
    String getClientTier();
    boolean enrollClient(Map<String, String> clientInfo);
    int getPointBalance();
    void addPoints(double transactionAmount);
    boolean deductPoints(int pointsToDeduct);
    int calulatePoints(double amount);
    boolean isEligibleForRedemption(Reward reward);
    Map<String, Reward> getAvailableRewards();
    boolean hasSufficientPoints(int requiredPoints);
}
