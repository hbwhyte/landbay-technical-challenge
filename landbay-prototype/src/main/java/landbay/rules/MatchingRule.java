package landbay.rules;

import landbay.model.InvRequest;
import landbay.model.MatchedLoan;

import java.util.List;

public interface MatchingRule {

    List<InvRequest> applyRules(List<InvRequest> invRequests, MatchedLoan ml);

}
