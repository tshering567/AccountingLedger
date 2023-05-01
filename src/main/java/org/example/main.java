package org.example;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        homescreen();
    }

    private static void homescreen() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to your accounting Ledger: ");
        System.out.println(("Main Menu"));
        System.out.println("[D] - Add Deposit");
        System.out.println("[P] - Make Payment");
        System.out.println("[L] - Ledger");
        System.out.println("[X] - Exit");

        String input = scanner.nextLine();
        switch (input.toUpperCase()){
            case "D":
                addDeposit();
                break;
            case "P":
                makePayment();
                break;
            case "L":
                showLedger();
                break;
            case "X":
                System.exit(0);
                break;
            default:
                System.out.println("Please enter a valid option");
                break;
        }


    }

    private static void showLedger() {

        }

    }

    private static void makePayment() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Date:(yyyy-MM-dd)");
        String date = scanner.nextLine();
        System.out.println("Enter Time: ( HH:mm:SS");
        String time = scanner.nextLine();
        System.out.println("Enter Description");
        String description =scanner.nextLine();
        System.out.println("Enter vendor");
        String vendor = scanner.next();
        System.out.println("Enter amount");
        double amount = scanner.nextDouble();

        try{
            FileWriter fileWriter = new FileWriter("transactions.csv", true);
            fileWriter.write( "\n" + date + "|" + time + "|" + vendor + "|" + "-"  + amount + "|");
            System.out.println("Payment made successfully");
            fileWriter.close();
        } catch (IOException e){
            System.out.println("Error inputting data!");
        }
    }

    private static void addDeposit() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter Date:(yyyy-MM-dd)");
        String date = scanner.nextLine();
        System.out.println("Enter Time: ( HH:mm:SS");
        String time = scanner.nextLine();
        System.out.println("Enter Description");
        String description =scanner.nextLine();
        System.out.println("Enter vendor");
        String vendor = scanner.next();
        System.out.println("Enter amount");
        double amount = scanner.nextDouble();

        try{
            FileWriter fileWriter = new FileWriter("transactions.csv", true);
            fileWriter.write( "\n" + date + "|" + time + "|" + vendor + "|" + amount + "|");
            System.out.println("Deposit added successfully");
            fileWriter.close();
        } catch (IOException e){
            System.out.println("Error inputting data!");
        }

    }
}
