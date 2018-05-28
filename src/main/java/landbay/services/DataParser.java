package landbay.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import landbay.model.InvRequest;
import landbay.model.Loan;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;

/**
 * Parses date from CSVs and prepares it for the matching services
 */
public class DataParser {

    private final Logger logger = LoggerFactory.getLogger(LoanMatcher.class);

    /**
     * Reads CSV from file from disk using OpenCSV and maps into
     * a List of InvRequest objects
     *
     * @return List of unprepared InvRequest objects
     */
    public List<InvRequest> parseInvestmentRequests() {
        String investmentRequestPath = "src/main/resources/inputs/investmentRequests.csv";
        List<InvRequest> requests = null;

        try (Reader reader = Files.newBufferedReader(Paths.get(investmentRequestPath))) {

            ColumnPositionMappingStrategy mappingStrategy =
                    new ColumnPositionMappingStrategy();
            //Set mappingStrategy type to Employee Type
            mappingStrategy.setType(InvRequest.class);

            CsvToBean<InvRequest> csvToBean = new CsvToBeanBuilder<InvRequest>(reader)
                    .withType(InvRequest.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            requests = csvToBean.parse();
            setAmountAvailable(requests);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return requests;
    }

    /**
     * Reads CSV from file from disk using OpenCSV and maps into
     * a List of Loan objects
     *
     * @return List of unprepared Loan objects
     */
    public List<Loan> parseLoans() {

        String loanPath = "src/main/resources/inputs/loans.csv";
        List<Loan> loans = null;

        try (Reader reader = Files.newBufferedReader(Paths.get(loanPath))) {

            ColumnPositionMappingStrategy mappingStrategy =
                    new ColumnPositionMappingStrategy();
            //Set mappingStrategy type to Employee Type
            mappingStrategy.setType(Loan.class);

            CsvToBean<Loan> csvToBean = new CsvToBeanBuilder<Loan>(reader)
                    .withType(Loan.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            loans = csvToBean.parse();
        } catch (IOException e) {
            logger.error("IOException: Unable to read file." + e.getStackTrace());
        }
        try {
            convertDate(loans);
        } catch (NullPointerException e) {
            logger.error("NullPointerException: Loans object was empty." + e.getStackTrace());
        }

        return loans;
    }

    /**
     * Prints Loans into formatted JSON
     *
     * @param loans List of Loan objects
     */
    public void printLoans(List<Loan> loans) {
        for (Loan loan : loans) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(loan);
            System.out.println(json);
        }
    }

    /**
     * Prints Investment Requests into formatted JSON
     *
     * @param requests List of InvRequest objects
     */
    public void printRequests(List<InvRequest> requests) {
        for (InvRequest request : requests) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(request);
            System.out.println(json);
        }
    }

    /**
     * Takes the dd/MM/yyyy String date from and parses into java.util.Instant
     * for proper sorting & ordering.
     *
     * Instant could be seen as a bit of overkill, but it is useful to ensure that
     * the order you process loans is still correct & fair regardless of time
     * zones.
     *
     * @param rawLoans List of unprepared Loan objects
     * @return List of Loan objects
     */
    public List<Loan> convertDate(List<Loan> rawLoans) {
        for (Loan loan : rawLoans) {
            // Date format of completedDate from CSV
            String pattern = "dd/MM/yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

            // Change string to formatted Date object
            try {
                Instant convertedDate = simpleDateFormat.parse(loan.getCompletedDate()).toInstant();
                loan.setConvertedDate(convertedDate);
            } catch (ParseException e) {
                logger.error("ParseException: Unable to parse date" + e.getMessage());
            }
        }
        return rawLoans;
    }

    /**
     * Sets initial amount available equal to investment full amount, since
     * amountAvailable is not a given parameter from the CSV
     *
     * @param rawRequests List of unprepared InvRequest objects
     */
    private void setAmountAvailable(List<InvRequest> rawRequests) {
        for (InvRequest request : rawRequests) {
            request.setAmountAvailable(request.getInvestmentAmount());
        }
    }
}