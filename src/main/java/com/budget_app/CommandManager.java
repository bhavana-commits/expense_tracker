package com.budget_app;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CommandManager {
    private ExpenseRegistry expenseRegistry;

    public CommandManager(ExpenseRegistry expenseRegistry)
    {
        this.expenseRegistry=expenseRegistry;
    }

    public void processCommand(String commandLine)
    {
        String[] command=commandLine.split(" ");
        Map<String,String> commandData=new HashMap<>();
        switch (command[0])
        {
            case "add":
                commandData=parseData(String.join(" ", Arrays.copyOfRange(command,1,command.length)));
                processAddCommand(commandData);
                break;
            case "delete":
                commandData=parseData(String.join(" ", Arrays.copyOfRange(command,1,command.length)));
                processDeleteCommand(commandData);
                break;
            case "list":
                processListCommand();
                break;
            case "summary":
                processSummaryCommand();
                break;
        }
    }

    private Map<String,String> parseData(String Line)
    {
        Map<String,String> data=new HashMap<>();
        String[] arguments=Line.split(" ");
        for(int i=0;i<arguments.length;i=i+2)
        {
            data.put(arguments[i],arguments[i+1]);
        }
        return data;

    }

    private void processAddCommand(Map<String,String> commandData)
    {
        expenseRegistry.addExpense(new Expense.ExpenseBuilder()
                .setDescription(commandData.get("--description"))
                .setCategory(Category.valueOf(commandData.get("--category").toUpperCase()))
                .setAmount(Integer.parseInt(commandData.get("--amount")))
                .build());

    }

    private void processDeleteCommand(Map<String,String> commandData)
    {
        expenseRegistry.deleteExpense(Integer.parseInt(commandData.get("--id")));
    }

    private void processListCommand()
    {
        expenseRegistry.displayExpense();
    }

    private void processSummaryCommand()
    {
        expenseRegistry.totalExpense();
    }

}
