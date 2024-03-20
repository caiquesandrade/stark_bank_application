package src.application;

import src.entities.Account;
import src.validators.InputValidator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;

public class Program {
    private static final int NUMBER_START_MENU = 1;
    private static final int NUMBER_END_MENU = 7;

    private static final Scanner sc = new Scanner(System.in);
    private static final HashMap<Integer, Account> accountActive = new HashMap<>();
    private static final InputValidator inputValidator = new InputValidator();

    public static void main(String[] args) throws IOException {
        Locale.setDefault(Locale.US);
        sc.useLocale(Locale.US);

        System.out.println("=====================================");
        System.out.println("===== Welcome to the STARK BANK =====");
        try {
            init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void init() throws IOException {
        while (true) {
            showMenu();
            int userOption = checkMenuUserInputFormatAndValue();

            switch (userOption) {
                case 1:
                    System.out.println("SELECTED -> Option 1 Create a NEW account");
                    createNewAccount();
                    break;
                case 2:
                    System.out.println("SELECTED -> Option 2 Make a big deposit");
                    makeDeposit();
                    break;
                case 3:
                    System.out.println("SELECTED -> Option 3 Amount info");
                    checkAmountInfo();
                    break;
                case 4:
                    System.out.println("SELECTED -> Option 4 Withdraw");
                    makeWithdraw();
                    break;
                case 5:
                    System.out.println("SELECTED -> Option 5 Transfer money");
                    transferMoney();
                    break;
                case 6:
                    System.out.println("SELECTED -> Option 6 Print a statement");
                    printStatement();
                    break;
                case 7:
                    System.out.println("Closing aplication ... See you!");
                    sc.close();
                    return;
            }
        }
    }

    private static void printStatement() throws IOException {
        System.out.print("Please type the ACCOUNT NUMBER: ");
        Account originAccountNumber = accountActive.get(inputValidator.checkInputUserValueByQuantityDisired(6, sc));
        BufferedReader in = new BufferedReader(new FileReader(originAccountNumber.getAccountNumber() + "_" + originAccountNumber.getAccountName() + ".csv"));
        String line = in.readLine();
        System.out.print("\n");
        System.out.println("==== START OF YOUR STATEMENT ==== ");
        System.out.print("\n");
        while(line != null)
        {
            System.out.println(line);
            line = in.readLine();
        }
        in.close();
        System.out.print("\n");
        System.out.println("==== END OF STATEMENT ==== ");
    }

    private static int checkMenuUserInputFormatAndValue() {
        int userOption;
        if (sc.hasNextInt()) {
            userOption = sc.nextInt();
        } else {
            sc.next();
            userOption = 0;
        }
        while (userOption < NUMBER_START_MENU || userOption > NUMBER_END_MENU) {
            System.out.println("Invalid value or format! Please type numbers between " + NUMBER_START_MENU + " AND " + NUMBER_END_MENU);
            showMenu();
            if (sc.hasNextInt()) {
                userOption = sc.nextInt();
            } else {
                sc.next();
                userOption = 0;
            }
        }
        return userOption;
    }

    private static void transferMoney() {
        System.out.print("Please type the ORIGIN account number: ");
        Account originAccountNumber = accountActive.get(inputValidator.checkInputUserValueByQuantityDisired(6, sc));

        System.out.print("Please type the DESTINATION account number: ");
        Account destinationAccountNumber = accountActive.get(inputValidator.checkInputUserValueByQuantityDisired(6, sc));

        System.out.print("Please type the VALUE to do a transfer: ");
        double valueToTransfer = inputValidator.checkDoubleUserValue(sc);

        if (checkTransferencePeriodAllowed()) {
            doTransferMoney(originAccountNumber, destinationAccountNumber, valueToTransfer);
        } else {
            if (valueToTransfer > originAccountNumber.getAccountTransferLimit()) {
                System.out.printf("You value to transfer %.2f is greater than your account limit to transfer %s", valueToTransfer, originAccountNumber.getAccountTransferLimit());
            } else {
                doTransferMoney(originAccountNumber, destinationAccountNumber, valueToTransfer);
            }
        }
    }

    private static void doTransferMoney(Account originAccountNumber, Account destinationAccountNumber, double valueToTransfer) {
        if (originAccountNumber.withdraw(valueToTransfer, true)) {
            destinationAccountNumber.deposit(valueToTransfer, true);
        } else {
            System.out.print("Sorry, you can't transfer the money! Try again.");
        }
    }

    private static boolean checkTransferencePeriodAllowed() {
        LocalTime maxLimitReferenceTime = LocalTime.of(20, 0);
        LocalTime minLimitReferenceTime = LocalTime.of(8, 0);
        LocalTime nowTime = LocalTime.now();
        return nowTime.isAfter(minLimitReferenceTime) && nowTime.isBefore(maxLimitReferenceTime);
    }

    private static void makeWithdraw() {
        System.out.print("Please type the account number: ");
        int accountNumber = inputValidator.checkInputUserValueByQuantityDisired(6, sc);
        System.out.print("Please type the value to withdraw: ");
        accountActive.get(accountNumber).withdraw(inputValidator.checkDoubleUserValue(sc), false);
    }

    private static void checkAmountInfo() {
        System.out.print("Please type the account number: ");
        double amount = accountActive.get(inputValidator.checkInputUserValueByQuantityDisired(6, sc)).getAmount();
        System.out.println("Your amount is: " + amount);
    }

    private static void makeDeposit() {
        System.out.println("Type the account number to make a deposit: ");
        int accountNumber = inputValidator.checkInputUserValueByQuantityDisired(6, sc);
        //preciso checar se accontNumber foi criado em tempo de execução

        System.out.println("Type the amount to deposit: ");
        double amount = inputValidator.checkDoubleUserValue(sc);
        try {
            accountActive.get(accountNumber).deposit(amount, false);
        } catch (NullPointerException e) {
            System.out.println("Error message: " + e.getMessage());
        }
    }

    private static void createNewAccount() {
        Account account = new Account();
        accountActive.put(account.createNewAccount().getAccountNumber(), account);
    }

    private static void showMenu() {
        System.out.println("=====================================");
        System.out.println("====== Please select a option: ======");
        System.out.println("=====================================");
        System.out.printf("===== Now is: %s o'clock ======\n", new SimpleDateFormat("HH:mm:ss").format(new Date()));
        System.out.println("=====================================");
        System.out.println("1 - Create a NEW millionaire account");
        System.out.println("2 - Make a big deposit");
        System.out.println("3 - Amount info");
        System.out.println("4 - Withdraw");
        System.out.println("5 - Transfer money");
        System.out.println("6 - Print a statement");
        System.out.println("7 - Exit");
    }
}
