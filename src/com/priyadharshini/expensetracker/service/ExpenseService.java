package com.priyadharshini.expensetracker.service;
import java.util.*;
import com.priyadharshini.expensetracker.model.Expense;
import com.priyadharshini.expensetracker.model.Category;

public class ExpenseService {
    private List<Expense> expenses = new ArrayList<>();
    public void addExpense(Expense expense){
        expenses.add(expense);
    }
    public List<Expense> getAllExpenses(){
        return expenses;
    }
    public double getTotalExpense(){
        return expenses.stream()
                        .mapToDouble(Expense::getAmount)
                        .sum();
    }
    public List<Expense> getExpenseByCategory(Category category){
        return expenses.stream()
                        .filter(e -> e.getCategory() == category)
                        .toList();
    }


    
}
