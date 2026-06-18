package com.mycompany.paniclab.Model;

import com.mycompany.paniclab.Model.Enums.TipoSpeciale;

public class CartaSpeciale extends Carta {
    
    private final TipoSpeciale tipo;
    
    public CartaSpeciale(int id, String immaginePath, TipoSpeciale tipo)
    {
        super(id, immaginePath);
        this.tipo = tipo;
    }
    
    public TipoSpeciale getTipoSpeciale()
    {
        return this.tipo;
    }
}