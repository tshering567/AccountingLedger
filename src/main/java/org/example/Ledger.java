package org.example;
import org.example.Main;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;


public class Ledger {
   // initializing an arraylist which holds transaction objects and calling it transactionList
    // and we are inheriting the transactions array list from the getTransactions()
    public static ArrayList<Transaction> transactionsList = getTransactions();
    public static ArrayList<Transaction> getTransactions() { //Declaring a method called getTransactions
        //of the type arraylist that holds Transaction objects
        ArrayList<Transaction> transactions = new ArrayList<>();//making an arraylist of transcation objects named transactions
        try { //we're creating filereader and bufferreader and passing the transaction.csv file into it.
            FileReader fileReader = new FileReader("transactions.csv");
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String input; // declaring a variable named input of type String
            while ((input = bufferedReader.readLine()) != null) {// looping through the csv file,
                // we're taking the current line from the csv file and storing it inside the input variable
                // and this loop will keep repeating until the next line is null()

                String[] details = input.split("\\|");// Splitting the input with "|" and storing all the pieces
                // into the String array variable called details.

                LocalDate date = LocalDate.parse(details[0]); //we're taking the string at index 0 from the detail's array and
                // converting it to LocalDate type using the LocalDate class and storing it to date variable of type Localdate

                LocalTime time = LocalTime.parse(details[1]);// we're taking the string at index 1 from detail's array
                // and converting it to Local time class and storing it to time variable of type Local time.

                String description = details[2];
                // we're taking the string at index 2 from detail's array
                //  and converting it to string class and storing it to description variable of type String

                String vendor = details[3];
                // we're taking the string at index 3 from detail's array
                //  and converting it to string class and storing it to vendor variable of type String

                double amount = Double.parseDouble(details[4]);
                // we're taking the string at index 4 from detail's array
                //  and converting it to double class and storing it to amount variable of type Double

                Transaction transaction = new Transaction(date, time, description, vendor, amount);
                //we're using the constructor from the transaction class and we're passing  (date, time, description)
                //into the constructor to make a new object called transaction of type Transaction

                transactions.add(transaction); //adding the transaction object we just created into the transactions array list
                //we will repeat all the steps above for each iteration of the loop(for each line in the csv file)
            }
        } catch (FileNotFoundException e) { // if the file can't be found , throw an error.
            throw new RuntimeException(e);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // Sort our transactions ArrayList in ascending order before returning it
        Comparator<Transaction> compareByDate = Comparator.comparing(Transaction::getDate).reversed();
        Comparator<Transaction> compareByTime = Comparator.comparing(Transaction::getTime).reversed();
        Comparator<Transaction> compareByDateTime = compareByDate.thenComparing(compareByTime);
        transactions.sort(compareByDateTime);
        return transactions;// we're returning the transactions array list to our method, so that when we call on out
        //method, we get an array list returned to us.
    }

    public static void showLedger() { // creating a method called showLedger() to display the ledger menu
        String input = null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to your ledger ");
        System.out.println(("-----------Main Menu---------- "));
        System.out.println("[A] - Display all entries");
        System.out.println("[D] - Display only the entries that are deposited into the account");
        System.out.println("[p] - Display only the negative entries(payments)");
        System.out.println("[R] - Reports");
        System.out.println("[H] - Go back to Home page");

        //using switch method instead of if/else statement to run the corresponding method based on user's input
        input = scanner.nextLine();
        switch (input.toUpperCase()) {
            case "A":
                allEntries();
                break;
            case "D":
                showDepositedEntries();
                break;
            case "P":
                showPaymentEntries();
                break;
            case "R":
                showReports();
                break;
            case "H":
                backToHomeScreen();
                break;
            default:
                System.out.println("Please enter a valid option");
                break;

        }
    }
    private static void backToHomeScreen() {
        System.out.println("Going back to Home Screen...");
        Main.homescreen();
    }

    private static void showReports() {
        Scanner scanner = new Scanner(System.in);
        //printing out a Report Screen
        System.out.println("Your reports ");
        System.out.println(("-----------Main Menu---------- "));
        System.out.println("[1] - Month To Date");
        System.out.println("[2] - Previous Month");
        System.out.println("[3] - Year To Date");
        System.out.println("[4] - Previous Year");
        System.out.println("[5] - Search by Vendor");
        System.out.println("[0] - Back");

        //using switch method instead of if/else statement to run the corresponding method based on user's input
        int input = scanner.nextInt();
        switch (input) {
            case 1:
                monthToDate();
                break;
            case 2:
                previousMonth();
                break;
            case 3:
                yearToDate();
                break;
            case 4:
                previousYear();
                break;
            case 5:
                searchByVendor();
                break;
            case 0:
                goBackToReturnMenu();
                break;
            default:
                System.out.println("Please enter a valid input");
                break;

        }
    }

    private static void goBackToReturnMenu() {
        System.out.println("Going back.....");
        showLedger();
    }

    private static void searchByVendor() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(" Please enter Vendor's name: ");
        String vendorName = scanner.nextLine();
        System.out.println("Here are all your transaction from" + " " + vendorName);

        for(Transaction item : transactionsList){
            if(item.getVendor().equalsIgnoreCase(vendorName)){
                System.out.println(item.getDate() + " | " + item.getTime() + " | " + item.getDescription() + " | " +
                        item.getVendor() + " | " +  item.getAmount());
            }

        }


    }

