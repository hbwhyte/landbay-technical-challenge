package landbay.model;

import com.opencsv.bean.CsvBindByName;

/**
 * Models data input from the investmentRequest.csv into Java object
 */
public class InvRequest {

    @CsvBindByName
    private String investor;

    @CsvBindByName
    private int investmentAmount;

    @CsvBindByName
    private String productType; // FIXED or TRACKER

    @CsvBindByName
    private int term; // in months

    private int amountAvailable; // Added later, starts as same as investmentAmount

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

    public int getAmountAvailable() {
        return amountAvailable;
    }

    public void setAmountAvailable(int amountAvailable) {
        this.amountAvailable = amountAvailable;
    }

    @Override
    public String toString() {
        return "InvRequest{" +
                "investor='" + investor + '\'' +
                ", investmentAmount=" + investmentAmount +
                ", productType='" + productType + '\'' +
                ", term=" + term +
                '}';
    }
}
