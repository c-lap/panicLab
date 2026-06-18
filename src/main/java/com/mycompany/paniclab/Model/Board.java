package com.mycompany.paniclab.Model;

import com.mycompany.paniclab.Model.Enums.*;
import java.util.List;

public class Board {
    
    private static final int DIMENSIONE_TABELLONE = 25;
    private final Carta[] caselle = new Carta[25];
    
    public void posizionaCarte(List<Carta> mazzoGenerato)
    {
        if(mazzoGenerato.size() != DIMENSIONE_TABELLONE)
            throw new IllegalArgumentException("Il mazzo deve contenere esattamente 25 carte");
        
        for(int i = 0; i < DIMENSIONE_TABELLONE; i++)
            this.caselle[i] = mazzoGenerato.get(i);
    }
    
    public Carta getCarta(int index)
    {
        return caselle[index];
    }
    
    public int prossimaPosizione(int indexAttuale, Direzione direzione)
    {
        if(indexAttuale < 0 || indexAttuale >= DIMENSIONE_TABELLONE)
            throw new IllegalArgumentException("Indice di partenza non valido");
     
        if(direzione == Direzione.ORARIO)
            return (indexAttuale + 1) % DIMENSIONE_TABELLONE;
        
        else
           return (indexAttuale - 1 + DIMENSIONE_TABELLONE) % DIMENSIONE_TABELLONE;
    }
    
    public int trovaIndicePartenza(ColorePartenza colorePartenza)
    {        
        TipoSpeciale tipoCercato = null;
        
        if (colorePartenza != null)
        {
            switch (colorePartenza)
            {
                case ROSSA:
                    tipoCercato = TipoSpeciale.PARTENZA_ROSSA;
                    break;
                case BLU:
                    tipoCercato = TipoSpeciale.PARTENZA_BLU;
                    break;
                case GIALLA:
                    tipoCercato = TipoSpeciale.PARTENZA_GIALLA;
                    break;
                default:
                    break;
            }
        }

        for (int i = 0; i < DIMENSIONE_TABELLONE; i++)
        {
            Carta c = caselle[i];            
            if (c instanceof CartaSpeciale speciale)
            {
                if (speciale.getTipoSpeciale() == tipoCercato)
                    return i;
            }
        }
        throw new IllegalStateException("Errore: laboratorio di partenza non trovato sul tabellone!");
    }
}