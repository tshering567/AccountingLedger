package org.example;

import java.util.Scanner;

public class ledger {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to your ledger ");
        System.out.println(("Main Menu: "));
        System.out.println("[A] - Display all entries");
        System.out.println("[D] - Display only the entries that are deposited into the account");
        System.out.println("[p] - Display only the negative entries(payments)");
        System.out.println("[X] - Exit the application");

        String input = scanner.nextLine();
        switch (input.toUpperCase()){
            case "A":
                allEntries();
                break;
            case "D":
                showDepositedEntries();
                break;
            case "P":
                showPaymentEntries();
                break;
            case "X":
                System.exit(0);
                break;
            default:
                System.out.println("Please enter a valid option");
                break;
        }
    }

    private static void showPaymentEntries() {
    }

    private static void showDepositedEntries() {
        
    }

    private static void allEntries() {
        
    }

}
