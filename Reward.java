import java.util.Map;
import java.util.HashMap;
//code by Soumaya
public abstract class Reward implements Loyaltyprogram {
    private String rewardId;
    private String rewardName;
    private String description;
    private int pointCost;
    private String category;
    private int quantityAvailable;
    private String startDate;
    private String endDate;
    private boolean isActive;
    
    // Client info from LoyaltyProgram interface
    private String clientId;
    private String clientTier;
    private int pointBalance;
    private Map<String, Reward> availableRewards;

    public Reward(String rewardId, String rewardName, String description, int pointCost, 
                  String category, int quantityAvailable, String startDate, String endDate) {
        this.rewardId = rewardId;
        this.rewardName = rewardName;
        this.description = description;
        this.pointCost = pointCost;
        this.category = category;
        this.quantityAvailable = quantityAvailable;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = true;
        this.pointBalance = 0;
        this.availableRewards = new HashMap<>();
    }

    // Reward-specific methods
    public String getRewardId() {
        return rewardId;
    }

    public String getRewardName() {
        return rewardName;
    }

    public String getDescription() {
        return description;
    }

    public int getPointCost() {
        return pointCost;
    }

    public String getCategory() {
        return category;
    }

    public int getQuantityAvailable() {
        return quantityAvailable;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void updateQuantity(int newQuantity) {
        this.quantityAvailable = newQuantity;
    }

    public boolean isAvailable() {
        return quantityAvailable > 0 && isActive;
    }

    public String getRewardDetails() {
        return "Reward: " + rewardName + ", costs " + pointCost + " points, description: " + 
               description + ", category: " + category;
    }

    public void activateReward() {
        this.isActive = true;
    }

    public void deactivateReward() {
        this.isActive = false;
    }

    public boolean updatePointCost(int newCost) {
        if (newCost > 0) {
            this.pointCost = newCost;
            return true;
        }
        return false;
    }

    // LoyaltyProgram interface implementation (removed @Override temporarily)
    public String getClientId() {
        return clientId;
    }

    public String getClientTier() {
        return clientTier;
    }

    public boolean enrollClient(Map<String, String> clientInfo) {
        this.clientId = clientInfo.get("clientId");
        this.clientTier = clientInfo.get("clientTier");
        return this.clientId != null;
    }

    public int getPointBalance() {
        return pointBalance;
    }

    public void addPoints(double transactionAmount) {
        int points = calculatePoints(transactionAmount);
        pointBalance += points;
    }

    public boolean deductPoints(int pointsToDeduct) {
        if (pointsToDeduct <= pointBalance && pointsToDeduct > 0) {
            pointBalance -= pointsToDeduct;
            return true;
        }
        return false;
    }

    public int calculatePoints(double amount) {
        // Enhanced point calculation based on tier
        double multiplier = 1.0;
        if ("Gold".equals(clientTier)) {
            multiplier = 1.5;
        } else if ("Silver".equals(clientTier)) {
            multiplier = 1.25;
        }
        return (int) (amount * multiplier);
    }

    public boolean isEligibleForRedemption(Reward reward) {
        return pointBalance >= reward.getPointCost() && reward.isAvailable();
    }

    public Map<String, Reward> getAvailableRewards() {
        return new HashMap<>(availableRewards);
    }

    public boolean hasSufficientPoints(int requiredPoints) {
        return pointBalance >= requiredPoints;
    }

    // Helper method to add rewards to available rewards
    public void addAvailableReward(Reward reward) {
        availableRewards.put(reward.getRewardId(), reward);
    }

    // Purchase this specific reward
    public boolean purchaseReward() {
        if (isAvailable() && hasSufficientPoints(pointCost)) {
            return deductPoints(pointCost);
        }
        return false;
    }

    // Redeem reward method
    public boolean redeemReward(String rewardId) {
        Reward reward = availableRewards.get(rewardId);
        if (reward != null && isEligibleForRedemption(reward)) {
            boolean success = deductPoints(reward.getPointCost());
            if (success) {
                reward.updateQuantity(reward.getQuantityAvailable() - 1);
            }
            return success;
        }
        return false;
    }
}