package com.budget_app;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            case "update":
                commandData=parseData(String.join(" ",Arrays.copyOfRange(command,1,command.length)));
                processUpdateCommand(commandData);
                break;
            case "list":
                processListCommand();
                break;
            case "summary":
                processSummaryCommand();
                break;
        }
    }

    private void processUpdateCommand(Map<String, String> commandData) {
        int id=Integer.parseInt(commandData.get("--id"));
        Expense temp=expenseRegistry.getExpense(id);
        if(commandData.containsKey("--description"))
            temp.setDescription(commandData.get("--description"));


        if(commandData.containsKey("--category"))
            temp.setCategory(Category.valueOf(commandData.get("--category").toUpperCase()));


        if(commandData.containsKey("--amount"))
            temp.setAmount(Integer.parseInt(commandData.get("--amount")));

        expenseRegistry.deleteExpense(id);
        expenseRegistry.addExpense(temp);
    }


    private Map<String,String> parseData(String Line)
    {
        String regex="\"([^\"]*)\"|(\\S+)";
        Matcher m= Pattern.compile(regex).matcher(Line);
        List<String> tokens=new ArrayList<>();
        while(m.find()){
            tokens.add(m.group(1) != null ? m.group(1) : m.group(2));
        }

        Map<String,String> data=new HashMap<>();
        for(int i=0;i<tokens.size();i=i+2)
        {
            data.put(tokens.get(i),tokens.get(i+1));
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
        expenseRegistry.displayExpenses();
    }

    private void processSummaryCommand()
    {
        expenseRegistry.totalExpense();
    }

}
