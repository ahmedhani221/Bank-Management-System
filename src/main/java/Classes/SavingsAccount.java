package Classes;
import java.io.*;
import java.util.*;

public class SavingsAccount extends Account{
    private double accountInterestRate;
    private String accountInterestType; // monthly or yearly
    private int maxTransactions;
    private int numberOfTransactions;

    public SavingsAccount(String accountNumber, String ClientID) {
        super(accountNumber, ClientID, "Savings");
        this.accountInterestRate = -1;
        this.accountInterestType = "";
        this.maxTransactions = 5;
        this.numberOfTransactions = 0;
    }

    // Default constructor
    public SavingsAccount(String accountNumber, String ClientID, double accountBalance, double accountInterestRate,
                          String accountInterestType) {
        super(accountNumber, ClientID, "Savings", accountBalance);
        this.accountInterestRate = accountInterestRate / 100;
        this.accountInterestType = accountInterestType;
        this.maxTransactions = 5;
        this.numberOfTransactions = 0;
    }

    // Read constructor
    public SavingsAccount(String accountNumber, String ClientID, String accountType, String accountStatus, double accountBalance,
                          double accountInterestRate, String accountInterestType, int maxTransactions, int numberOfTransactions) {
        super(accountNumber, ClientID, accountType, accountStatus, accountBalance);
        this.accountInterestRate = accountInterestRate;
        this.accountInterestType = accountInterestType;
        this.maxTransactions = maxTransactions;
        this.numberOfTransactions = numberOfTransactions;
    }

    // Getters and Setters
    public double getAccountInterestRate() {
        return accountInterestRate;
    }

    public String getAccountInterestType() {
        return accountInterestType;
    }

    public int getMaxTransactions() {
        return maxTransactions;
    }

    public int getNumberOfTransactions() {
        return numberOfTransactions;
    }

    public void setAccountInterestType(String accountInterestType) {
        this.accountInterestType = accountInterestType;
    }

    public void setNumberOfTransactions(int numberOfTransactions) {
        this.numberOfTransactions = numberOfTransactions;
    }

    public void setMaxTransactions(int maxTransactions) {
        this.maxTransactions = maxTransactions;
    }

    public void setAccountInterestRate(double accountInterestRate) {
        this.accountInterestRate = accountInterestRate;
    }

