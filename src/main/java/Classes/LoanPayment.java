package Classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import static com.oop.projectWithGUI.CSVController.loanHistory;

public class LoanPayment {
    private final String clientID;
    private final double amount;
    private final String status;
    private final LocalDate date;
    private final int months;

    // Default Constructor
    public LoanPayment(String clientID, double amount, String status, int months) {
        this.clientID = clientID;
        this.amount = amount;
        this.status = status;
        this.date = LocalDate.now();
        this.months = months;
    }

    // Read Constructor
    public LoanPayment(String clientID, double amount, String status, LocalDate date, int months) {
        this.clientID = clientID;
        this.amount = amount;
        this.status = status;
        this.date = date;
        this.months = months;
    }

    // Getters
    public String getClientID() {
        return clientID;
    }

    public double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getMonths() {
        return months;
    }

    // Add payment to history
    public static void addToHistory(LoanPayment payment) {
        loanHistory.add(payment);
    }

    // Save payments to a file (.csv)
    public static void savePayments(ArrayList<LoanPayment> payments, String fileName) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            // Write CSV header
            writer.write("Client ID,Amount,Status,Date,Months\n");

            // Write credit card details
            for (LoanPayment payment : payments) {
                writer.write(String.format("%s,%.2f,%s,%s,%d\n",
                        payment.getClientID(),
                        payment.getAmount(),
                        payment.getStatus(),
                        payment.getDate(),
                        payment.getMonths()
                ));
            }
            System.out.println("All Loan Payments saved successfully.");
        } catch (IOException e) {
            System.err.println("An error occurred while saving to the file.");
        }
    }

    // Read payments from a file (.csv)
    public static ArrayList<LoanPayment> readPayments(String fileName) {
        ArrayList<LoanPayment> Payments = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            // Take One Record.
            String line;
            // Skip the Header.

            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                // Convert CSV fields into appropriate data types
                String clientID = fields[0];
                double amount = Double.parseDouble(fields[1]);
                String status = fields[2];
                LocalDate date = LocalDate.parse(fields[3]);
                int months = Integer.parseInt(fields[4]);

                LoanPayment payment = new LoanPayment(clientID, amount, status, date, months);
                Payments.add(payment);
            }
            System.out.println("All Loan Payments read successfully.");
        } catch (IOException e) {
            System.err.println("An error occurred while reading from the file.");
        }
        return Payments;
    }

    // Find payment by account number
    public static ArrayList<LoanPayment> findPayments(ArrayList<LoanPayment> payments, String clientID) {
        ArrayList<LoanPayment> foundPayments = new ArrayList<>();

        for (LoanPayment payment : payments) {
            if (payment.getClientID().equalsIgnoreCase(clientID)) {
                foundPayments.add(payment);
            }
        }
        return foundPayments;
    }
}
