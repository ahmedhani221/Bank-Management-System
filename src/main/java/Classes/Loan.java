package Classes;
import java.io.*;
import java.util.*;
import java.time.*;
import static com.oop.projectWithGUI.CSVController.loans;

public class Loan implements LoanManagement{
    private final int loanID;
    private final String clientID;
    private final String borrowerName; // Client
    private final String loanType; // car , house , project.
    private final double loanAmount;
    private final double interestRate; // Annual interest rate.
    private String loanStatus; // Active or Paid off.
    private int loanTerm; // Loan term in months only.
    private LocalDate expiryDate;
    private double monthlyInstallment;
    private double remainingBalance;
    private int remainingMonths;

    // Default Constructor.
    public Loan(String borrowerName, String loanType, double loanAmount, double interestRate,
                int loanTerm, String clientID) {
        this.loanID = getMaxLoanID() + 1;
        this.borrowerName = borrowerName;
        this.loanType = loanType;
        this.loanAmount = loanAmount;
        this.interestRate = interestRate / 100;
        this.loanStatus = "Active"; // Default status
        this.loanTerm = loanTerm;
        this.expiryDate = calExpiryDate();
        this.clientID = clientID;
        this.remainingMonths = loanTerm;
        calcMonthlyInstallment();
    }

    // Constructor for Read.
    public Loan(int loanID, String borrowerName, String loanType, double loanAmount, double interestRate,
                String loanStatus, int loanTerm, LocalDate expiryDate, double monthlyInstallment,
                double remainingBalance, String clientID, int remainingMonths) {
        this.loanID = loanID;
        this.borrowerName = borrowerName;
        this.loanType = loanType;
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.loanStatus = loanStatus;
        this.loanTerm = loanTerm;
        this.expiryDate = expiryDate;
        this.monthlyInstallment = monthlyInstallment;
        this.remainingBalance = remainingBalance;
        this.remainingMonths = remainingMonths;
        this.clientID = clientID;
    }

