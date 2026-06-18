package com.mycompany.paniclab.Controller;

import com.mycompany.paniclab.Model.*;
import com.mycompany.paniclab.Model.Enums.*;
import com.mycompany.paniclab.Utils.*;
import java.util.ArrayList;
import java.util.List;

public class GameController {
    
    private final List<Giocatore> giocatori;
    private final Board tabellone;
    private RisultatoDadi risultatoDadiAttuale;
    private final int turniTotali;
    private int turnoCorrente;
    private long inizioTimer;
    private long fineTimer;
    private int indiceGiocatoreAttuale;
    
    public GameController(int turni)
    {
        this.giocatori = new ArrayList();
        this.tabellone = new Board();
        this.turniTotali = turni;
        this.turnoCorrente = 0;
        this.inizioTimer = 0;
        this.fineTimer = 0;
        this.indiceGiocatoreAttuale = -1;
    }
    
    public void aggiungiGiocatore(String nome)
    {
        giocatori.add(new Giocatore(nome));
    }
    
    public void avviaGioco()
    {
        List<Carta> mazzo = GeneratoreCarte.creaMazzoRandom();
        tabellone.posizionaCarte(mazzo);
    }
    
    public boolean prossimoTurno()
    {
        if (giocatori.isEmpty())
            return false;

        indiceGiocatoreAttuale++;
        if (indiceGiocatoreAttuale >= giocatori.size())
        {
            indiceGiocatoreAttuale = 0;
            turnoCorrente++;
        }
        if (turnoCorrente >= turniTotali)
            return false;
                
        risultatoDadiAttuale = LanciatoreDadi.lanciaTutti();
        avviaTimer();
        
        return true;
    }
    
    public Carta calcolaSoluzione(RisultatoDadi dadi)
    {
        // algoritmo per calcolare la carta soluzione
        ColoreAmeba coloreCercato = dadi.getColoreAmeba();
        Forma formaCercata = dadi.getForma();
        Fantasia fantasiaCercata = dadi.getFantasia();
        Direzione direzione = dadi.getDirezione();
        
        int posizioneCorrente = tabellone.trovaIndicePartenza(dadi.getColorePartenza());
        Carta soluzioneTrovata = null;
        List<CartaSpeciale> mutazioniVisitate = new ArrayList<>();
        
        while (soluzioneTrovata == null) {
            posizioneCorrente = tabellone.prossimaPosizione(posizioneCorrente, direzione);
            Carta cartaAttuale = tabellone.getCarta(posizioneCorrente);
            
            if (cartaAttuale instanceof CartaSpeciale speciale)
            {
                if (speciale.getTipoSpeciale() == TipoSpeciale.MUTA_COLORE || 
                    speciale.getTipoSpeciale() == TipoSpeciale.MUTA_FORMA || 
                    speciale.getTipoSpeciale() == TipoSpeciale.MUTA_FANTASIA) 
                {
                    if (mutazioniVisitate.contains(speciale))
                    {                        
                        soluzioneTrovata = speciale;
                        break;
                        
                    }
                    else
                        mutazioniVisitate.add(speciale);
                }
                
                switch (speciale.getTipoSpeciale())
                {
                    case GRATA:
                        posizioneCorrente = saltaAllaProssimaGrata(posizioneCorrente, direzione);
                        break;
                    case MUTA_COLORE:
                        coloreCercato = mutaColore(coloreCercato);
                        break;
                    case MUTA_FORMA:
                        formaCercata = mutaForma(formaCercata);
                        break;
                    case MUTA_FANTASIA:
                        fantasiaCercata = mutaFantasia(fantasiaCercata);
                        break;
                    default:
                        break;
                }
            }
            else if (cartaAttuale instanceof CartaBase ameba)
            {                
                if (ameba.getColoreAmeba() == coloreCercato &&
                    ameba.getForma() == formaCercata &&
                    ameba.getFantasia() == fantasiaCercata)
                {
                    soluzioneTrovata = ameba;
                }
            }
        }
        return soluzioneTrovata;
    }
    
    public Giocatore calcolaVincitore()
    {
        if (giocatori.isEmpty())
            return null;

        Giocatore vincitore = giocatori.get(0);
        for (Giocatore g : giocatori)
        {
            if (g.getTempoTotale() < vincitore.getTempoTotale())
                vincitore = g;
        }
        return vincitore;
    }

    public void avviaTimer()
    {
        inizioTimer = System.currentTimeMillis();
    }
    
    public boolean stopTimerAssegnaPunti(Giocatore g, Carta cartaScelta)
    {
        fineTimer = System.currentTimeMillis();
        long tempoImpiegato = fineTimer - inizioTimer;
    
        Carta soluzioneCorretta = calcolaSoluzione(risultatoDadiAttuale);
    
        if (cartaScelta.equals(soluzioneCorretta))
        {
            g.aggiungiTempo(tempoImpiegato); 
            return true;

        } else {
            assegnaPenalita(g);
            return false;
        }
    }
    
    public Board getTabellone() {
        return this.tabellone;
    }
    
    public RisultatoDadi getRisultatoDadiAttuale() {
        return this.risultatoDadiAttuale;
    }
    
    public Giocatore getGiocatoreAttuale()
    {
        if (giocatori.isEmpty())
            return null; 

        return giocatori.get(indiceGiocatoreAttuale);
    }
    
    private void assegnaPenalita(Giocatore g)
    {
        g.aggiungiTempo(5000); 
    }
    
    private int saltaAllaProssimaGrata(int indiceAttuale, Direzione dir)
    {
        int indiceRicerca = tabellone.prossimaPosizione(indiceAttuale, dir);
        int indiceGrataSuccessiva = -1;
        
        while (indiceGrataSuccessiva == -1)
        {
            Carta c = tabellone.getCarta(indiceRicerca);
            if (c instanceof CartaSpeciale cartaSpeciale)
            {
                if (cartaSpeciale.getTipoSpeciale() == TipoSpeciale.GRATA) {
                    indiceGrataSuccessiva = indiceRicerca;
                }
            }
            indiceRicerca = tabellone.prossimaPosizione(indiceRicerca, dir);
        }
        return indiceGrataSuccessiva;
    }

    private ColoreAmeba mutaColore(ColoreAmeba attuale) {
        return (attuale == ColoreAmeba.ARANCIONE) ? ColoreAmeba.VIOLA : ColoreAmeba.ARANCIONE;
    }

    private Forma mutaForma(Forma attuale) {
        return (attuale == Forma.LUMACA) ? Forma.FANTASMA : Forma.LUMACA;
    }

    private Fantasia mutaFantasia(Fantasia attuale) {
        return (attuale == Fantasia.STRISCE) ? Fantasia.POIS : Fantasia.STRISCE;
    }
}