package com.mycompany.paniclab.Model;

public class Giocatore {
    
    private final String nome;
    private long tempoTotale;
    private long ultimoTempo;
    
    public Giocatore(String nome)
    {
        this.nome = nome;
        this.tempoTotale = 0;
        this.ultimoTempo = 0;
    }
    
    public void aggiungiTempo(long tempo)
    {
        this.ultimoTempo = tempo;
        this.tempoTotale += tempo;
    }
    
    public String getNome()
    {
        return this.nome;
    }
    
    public long getUltimoTempo()
    {
        return this.ultimoTempo;
    }
    
    public long getTempoTotale()
    {
        return this.tempoTotale;
    }
}