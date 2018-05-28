package landbay;

import landbay.services.DataParser;
import landbay.services.LoanMatcher;

public class MainApplication {

    public static void main(String[] args) {
        // Print out all loans
        DataParser.printLoans(DataParser.parseLoans());
        // Print out all investment requests
        DataParser.printRequests(DataParser.parseInvestmentRequests());

        LoanMatcher matcher = new LoanMatcher();
        // Match Fixed Loans & Requests
        matcher.match("FIXED");
        // Match Tracker Loans & Requests
        matcher.match("TRACKER");
    }
}
