package landbay.services;

import landbay.model.InvRequest;
import landbay.model.MatchedLoan;
import landbay.model.Loan;
import landbay.rules.MatchingRule;
import landbay.rules.ProductTypeRule;
import landbay.rules.TermLengthRule;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class LoanMatcher {

    private final Logger logger = LoggerFactory.getLogger(LoanMatcher.class);

    /**
     * Core method that matches loans to investment requests, based on the
     * given data and parameters.
     * <p>
     * Funds loan in order of oldest to newest (Completion Date) and uses
     * investment requests based on order in CSV since no dates were included.
     * <p>
     * Prints only fully funded matches as JSON output.
     */
    public void match() {
        // Prepare loans to be matched
        List<MatchedLoan> preppedLoans = loanPreparer();
        // Parse investment requests of a given type
        DataParser parser = new DataParser();
        List<InvRequest> requests = parser.parseInvestmentRequests();

        // Create a list for the results of matched loans
        List<MatchedLoan> matchedLoans = new ArrayList<>();

        // Iterate through loans
        for (MatchedLoan ml : preppedLoans) {
            // Hashmap of investors
            HashMap<String, Integer> investorList = new HashMap<>();
            // Apply matching rules
            List<InvRequest> qualifiedRequests = qualifyRequests(requests, ml);

            logger.info("Looking for matches for loan #" + ml.getLoanId());
            for (InvRequest request : qualifiedRequests) {
                // Skip if loan or investment request are already fulfilled
                if (ml.getAmountRemaining() > 0 && request.getAmountAvailable() > 0) {
                    // If investment amount available is less than loan amount remaining, invest it all.
                    if (request.getAmountAvailable() <= ml.getAmountRemaining()) {
                        // add investor and amount in investor hashmap
                        investorList.put(request.getInvestor(), request.getAmountAvailable());
                        // update loan & request remaining balances
                        ml.setAmountRemaining(ml.getAmountRemaining() - request.getAmountAvailable());
                        request.setAmountAvailable(0);

                    } else {  // otherwise, invest up to the remaining loan balance.
                        // add investor and amount in investor hashmap
                        investorList.put(request.getInvestor(), ml.getAmountRemaining());
                        // update loan & request remaining balances
                        request.setAmountAvailable(request.getAmountAvailable() - ml.getAmountRemaining());
                        ml.setAmountRemaining(0);
                        ml.setFullyFunded(true);
                        // skip to next loan
                        break;
                    }
                }

            }
            // Set full HashMap of investors & amount of contributions
            ml.setInvestorList(investorList);
            // Only add loans to MatchedLoan object if it was fully funded
            if (ml.isFullyFunded()) {
                matchedLoans.add(ml);
                logger.info("Success! Investors found for loan number #" + ml.getLoanId());
            } else {
                logger.warn("Sorry, unable to fulfill loan #" + ml.getLoanId());
            }
        }
        // Print all matched loans in pretty JSON
        printMatches(matchedLoans);
    }

    /**
     * Applies matching rules to Investment Requests to filter which requests could
     * invest in a given loan.
     *
     * @param requests List of InvRequest objects
     * @param ml       MatchedLoan to compare the InvRequests to
     * @return List of qualified InvRequest objects
     */
    private List<InvRequest> qualifyRequests(List<InvRequest> requests, MatchedLoan ml) {
        List<MatchingRule> matchingRules = new ArrayList<>();
        matchingRules.add(new TermLengthRule());
        matchingRules.add(new ProductTypeRule());

        for (MatchingRule rules : matchingRules) {
            requests = rules.applyRules(requests, ml);
        }
        return requests;
    }

    /**
     * Parses loan data from the CSV, and maps it into a MatchedLoan
     * object.
     * <p>
     * The loans are then sorted in ascending order based on their
     * formatted completed date.
     *
     * @return List of mapped and sorted MatchedLoan objects
     */
    public List<MatchedLoan> loanPreparer() {
        DataParser parser = new DataParser();
        List<Loan> loans = parser.parseLoans();
        List<MatchedLoan> preppedLoans = new ArrayList<>();
        // Take loans of a given type, and map them to an MatchedLoan object
        for (Loan loan : loans) {
            MatchedLoan ml = new MatchedLoan();
            ml.setLoanId(loan.getLoanId());
            ml.setType(loan.getProduct());
            ml.setTerm(loan.getTerm());
            ml.setFullAmount(loan.getLoanAmount());
            ml.setCompletedDate(loan.getFormattedDate());
            ml.setAmountRemaining(loan.getLoanAmount());
            // and to list
            preppedLoans.add(ml);
        }
        // sort loans by completed date
        preppedLoans.sort(Comparator.comparing(MatchedLoan::getCompletedDate));

        return preppedLoans;
    }

    /**
     * Print MatchedLoans into formatted JSON.
     *
     * @param matchedLoans List of prepared MatchedLoan objects
     */
    private void printMatches(List<MatchedLoan> matchedLoans) {
        for (MatchedLoan match : matchedLoans) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(match);
            System.out.println(json);
        }
    }
}
