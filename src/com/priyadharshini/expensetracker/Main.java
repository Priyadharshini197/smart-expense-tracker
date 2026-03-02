package com.priyadharshini.expensetracker;
import java.util.*;
import com.priyadharshini.expensetracker.service.ExpenseService;

import java.io.File;
import java.time.LocalDate;
import com.priyadharshini.expensetracker.model.Expense;
import com.priyadharshini.expensetracker.model.Category;
import java.time.format.DateTimeParseException;
import com.priyadharshini.expensetracker.util.FileUtil;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        ExpenseService service = new ExpenseService();
        List<Expense> loadedExpenses = FileUtil.loadExpenses();
        for(Expense e : loadedExpenses){
            service.addExpense(e);
        }
        boolean running = true;
        while(running){
            System.out.println("\nSmart Expense Tracker");
            System.out.println("1. Add Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. Exit");
            System.out.println("4. Show Total Expense");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            
            switch(choice){
                case 1 :
                    try{
                        System.out.print("Enter amount: ");
                        double amount = sc.nextDouble();
                        sc.nextLine();
                        System.out.print("Enter description: ");
                        String description = sc.nextLine();
                        System.out.print("Enter date (YYYY-MM-DD): ");
                        String dateInput = sc.nextLine();
                        LocalDate date = LocalDate .parse(dateInput);
                        System.out.print("Enter category (FOOD/TRAVEL/...): ");
                        String catInput = sc.nextLine();    
                        Category category = Category.valueOf(catInput.toUpperCase());
                        Expense expense = new Expense(amount , description , date , category);
                        service.addExpense(expense);
                        FileUtil.saveExpense(expense);
                        System.out.println("Expense added successfully");
                        break;
                    }
                    catch(InputMismatchException e){
                        System.out.println("Invalid amount! Please enter a valid number.");
                        sc.nextLine();

                    }
                    catch(DateTimeParseException e ){
                        System.out.println("Invalid date format ! Please use YYYY-MM-DD.");
                    }
                    catch(IllegalArgumentException e ){
                        System.out.println("Invalid category! Please enter a valid category");
                    }
                    catch(Exception e ){
                        System.out.println("Something went wrong . Please try again.");
                    }
                    break;
                        
                case 2 :
                    for(Expense e : service.getAllExpenses()){
                        System.out.println(e);

                    }
                    if(service.getAllExpenses().isEmpty()) {
                        System.out.println("No expenses recorded yet.");
                    }
                    break;
                case 3 :
                    running = false;
                    break;
                case 4 :
                    double total = service.getTotalExpense();
                    System.out.println("Total Expense: "+total);
                    break;
                default :
                    System.out.println("Invalid choice!");
            }
        }

        
    }
    
}
