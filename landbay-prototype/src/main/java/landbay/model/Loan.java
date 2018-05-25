package landbay.model;

/**
 * Models data input from the loans.csv into Java object
 */
public class Loan {

    int loanId;
    int loanAmount; // in pounds
    String product; // FIXED or TRACKER
    int term; // in months
    String completedDate; // dd/MM/yyyy

    // Constructor
    public Loan(int loanId, int loanAmount, String product, int term, String completedDate) {
        this.loanId = loanId;
        this.loanAmount = loanAmount;
        this.product = product;
        this.term = term;
        this.completedDate = completedDate;
    }

    // Getters & Setters
    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public int getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(int loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public String getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(String completedDate) {
        this.completedDate = completedDate;
    }
}
