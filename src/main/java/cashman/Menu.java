package cashman;

import cashman.atm.Cashman;
import cashman.enums.DenominationType;
import cashman.exception.CurrencyFulfilmentException;
import cashman.exception.InsufficentFundsException;

import java.math.BigDecimal;
import java.util.Scanner;

public class Menu {

    Cashman cashman;
    Scanner input;

    public Menu() {
        cashman = new Cashman();
        input = new Scanner(System.in);
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.showMenu();
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n");
            System.out.println("############ Automated Teller Machine ###############");
            System.out.println("Choose 1 for Withdraw");
            System.out.println("Choose 2 for Withdraw with Option");
            System.out.println("Choose 3 for Toppping Up ATM");
            System.out.println("Choose 4 for Report on ATM Balance");
            System.out.println("Choose any other key for EXIT");
            System.out.print("Choose the operation you want to perform: >");
            int option = input.nextInt();

            switch (option) {
                case 1:
                case 2:
                    //Call withdraw method
                    try {
                        if (option == 1) {
                            System.out.print("How much to Withdraw: > $");
                            BigDecimal amount = input.nextBigDecimal();
                            cashman.withdrawal(amount);
                        } else {
                            withdrawDisplay();
                        }
                        System.out.printf("\n**** Success! Go to option 3 to check balance.****\n");
                    } catch (CurrencyFulfilmentException ex) {
                        System.out.print("\n#Error : Unable to fulfill your request with the denominations available#");
                    } catch (InsufficentFundsException ex) {
                        System.out.print("\n#### Error : Insufficient Fund in ATM to fulfill your request ####");
                    } catch (IllegalArgumentException ex) {
                        System.out.print("\n#### Error : Amount can't be negative or Incorrect Denomination ####");
                    }
                    break;
                case 3:
                    //Call Top up method
                    try {
                        topUpDisplay();
                    } catch (IllegalArgumentException e) {
                        System.out.print("#### Error : Invalid Currency Denomination Enter ####");
                    }
                    break;
                case 4:
                    //Call reporting method
                    cashman.reportAtmBalance();
                    break;
                default:
                    System.exit(0);
            }
        }
    }

    private void topUpDisplay() throws IllegalArgumentException {
        input = new Scanner(System.in);

        System.out.println("Top Up Automated Teller Machine");
        System.out.print("Enter denomination to top up > $");
        Double denomination = input.nextDouble();
        System.out.print("Enter quantity to top up > ");
        int quantity = input.nextInt();
        DenominationType denominationType = DenominationType.findByValue(denomination);
        if (denominationType != null) {
            cashman.topUp(denominationType, quantity);
        } else {
            throw new IllegalArgumentException("Denomination does not exist");
        }
    }

    private void withdrawDisplay() throws IllegalArgumentException, CurrencyFulfilmentException,
                                InsufficentFundsException {
        input = new Scanner(System.in);

        System.out.println("Withdraw with Option");
        System.out.print("How much to Withdraw: > $");
        BigDecimal amount = input.nextBigDecimal();
        System.out.print("Enter denomination to withdraw > $");
        Double denomination = input.nextDouble();
        DenominationType denominationType = DenominationType.findByValue(denomination);
        if (denominationType != null) {
            cashman.withdrawal(amount, denominationType);
        } else {
            throw new IllegalArgumentException("Denomination does not exist");
        }
    }

}
