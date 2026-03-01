package com.priyadharshini.expensetracker;
import java.util.*;
import com.priyadharshini.expensetracker.service.ExpenseService;
import java.time.LocalDate;
import com.priyadharshini.expensetracker.model.Expense;
import com.priyadharshini.expensetracker.model.Category;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        ExpenseService service = new ExpenseService();
        boolean running = true;
        while(running){
            System.out.println("\nSmart Expense Tracker");
            System.out.println("1. Add Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            switch(choice){
                case 1 :
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
                default :
                    System.out.println("Invalid choice!");
            }
        }

        
    }
    
}
