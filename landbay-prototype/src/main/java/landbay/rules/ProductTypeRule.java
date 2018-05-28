package landbay.rules;

import landbay.model.InvRequest;
import landbay.model.MatchedLoan;

import java.util.ArrayList;
import java.util.List;

/**
 * Matching Rule ensuring that the request and loan product type is the same
 */
public class ProductTypeRule implements MatchingRule {

    public List<InvRequest> applyRules(List<InvRequest> requests, MatchedLoan ml) {
        List<InvRequest> qualifiedInvestments = new ArrayList<>();
        for (InvRequest request : requests) {
            // if loan type is the same as than request type, add to list
            if (request.getProductType().equalsIgnoreCase(ml.getType())) {
                qualifiedInvestments.add(request);
            }
        }
        return qualifiedInvestments;
    }
}
