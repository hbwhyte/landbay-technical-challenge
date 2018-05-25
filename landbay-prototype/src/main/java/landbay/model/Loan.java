package landbay.model;

import com.opencsv.bean.CsvBindByName;

import java.util.Date;

/**
 * Models data input from the loans.csv into Java object
 */
public class Loan {

    @CsvBindByName
    int loanId;

    @CsvBindByName
    int loanAmount; // in pounds

    @CsvBindByName
    String product; // FIXED or TRACKER

    @CsvBindByName
    int term; // in months

    @CsvBindByName
    String completedDate; // dd/MM/yyyy

    Date formattedDate; // formatted for sorting

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

    public Date getFormattedDate() {
        return formattedDate;
    }

    public void setFormattedDate(Date formattedDate) {
        this.formattedDate = formattedDate;
    }
}
