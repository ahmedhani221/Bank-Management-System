package Classes;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Employee  extends User {

    private Mode mode;
    private String address;
    private String position;
    private String graduatedCollege;
    private String yearOfGraduation;
    private String totalGrade;

    public Employee()
    {
        super(null, null ,null,null ,null, "Employee");

        this.address = "";
        this.graduatedCollege = "";
        this.position = "";
        this.yearOfGraduation = "";
        this.totalGrade = "";

        mode = Mode.Add;
    }

    public Employee(String ID, String firstName, String lastName, String username, String password,
                    String address, String position, String graduatedCollege, String yearOfGraduation, String totalGrade)
    {
        super(ID, firstName, lastName, username, password, "Employee");

        this.address = address;
        this.graduatedCollege = graduatedCollege;
        this.position = position;
        this.yearOfGraduation = yearOfGraduation;
        this.totalGrade = totalGrade;

        mode = Mode.Update;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getGraduatedCollege() {
        return graduatedCollege;
    }

    public void setGraduatedCollege(String graduatedCollege) {
        this.graduatedCollege = graduatedCollege;
    }

    public String getYearOfGraduation() {
        return yearOfGraduation;
    }

    public void setYearOfGraduation(String yearOfGraduation) {
        this.yearOfGraduation = yearOfGraduation;
    }

    public String getTotalGrade() {
        return totalGrade;
    }

    public void setTotalGrade(String totalGrade) {
        this.totalGrade = totalGrade;
    }

    public boolean Save(ArrayList<Employee> employees)
    {
        switch (mode)
        {
            case Add:
                this.mode = Mode.Update;
                return Add(employees);
            case Update:
                return Update(employees);
        }
        return false;
    }

    public void DisplayInfo() {

        System.out.println("Employee Details: ");
        System.out.println("__________________________________");
        System.out.println("Id: " + this.getID());
        System.out.println("Username: " + this.getUsername());
        System.out.println("Name: " + getFullName());
        System.out.println("Position: " + this.getPosition());
        System.out.println("Graduated Collage: " + (this.getGraduatedCollege()));
        System.out.println("Year Of Graduation: " + this.getYearOfGraduation());
        System.out.println("Total of Your Grades" + this.getTotalGrade());
        System.out.println("__________________________________");

    }

    public static Employee FindByUsername(ArrayList<Employee> employees, String username) {
        for(Employee E: employees){
            if(E.getUsername().equals(username)){
                return E;
            }
        }
        return null;
    }

    public static Employee Find(ArrayList<Employee> employees, String ID) {
        for(Employee E: employees){
            if(E.getID().equals(ID)){
                return E;
            }
        }
        return null;
    }

    public static Employee Find(ArrayList<Employee> employees, String FirstName, String LastName) {
        for(Employee E: employees){
            if(E.getFullName().equals(FirstName + ' ' + LastName)){
                return E;
            }
        }
        return null;
    }

    private boolean Add(ArrayList <Employee> employees){
        boolean isAdded = true;
        try
        {
            employees.add(this);
        }
        catch (RuntimeException e)
        {
            isAdded = false;
            System.out.println("Error Message: " + e.getMessage());
        }
        return isAdded;
    }

    private boolean Update(ArrayList<Employee> employees)
    {
        for(Employee E: employees){
            if(E.getUsername().equals(this.getUsername()) ){
                E = this;
                return true;
            }
        }
        return false;
    }

    public boolean Delete(ArrayList<Employee> employees)
    {
        if(employees.isEmpty())
            return false;

        for (int i = 0; i < employees.size(); i++){
            if(employees.get(i).getID().equals(getID())){
                employees.remove(i);
                return true;
            }
        }

        return false;
    }

    public static void saveEmployeesToCSV(ArrayList<Employee> employees, String fileName){
        try (FileWriter writer = new FileWriter(fileName, false)) {
            writer.write("EmployeeID,First Name,Last Name,Username,Password,Address,Position,Graduated College," +
                    "Year of Graduation,Total Grade\n");

            for (Employee employee : employees) {
                writer.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n",
                        employee.getID(),
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getUsername(),
                        employee.getPassword(),
                        employee.address,
                        employee.position,
                        employee.graduatedCollege,
                        employee.yearOfGraduation,
                        employee.totalGrade
                ));
            }
            System.out.println("All Employees details saved to " + fileName);
        } catch (IOException e) {
            System.err.println("An error occurred while saving to the CSV file: " + e.getMessage());
        }
    }

    public static ArrayList<Employee> readEmployeesFromCSV(String fileName) {
        ArrayList<Employee> employees = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            // delete header
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                Employee e = convertLineToEmployee(line);
                employees.add(e);
            }

            System.out.println("Employees successfully loaded from " + fileName);

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        return employees;
    }

    private static Employee convertLineToEmployee(String line) {
        String[] fields = line.split(",");

        return new Employee(fields[0], fields[1], fields[2], fields[3], fields[4],
                fields[5], fields[6], fields[7], fields[8], fields[9]);
    }
}
