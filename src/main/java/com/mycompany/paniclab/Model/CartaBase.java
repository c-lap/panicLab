package com.mycompany.paniclab.Model;

import com.mycompany.paniclab.Model.Enums.ColoreAmeba;
import com.mycompany.paniclab.Model.Enums.Fantasia;
import com.mycompany.paniclab.Model.Enums.Forma;

public class CartaBase extends Carta {
     
    private final ColoreAmeba colore;
    private final Forma forma;
    private final Fantasia fantasia;
    
    public CartaBase(int id, String immaginePath, ColoreAmeba colore, Forma forma, Fantasia fantasia)
    {
        super(id, immaginePath);
        this.colore = colore;
        this.forma = forma;
        this.fantasia = fantasia;
    }
    
    public ColoreAmeba getColoreAmeba()
    {
        return this.colore;
    }
    
    public Forma getForma()
    {
        return this.forma;
    }
    
    public Fantasia getFantasia()
    {
        return this.fantasia;
    }
}