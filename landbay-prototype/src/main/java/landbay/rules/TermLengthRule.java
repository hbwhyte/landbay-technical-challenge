package landbay.rules;

import landbay.model.InvRequest;
import landbay.model.MatchedLoan;

import java.util.ArrayList;
import java.util.List;

public class TermLengthRule implements MatchingRule {

    public List<InvRequest> applyRules(List<InvRequest> requests, MatchedLoan ml) {
        List<InvRequest> qualifiedInvestments = new ArrayList<>();
        for (InvRequest request : requests) {
            if (ml.getTerm() < request.getTerm()) {
                qualifiedInvestments.add(request);
            }
        }
        return qualifiedInvestments;
    }
}
