package com.budget_app;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExpenseRegistry {
    private Map<Integer,Expense> data;
    private FileHandler fileHandler;
    public ExpenseRegistry()
    {

        this.fileHandler=new FileHandler();
        this.data=fileHandler.loadExpenses();
    }

    public Expense getExpense(Integer id)
    {
        return data.get(id);
    }

    public void addExpense(Expense expense)
    {
        data.put(expense.getId(),expense);
        fileHandler.saveExpenses(data);
    }

    public void deleteExpense(Integer id)
    {
        data.remove(id);
        fileHandler.saveExpenses(data);

    }

    public Map<Integer,Expense> getExpenses()
    {
        return data;
    }

    public void totalExpense()
    {
        int totalAmount=0;
        for(Expense e:data.values())
        {
            totalAmount+=e.getAmount();
        }

        System.out.println("Total Expenses: "+totalAmount);

    }

    public void displayExpenses()
    {
        System.out.println("ID | Date | Description | Category | Amount");
        for(Expense e:data.values())
        {
            System.out.println(e.getId()+ " | "+e.getDate()+" | "+e.getDescription()+" | "+e.getCategory()+" | "+e.getAmount());
        }
    }

}
