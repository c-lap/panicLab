package com.mycompany.paniclab;

import com.mycompany.paniclab.Controller.GameController;
import com.mycompany.paniclab.View.GameUI;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class PanicLab {
    public static void main(String[] args)
    {        
        // inizializzazione gioco
        JFrame frameIniziale = new JFrame("Panic Lab!");
        frameIniziale.setSize(800, 700);
        frameIniziale.setLocationRelativeTo(null);
        frameIniziale.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel pannelloSfondo = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                URL imgUrl = getClass().getResource("/img/panic_lab.jpeg");
                if (imgUrl != null) {
                    Image img = new ImageIcon(imgUrl).getImage();
                    g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        
        frameIniziale.setContentPane(pannelloSfondo);
        frameIniziale.setVisible(true);

        int turni = 3;
        try {
            String inputTurni = JOptionPane.showInputDialog(frameIniziale, 
                    "Quanti turni volete giocare?", 
                    "Impostazioni Partita", 
                    JOptionPane.QUESTION_MESSAGE);
            
            if (inputTurni == null) System.exit(0); 
            turni = Integer.parseInt(inputTurni);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frameIniziale, "Numero non valido, partiranno 3 turni di default");
        }

        GameController controller = new GameController(turni);

        int numGiocatori = 2;
        try
        {
            String inputGiocatori = JOptionPane.showInputDialog(frameIniziale, 
                    "Quanti giocatori partecipano?", 
                    "Impostazioni Giocatori", 
                    JOptionPane.QUESTION_MESSAGE);
            
            if (inputGiocatori == null) System.exit(0);
            numGiocatori = Integer.parseInt(inputGiocatori);
        } catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(frameIniziale, "Numero non valido, si giocherà con 2 giocatori di default");
        }

        for (int i = 1; i <= numGiocatori; i++) {
            String nome = JOptionPane.showInputDialog(frameIniziale, 
                    "Inserisci il nome del Giocatore " + i + ":", 
                    "Nome Giocatore", 
                    JOptionPane.PLAIN_MESSAGE);
            
            if (nome == null || nome.trim().isEmpty()) {
                nome = "Giocatore " + i;
            }
            controller.aggiungiGiocatore(nome);
        }
        frameIniziale.dispose();
        
        // avvio gioco
        GameUI gui = new GameUI(controller);        
        controller.avviaGioco();        
        gui.inizializzaTabellone(controller.getTabellone());        
        controller.prossimoTurno();
        
        gui.mostraTurno(controller.getGiocatoreAttuale());
        gui.mostraRisultatoDadi(controller.getRisultatoDadiAttuale());        
        gui.setVisible(true);
    }
}