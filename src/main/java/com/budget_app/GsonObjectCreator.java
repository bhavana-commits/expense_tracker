package com.budget_app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.LocalDate;

public class GsonObjectCreator {
    private static GsonObjectCreator instance=null;
    private Gson gson;


    private GsonObjectCreator(){

        this.gson=new GsonBuilder().registerTypeAdapter(LocalDate.class,new LocalDateAdapter()).create();
    }

    public static GsonObjectCreator getInstance()
    {
        if(instance==null)
        {
            synchronized (GsonObjectCreator.class)
            {
                if(instance==null)
                {
                    instance=new GsonObjectCreator();
                }
            }
        }

        return  instance;
    }

    public Gson getGson()
    {
        return this.gson;
    }
}
