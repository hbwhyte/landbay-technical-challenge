package landbay;

import landbay.services.DataParser;
import landbay.services.LoanMatcher;

/**
 * Main application to run and test core functionality:
 *   - Print Loans
 *   - Print Investment Requests
 *   - Print Matched Loans
 */
public class MainApplication {

    public static void main(String[] args) {
        DataParser parser = new DataParser();
        // Print out all loans
        parser.printLoans(parser.parseLoans());
        // Print out all investment requests
        parser.printRequests(parser.parseInvestmentRequests());

        LoanMatcher matcher = new LoanMatcher();
        // Match all Loans & Requests
        matcher.match();
    }
}
