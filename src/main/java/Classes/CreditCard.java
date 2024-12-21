package Classes;
import java.time.*;
import java.io.*;
import java.util.*;

public class CreditCard {
    private final String cardNumber;
    private final String accountNumber;
    private final String cardHolderName;
    private final String cardType; // visa or mastercard.
    private final String cardCategory; // Business, Standard, Premium.
    private final int password;
    private final int CVV; // "123"
    private LocalDate expiryDate;
    private double creditLimit; // Limit for One operation and depend on cardCategory.
    private String status; // Active or Blocked.


    // Default Constructor.
    public CreditCard(String cardNumber, String cardHolderName, String cardType, String cardCategory, int password,
                      int CVV, String accountNumber) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.cardType = cardType;
        this.cardCategory = cardCategory;
        this.password = password;
        this.CVV = CVV;
        this.creditLimit = setCreditLimit();
        this.expiryDate = calExpiryDate(cardCategory);
        this.status = "Active"; // Default status
        this.accountNumber = accountNumber;
    }

    // Read Constructor.
    public CreditCard(String cardNumber, String cardHolderName, String cardType, String cardCategory, int password,
                      int CVV, LocalDate expiryDate, double creditLimit, String status, String accountNumber) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.cardType = cardType;
        this.cardCategory = cardCategory;
        this.password = password;
        this.CVV = CVV;
        this.expiryDate = expiryDate;
        this.creditLimit = creditLimit;
        this.status = status;
        this.accountNumber = accountNumber;
    }

    // Getters and Setters.
    public String getCardNumber() {
        return cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public String getCardType() {
        return cardType;
    }

    public String getCardCategory() {
        return cardCategory;
    }

    public int getPassword() {
        return password;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public String getStatus() {
        return status;
    }

    public int getCVV() {
        return CVV;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Set Credit limit based on Card Category.
    public double setCreditLimit() {
        return switch (cardCategory.toLowerCase()) {
            case "standard" -> this.creditLimit = 50000;
            case "premium" -> this.creditLimit = 250000;
            case "business" -> this.creditLimit = 20000;
            default -> throw new IllegalStateException("Unexpected value: " + cardCategory.toLowerCase());
        };
    }

    // Calculate expiry date based on card category.
    private LocalDate calExpiryDate(String cardCategory) {
        LocalDate today = LocalDate.now();
        return switch (cardCategory.toLowerCase()) {
            case "standard" -> today.plusYears(3);
            case "premium" -> today.plusYears(5);
            case "business" -> today.plusYears(2);
            default -> throw new IllegalArgumentException("Invalid card category: " + cardCategory);
        };
    }

    // Display All Details of The Credit Card.
    public void getCreditDetails() {
        System.out.println("Card Number: " + getCardNumber());
        System.out.println("Card Holder Name: " + getCardHolderName());
        System.out.println("Card Type: " + getCardType());
        System.out.println("Card Category: " + getCardCategory());
        System.out.println("Expiry Date: " + getExpiryDate());
        System.out.println("Credit Limit: " + getCreditLimit());
        System.out.println("Status: " + getStatus());
    }
    
    // Check if Card is Expired.
    public boolean isCardExpired() {
        return LocalDate.now().isAfter(expiryDate);
    }

    // Request Disable credit
    public void disableCreditCard() {
        setStatus("Blocked");
        System.out.println("The Card has been Disabled");
    }

    // Block Credit.
    public void blockCredit() {
        if (isCardExpired()) {
            setStatus("Blocked");
            System.out.println("The card has been blocked.");
        } else {
            System.out.println("The card can not be blocked as it still Active.");
        }
    }

    // Activate Credit.
    public void activateCreditCard() {
        setStatus("Active");
        System.out.println("The card has been Activated.");
    }

    // Renew Card if Expired.
    public boolean renewCard() {
        if (isCardExpired()) {
            this.expiryDate = calExpiryDate(getCardCategory());
            System.out.println("The card has been renewed. New expiry date: " + getExpiryDate());
            return true;
        } else {
            System.out.println("The card is not expired yet. No need to renew.");
            return false;
        }
    }

    public boolean cardVerification(int password, int cvv) {
        return this.password != password || this.CVV != cvv;
    }

    // Save Credit Cards to The File (.csv).
    public static void saveCreditCards(ArrayList<CreditCard> creditCards, String fileName) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            // Write CSV header
            writer.write("Card Number,Card Holder Name,Card Type,Card Category,Password,CVV,Expiry Date,Credit Limit,Status, Account Number\n");

            // Write credit card details
            for (CreditCard card : creditCards) {
                writer.write(String.format("%s,%s,%s,%s,%d,%d,%s,%.2f,%s,%s\n",
                        card.getCardNumber(),
                        card.getCardHolderName(),
                        card.getCardType(),
                        card.getCardCategory(),
                        card.getPassword(),
                        card.getCVV(),
                        card.getExpiryDate(),
                        card.getCreditLimit(),
                        card.getStatus(),
                        card.getAccountNumber()
                ));
            }
            System.out.println("All credit cards saved successfully.");
        } catch (IOException e) {
            System.err.println("An error occurred while saving to the file.");
        }
    }

    // Read Credit Cards from The File (.csv).
    public static ArrayList<CreditCard> readCreditCards(String fileName) {
        ArrayList<CreditCard> creditCards = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            // Take One Record.
            String line;
            // Skip the Header.

            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                // Convert CSV fields into appropriate data types
                String cardNumber = fields[0];
                String cardHolderName = fields[1];
                String cardType = fields[2];
                String cardCategory = fields[3];
                int password = Integer.parseInt(fields[4]);
                int CVV = Integer.parseInt(fields[5]);
                LocalDate expiryDate = LocalDate.parse(fields[6]); // Assuming ISO format yyyy-MM-dd
                double creditLimit = Double.parseDouble(fields[7]);
                String status = fields[8];
                String accountNumber = fields[9];

                CreditCard card = new CreditCard(cardNumber, cardHolderName, cardType, cardCategory, password, CVV, expiryDate, creditLimit, status, accountNumber);
                creditCards.add(card);
            }
            System.out.println("All credit cards read successfully.");
        } catch (IOException e) {
            System.err.println("An error occurred while reading from the file.");
        }
        return creditCards;
    }

    // Find Credit Cards by Account Number.
    public static CreditCard findCreditCards(ArrayList<CreditCard> creditCards, String accountNumber) {
        for (CreditCard card : creditCards) {
            if (card.getAccountNumber().equalsIgnoreCase(accountNumber)) {
                return card;
            }
        }
        return null;
    }
}