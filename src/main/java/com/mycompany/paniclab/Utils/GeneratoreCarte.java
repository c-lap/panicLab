package com.mycompany.paniclab.Utils;

import com.mycompany.paniclab.Model.Carta;
import com.mycompany.paniclab.Model.CartaBase;
import com.mycompany.paniclab.Model.CartaSpeciale;
import com.mycompany.paniclab.Model.Enums.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeneratoreCarte {
    
    public static List<Carta> creaMazzoRandom()
    {
        List<Carta> mazzo = new ArrayList<>();
        int idContatore = 1;
        
        mazzo.add(new CartaSpeciale(idContatore++, "/img/partenza_rossa.jpeg", TipoSpeciale.PARTENZA_ROSSA));
        mazzo.add(new CartaSpeciale(idContatore++, "/img/partenza_blu.jpeg", TipoSpeciale.PARTENZA_BLU));
        mazzo.add(new CartaSpeciale(idContatore++, "/img/partenza_gialla.jpeg", TipoSpeciale.PARTENZA_GIALLA));
        
        mazzo.add(new CartaSpeciale(idContatore++, "/img/muta_colore.jpeg", TipoSpeciale.MUTA_COLORE));
        mazzo.add(new CartaSpeciale(idContatore++, "/img/muta_forma.jpeg", TipoSpeciale.MUTA_FORMA));
        mazzo.add(new CartaSpeciale(idContatore++, "/img/muta_fantasia.jpeg", TipoSpeciale.MUTA_FANTASIA));

        mazzo.add(new CartaSpeciale(idContatore++, "/img/grata.jpeg", TipoSpeciale.GRATA));
        mazzo.add(new CartaSpeciale(idContatore++, "/img/grata.jpeg", TipoSpeciale.GRATA));
        mazzo.add(new CartaSpeciale(idContatore++, "/img/grata.jpeg", TipoSpeciale.GRATA));
        
        for (ColoreAmeba c : ColoreAmeba.values())
        {
            for (Forma f : Forma.values())
            {
                for (Fantasia fa : Fantasia.values())
                {                    
                    String imgPath = "/img/" + f.name().toLowerCase() + "_" + c.name().toLowerCase() + "_" + fa.name().toLowerCase() + ".jpeg";                    
                    mazzo.add(new CartaBase(idContatore++, imgPath, c, f, fa));
                    mazzo.add(new CartaBase(idContatore++, imgPath, c, f, fa));
                }
            }
        }
        Collections.shuffle(mazzo);
            
        return mazzo;
    }
}