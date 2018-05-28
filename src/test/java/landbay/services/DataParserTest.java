package landbay.services;

import landbay.model.Loan;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.time.Instant;
import java.util.ArrayList;
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
    public void convertDate() {
        // Create mock Loan object
        Loan mockLoan = new Loan();
        mockLoan.setLoanId(1);
        mockLoan.setLoanAmount(100_000);
        mockLoan.setProduct("FIXED");
        // Completed date in the format from CSV: String dd/MM/yyyy
        mockLoan.setCompletedDate("01/01/2018");
        mockLoan.setTerm(12);

        // Add mock object to List<Loan>
        List<Loan> mockLoanList = new ArrayList<>();
        mockLoanList.add(mockLoan);

        // expectedDate in format of 01/01/2018 in time in milli (long)
        Instant expectedDate = Instant.ofEpochMilli(1514739600000L);

        // Run method
        List<Loan> test = dataParser.convertDate(mockLoanList);
        // Check that conversion to Date format works
        assertEquals(expectedDate, test.get(0).getConvertedDate());
    }
}