package landbay.rules;

import landbay.model.InvRequest;
import landbay.model.MatchedLoan;

import java.util.List;

/**
 * Interface for creating matching rules to apply when deciding if an Investment
 * Request can be matched to a potential loan.
 */
public interface MatchingRule {

    List<InvRequest> applyRules(List<InvRequest> invRequests, MatchedLoan ml);

}
