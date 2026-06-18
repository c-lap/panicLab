package com.mycompany.paniclab.Utils;

import com.mycompany.paniclab.Model.Enums.*;
import com.mycompany.paniclab.Model.RisultatoDadi;
import java.util.Random;

public class LanciatoreDadi {
    
    private static final Random random = new Random();
        
    public static RisultatoDadi lanciaTutti()
    {        
        RisultatoDadi risultato = new RisultatoDadi(
                estraiRandom(ColoreAmeba.class),
                estraiRandom(Forma.class),
                estraiRandom(Fantasia.class),
                estraiRandom(Direzione.class),
                estraiRandom(ColorePartenza.class));
    
        return risultato;
    }
    
    private static <T extends Enum<T>> T estraiRandom(Class<T> classeEnum)
    {
        T[] valori = classeEnum.getEnumConstants();
        return valori[random.nextInt(valori.length)];
    }
}