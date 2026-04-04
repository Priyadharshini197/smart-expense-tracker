package com.priyadharshini.expensetracker.service;
import java.util.*;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.YearMonth;

import java.util.Map;

import com.priyadharshini.expensetracker.model.Expense;
import com.priyadharshini.expensetracker.model.Category;

public class ExpenseService {
    private List<Expense> expenses = new ArrayList<>();
    private int nextId = 1 ;
    public void addExpense(Expense expense){
        expense.setId(nextId++);
        expenses.add(expense);
    }
    public void addLoadedExpense(Expense expense){
        expenses.add(expense);
    }
    public void initializeNextId(){
        nextId = expenses.stream()
                .mapToInt(Expense::getId)
                .max()
                .orElse(0) + 1;
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
    public Optional<Expense> getHighestExpense(){
        return expenses.stream()
                .max(Comparator.comparingDouble(Expense::getAmount));
    }
    public List<Expense> searchByDescription(String keyword){
        return expenses.stream()
                        .filter(e -> e.getDescription().toLowerCase()
                        .contains(keyword.toLowerCase()))
                        .toList();
    }
    public boolean deleteExpenseById(int id){
        return expenses.removeIf(e -> e.getId() == id);
    }
    public boolean updateExpense(int id , double amount,String description,LocalDate date , Category category ){
        for(Expense expense : expenses){
            if(expense.getId() == id){
                expense.setAmount(amount);
                expense.setDescription(description);
                expense.setDate(date);
                expense.setCategory(category);
                return true;
            }
        }
        return false;
    }
    public double getAverageExpense(){
        return expenses.stream()
                .mapToDouble(Expense::getAmount)
                .average()
                .orElse(0);
    }
    public List<Expense> getTopExpenses(int n){
        return expenses.stream()
                .sorted(Comparator.comparingDouble(Expense::getAmount).reversed())
                .limit(n)
                .toList();
    }
    public Map<Category,Double> getCategoryTotals(){
        return expenses.stream()
                .collect(Collectors.groupingBy(Expense::getCategory,
                    Collectors.summingDouble(Expense::getAmount)
                ));
    }

                            

                                


    
}
