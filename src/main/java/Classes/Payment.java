package Classes;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import static com.oop.projectWithGUI.CSVController.paymentsHistory;

public class Payment {
    private final String accountNumber;
    private final String cardNumber;
    private final LocalDate date;
    private final double amount;

    // Default constructor
    public Payment(String accountNumber, String cardNumber, double amount) {
        this.accountNumber = accountNumber;
        this.cardNumber = cardNumber;
        this.date = LocalDate.now();
        this.amount = amount;
    }

    // Read constructor
    public Payment(String accountNumber, String cardNumber, LocalDate date, double amount) {
        this.accountNumber = accountNumber;
        this.cardNumber = cardNumber;
        this.date = date;
        this.amount = amount;
    }

    // Getters and Setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    // Add payment to history
    public static void addToHistory(Payment payment) {
        paymentsHistory.add(payment);
    }

    // Save payments to a file (.csv)
    public static void savePayments(ArrayList<Payment> payments, String fileName) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            // Write CSV header
            writer.write("Account Number,Card Number,Date,Amount\n");

            // Write credit card details
            for (Payment payment : payments) {
                writer.write(String.format("%s,%s,%s,%.2f\n",
                        payment.getAccountNumber(),
                        payment.getCardNumber(),
                        payment.getDate(),
                        payment.getAmount()
                ));
            }
            System.out.println("All Payments saved successfully.");
        } catch (IOException e) {
            System.err.println("An error occurred while saving to the file.");
        }
    }

    // Read payments from a file (.csv)
    public static ArrayList<Payment> readPayments(String fileName) {
        ArrayList<Payment> Payments = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            // Take One Record.
            String line;
            // Skip the Header.

            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                // Convert CSV fields into appropriate data types
                String accountNumber = fields[0];
                String cardNumber = fields[1];
                LocalDate date = LocalDate.parse(fields[2]);
                double amount = Double.parseDouble(fields[3]);

                Payment payment = new Payment(accountNumber, cardNumber, date, amount);
                Payments.add(payment);
            }
            System.out.println("All Payments read successfully.");
        } catch (IOException e) {
            System.err.println("An error occurred while reading from the file.");
        }
        return Payments;
    }

    // Find payment by account number
    public static ArrayList<Payment> findPayments(ArrayList<Payment> payments, String accountNumber) {
        ArrayList<Payment> foundPayments = new ArrayList<>();

        for (Payment payment : payments) {
            if (payment.getAccountNumber().equals(accountNumber)) {
                foundPayments.add(payment);
            }
        }
        return foundPayments;
    }
}
