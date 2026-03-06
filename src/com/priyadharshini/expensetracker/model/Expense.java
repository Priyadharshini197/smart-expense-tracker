package com.priyadharshini.expensetracker.model;
import java.time.LocalDate;


public class Expense {
    private int id;
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
    public Expense(int id , double amount , String description,LocalDate date,Category category){
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.date = date ;
        this.category = category;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public double getAmount(){
        return amount;
    }
    public void setAmount(double amount){
        this.amount = amount;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setDate(LocalDate date){
        this.date = date;
    }
    public void setCategory(Category category){
        this.category = category;
        
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
        return "ID: " + id +
            " | Amount: " + amount +
            " | Description:  "+  description + 
            " | Date:  " + date + 
            " | Category:  " + category ;
    }

    
}
