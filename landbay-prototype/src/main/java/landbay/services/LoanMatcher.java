package landbay.services;

import landbay.model.InvestmentRequest;
import landbay.model.Loan;
import landbay.model.MatchedLoan;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class LoanMatcher {

    public static void main(String[] args) throws IOException, ParseException {
        match("FIXED");
    }

    public static void loanAmounts(String type) throws IOException, ParseException {
        List<Loan> loans = DataParser.parseLoans();
        int loanSum = 0;
        for(Loan loan : loans) {
            if (loan.getProduct().equalsIgnoreCase(type)) {
                loanSum += loan.getLoanAmount();
            }
        }
        System.out.println("Fixed loan sum : " + loanSum);

        List<InvestmentRequest> requests = DataParser.parseInvestmentRequests();
        int requestSum = 0;
        for(InvestmentRequest request : requests) {
            if (request.getProductType().equalsIgnoreCase(type)) {
                requestSum += request.getInvestmentAmount();
            }
        }
        System.out.println("Fixed request sum : " + requestSum);
    }

    public static void match(String type) throws IOException, ParseException {
        List<Loan> fixedLoans = loanSeparator(type);
        List<InvestmentRequest> fixedRequests = investmentSeparator(type);

        List<MatchedLoan> matchedLoans = new ArrayList<>();
        for (Loan loan : fixedLoans) {
            MatchedLoan ml = new MatchedLoan();
            HashMap<String,Integer> investors = new HashMap<>();
            ml.setLoanId(loan.getLoanId());
            ml.setType(loan.getProduct());
            ml.setFullAmount(loan.getLoanAmount());
            ml.setAmountRemaining(loan.getLoanAmount());

            for(InvestmentRequest request : fixedRequests) {
                if (ml.getAmountRemaining() > 0 && request.getAmountAvailable() > 0 && loan.getTerm() < request.getTerm()) {
                    // If amount available is less than amount remaining, invest it all.
                    if (request.getAmountAvailable() <= ml.getAmountRemaining()) {
                        investors.put(request.getInvestor(), request.getAmountAvailable());
                        System.out.println(request.getInvestor() + " invests " + request.getAmountAvailable()
                            + " in to loan number " + ml.getLoanId());
                        request.setAmountAvailable(0);
                        ml.setAmountRemaining(ml.getAmountRemaining()-request.getInvestmentAmount());
                        System.out.println("ml amount remaining: " + ml.getAmountRemaining());
                    // otherwise, invest up to the remaining balance.
                    } else {
                        investors.put(request.getInvestor(),ml.getAmountRemaining());
                        System.out.println(request.getInvestor() + " invests " + ml.getAmountRemaining()
                                + " in to loan number " + ml.getLoanId());
                        request.setAmountAvailable(request.getAmountAvailable()-ml.getAmountRemaining());
                        ml.setAmountRemaining(0);
                        System.out.println("Loan number " + ml.getLoanId() + " fulfilled");
                        break;
                    }
                }

            }
            ml.setInvestment(investors);
            if (ml.getAmountRemaining()==0) {matchedLoans.add(ml);}
        }
        for (MatchedLoan match : matchedLoans) {
            System.out.println(match.toString());
        }
    }

    public static List<Loan> loanSeparator(String type) throws IOException, ParseException {
        List<Loan> loans = DataParser.parseLoans();
        List<Loan> separatedLoans = new ArrayList<>();
        for (Loan loan : loans) {
            if (loan.getProduct().equalsIgnoreCase(type)) {
                separatedLoans.add(loan);
            }
        }
        return separatedLoans;
    }

    public static List<InvestmentRequest> investmentSeparator(String type) throws IOException, ParseException {
        List<InvestmentRequest> requests = DataParser.parseInvestmentRequests();
        List<InvestmentRequest> separatedRequests = new ArrayList<>();
        for (InvestmentRequest request : requests) {
            if (request.getProductType().equalsIgnoreCase(type)) {
                separatedRequests.add(request);
            }
        }
        return separatedRequests;
    }
}
