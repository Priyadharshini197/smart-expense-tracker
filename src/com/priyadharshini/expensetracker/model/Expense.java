package com.priyadharshini.expensetracker.model;
import java.time.LocalDate;


public class Expense {
    private double amount;
    private  String description;
    private LocalDate date ;
    private Category category;
    public Expense(double amount , String description ,LocalDate date,Category category){
        this.amount = amount;
        this.description = description;
        this.date = date;
        this.category = category;
    }
    public double getAmount(){
        return amount;
    }
    public String getDescription(){
        return description;
    }
    public LocalDate getDate(){
        return date;
    }
    public Category getCategory(){
        return category;
    }
    @Override
    public String toString() {
        return "Amount: " + amount +
        " | Description:  "+  description + 
        " | Date:  " + date + 
        " | Category:  " + category ;
    }

    
}