    // Display account details for clients
    public void getAccountDetails() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder Name: " + ClientID);
        System.out.println("Account Type: " + accountType);
        System.out.println("Account Status: " + accountStatus);
        System.out.println("Balance: " + accountBalance);
        System.out.println("Account Interest Rate: " + (accountInterestRate * 100) + "%");
        System.out.println("Max Transactions: " + maxTransactions);
        System.out.println("Remain Transactions: " + (maxTransactions - numberOfTransactions));
    }

    // Freeze account under certain condition
    public void freezeAccount() {
        if (numberOfTransactions > maxTransactions) {
            accountStatus = "Frozen";
        }
    }

    // Calculate interest based on interest type and display it when needed
    public double calcInterest() {
        double displayedInterest = accountBalance * accountInterestRate;
        return switch (accountInterestType.toLowerCase()) {
            case "monthly" -> displayedInterest / 12;
            case "yearly" -> displayedInterest;
            default -> throw new IllegalArgumentException("Invalid Interest Type: " + accountInterestType);
        };
    }

    // To display balance after adding interest
    public double applyInterest() {
        return accountBalance + calcInterest();
    }

    // Change interest type if client needed
    public void changeInterestType(String type) {
        accountInterestType = type;
    }

    // Change max transactions with fees
    public void changeMaxTransactions() {
        accountBalance -= 500;
        maxTransactions += 5;
    }

    // Deposit Amount to Client's Account.
    public boolean Deposit(double Amount) {
        if (Amount < 0) return false;

        this.accountBalance += Amount;

        Transaction t = new Transaction(tID++, this.accountNumber, "NULL", "Deposit", Amount, this.ClientID);
        t.AddToHistory();

        numberOfTransactions++;
        return true;
    }

    // Withdraw Amount from Client's Account.
    public boolean Withdraw(double Amount) {
        if(Amount < 0 || Amount > this.accountBalance) return false;

        this.accountBalance -= Amount;

        Transaction t = new Transaction(tID++, this.accountNumber, "NULL", "Withdraw", Amount, this.ClientID);
        t.AddToHistory();

        numberOfTransactions++;
        return true;
    }

    public boolean Update(ArrayList<SavingsAccount> accounts)
    {
        for(SavingsAccount s: accounts){
            if(s.getAccountNumber().equals(this.getAccountNumber())){
                s = this;
                return true;
            }
        }

        return false;
    }

    public boolean Delete(ArrayList<SavingsAccount> accounts){
        if(accounts.isEmpty()) return false;

        for (int i = 0; i < accounts.size(); i++){
            if(accounts.get(i).getAccountNumber().equals(this.getAccountNumber())){
                accounts.remove(i);
                return true;
            }
        }

        return false;
    }

    // Save Savings Accounts to The File (.csv).
    public static void saveSavingsAccount(ArrayList<SavingsAccount> savingsAccount, String fileName) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            // Write CSV header
            writer.write("Account Number,Account's ClientID,Account Type,Account Status,Account Balance,Account Interest Rate,Account Interest Type,Max Transactions,Number Of Transactions\n");

            // Write credit card details
            for (SavingsAccount account : savingsAccount) {
                writer.write(String.format("%s,%s,%s,%s,%.2f,%.2f,%s,%d,%d\n",
                        account.accountNumber,
                        account.ClientID,
                        account.accountType,
                        account.accountStatus,
                        account.accountBalance,
                        account.accountInterestRate,
                        account.accountInterestType,
                        account.maxTransactions,
                        account.numberOfTransactions
                ));
            }
            System.out.println("All Savings Accounts saved successfully.");
        } catch (IOException e) {
            System.err.println("An error occurred while saving to the file.");
        }
    }

    // Read Savings Accounts from The File (.csv).
    public static ArrayList<SavingsAccount> readSavingsAccount(String fileName) {
        ArrayList<SavingsAccount> savingsAccount = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            // Take One Record.
            String line;
            // Skip the Header.

            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                // Convert CSV fields into appropriate data types
                String accountNumber = fields[0];
                String ClientID = fields[1];
                String accountType = fields[2];
                String accountStatus = fields[3];
                double accountBalance = Double.parseDouble(fields[4]);
                double accountInterestRate = Double.parseDouble(fields[5]);
                String accountInterestType = fields[6];
                int maxTransactions = Integer.parseInt(fields[7]);
                int numberOfTransactions = Integer.parseInt(fields[8]);

                SavingsAccount account = new SavingsAccount(accountNumber, ClientID, accountType, accountStatus, accountBalance,
                        accountInterestRate, accountInterestType, maxTransactions, numberOfTransactions);
                savingsAccount.add(account);
            }
            System.out.println("All Savings Accounts read successfully.");
        } catch (IOException e) {
            System.err.println("An error occurred while reading from the file.");
        }
        return savingsAccount;
    }

    // Find Savings Account by Account Number, Username, Account Status, or Account Interest Type
    public static ArrayList<SavingsAccount> findSavingsAccount(ArrayList<SavingsAccount> savingsAccounts, String searchBy, String searchTerm) {

        ArrayList<SavingsAccount> foundAccounts = new ArrayList<>();

        if (searchTerm == null || searchTerm.isEmpty()) {
            return foundAccounts; // No search term provided, return empty list.
        }

        searchTerm = searchTerm.toLowerCase(); // Normalize search term.
        searchBy = searchBy.toLowerCase(); // Normalize search criteria.

        for (SavingsAccount account : savingsAccounts) {
            if (account == null) continue; // Skip null accounts to prevent NullPointerException.

            switch (searchBy) {
                case "accountnumber":
                    if (account.accountNumber.equals(searchTerm)) {
                        foundAccounts.add(account);
                    }
                    break;
                case "username":
                    if (account.ClientID != null &&
                            account.ClientID.toLowerCase().contains(searchTerm)) {
                        foundAccounts.add(account);
                    }
                    break;
                case "accountstatus":
                    if (account.accountStatus != null &&
                            account.accountStatus.equalsIgnoreCase(searchTerm)) {
                        foundAccounts.add(account);
                    }
                    break;
                case "accountinteresttype":
                    if (account.accountInterestType != null &&
                            account.accountInterestType.equalsIgnoreCase(searchTerm)) {
                        foundAccounts.add(account);
                    }
                    break;
                default:
                    System.err.println("Invalid search criteria.");
                    return foundAccounts; // Exit early on invalid criteria.
            }
        }
        return foundAccounts;
    }

    // Find Savings Account by Account Number
    public static SavingsAccount findAccount(ArrayList<SavingsAccount> savingsAccounts, String accNumber) {

        for (SavingsAccount account : savingsAccounts) {
            if (account == null) continue; // Skip null accounts to prevent NullPointerException.

            if (account.accountNumber.equals(accNumber)) {
                return account;
            }
        }
        return null;
    }
}