    private static void previousYear() {
        System.out.println(" Here is your report for the previous year: ");
        LocalDate currentDate = LocalDate.now();
        int previousYear = currentDate.minusYears(1).getYear();

        for (Transaction item : transactionsList){
            LocalDate transactionDate = item.getDate();
            if (transactionDate.getYear()==previousYear) {
                System.out.println(item.getDate() + " | " + item.getTime() + " | " + item.getDescription() + " | " +
                        item.getVendor() + " | " +  item.getAmount());
            }
        }
    }

    private static void yearToDate() { //prints the transactions from the first date of the current year to the current date(today)
        System.out.println("Here is your year to date report: ");
        LocalDate currentDate = LocalDate.now();// gets the current date using 'LocalDate.now() method'
        LocalDate startOfTheYear = currentDate.withDayOfYear(1); // gets the first date of the year ,using withDayOfYear method

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy, MMM dd");
        System.out.println("From" + " " + startOfTheYear.format(formatter) + " " + "to" + " " + currentDate.format(formatter) );

        for (Transaction item : transactionsList){
            if (item.getDate().isAfter(startOfTheYear.minusDays(1)) || item.getDate().isEqual(currentDate))
            {
                System.out.println(item.getDate() + " | " + item.getTime() + " | " + item.getDescription() + " | " +
                        item.getVendor() + " | " +  item.getAmount());
            }

        }
    }

    private static void previousMonth() {//prints the transactions from the previous month
        System.out.println(" Here is your report for the previous month: ");
        LocalDate currentDate = LocalDate.now();
        int previousMonth = currentDate.minusMonths(1).getMonthValue();

        for (Transaction item : transactionsList){
            LocalDate transactionDate = item.getDate();
            if (transactionDate.getMonthValue()==previousMonth && transactionDate.getYear() == currentDate.getYear()) {
                System.out.println(item.getDate() + " | " + item.getTime() + " | " + item.getDescription() + " | " +
                        item.getVendor() + " | " +  item.getAmount());
            }
        }
    }

    private static void monthToDate() { // prints the 1st date of the current month to the current date(today)
        System.out.println("Here is your month to date report: ");
        LocalDate currentDate = LocalDate.now(); // this method gets the current date using 'LocalDate.now()
        LocalDate startOfTheCurrentMonth = currentDate.withDayOfMonth(1); // this method gets the first day of the month
        //using the 'withDayOfMonth(1) method
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy"); // using the DateTimeFormatter
        //class to format the dates in month to date format
        System.out.println("From" + " " + startOfTheCurrentMonth.format(formatter) + " to " + currentDate.format(formatter));

        for (Transaction item :transactionsList) {
            //if we don't subtract 1 , the first day of the month will be excluded since we are using 'isAfter' method
            if (item.getDate().isAfter(startOfTheCurrentMonth.minusDays(1)) || item.getDate().isEqual(currentDate)){
                System.out.println(item.getDate() + " | " + item.getTime() + " | " + item.getDescription() + " | " +
                        item.getVendor() + " | " +  item.getAmount());
            }


        }


    }

    private static void showPaymentEntries() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Payments");
        for (Transaction item : transactionsList) { // loop through each transaction object(item) in the transactionslist
            // array list and check if the price is negative (payment)

            if(item.getAmount() < 0){
                //printing out it's private variables using the getter methods
            System.out.println(item.getDate() + " | " + item.getTime() + " | " + item.getDescription() + " | " +
                    item.getVendor() + " | " +  item.getAmount());
            }
        }


    }



    private static void showDepositedEntries() {
        System.out.println(" Deposits");
        for (Transaction item : transactionsList) { // loop through each transaction object(item) in the transactionslist
            // array list and check if the amount is positive(Deposits)

            if(item.getAmount() > 0) {
                //printing out it's private variables using the getter methods
                System.out.println(item.getDate() + "| " + item.getTime() + " | " + item.getDescription() + " | " +
                        item.getVendor() + " | " + item.getAmount());
            }
        }



    }

    private static void allEntries() { // Declaring the allentries() method
        System.out.println(" All Entries");
        for (Transaction item : transactionsList) { // loop through each transaction object(item) in the transactionslist
            // array list and printing out it's private variables using the getter methods
            System.out.println(item.getDate() + "| " + item.getTime() + "| " + item.getDescription() + " |" +
                    item.getVendor() + "| " +  item.getAmount());
        }
    }
}



