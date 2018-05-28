package landbay.services;

import landbay.model.Loan;
import landbay.model.MatchedLoan;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;


public class LoanMatcherTest {

    @InjectMocks
    @Spy
    LoanMatcher loanMatcher;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void loanPreparer() {
        Loan mockLoan = new Loan();
        mockLoan.setLoanId(1);
        mockLoan.setLoanAmount(100_000);
        mockLoan.setProduct("FIXED");
        mockLoan.setCompletedDate("01/01/2018");
        mockLoan.setFormattedDate(new Date(1514739600000L));
        mockLoan.setTerm(12);

        List<Loan> mockLoanList = new ArrayList<>();
        mockLoanList.add(mockLoan);

        MatchedLoan mockMatched = new MatchedLoan();
        mockMatched.setLoanId(1);
        mockMatched.setFullAmount(100_000);
        mockMatched.setAmountRemaining(100_000);
        mockMatched.setType("FIXED");
        mockMatched.setCompletedDate(new Date(1514739600000L));
        mockMatched.setTerm(12);
        mockMatched.setFullyFunded(false);

        List<MatchedLoan> test = loanMatcher.loanPreparer(mockLoanList);
        assertEquals(mockMatched.getLoanId(), test.get(0).getLoanId());
        assertEquals(mockMatched.getFullAmount(), test.get(0).getFullAmount());
        assertEquals(mockMatched.getFullAmount(), test.get(0).getAmountRemaining());
        assertEquals(mockMatched.getType(), test.get(0).getType());
        assertEquals(mockMatched.getCompletedDate(), test.get(0).getCompletedDate());
        assertEquals(mockMatched.getInvestorList(), test.get(0).getInvestorList());
    }
}