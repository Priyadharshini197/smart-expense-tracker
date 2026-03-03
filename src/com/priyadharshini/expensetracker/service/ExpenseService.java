package com.priyadharshini.expensetracker.service;
import java.util.*;
import java.util.stream.Collectors;
import java.time.YearMonth;

import java.util.Map;

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
    public Map<YearMonth , Double> getMonthlySummary(){
        return expenses.stream()
                .collect(Collectors.groupingBy(
                        e->YearMonth.from(e.getDate()),
                        Collectors.summingDouble(Expense::getAmount)));
    }
    public List<Expense> getExpensesSortedByDate(){
        return expenses.stream()
                .sorted(Comparator.comparing(Expense::getDate))
                .toList();

    }


    
}
