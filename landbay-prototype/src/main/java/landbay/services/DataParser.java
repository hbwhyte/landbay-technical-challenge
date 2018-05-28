package landbay.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import landbay.model.InvRequest;
import landbay.model.Loan;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DataParser {

    private static final Logger logger = LoggerFactory.getLogger(LoanMatcher.class);

    public static List<InvRequest> parseInvestmentRequests() {
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
//
//            for (InvRequest request : requests) {
//                System.out.println("Investor : " + request.getInvestor());
//                System.out.println("MatchedLoan Amount : " + request.getInvestmentAmount());
//                System.out.println("Product Type : " + request.getProductType());
//                System.out.println("Term : " + request.getTerm());
//                System.out.println("Amount available to invest : " + request.getAmountAvailable());
//                System.out.println("==========================");
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return requests;
    }

    public static List<Loan> parseLoans() {

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
            formatDate(loans);
        } catch (NullPointerException e) {
            logger.error("NullPointerException: Loans object was empty." + e.getStackTrace());
        }

        return loans;
    }

    public static void printLoans(List<Loan> loans) {
        for (Loan loan : loans) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(loan);
            System.out.println(json);
        }
    }

    public static void printRequests(List<InvRequest> requests) {
        for (InvRequest request : requests) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(request);
            System.out.println(json);
        }
    }

    /**
     * For each element of the List<Loan>, add the formatted date
     */
    private static void formatDate(List<Loan> rawLoans) {
        for (Loan loan : rawLoans) {
            // Date format of completedDate
            String pattern = "dd/MM/yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            // Change string to formatted Date object
            try {
                Date formattedDate = simpleDateFormat.parse(loan.getCompletedDate());
                loan.setFormattedDate(formattedDate);
            } catch (ParseException e) {
                logger.error("ParseException: Unable to parse date" + e.getMessage());
            }
        }
    }

    private static void setAmountAvailable(List<InvRequest> rawRequests) {
        for (InvRequest request : rawRequests) {
            request.setAmountAvailable(request.getInvestmentAmount());
        }
    }
}