    // Getters and Setters.
    public int getLoanID() {
        return loanID;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public String getClientID() {
        return clientID;
    }

    public String getLoanType() {
        return loanType;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public String getLoanStatus() {
        return loanStatus;
    }

    public int getLoanTerm() {
        return loanTerm;
    }

    public double getMonthlyInstallment() {
        return monthlyInstallment;
    }

    public double getRemainingBalance() {
        return remainingBalance;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public int getRemainingMonths() {
        return remainingMonths;
    }

    public void setRemainingMonths(int months) {
        this.remainingMonths += months;
    }

    public void setLoanStatus(String loanStatus) {
        this.loanStatus = loanStatus;
    }

    public void setLoanTerm(int loanTerm) {
        this.loanTerm = loanTerm;
    }

    public void setMonthlyInstallment(double monthlyInstallment) {
        this.monthlyInstallment = monthlyInstallment;
    }

    public void setRemainingBalance(double remainingBalance) {
        this.remainingBalance = remainingBalance;
    }

    // Get the maximum loan ID from the file
    private static int getMaxLoanID() {
        if (loans.isEmpty()) {
            return 0; // No loans exist, start from 1
        }
        return loans.stream()
                .mapToInt(Loan::getLoanID) // Extract loan IDs
                .max() // Find the maximum
                .orElse(0); // Default to 0 if no IDs
    }

    // Calculate expiry date
    private LocalDate calExpiryDate() {
        LocalDate today = LocalDate.now();
        return today.plusMonths(loanTerm);
    }

    // Get Amount Paid Per Month and Total Amount that Should be Paid.
    private void calcMonthlyInstallment() {
        if (getLoanStatus().equals("Active")) {
            double monthlyInterestRate = getInterestRate() / 12;
            this.monthlyInstallment = getLoanAmount() * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, getLoanTerm()) / (Math.pow(1 + monthlyInterestRate, getLoanTerm()) - 1);
            this.remainingBalance = getMonthlyInstallment() * getLoanTerm();
        } else {
            setMonthlyInstallment(0.0); // Paid-off loans
            setRemainingBalance(0.0); // Paid-off loans
        }
    }

    // Client Paid The Amount of The Month.
    public void payMonthlyInstallment() {
        if (getRemainingBalance() <= 0) {
            setRemainingBalance(0.0);
            setLoanStatus("Paid Off");
            System.out.println("Loan has been fully paid off.");
        } else {
            remainingBalance -= getMonthlyInstallment();
            remainingMonths -= 1;
            LoanPayment payment = new LoanPayment(getClientID(), getMonthlyInstallment(), getLoanStatus(), getRemainingMonths());
            LoanPayment.addToHistory(payment);
            if (getRemainingBalance() <= 0) {
                setRemainingBalance(0.0);
                setLoanStatus("Paid Off");
                System.out.println("Payment successful. Loan has been fully paid off.");
            } else {
                System.out.printf("Payment successful. Remaining balance: %.2f%n", getRemainingBalance());
            }
        }
    }

    // Display All Details of The Loan.
    public void getLoanDetails() {
        System.out.println("Loan ID: " + getLoanID());
        System.out.println("Borrower: " + getBorrowerName());
        System.out.println("Loan Type: " + getLoanType());
        System.out.println("Loan Amount: " + getLoanAmount());
        System.out.println("Interest Rate: " + (getInterestRate() * 100) + "%");
        System.out.println("Loan Status: " + getLoanStatus());
        System.out.println("Loan Term: " + getLoanTerm() + " months");
        System.out.println("Expiry Date: " + getExpiryDate());
        System.out.printf("Monthly Installment: %.2f%n" , getMonthlyInstallment());
        System.out.printf("Remaining Balance: %.2f%n", getRemainingBalance());
    }

    // To Extend Loan Term but There is Fees 5% of Loan Amount if Period Exceeds 6 Months.
    public void extendLoanTerm(int additionalMonths) {
        double fees =  getLoanAmount() * 0.05;
        if (additionalMonths <= 0) {
            System.out.println("Additional months must be greater than zero.");
            return;
        }
        if (additionalMonths <= 6) {
            loanTerm += additionalMonths;
            expiryDate = expiryDate.plusMonths(additionalMonths);
            monthlyInstallment = getRemainingBalance() / getLoanTerm();
            System.out.printf("Loan term extended. Monthly Installment: %.2f and Remaining Balance: %.2f" , getMonthlyInstallment() , getRemainingBalance());
        } else {
            loanTerm += additionalMonths;
            expiryDate = expiryDate.plusMonths(additionalMonths);
            remainingBalance = getRemainingBalance() + fees;
            monthlyInstallment = getRemainingBalance() / getLoanTerm();
            System.out.printf("Loan term extended. Monthly Installment: %.2f and Remaining Balance: %.2f" , getMonthlyInstallment() , getRemainingBalance());
        }
    }

    // Save Loans to The File (.csv).
    public static void saveLoansToCSV(List<Loan> loans, String fileName) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            // Write CSV header
            writer.write("Loan ID,Borrower,Loan Type,Loan Amount,Interest Rate,Loan Status,Loan Term," +
                    "Expiry Date,Monthly Installment,Remaining Balance,Client ID,Remaining Months\n");

            // Write credit card details
            for (Loan loan : loans) {
                writer.write(String.format("%d,%s,%s,%.2f,%.2f,%s,%d,%s,%.2f,%.2f,%s,%d\n",
                        loan.getLoanID(),
                        loan.getBorrowerName(),
                        loan.getLoanType(),
                        loan.getLoanAmount(),
                        loan.getInterestRate() * 100,
                        loan.getLoanStatus(),
                        loan.getLoanTerm(),
                        loan.getExpiryDate(),
                        loan.getMonthlyInstallment(),
                        loan.getRemainingBalance(),
                        loan.getClientID(),
                        loan.getRemainingMonths()
                ));
            }
            System.out.println("All loans saved successfully");
        } catch (IOException e) {
            System.err.println("An error occurred while saving to the file");
        }
    }

    // Read Loans from The File (.csv).
    public static ArrayList<Loan> readLoansFromCSV(String fileName) {
        ArrayList<Loan> loans = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            // Take One Record.
            String line;
            // Skip the Header.
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                // Convert CSV fields into appropriate data types.
                int loanID = Integer.parseInt(fields[0]);
                String borrowerName = fields[1];
                String loanType = fields[2];
                double loanAmount = Double.parseDouble(fields[3]);
                double interestRate = Double.parseDouble(fields[4]) / 100;
                String loanStatus = fields[5];
                int loanTerm = Integer.parseInt(fields[6]);
                LocalDate expiryDate = LocalDate.parse(fields[7]);
                double monthlyInstallment = Double.parseDouble(fields[8]);
                double remainingBalance = Double.parseDouble(fields[9]);
                String clientID = fields[10];
                int remainingMonths = Integer.parseInt(fields[11]);

                Loan loan = new Loan(loanID, borrowerName, loanType, loanAmount, interestRate, loanStatus,
                        loanTerm, expiryDate, monthlyInstallment, remainingBalance, clientID, remainingMonths);
                loans.add(loan);
            }
            System.out.println("All Loans Read Successfully");
        } catch (IOException e) {
            System.err.println("An error occurred while reading to the file");
        }
        return loans;
    }

    // Find Loan by Status or Borrower Name.
    public static Loan findLoans(ArrayList<Loan> loans, String clientID) {

        if (clientID.isEmpty()) {
            return null;
        }

        for (Loan loan : loans) {
            if (loan.getClientID().equalsIgnoreCase(clientID)) {
                return loan;
            }
        }
        return null;
    }
}
