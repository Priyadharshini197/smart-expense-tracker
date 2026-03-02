package com.priyadharshini.expensetracker.service;
import java.util.*;
import com.priyadharshini.expensetracker.model.Expense;
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


    
}
