package src.entities;

import src.validators.InputValidator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class Account {
    protected static String ISO_CODE = "BR";
    protected int accountNumber;
    protected String accountName;
    protected String iban;
    protected double amount;
    protected int bankCode;
    protected int checkDigits;
    protected int  accountTransferLimit;
    private final Scanner sc = new Scanner(System.in);
    private BufferedWriter output;
    private FileWriter fileWriter;
    private final InputValidator inputValidator = new InputValidator();
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public String getIban() {
        return ISO_CODE + checkDigits + bankCode + accountNumber;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public double getAmount() {
        return Double.parseDouble(df.format(amount));
    }

    public void setAmount(double amount) {
        this.amount = Double.parseDouble(df.format(amount));
    }

    public int getBankCode() {
        return bankCode;
    }

    public void setBankCode(int bankCode) {
        this.bankCode = bankCode;
    }

    public int getCheckDigits() {
        return checkDigits;
    }

    public void setCheckDigits(int checkDigits) {
        this.checkDigits = checkDigits;
    }
    public int getAccountTransferLimit() {
        return accountTransferLimit;
    }

    public void setAccountTransferLimit(int accountTransferLimit) {
        this.accountTransferLimit = !(accountTransferLimit > 0) ? 1000 : accountTransferLimit;
    }

    public Account createNewAccount() {
        System.out.print("Please insert your FIRST name (MAX 60 char):");
        setAccountName(inputValidator.checkStringValue(60, sc));

        System.out.print("Please insert 6 digits to account number: ");
        setAccountNumber(this.accountNumber = inputValidator.checkInputUserValueByQuantityDisired(6, sc));

        System.out.print("Please insert 4 digits to bank code: ");
        setBankCode(inputValidator.checkInputUserValueByQuantityDisired(4, sc));

        System.out.print("Please insert 2 digits to the check digits: ");
        setCheckDigits(inputValidator.checkInputUserValueByQuantityDisired( 2, sc));

        System.out.print("Please insert the amount value: ");
        setAmount(inputValidator.checkDoubleUserValue(sc));

        System.out.print("Please insert the ACCOUNT TRANSFER LIMIT (MIN=1000): ");
        setAccountTransferLimit(inputValidator.checkInputUserValueByQuantityDisired(4, sc));

        System.out.printf("Thank %s to create your account with number: %s and amount: %s ", this.accountName, this.accountNumber, this.amount);

        try {
            fileWriter = new FileWriter(this.accountNumber + "_" + this.accountName + ".csv");
            output = new BufferedWriter(fileWriter);
            output.write("Account Name: " + accountName);
            output.newLine();
            output.write("Account Number: " + accountNumber);
            output.newLine();
            output.write("Bank Code: " + bankCode);
            output.newLine();
            output.write("Check Digits: " + checkDigits);
            output.newLine();
            output.write("Initial Amount: " + amount);
            output.newLine();
            output.write("Create Date: " + new SimpleDateFormat("dd MMM yyyy hh:mm").format(Calendar.getInstance().getTime()));
            output.newLine();
            output.close();
        } catch (IOException e) {
            System.out.println("An error occurred. Error: " + e.getMessage());
            throw new RuntimeException(e);
        }

        return this;
    }



    public void deposit(double value, boolean isWireTransfer) {
        this.amount += value;
        System.out.println(accountName + " your amount is: " + amount);
        try {
            fileWriter = new FileWriter(this.accountNumber + "_" + this.accountName + ".csv", true);
            output = new BufferedWriter(fileWriter);
            output.write(" --------------------------- " );
            output.newLine();
            if (isWireTransfer) {
                output.write("Received : " + value);
            } else {
                output.write("Deposit : " + value);
            }
            output.newLine();
            output.write("Amount: " + amount);
            output.newLine();
            output.write("Transaction date: " + new SimpleDateFormat("dd MMM yyyy hh:mm").format(Calendar.getInstance().getTime()));
            output.newLine();
            output.close();
        } catch (IOException e) {
            System.out.println("An error occurred. Error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public boolean withdraw(double value, boolean isWireTransfer) {
        if (amount >= value) {
            this.amount -= value;
            System.out.println(accountName + " your amount is: " + amount);
            try {
                fileWriter = new FileWriter(this.accountNumber + "_" + this.accountName + ".csv", true);
                output = new BufferedWriter(fileWriter);
                output.write(" --------------------------- " );
                output.newLine();
                if (isWireTransfer) {
                    output.write("Transferred : " + value);
                } else {
                    output.write("Withdraw : " + value);
                }
                output.newLine();
                output.write("Amount: " + amount);
                output.newLine();
                output.write("Transaction date: " + new SimpleDateFormat("dd MMM yyyy hh:mm").format(Calendar.getInstance().getTime()));
                output.newLine();
                output.close();
            } catch (IOException e) {
                System.out.println("An error occurred. Error: " + e.getMessage());
                throw new RuntimeException(e);
            }
            return true;
        } else {
            System.out.println(accountName + " you have only" + amount + " it is no enough!");
            return false;
        }

    }

}
