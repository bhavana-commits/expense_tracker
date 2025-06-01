package com.budget_app;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FileHandler {
    private String FILE ="expenses.json";


    public FileHandler()
    {


    }

    private void createNewFile()
    {
        File jsonFile=new File(FILE);
        if(!jsonFile.exists())
        {
            try(Writer writer=new FileWriter(jsonFile))
            {
                writer.write("{}");
            } catch (IOException e) {
                System.out.println("Error");
            }
        }
    }

    private void checkCorruptedFile(boolean isCritical)
    {
        File jsonFile=new File(FILE);

        if(!jsonFile.exists()) return;

        try(Reader reader=new FileReader(jsonFile))
        {
            GsonObjectCreator.getInstance().getGson().fromJson(reader,new TypeToken<Map<Integer,Expense>>(){}.getType());
        }
        catch (JsonSyntaxException|IOException e)
        {
            System.out.println("Error");

            if(isCritical)
                throw  new RuntimeException();

            String backupFile="backup/expense_corrupted_"+generateTimeStamp()+".json";
            File backupjsonfile=new File(backupFile);
            jsonFile.renameTo(backupjsonfile);
            createNewFile();


        }



    }

    private String generateTimeStamp()
    {
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMdd_HHmmss");
        return dateFormat.format(new Date());
    }

    public Map<Integer,Expense> loadExpenses()
    {
        File jsonFile=new File(FILE);

        Map<Integer,Expense> data=new ConcurrentHashMap<>();

        if(!jsonFile.exists())
            return data;

        try(Reader reader=new FileReader(FILE))
        {
            data=GsonObjectCreator.getInstance().getGson().fromJson(reader,new TypeToken<Map<Integer,Expense>>(){}.getType());


        }
        catch (JsonSyntaxException e)
        {
           System.out.println("Error");
           checkCorruptedFile(false);
           return  new ConcurrentHashMap<>();
        }
        catch (IOException e)
        {
            System.out.println("Error");
        }

        return data;
    }

    public void saveExpenses(Map<Integer,Expense> data)
    {
        File jsonFile=new File(FILE);

        if(!jsonFile.exists())
        {
            createNewFile();
        }
        try(Writer writer=new FileWriter(jsonFile))
        {
            GsonObjectCreator.getInstance().getGson().toJson(data,writer);
        }
        catch (IOException e)
        {
            System.out.println("Error");

        }
    }

}
