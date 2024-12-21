package Classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Transaction {
    private final LocalDateTime Date;
    private final int TransactionID;
    private final String AccountFrom;
    private final String AccountTo;
    private final String TransactionType;
    private final double Amount;
    private final String UserID;
    public static ArrayList<Transaction> TransactionHistory = new ArrayList<>();

    public Transaction(int transactionID, String accountFrom, String accountTo, String transactionType, double amount, String ID) {
        this.AccountFrom = accountFrom;
        this.Date = LocalDateTime.now();
        this.TransactionID = transactionID;
        this.AccountTo = accountTo;
        this.TransactionType = transactionType;
        this.Amount = amount;
        this.UserID = ID;
    }

    private Transaction(int TransactionID, String AccountFrom, String AccountTo, String TransactionType,
                        double Amount, LocalDateTime Date, String ID)
    {
        this.AccountFrom = AccountFrom;
        this.Date = Date;
        this.TransactionID = TransactionID;
        this.AccountTo = AccountTo;
        this.TransactionType = TransactionType;
        this.Amount = Amount;
        this.UserID = ID;
    }

    public LocalDateTime getDate() {
        return Date;
    }

    public static ArrayList<Transaction> getTransactionHistory() {
        return TransactionHistory;
    }

    public String getUserID() {
        return UserID;
    }

    public double getAmount() {
        return Amount;
    }

    public String getTransactionType() {
        return TransactionType;
    }

    public String getAccountTo() {
        return AccountTo;
    }

    public String getAccountFrom() {
        return AccountFrom;
    }

    public int getTransactionID() {
        return TransactionID;
    }

    public void AddToHistory(){
        TransactionHistory.add(this);
    }

    public static void AddToHistory(Transaction transaction){
        TransactionHistory.add(transaction);
    }

    public void TransactionDetails(){
        System.out.println("______________________________");
        System.out.println("    Transaction Details:");
        System.out.println("______________________________");
        System.out.printf("TransactionID: %d\n", this.TransactionID);
        System.out.printf("Transaction Type: %s\n", this.TransactionType);
        if(this.TransactionType.equals("Transfer")){
            System.out.printf("Account From: %s\n", this.AccountFrom);
            System.out.printf("Account To: %s\n", this.AccountTo);
        }
        else{
            System.out.printf("Account: %s\n", this.AccountFrom);
        }
        System.out.printf("Amount: %.2f\n", this.Amount);
        System.out.printf("ID: %s\n", this.UserID);
        System.out.println("______________________________");
    }

    private static Transaction convertLineToTransaction(String line) {
        String[] field = line.split(",");
        return new Transaction(Integer.parseInt(field[0]), field[1], field[2],
                field[4], Double.parseDouble(field[3]), LocalDateTime.parse(field[5]), field[6]);
    }

    public static ArrayList<Transaction> readTransactionsFromCSV(String FileName) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FileName))) {
            String line;

            // delete header
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                Transaction t = convertLineToTransaction(line);
                transactions.add(t);
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        return transactions;
    }

    public static void saveTransactionsToCSV(ArrayList<Transaction> transactions, String FileName) {
        try (FileWriter writer = new FileWriter(FileName, false)) {
            writer.write("TransactionID,Account From,Account To,Amount,Transaction Type,Date,ID\n");

            for (Transaction transaction : transactions) {
                writer.write(String.format("%d,%s,%s,%.2f,%s,%s,%s\n",
                        transaction.TransactionID,
                        transaction.AccountFrom,
                        transaction.AccountTo,
                        transaction.Amount,
                        transaction.TransactionType,
                        transaction.Date.toString(),
                        transaction.UserID
                ));
            }

            System.out.println("All Transactions details saved to " + FileName);

        }
        catch (IOException e) {
            System.err.println("An error occurred while saving to the CSV file: " + e.getMessage());
        }
    }

    public static ArrayList<Transaction> findTransaction (ArrayList<Transaction> Transactions, String userID)
    {
        return Transactions.stream()
                .filter(t -> t.UserID.equals(userID))
                .collect(Collectors.toCollection(ArrayList::new));
    }


    public static ArrayList<Transaction> findTransaction(ArrayList<Transaction> transactions, LocalDateTime date)
    {
        return transactions.stream()
                .filter(t -> t.Date != null && date != null && t.Date.toLocalDate().equals(date.toLocalDate()))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public static Transaction findTransaction (ArrayList<Transaction> Transactions, int transactionID)
    {
        for (Transaction t: Transactions)
        {
            if (t.TransactionID == transactionID)
                return t;
        }

        return null;
    }

}