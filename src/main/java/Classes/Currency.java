package Classes;
import java.io.*;
import java.util.ArrayList;
import static com.oop.projectWithGUI.CSVController.currencies;

public class Currency {
    private String CountryName;
    private String CurrencyCode;
    private String CurrencyName;
    private Double ExchangeRate;

    public Currency(String countryName, String currencyCode, String currencyName, double exchangeRate)
    {
        CountryName = countryName;
        CurrencyCode = currencyCode;
        CurrencyName = currencyName;
        ExchangeRate = exchangeRate;
    }

    public String getCountryName() {
        return CountryName;
    }

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public String getCurrencyName() {
        return CurrencyName;
    }

    public double getExchangeRate() {
        return ExchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        ExchangeRate = exchangeRate;
    }

    public boolean UpdateRate (Double NewRate)
    {
        for(Currency c : currencies){
            if(c.CountryName.equals(this.CountryName)){
                c.ExchangeRate = NewRate;
                return true;
            }
        }

        return false;
    }

    private double ConvertToDollar (double amount)
    {
        return (amount / this.ExchangeRate);
    }

    public double ConvertToCurrency (double amount,Currency C2)
    {
        double USDAmount = ConvertToDollar(amount);
        if (C2.CurrencyCode.equals("USD"))
        {
            return USDAmount;
        }
        else
        {
            return USDAmount * C2.ExchangeRate;
        }
    }

    private static Currency convertLineToCurrency(String line) {
        String[] currency = line.split(",");
        return new Currency(currency[0], currency[1], currency[2], Float.parseFloat(currency[3]));
    }

    public static ArrayList<Currency> readCurrenciesFromCSV(String fileName) {
        ArrayList<Currency> currencies = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            // delete header
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                Currency c = convertLineToCurrency(line);
                currencies.add(c);
            }

            System.out.println("Currencies successfully loaded from " + fileName);

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        return currencies;
    }

    public static void saveCurrenciesToCSV(ArrayList<Currency> currencies, String FileName) {
        try (FileWriter writer = new FileWriter(FileName, false)) {
            writer.write("Country Name,Currency Code,Currency Name,Exchange Rate\n");

            for (Currency currency : currencies) {
                writer.write(String.format("%s,%s,%s,%.3f\n",
                        currency.CountryName,
                        currency.CurrencyCode,
                        currency.CurrencyName,
                        currency.ExchangeRate
                ));
            }

            System.out.println("All Currencies details saved to " + FileName);

        }
        catch (IOException e) {
            System.err.println("An error occurred while saving to the CSV file: " + e.getMessage());
        }
    }

    public static Currency Find(ArrayList<Currency> currencies, String countryName){
        for(Currency c : currencies){
            if(c.CountryName.equals(countryName)){
                System.out.printf("\nCountry Name: %s\n", c.CountryName);
                System.out.printf("Currency Code: %s\n", c.CurrencyCode);
                System.out.printf("Currency Name: %s\n", c.CurrencyName);
                System.out.printf("Rate ($): %.2f\n", c.ExchangeRate);

                return c;
            }
        }

        System.out.println("No Currency Found!");
        return null;
    }
}
