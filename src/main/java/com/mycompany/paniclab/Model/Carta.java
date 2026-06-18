package com.mycompany.paniclab.Model;

public abstract class Carta {
    
    private final int id;
    private final String immaginePath;
    
    public Carta(int id, String immaginePath)
    {
        this.id = id;
        this.immaginePath = immaginePath;
    }
    
    public int getId()
    {
        return this.id;
    }
    
    public String getImagePath()
    {
        return this.immaginePath;
    }
}