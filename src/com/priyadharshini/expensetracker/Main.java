package com.priyadharshini.expensetracker;
import java.util.*;
import com.priyadharshini.expensetracker.service.ExpenseService;

import java.io.File;
import java.time.LocalDate;
import com.priyadharshini.expensetracker.model.Expense;
import com.priyadharshini.expensetracker.model.Category;
import java.time.format.DateTimeParseException;
import com.priyadharshini.expensetracker.util.FileUtil;
import java.time.YearMonth;
import java.util.stream.Collectors;
import java.util.Map;


public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        
        ExpenseService service = new ExpenseService();
        List<Expense> loadedExpenses = FileUtil.loadExpenses();
        for(Expense e : loadedExpenses){
            service.addExpense(e);
        }
        service.initializeNextId();
        boolean running = true;
        while(running){
            System.out.println("\nSmart Expense Tracker");
            System.out.println("1. Add Expense");
            System.out.println("2. View All Expenses");
            System.out.println("3. Exit");
            System.out.println("4. Show Total Expense");
            System.out.println("5. Filter by Category");
            System.out.println("6. Monthly Summary");
            System.out.println("7. Sort by Date");
            System.out.println("8. Get Highest Expense:");
            System.out.println("9. Delete Expense by ID ");
            System.out.println("10. Update Expense by ID");

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
                case 5 :
                    try{
                        System.out.print("Enter category to filter: ");
                        String input = sc.nextLine();
                        Category cat = Category.valueOf(input.toUpperCase());
                        List<Expense> filtered = service.getExpenseByCategory(cat);
                        if (filtered.isEmpty()){
                            System.out.println("No expenses found for this category.");

                        }
                        else{
                            filtered.forEach(System.out::println);
                        }
                    }
                    catch(IllegalArgumentException e){
                        System.out.println("Invalid category ! Please enter a valid category.");
                    }
                    break;
                case 6:
                    Map<YearMonth , Double> summary = service.getMonthlySummary();
                    if(summary.isEmpty()){
                        System.out.println("No expenses recorded");
                    }
                    else{
                        summary.forEach((month,monthlytotal) -> 
                                System.out.println(month + "->" + monthlytotal));

                    }
                    break;
                case 7 :
                    List<Expense> sorted = service.getExpensesSortedByDate();
                    if (sorted.isEmpty()){
                        System.out.println("No expenses recorded");
                    }
                    else{
                        sorted.forEach(System.out::println);
                    }
                    break;
                case 8 :
                    Optional<Expense> highest = service.getHighestExpense();
                    if(highest.isPresent()){
                        System.out.println("Highest Expense: ");
                        System.out.println(highest.get());
                    }
                    else{
                        System.out.println("No expenses recorded.");
                    }
                    break;
                case 9 :
                    System.out.print("Enter ID to delete: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    boolean deleted = service.deleteExpenseById(id);
                    if(deleted){
                        FileUtil.rewriteAllExpenses(service.getAllExpenses());
                        System.out.println("Expense deleted successfully");
                    }
                    else{
                        System.out.println("Expense not found");
                    }
                    break;
                case 10 :
                    System.out.print("Enter ID to update: ");
                    int updateId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter new amount: ");
                    double newAmount = sc.nextDouble();
                    sc.nextLine();

                    System.out.print("Enter new description: ");
                    String newDescription = sc.nextLine();

                    System.out.print("Enter new date (YYYY-MM-DD): ");
                    LocalDate newDate = LocalDate.parse(sc.nextLine());

                    System.out.print("Enter new category: ");
                    Category newCategory = Category.valueOf(sc.nextLine().toUpperCase());
                    boolean updated = service.updateExpense(updateId , newAmount , newDescription ,newDate , newCategory);
                    if(updated){
                        FileUtil.rewriteAllExpenses(service.getAllExpenses());
                        System.out.println("Expense updated successfully");
                    }
                    else{
                        System.out.println("Expense ID not found.");
                    }
                    break;

                default :
                    System.out.println("Invalid choice!");
            }
        }

        
    }
    
}
