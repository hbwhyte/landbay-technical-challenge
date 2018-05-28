package landbay.services;

import landbay.model.Loan;
import landbay.model.MatchedLoan;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class DataParserTest {

    @InjectMocks
    @Spy
    DataParser dataParser;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void formatDate() {
        Loan mockLoan = new Loan();
        mockLoan.setLoanId(1);
        mockLoan.setLoanAmount(100_000);
        mockLoan.setProduct("FIXED");
        mockLoan.setCompletedDate("01/01/2018");
        mockLoan.setTerm(12);

        List<Loan> mockLoanList = new ArrayList<>();
        mockLoanList.add(mockLoan);

        Date expectedDate = new Date(1514739600000L);

        List<Loan> test = dataParser.formatDate(mockLoanList);
        assertEquals(expectedDate, test.get(0).getFormattedDate());
    }
}