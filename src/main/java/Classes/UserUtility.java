package Classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static com.oop.projectWithGUI.CSVController.users;


public class UserUtility {
    public static void saveUsersToCSV(ArrayList<Client> clients, ArrayList<Employee> employees, String fileName) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            // Write the header
            writer.write("ID,First Name,Last Name,Username,Password,UserType\n");

            // Write clients
            for (Client client : clients) {
                writer.write(String.format("%s,%s,%s,%s,%s,%s\n",
                        client.getID(),
                        client.getFirstName(),
                        client.getLastName(),
                        client.getUsername(),
                        client.getPassword(),
                        client.getUserType()
                ));
            }

            // Write employees
            for (Employee employee : employees) {
                writer.write(String.format("%s,%s,%s,%s,%s,%s\n",
                        employee.getID(),
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getUsername(),
                        employee.getPassword(),
                        employee.getUserType()
                ));
            }

            System.out.println("Users successfully saved to " + fileName);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public static ArrayList<User> readUsersFromCSV(String fileName) {
        ArrayList<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            // Skip the header
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");

                String id = fields[0];
                String firstName = fields[1];
                String lastName = fields[2];
                String username = fields[3];
                String password = fields[4];
                String userType = fields[5];

                User user;
                if (userType.equals("Client")) {
                    user = new Client(id, firstName, lastName, username, password, "", "", "");
                } else if (userType.equals("Employee")) {
                    user = new Employee(id, firstName, lastName, username, password, "", "", "", "", "");
                } else {
                    // Handle unknown user type, if necessary
                    System.err.println("Unknown user type: " + userType);
                    continue;
                }

                users.add(user);
            }

            System.out.println("Users successfully loaded from " + fileName);
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error parsing file: " + e.getMessage());
        }
        return users;
    }

    public static ArrayList<User> Find(String username, String password){
        ArrayList<User> user = new ArrayList<>();
        for(User u : users){
            if(u.getUsername().equals(username) && u.getPassword().equals(password)){
                user.add(u);
                return user;
            }
        }
        return null;
    }
}

