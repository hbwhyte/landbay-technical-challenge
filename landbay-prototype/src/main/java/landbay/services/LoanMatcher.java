package landbay.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import landbay.model.InvRequest;
import landbay.model.MatchedLoan;
import landbay.model.Loan;
import landbay.rules.MatchingRule;
import landbay.rules.TermLengthRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class LoanMatcher {

    private final Logger logger = LoggerFactory.getLogger(LoanMatcher.class);

    public static void main(String[] args) {
        LoanMatcher matcher = new LoanMatcher();

        matcher.match("FIXED");
        matcher.match("TRACKER");
    }

    public void match(String type) {
        // Parse loans of a given type
        List<MatchedLoan> separatedLoans = loanSeparator(type);
        // Parse investment requests of a given type
        List<InvRequest> separatedRequests = investmentSeparator(type);
        // Create a list for the results of matched loans
        List<MatchedLoan> matchedLoans = new ArrayList<>();

        // Iterate through loans
        for (MatchedLoan ml : separatedLoans) {
            // Hashmap of investors
            HashMap<String,Integer> investorList = new HashMap<>();
            List<InvRequest> qualifiedRequests = qualifyLoans(separatedRequests, ml);
            logger.info("Looking for matches for loanId " + ml.getLoanId());
            for(InvRequest request : qualifiedRequests) {
                if (ml.getAmountRemaining() > 0 && request.getAmountAvailable() > 0) {
                    // If amount available is less than amount remaining, invest it all.
                    if (request.getAmountAvailable() <= ml.getAmountRemaining()) {
                        investorList.put(request.getInvestor(), request.getAmountAvailable());
                        ml.setAmountRemaining(ml.getAmountRemaining()-request.getAmountAvailable());
                        request.setAmountAvailable(0);
                    // otherwise, invest up to the remaining balance.
                    } else {
                        investorList.put(request.getInvestor(),ml.getAmountRemaining());
                        request.setAmountAvailable(request.getAmountAvailable()-ml.getAmountRemaining());
                        ml.setAmountRemaining(0);
                        ml.setFullyFunded(true);
//                        System.out.println("Loan number " + ml.getLoanId() + " fulfilled");
                        break;
                    }
                }

            }
            // Set full HashMap of investors & amount of contributions
            ml.setInvestorList(investorList);
            // Only add loans to MatchedLoan object if it was fully funded
            if (ml.isFullyFunded()) {
                matchedLoans.add(ml);
                logger.info("Investors found for loan number " + ml.getLoanId());
            } else {
                logger.warn("Unable to fulfill loan number " + ml.getLoanId());
            }
        }
        // Print all matched loans in pretty JSON
        for (MatchedLoan match : matchedLoans) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(match);
            System.out.println(json);
        }
    }

    private List<InvRequest> qualifyLoans(List<InvRequest> separatedRequests, MatchedLoan ml) {
        List<MatchingRule> matchingRules = new ArrayList<>();
        matchingRules.add(new TermLengthRule());

        for (MatchingRule rules : matchingRules) {
            separatedRequests = rules.applyRules(separatedRequests, ml);
        }
        return separatedRequests;
    }

    public List<MatchedLoan> loanSeparator(String type) {
        List<Loan> loans = DataParser.parseLoans();
        List<MatchedLoan> separatedLoans = new ArrayList<>();
        // Take loans of a given type, and map them to an MatchedLoan object
        for (Loan loan : loans) {
            if (loan.getProduct().equalsIgnoreCase(type)) {
                MatchedLoan ml = new MatchedLoan();
                ml.setLoanId(loan.getLoanId());
                ml.setType(loan.getProduct());
                ml.setTerm(loan.getTerm());
                ml.setFullAmount(loan.getLoanAmount());
                ml.setCompletedDate(loan.getFormattedDate());
                ml.setAmountRemaining(loan.getLoanAmount());
                // and to list
                separatedLoans.add(ml);
            }
        }
        // sort loans by completed date
        separatedLoans.sort(Comparator.comparing(MatchedLoan::getCompletedDate));

        return separatedLoans;
    }

    public List<InvRequest> investmentSeparator(String type) {
        List<InvRequest> requests = DataParser.parseInvestmentRequests();
        List<InvRequest> separatedRequests = new ArrayList<>();
        for (InvRequest request : requests) {
            if (request.getProductType().equalsIgnoreCase(type)) {
                separatedRequests.add(request);
            }
        }
        return separatedRequests;
    }
}
