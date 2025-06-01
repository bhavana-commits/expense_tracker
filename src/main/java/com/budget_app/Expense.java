package com.budget_app;

import java.io.*;
import java.time.LocalDate;
import java.util.logging.FileHandler;

public class Expense {
    private int id;
    private String description;
    private int amount;
    private LocalDate date;
    private Category category;

    private Expense(ExpenseBuilder builder) {
        this.id=builder.id;
        this.description = builder.description;
        this.amount = builder.amount;
        this.date =builder.date;
        this.category=builder.category;
    }



    public int getId() {
        return id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if(description.isEmpty() || description.trim().isEmpty())
            throw new IllegalArgumentException("Description cannot be empty");
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        if(amount<=0)
            throw new IllegalArgumentException("Amount must be positive");
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        if(category==null)
            throw new IllegalArgumentException("Category cannot be null");
        this.category = category;
    }

    @Override
    public String toString() {
        return "Expense { ID: " + id +
                ", Description: '" + description + '\'' +
                ", Amount: " + amount +
                ", Date: " + date +
                ", Category: " + category + " }";
    }

    public static class ExpenseBuilder
    {
        private int id;
        private String description;
        private int amount;
        private LocalDate date;
        private Category category;

        public ExpenseBuilder() {
            this.id=getUniqueID();
            this.date = LocalDate.now();
        }

        private int getUniqueID()
        {
            int id=1;
            File file=new File("uniqueid.txt");
            try {
                if (!file.exists())
                    file.createNewFile();
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line = reader.readLine();
                    if (line != null && !line.isEmpty()) {
                        id = Integer.parseInt(line);
                    }
                }

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
                    writer.write(String.valueOf(id + 1));

                }
            }catch (IOException e)
            {
                System.out.println("Error");
            }
            return id;
        }

        public ExpenseBuilder setDescription(String description) {
            if(description.isEmpty() || description.trim().isEmpty())
                throw new IllegalArgumentException("Description cannot be empty");
            this.description = description;
            return this;
        }

        public ExpenseBuilder setAmount(int amount) {
            if(amount<=0)
                throw new IllegalArgumentException("Amount must be positive");
            this.amount = amount;
            return  this;
        }

        public ExpenseBuilder setCategory(Category category) {
            this.category =category;
            return  this;
        }

        public Expense build()
        {
            if(this.category==null ||this.amount<=0 || this.description==null)
                throw new IllegalArgumentException("provide required fields");
            return  new Expense(this);
        }


    }
}
