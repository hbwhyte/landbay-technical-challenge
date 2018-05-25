package landbay.model;

/**
 * Models data input from the investmentRequest.csv into Java object
 */
public class InvestmentRequest {

    String investor;
    int investmentAmount;
    String productType; // could be enum?
    int term;

    // Constructor
    public InvestmentRequest(String investor, int investmentAmount, String productType, int term) {
        this.investor = investor;
        this.investmentAmount = investmentAmount;
        this.productType = productType;
        this.term = term;
    }

    // Getters & Setters
    public String getInvestor() {
        return investor;
    }

    public void setInvestor(String investor) {
        this.investor = investor;
    }

    public int getInvestmentAmount() {
        return investmentAmount;
    }

    public void setInvestmentAmount(int investmentAmount) {
        this.investmentAmount = investmentAmount;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }
}
