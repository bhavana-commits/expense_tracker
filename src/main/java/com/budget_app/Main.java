package com.budget_app;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {

        Scanner scanner=new Scanner(System.in);
        ExpenseRegistry expenseRegistry=new ExpenseRegistry();
        CommandManager commandManager=new CommandManager(expenseRegistry);
        while(true)
        {
            System.out.println("Enter command:");

            String command=scanner.nextLine();
            if(command.equalsIgnoreCase("exit"))
                break;
            commandManager.processCommand(command);
        }


    }
}