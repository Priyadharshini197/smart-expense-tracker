package com.priyadharshini.expensetracker;
import java.util.*;
import com.priyadharshini.expensetracker.service.ExpenseService;
import com.priyadharshini.expensetracker.model.Category;

import java.io.File;
import java.time.LocalDate;
import com.priyadharshini.expensetracker.model.Expense;
import com.priyadharshini.expensetracker.model.Category;
import java.time.format.DateTimeParseException;
import com.priyadharshini.expensetracker.util.FileUtil;
import java.time.YearMonth;
import java.util.stream.Collectors;
import java.util.Map;
import com.priyadharshini.expensetracker.ui.MenuRenderer;
import com.priyadharshini.expensetracker.input.InputHandler;


public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        
        ExpenseService service = new ExpenseService();
        List<Expense> loadedExpenses = FileUtil.loadExpenses();
        for(Expense e : loadedExpenses){
            service.addLoadedExpense(e);
        }
        service.initializeNextId();
        boolean running = true;
        while(running){
            MenuRenderer.showMenu();
            int choice = sc.nextInt();
            sc.nextLine();
            
            switch(choice){
                case 1:

                    double amount = InputHandler.readAmount(sc);

                    String description = InputHandler.readDescription(sc);

                    LocalDate date = InputHandler.readDate(sc);

                    Category category = InputHandler.readCategory(sc);

                    Expense expense = new Expense(amount, description, date, category);

                    service.addExpense(expense);

                    FileUtil.saveExpense(expense);

                    System.out.println("Expense added successfully");

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

                case 11:
                    System.out.print("Enter keyword to search: ");
                    String keyword = sc.nextLine();
                    List<Expense> results = service.searchByDescription(keyword);
                    if(results.isEmpty()){
                        System.out.println("No matching expenses found.");
                    }
                    else{
                        results.forEach(System.out::println);
                    }
                    break;
                
                case 12 :
                    double avg = service.getAverageExpense();
                    
                    System.out.println("Average Expense: "+ avg);
                    break;

                case 13 :
                    List<Expense> top = service.getTopExpenses(3);
                    if(top.isEmpty()){
                        System.out.println("No expenses recorded");
                    }
                    else{
                        top.forEach(System.out::println);
                    }
                    break;

                case 14 :
                    Map<Category,Double> totals = service.getCategoryTotals();
                    if(totals.isEmpty()){
                        System.out.println("No expenses recorded.");
                    }
                    else{
                        totals.forEach((cat,value) ->
                                        System.out.println(cat +" -> "+ value));

                    
                    }

                default :
                    System.out.println("Invalid choice!");
            }
        }

        
    }
    
}
