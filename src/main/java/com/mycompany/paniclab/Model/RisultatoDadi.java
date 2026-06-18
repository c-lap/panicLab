package com.mycompany.paniclab.Model;

import com.mycompany.paniclab.Model.Enums.ColoreAmeba;
import com.mycompany.paniclab.Model.Enums.Forma;
import com.mycompany.paniclab.Model.Enums.Fantasia;
import com.mycompany.paniclab.Model.Enums.Direzione;
import com.mycompany.paniclab.Model.Enums.ColorePartenza;

public class RisultatoDadi {
    
    private final ColoreAmeba coloreAmeba;
    private final Forma forma;
    private final Fantasia fantasia;
    private final Direzione direzione;
    private final ColorePartenza  colorePartenza;
    
    public RisultatoDadi(ColoreAmeba coloreAmeba, Forma forma, Fantasia fantasia, Direzione direzione, ColorePartenza colorePartenza)
    {
        this.coloreAmeba = coloreAmeba;
        this.forma = forma;
        this.fantasia = fantasia;
        this.direzione = direzione;
        this.colorePartenza = colorePartenza;
    }
    
    public ColoreAmeba getColoreAmeba()
    {
        return this.coloreAmeba;
    }
    
    public Forma getForma()
    {
        return this.forma;
    }
    
    public Fantasia getFantasia()
    {
        return this.fantasia;
    }
    
    public Direzione getDirezione()
    {
        return this.direzione;
    }
    
    public ColorePartenza getColorePartenza()
    {
        return this.colorePartenza;
    }
}