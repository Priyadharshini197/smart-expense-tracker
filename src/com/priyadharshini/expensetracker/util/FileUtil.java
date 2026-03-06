package com.priyadharshini.expensetracker.util;
import java.io.*;
import java.util.*;
import com.priyadharshini.expensetracker.model.*;
import java.time.LocalDate;


public class FileUtil {
    private static final String FILE_PATH = "data/expenses.csv";
    public static void saveExpense(Expense expense){
        try{
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file,true));
            String line = expense.getId() + "," +
                          expense.getAmount() + "," +
                          expense.getDescription() + "," +
                          expense.getDate() + "," +
                          expense.getCategory();
            writer.write(line);
            writer.newLine();
            writer.close();
            System.out.println("Expense saved to file successfully");
        }
        catch(IOException e){
            System.out.println("Error savings expense to file.");
            e.printStackTrace();
        }
    }
    public static List<Expense> loadExpenses(){
        List<Expense> expenses = new ArrayList<>();
        try{
            File file = new File(FILE_PATH);
            if(!file.exists()){
                return expenses;
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine()) != null){
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                double amount = Double.parseDouble(parts[1]);
                String description = parts[2];
                LocalDate date = LocalDate.parse(parts[3]);
                Category category = Category.valueOf(parts[4]);
                Expense expense = new Expense(id,amount , description ,date , category);
                expenses.add(expense);

            }
            reader.close();
        }
        catch(IOException e){
            System.out.println("Error loading expenses from file.");
            e.printStackTrace();

        }
        return expenses;
    }
    public static void rewriteAllExpenses(List<Expense> expenses){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH , false))){
            for(Expense expense : expenses){
                String line = expense.getId() +"," +
                              expense.getAmount() + "," +
                              expense.getDescription() + "," +
                              expense.getDate() + "," +
                              expense.getCategory();
                writer.write(line);
                writer.newLine();

            }
            System.out.println("File updated successfully.");
        }
        catch (IOException e){
            System.out.println("Error rewriting file.");
            e.printStackTrace();
        }
    }

    

}
