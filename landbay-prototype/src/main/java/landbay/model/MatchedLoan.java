package landbay.model;

import java.util.HashMap;
import java.util.List;

public class MatchedLoan {

    int loanId;
    int fullAmount;
    HashMap<String,Integer> investment; // Investor and how much they invested
    String type; // FIXED or TRACKER
    int amountRemaining;

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public HashMap<String, Integer> getInvestment() {
        return investment;
    }

    public void setInvestment(HashMap<String, Integer> investment) {
        this.investment = investment;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getFullAmount() {
        return fullAmount;
    }

    public void setFullAmount(int fullAmount) {
        this.fullAmount = fullAmount;
    }

    public int getAmountRemaining() {
        return amountRemaining;
    }

    public void setAmountRemaining(int amountRemaining) {
        this.amountRemaining = amountRemaining;
    }

    @Override
    public String toString() {
        return "MatchedLoan{" +
                "loanId=" + loanId +
                ", fullAmount=" + fullAmount +
                ", investment=" + investment +
                ", type='" + type + '\'' +
                ", amountRemaining=" + amountRemaining +
                '}';
    }
}
