package landbay.rules;

import landbay.model.InvRequest;
import landbay.model.MatchedLoan;

import java.util.ArrayList;
import java.util.List;

/**
 * Matching rule ensuring that the investment request term is
 * longer than the loan request.
 */
public class TermLengthRule implements MatchingRule {

    public List<InvRequest> applyRules(List<InvRequest> requests, MatchedLoan ml) {
        List<InvRequest> qualifiedInvestments = new ArrayList<>();
        for (InvRequest request : requests) {
            // if loan term is smaller than request term, add to list
            if (ml.getTerm() < request.getTerm()) {
                qualifiedInvestments.add(request);
            }
        }
        return qualifiedInvestments;
    }
}
