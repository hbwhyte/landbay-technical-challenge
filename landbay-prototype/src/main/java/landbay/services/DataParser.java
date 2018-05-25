package landbay.services;

import landbay.model.Loan;
import landbay.model.InvestmentRequest;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DataParser {

    public static void main(String[] args) throws IOException, ParseException {
        parseInvestmentRequests();
        parseLoans();
    }

    public static List<InvestmentRequest> parseInvestmentRequests() throws IOException {
        String investmentRequestPath = "src/main/resources/inputs/investmentRequests.csv";
        List<InvestmentRequest> requests;

        try (Reader reader = Files.newBufferedReader(Paths.get(investmentRequestPath))) {

            ColumnPositionMappingStrategy mappingStrategy =
                    new ColumnPositionMappingStrategy();
            //Set mappingStrategy type to Employee Type
            mappingStrategy.setType(InvestmentRequest.class);

            CsvToBean<InvestmentRequest> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(InvestmentRequest.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            requests = csvToBean.parse();
            setAmountAvailble(requests);

            for (InvestmentRequest request : requests) {
                System.out.println("Investor : " + request.getInvestor());
                System.out.println("Investment Amount : " + request.getInvestmentAmount());
                System.out.println("Product Type : " + request.getProductType());
                System.out.println("Term : " + request.getTerm());
                System.out.println("Amount available to invest : " + request.getAmountAvailable());
                System.out.println("==========================");
            }
        }
        return requests;
    }

    public static List<Loan> parseLoans() throws IOException, ParseException {

        String loanPath = "src/main/resources/inputs/loans.csv";
        List<Loan> loans;

        try (Reader reader = Files.newBufferedReader(Paths.get(loanPath))) {

            ColumnPositionMappingStrategy mappingStrategy =
                    new ColumnPositionMappingStrategy();
            //Set mappingStrategy type to Employee Type
            mappingStrategy.setType(Loan.class);

            CsvToBean<Loan> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Loan.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            loans = csvToBean.parse();

            formatDate(loans);

            for (Loan loan : loans) {
                System.out.println("Loan Id : " + loan.getLoanId());
                System.out.println("Loan Amount : " + loan.getLoanAmount());
                System.out.println("Product Type : " + loan.getProduct());
                System.out.println("Term : " + loan.getTerm());
                System.out.println("Completed Date : " + loan.getCompletedDate());
                System.out.println("Formatted Date : " + loan.getFormattedDate());
                System.out.println("==========================");
            }
        }

        return loans;
    }

    /**
     * For each element of the List<Loan>, add the formatted date
     */
    public static void formatDate(List<Loan> rawLoans) throws ParseException {
        for (Loan loan : rawLoans) {
            // Date format of completedDate
            String pattern = "dd/MM/yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            //
            Date formattedDate = simpleDateFormat.parse(loan.getCompletedDate());
            loan.setFormattedDate(formattedDate);
        }
    }

    public static void setAmountAvailble(List<InvestmentRequest> rawRequests) {
        for (InvestmentRequest request : rawRequests) {
            request.setAmountAvailable(request.getInvestmentAmount());
        }
    }
}