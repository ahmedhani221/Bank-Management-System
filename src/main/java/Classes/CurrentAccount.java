package Classes;
import java.io.*;
import java.util.*;

import static com.oop.projectWithGUI.CSVController.bankCards;

public class CurrentAccount extends Account{
    private double overdraftLimit;
    private double withdrawLimit;

    public CurrentAccount(String accountNumber, String clientID) {
        super(accountNumber, clientID, "Current");
        this.overdraftLimit = -1;
        this.withdrawLimit = 100000; // Default
    }

    // Default constructor
    public CurrentAccount(String accountNumber, String clientID, double accountBalance, double overdraftLimit) {
        super(accountNumber, clientID, "Current", accountBalance);
        this.overdraftLimit = overdraftLimit;
        this.withdrawLimit = 100000; // Default
    }

    // Read constructor
    public CurrentAccount(String accountNumber, String ClientID, String accountType, String accountStatus,
                          double accountBalance, double overdraftLimit, double withdrawLimit) {
        super(accountNumber, ClientID, accountType, accountStatus, accountBalance);
        this.overdraftLimit = overdraftLimit;
        this.withdrawLimit = withdrawLimit;
    }

    // Getters and Setters
    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public double getWithdrawalLimit() {
        return withdrawLimit;
    }

    public void setOverdraftLimit(double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    public void setWithdrawLimit(double withdrawLimit) {
        this.withdrawLimit = withdrawLimit;
    }

    // Display account details for clients
    public void getAccountDetails() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder Name: " + ClientID);
        System.out.println("Account Type: " + accountType);
        System.out.println("Account Status: " + accountStatus);
        System.out.println("Balance: " + accountBalance);
        System.out.println("Overdraft Limit: " + overdraftLimit);
    }

    // Freeze account under certain condition
    public void freezeAccount() {
        if (accountBalance < -overdraftLimit) {
            accountStatus = "Frozen";
        }
    }

    // Check if balance is less than 3000
    public boolean isChecked() {
        return !(accountBalance < 3000);
    }

    // Pay fees
    public void pay() {
        if (!isChecked()) {
            System.out.println("You paid Successfully");
        } else {
            System.out.println("No Fees to Pay");
        }
    }

    public boolean Update(ArrayList<CurrentAccount> accounts) {
        for(CurrentAccount c: accounts){
            if(c.getAccountNumber().equals(this.getAccountNumber())){
                c = this;
                return true;
            }
        }
        return false;
    }

    public boolean Delete(ArrayList<CurrentAccount> accounts){
        if(accounts.isEmpty()) return false;

        for (int i = 0; i < accounts.size(); i++){
            if(accounts.get(i).getAccountNumber().equals(this.getAccountNumber())){
                accounts.remove(i);
                return true;
            }
        }

        return false;
    }

    // Save Current Accounts to The File (.csv).
    public static void saveCurrentAccounts(ArrayList<CurrentAccount> currentAccounts, String fileName) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            // Write CSV header
            writer.write("Account Number,Account's ClientID,Account Type,Account Status,Account Balance,Overdraft Limit,Withdrawal Limit\n");

            // Write current account details
            for (CurrentAccount account : currentAccounts) {
                writer.write(String.format("%s,%s,%s,%s,%.2f,%.2f,%.2f\n",
                        account.accountNumber,
                        account.ClientID,
                        account.accountType,
                        account.accountStatus,
                        account.accountBalance,
                        account.overdraftLimit,
                        account.withdrawLimit
                ));
            }
            System.out.println("All Current Accounts saved successfully.");
        } catch (IOException e) {
            System.err.println("An error occurred while saving to the file.");
        }
    }

    // Read Current Accounts from The File (.csv).
    public static ArrayList<CurrentAccount> readCurrentAccounts(String fileName) {
        ArrayList<CurrentAccount> currentAccounts = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            // Skip the header
            reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                // Convert CSV fields into appropriate data types
                String accountNumber = fields[0];
                String clientID = fields[1];
                String accountType = fields[2];
                String accountStatus = fields[3];
                double accountBalance = Double.parseDouble(fields[4]);
                double overdraftLimit = Double.parseDouble(fields[5]);
                double withdrawLimit = Double.parseDouble(fields[6]);

                // Create and add CurrentAccount to the list
                CurrentAccount account = new CurrentAccount(accountNumber, clientID, accountType, accountStatus, accountBalance, overdraftLimit, withdrawLimit);
                currentAccounts.add(account);
            }
            System.out.println("All Current Accounts read successfully.");
        } catch (IOException e) {
            System.err.println("An error occurred while reading from the file.");
        }
        return currentAccounts;
    }

    // Find Current Account by Account Number, Username, Account Status
    public ArrayList<CurrentAccount> findCurrentAccount(ArrayList<CurrentAccount> currentAccounts, String searchBy, String searchTerm) {
        ArrayList<CurrentAccount> foundAccounts = new ArrayList<>();

        if (searchTerm == null || searchTerm.isEmpty()) {
            return foundAccounts; // No search term provided, return empty list.
        }

        searchTerm = searchTerm.toLowerCase(); // Normalize search term.
        searchBy = searchBy.toLowerCase(); // Normalize search criteria.

        for (CurrentAccount account : currentAccounts) {
            if (account == null) continue; // Skip null accounts to prevent NullPointerException.

            switch (searchBy) {
                case "accountnumber":
                    if (String.valueOf(account.getAccountNumber()).equals(searchTerm)) {
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
                    if (account.getAccountStatus() != null &&
                            account.getAccountStatus().equalsIgnoreCase(searchTerm)) {
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

    // Find Current Account by Account Number
    public static CurrentAccount findAccount(ArrayList<CurrentAccount> CurrentAccounts, String accNumber) {

        for (CurrentAccount account : CurrentAccounts) {
            if (account == null) continue; // Skip null accounts to prevent NullPointerException.

            if (account.accountNumber.equals(accNumber)) {
                return account;
            }
        }
        return null;
    }

}
