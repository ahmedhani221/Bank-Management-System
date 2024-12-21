package Classes;

import java.util.ArrayList;

import static com.oop.projectWithGUI.CSVController.bankCards;

public abstract class Account {
    protected final String accountNumber;
    protected final String ClientID;
    protected final String accountType; // Savings, Current
    protected String accountStatus; // Active, Frozen, Closed
    protected double accountBalance;
    public CreditCard bankCard;
    protected static int tID = Transaction.TransactionHistory.getLast().getTransactionID() + 1;

    // Default Constructor when adding new account
    public Account(String accountNumber, String ClientID, String accountType) {
        this.accountNumber = accountNumber;
        this.ClientID = ClientID;
        this.accountType = accountType;
        this.accountStatus = "Active"; // Default status when open account
        this.accountBalance = -1;
        this.bankCard = null;
    }

    // Default constructor
    public Account(String accountNumber, String ClientID, String accountType, double accountBalance) {
        this.accountNumber = accountNumber;
        this.ClientID = ClientID;
        this.accountType = accountType;
        this.accountStatus = "Active"; // Default status when open account
        this.accountBalance = accountBalance;
        this.bankCard = null;
    }

    // Read constructor
    public Account(String accountNumber, String ClientID, String accountType, String accountStatus, double accountBalance) {
        this.accountNumber = accountNumber;
        this.ClientID = ClientID;
        this.accountType = accountType;
        this.accountStatus = accountStatus;
        this.accountBalance = accountBalance;
        this.bankCard = CreditCard.findCreditCards(bankCards, this.accountNumber);
    }

    // Getters and Setters
    public String getAccountNumber() {
        return accountNumber;
    }

    public String getClientID() {
        return ClientID;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public double getBalance() {
        return accountBalance;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public void setBalance(double balance) {
        this.accountBalance = balance;
    }

    public void setBankCard () {
        if (this.bankCard == null) {
            System.out.println("Bank Card not Exist!!");
            System.out.println("You Must Make Credit Card First.");
        } else {
            bankCard.getCreditDetails();
        }
    }

    // Display account details for clients
    public abstract void getAccountDetails();

    // Freeze account under certain condition
    public abstract void freezeAccount();

    // When client ask employee to freeze account
    public void clientRequestFreeze() {
        this.accountStatus = "Frozen";
    }

    // When client ask employee to close account
    public void clientRequestClose() {
        this.accountStatus = "Closed";
    }

    // Display balance for client
    public double checkBalance() {
        return this.accountBalance;
    }

    // Deposit Amount to Client's Account.
    public boolean Deposit(double Amount) {
        if (Amount < 0) return false;

        this.accountBalance += Amount;

        Transaction t = new Transaction(tID++, this.accountNumber, "NULL", "Deposit", Amount, this.ClientID);
        t.AddToHistory();

        return true;
    }

    public boolean Deposit(double Amount, String ID) {
        if (Amount < 0) return false;

        this.accountBalance += Amount;

        Transaction t = new Transaction(tID++, this.accountNumber, "NULL", "Deposit", Amount, ID);
        t.AddToHistory();

        return true;
    }

    // Withdraw Amount from Client's Account.
    public boolean Withdraw(double Amount) {
        if(Amount < 0 || Amount > this.accountBalance) return false;

        this.accountBalance -= Amount;

        Transaction t = new Transaction(tID++, this.accountNumber, "NULL", "Withdraw", Amount, this.ClientID);
        t.AddToHistory();

        return true;
    }

    public boolean Withdraw(double Amount, String ID) {
        if(Amount < 0 || Amount > this.accountBalance) return false;

        this.accountBalance -= Amount;

        Transaction t = new Transaction(tID++, this.accountNumber, "NULL", "Withdraw", Amount, ID);
        t.AddToHistory();

        return true;
    }

    // Transfer Amount between Clients' Accounts.
    public boolean Transfer(Account targetAccount, double amount, String ID) {
        if (targetAccount instanceof CurrentAccount) {
            return transferToCurrent((CurrentAccount) targetAccount, amount, ID);
        } else if (targetAccount instanceof SavingsAccount) {
            return transferToSavings((SavingsAccount) targetAccount, amount, ID);
        }
        return false;
    }

    private boolean transferToCurrent(CurrentAccount Acc, double Amount, String ID) {
        if(Amount < 0 || Amount > this.accountBalance) return false;

        this.accountBalance -= Amount;
        Acc.accountBalance += Amount;


        Transaction t = new Transaction(tID++, this.accountNumber, Acc.accountNumber, "Transfer", Amount, ID);
        t.AddToHistory();

        return true;
    }

    private boolean transferToSavings(SavingsAccount Acc, double Amount, String ID) {
        if(Amount < 0 || Amount > this.accountBalance) return false;

        this.accountBalance -= Amount;
        Acc.accountBalance += Amount;

        Transaction t = new Transaction(tID++, this.accountNumber, Acc.accountNumber, "Transfer", Amount, ID);
        t.AddToHistory();

        return true;
    }

    public int amountVerification(double amount) {
        if (amount < 10) {
            return 101;
        } else if (amount > this.accountBalance) {
            return 102;
        } else if (amount > bankCard.getCreditLimit()) {
            return 103;
        } else {
            return 100;
        }
    }

    // Make Payment from Card.
    public void makePaymentWithCard(double amount) {
        this.accountBalance -= amount;
        Payment payment = new Payment(this.accountNumber, bankCard.getCardNumber(), amount);
        Payment.addToHistory(payment);
        System.out.println("Payment Successfully...");
        System.out.println("Your Balance: " + getBalance());
    }
}
