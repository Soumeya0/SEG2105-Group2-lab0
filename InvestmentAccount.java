public class InvestmentAccount {
    private String investmentType;
    private String riskLevel;
    private double portfolioValue;

    public InvestmentAccount(){}
    public void buyInvestment(String symbol, int quantity){

    }
    public void sellInvestment(String symbol, int quantity){

    }
    public double getPortfolioPreformence(){
        return portfolioValue*0.2;//retrun value %20
    }
    public double getPortfolioValue(){
        return portfolioValue;
    }
    public String getRiskLevel(){
        return riskLevel;
    }

    public double calculateReturn(){
        return portfolioValue * 0.08;
    }
};
