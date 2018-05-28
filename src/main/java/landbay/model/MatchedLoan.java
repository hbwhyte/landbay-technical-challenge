package landbay.model;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;

/**
 * Custom object for mapping loan/investment matches
 */
public class MatchedLoan {

    int loanId;
    int fullAmount;
    HashMap<String, Integer> investorList; // Investor and how much they invested
    String type; // FIXED or TRACKER
    int term;
    int amountRemaining; // Starts at full amount
    Instant completedDate; // Converted as Instant for proper sorting & time zones
    boolean fullyFunded = false; // Default of false

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public HashMap<String, Integer> getInvestorList() {
        return investorList;
    }

    public void setInvestorList(HashMap<String, Integer> investorList) {
        this.investorList = investorList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
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

    public Instant getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Instant completedDate) {
        this.completedDate = completedDate;
    }

    public boolean isFullyFunded() {
        return fullyFunded;
    }

    public void setFullyFunded(boolean fullyFunded) {
        this.fullyFunded = fullyFunded;
    }

    @Override
    public String toString() {
        return "MatchedLoan{" +
                "loanId=" + loanId +
                ", fullAmount=" + fullAmount +
                ", investorList=" + investorList +
                ", type='" + type + '\'' +
                ", amountRemaining=" + amountRemaining +
                ", fullyFunded=" + fullyFunded +
                '}';
    }
}
