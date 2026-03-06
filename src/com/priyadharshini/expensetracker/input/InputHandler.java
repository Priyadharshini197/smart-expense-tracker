package com.priyadharshini.expensetracker.input;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import com.priyadharshini.expensetracker.model.Category;
import com.priyadharshini.expensetracker.exception.InvalidAmountException;


public class InputHandler {
    public static double readAmount(Scanner sc){
        while(true){
            try{
                System.out.print("Enter amount: ");
                double amount = sc.nextDouble();
                sc.nextLine();
                if(amount <= 0){
                    throw new InvalidAmountException("Amount must be positive");
                }
                return amount;
            }
            catch (InvalidAmountException e){
                System.out.println("Invalid input.Please enter a number.");
                sc.nextLine();
            }
        }
    }
    public static String readDescription(Scanner sc) {

        System.out.print("Enter description: ");
        return sc.nextLine();
    }
    public static LocalDate readDate(Scanner sc) {
        while (true){
            try{
                System.out.print("Enter date (YYYY-MM-DD): ");
                String input = sc.nextLine();
                return LocalDate.parse(input);
            }
            catch(DateTimeParseException e){
                System.out.println("Invalid date format.Try again.");
            }
        }
    }
    public static Category readCategory(Scanner sc){
        while(true){
            try{
                System.out.print("Enter category: ");
                String input = sc.nextLine();
                return Category.valueOf(input.toUpperCase());
            }
            catch(IllegalArgumentException e){
                System.out.println("Invalid category.Try again");
            }
        }
    }
}
