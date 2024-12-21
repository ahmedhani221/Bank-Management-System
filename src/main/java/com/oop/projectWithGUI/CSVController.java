package com.oop.projectWithGUI;

import Classes.*;
import java.util.ArrayList;

public class CSVController {
    public static ArrayList<Client> clients = new ArrayList<>();
    public static ArrayList<Employee> employees = new ArrayList<>();
    public static ArrayList<SavingsAccount> savingsAccounts = new ArrayList<>();
    public static ArrayList<CurrentAccount>  currentAccounts = new ArrayList<>();
    public static ArrayList<CreditCard> bankCards = new ArrayList<>();
    public static ArrayList<Loan> loans = new ArrayList<>();
    public static ArrayList<Payment> paymentsHistory = new ArrayList<>();
    public static ArrayList<LoanPayment> loanHistory = new ArrayList<>();
    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<Currency> currencies = new ArrayList<>();

    public static void ReadFromCSVFiles(){
        paymentsHistory = Payment.readPayments("Files/PaymentHistory.csv");
        loanHistory = LoanPayment.readPayments("Files/LoanHistory.csv");
        Transaction.TransactionHistory = Transaction.readTransactionsFromCSV("Files/TransactionsHistory.csv");

        bankCards = CreditCard.readCreditCards("Files/BankCards.csv");
        loans = Loan.readLoansFromCSV("Files/Loans.csv");

        savingsAccounts = SavingsAccount.readSavingsAccount("Files/SavingsAccounts.csv");
        currentAccounts = CurrentAccount.readCurrentAccounts("Files/CurrentAccounts.csv");

        clients = Client.readClientsFromCSV("Files/Clients.csv");
        employees = Employee.readEmployeesFromCSV("Files/Employees.csv");
        users = UserUtility.readUsersFromCSV("Files/Users.csv");

        currencies = Currency.readCurrenciesFromCSV("Files/Currencies.csv");
    }

    public static void SaveToCSVFiles(){
        Client.saveClientsToCSV(clients, "Files/Clients.csv");
        Employee.saveEmployeesToCSV(employees, "Files/Employees.csv");
        UserUtility.saveUsersToCSV(clients, employees, "Files/Users.csv");
        Currency.saveCurrenciesToCSV(currencies, "Files/Currencies.csv");

        Loan.saveLoansToCSV(loans, "Files/Loans.csv");

        SavingsAccount.saveSavingsAccount(savingsAccounts, "Files/SavingsAccounts.csv");
        CurrentAccount.saveCurrentAccounts(currentAccounts, "Files/CurrentAccounts.csv");
        CreditCard.saveCreditCards(bankCards,"Files/BankCards.csv");
        Transaction.saveTransactionsToCSV(Transaction.TransactionHistory, "Files/TransactionsHistory.csv");
        Payment.savePayments(paymentsHistory, "Files/PaymentHistory.csv");

        LoanPayment.savePayments(loanHistory, "Files/LoanHistory.csv");

    }
}
