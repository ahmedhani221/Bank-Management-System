package Classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import static com.oop.projectWithGUI.CSVController.savingsAccounts;
import static com.oop.projectWithGUI.CSVController.currentAccounts;

public class Client extends User {
    private Mode _mode;
    private String PhoneNum;
    private String sAccountNum;
    private String cAccountNum;
    public SavingsAccount savingsAcc = null;
    public CurrentAccount currentAcc = null;

    // Constructor Set Client to Add mode
    public Client() {
        super(null, null, null, null, null, "Client");
        this.PhoneNum = "";
        this.sAccountNum = "";

        _mode = Mode.Add;
    }

    // Constructor Set Client to Update mode "Only Used in reading from Files"
    protected Client(String ID, String firstName, String lastName, String username, String password,
                     String sAccountNum, String cAccountNum, String Phone) {
        super(ID, firstName, lastName, username, password, "Client");

        this.PhoneNum = Phone;
        this.sAccountNum = sAccountNum;
        this.cAccountNum = cAccountNum;

        savingsAcc = SavingsAccount.findAccount(savingsAccounts, this.sAccountNum);

        currentAcc = CurrentAccount.findAccount(currentAccounts, this.cAccountNum);

        _mode = Mode.Update;
    }

    // Getter and Setters
    public String getSAccountNum() {
        return sAccountNum;
    }

    public String getCAccountNum() {
        return cAccountNum;
    }

    public void setsAccountNum(String sAccountNum) {
        this.sAccountNum = sAccountNum;
    }

    public String getPhoneNum() {
        return PhoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        PhoneNum = phoneNum;
    }

    public void setcAccountNum(String cAccountNum) {
        this.cAccountNum = cAccountNum;
    }

    public void setSavingsAcc(SavingsAccount s){
        savingsAcc = s;
    }

    public void setCurrentAcc(CurrentAccount c){
        currentAcc = c;
    }

    // Find Client in the array list
    public static boolean FindByUsername(ArrayList<Client> Clients, String Username) {
        for(Client c: Clients){
            if(c.getUsername().equals(Username)){
                return true;
            }
        }
        return false;
    }

    public static boolean FindByID(ArrayList<Client> Clients, String ID) {
        for(Client c: Clients){
            if(c.getID().equals(ID)){
                return true;
            }
        }
        return false;
    }

    public static Client Find(ArrayList<Client> Clients, String ID) {
        for(Client c: Clients){
            if(c.getID().equals(ID)){
                return c;
            }
        }
        return null;
    }

    public static Client Find(ArrayList<Client> Clients, String Username, String Password) {
        for(Client c: Clients){
            if(c.getUsername().equals(Username) && c.getPassword().equals(Password)){
                return c;
            }
        }
        return null;
    }

    // Add Client to the List
    private boolean Add(ArrayList<Client> Clients)
    {
        boolean isAdded = true;
        try
        {
            Clients.add(this);
        }
        catch (RuntimeException e)
        {
            isAdded = false;
            System.out.println("Error Message: " + e.getMessage());
        }
        return isAdded;
    }

    // Update Client in the list
    private boolean Update(ArrayList<Client> Clients)
    {
        for(Client C: Clients){
            if(C.getID().equals(this.getID())){
                C = this;
                return true;
            }
        }

        return false;
    }

    // Delete Client in the list
    public boolean Delete(ArrayList<Client> Clients)
    {
        if(Clients.isEmpty()) return false;

        for (int i = 0; i < Clients.size(); i++){
            if(Clients.get(i).getID().equals(this.getID())){
                Clients.remove(i);
                return true;
            }
        }

        return false;
    }

    // Save method that used to abstract Add and Update methods
    public boolean Save(ArrayList<Client> Clients)
    {
        return switch (_mode) {
            case Add -> {
                this._mode = Mode.Update;
                yield Add(Clients);
            }
            case Update -> Update(Clients);
        };
    }

    // Save Clients ArrayList into the CSV File
    public static void saveClientsToCSV(ArrayList<Client> clients, String fileName){
        try (FileWriter writer = new FileWriter(fileName, false)) {
            writer.write("ClientID,First Name,Last Name,Username,Password,Savings Account Number, Current Account Number,Phone\n");

            for (Client client : clients) {
                writer.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s\n",
                        client.getID(),
                        client.getFirstName(),
                        client.getLastName(),
                        client.getUsername(),
                        client.getPassword(),
                        client.sAccountNum,
                        client.cAccountNum,
                        client.PhoneNum
                ));
            }
            System.out.println("All Clients details saved to " + fileName);
        } catch (IOException e) {
            System.err.println("An error occurred while saving to the CSV file: " + e.getMessage());
        }
    }

    // Read Clients Data From CSV File
    public static ArrayList<Client> readClientsFromCSV(String fileName) {
        ArrayList<Client> clients = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            // delete header
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                Client c = convertLineToClient(line);
                clients.add(c);
            }

            System.out.println("Clients successfully loaded from " + fileName);

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        return clients;
    }

    // Converting Line From CSV File to Client Object
    private static Client convertLineToClient(String line) {
        String[] fields = line.split(",");

        return new Client(fields[0], fields[1], fields[2], fields[3], fields[4],
                fields[5], fields[6], fields[7]);
    }


}