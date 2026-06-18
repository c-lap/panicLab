package com.mycompany.paniclab.View;

import com.mycompany.paniclab.Controller.GameController;
import com.mycompany.paniclab.Model.Board;
import com.mycompany.paniclab.Model.Carta;
import com.mycompany.paniclab.Model.Giocatore;
import com.mycompany.paniclab.Model.RisultatoDadi;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class GameUI extends JFrame
{
    private final GameController controller;
    private Giocatore giocatoreAttuale;

    private final JPanel pannelloTabellone;
    private final JLabel lblTurno;
    private final JLabel lblDadi;    
    private final JLabel lblDadoPartenza = new JLabel("", SwingConstants.CENTER);
    private final JLabel lblDadoForma = new JLabel("", SwingConstants.CENTER);
    private final JLabel lblDadoColore = new JLabel("", SwingConstants.CENTER);
    private final JLabel lblDadoFantasia = new JLabel("", SwingConstants.CENTER);
    private final JLabel lblDadoDirezione = new JLabel("", SwingConstants.CENTER);    
    private final JLabel lblTimer;
    private final Timer timerGrafico;
    

    public GameUI(GameController controller)
    {
        this.controller = controller;
        
        setTitle("Panic Lab");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel pannelloInfo = new JPanel(new GridLayout(3, 1));
        lblTurno = new JLabel("Turno: ", SwingConstants.CENTER);
        lblTurno.setFont(new Font("Arial", Font.BOLD, 18));
        
        lblDadi = new JLabel("Dadi: (Lancia per iniziare)", SwingConstants.CENTER);
        lblDadi.setFont(new Font("Arial", Font.PLAIN, 16));
        
        lblTimer = new JLabel("Tempo: 0 ms", SwingConstants.CENTER);
        lblTimer.setFont(new Font("Arial", Font.BOLD, 16));
        lblTimer.setForeground(Color.RED);

        pannelloInfo.add(lblTurno);
        pannelloInfo.add(lblDadi);
        pannelloInfo.add(lblTimer);
        add(pannelloInfo, BorderLayout.NORTH);

        pannelloTabellone = new JPanel();
        pannelloTabellone.setLayout(new BorderLayout(10, 10));      
        add(pannelloTabellone, BorderLayout.CENTER);

        timerGrafico = new Timer(100, e -> aggiornaTimer(System.currentTimeMillis()));
    }

    public void inizializzaTabellone(Board tabellone)
    {
        pannelloTabellone.removeAll();

        JPanel pnlNord = new JPanel(new GridLayout(1, 7, 5, 5));
        JPanel pnlEst = new JPanel(new GridLayout(6, 1, 5, 5));
        JPanel pnlSud = new JPanel(new GridLayout(1, 6, 5, 5));
        JPanel pnlOvest = new JPanel(new GridLayout(6, 1, 5, 5));

        JButton[] bottoni = new JButton[25];
        
        for (int i = 0; i < 25; i++)
        {
            Carta carta = tabellone.getCarta(i);
            JButton bottoneCarta = new JButton();

            // Caricamento Immagine
            URL imgUrl = getClass().getResource(carta.getImagePath());
            if (imgUrl != null)
            {
                ImageIcon icona = new ImageIcon(new ImageIcon(imgUrl).getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH));
                bottoneCarta.setIcon(icona);
            }

            bottoneCarta.addActionListener(e ->
            {
                timerGrafico.stop();                
                boolean rispostaEsatta = controller.stopTimerAssegnaPunti(giocatoreAttuale, carta);
                
                if (rispostaEsatta)
                    JOptionPane.showMessageDialog(this, "RISPOSTA ESATTA!\nTempo: " + giocatoreAttuale.getUltimoTempo() + " ms");
                else
                    mostraPenalita();

                if (controller.prossimoTurno())
                {
                    Giocatore giocatoreDiTurno = controller.getGiocatoreAttuale();
                    mostraTurno(giocatoreDiTurno);
                    mostraRisultatoDadi(controller.getRisultatoDadiAttuale());
                } else {
                    Giocatore vincitore = controller.calcolaVincitore();
                    mostraVincitore(vincitore);
                }
            });
            bottoni[i] = bottoneCarta;
        }

        for (int i = 0; i <= 6; i++)
            pnlNord.add(bottoni[i]);

        for (int i = 7; i <= 12; i++)
            pnlEst.add(bottoni[i]);

        for (int i = 18; i >= 13; i--)
            pnlSud.add(bottoni[i]);

        for (int i = 24; i >= 19; i--)
            pnlOvest.add(bottoni[i]);

        pannelloTabellone.add(pnlNord, BorderLayout.NORTH);
        pannelloTabellone.add(pnlEst, BorderLayout.EAST);
        pannelloTabellone.add(pnlSud, BorderLayout.SOUTH);
        pannelloTabellone.add(pnlOvest, BorderLayout.WEST);
        
        JPanel pannelloDadiCentro = new JPanel();
        pannelloDadiCentro.setLayout(new BoxLayout(pannelloDadiCentro, BoxLayout.Y_AXIS));
        pannelloDadiCentro.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0)); 

        JPanel riga1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 0));
        riga1.add(lblDadoForma);
        riga1.add(lblDadoFantasia);
        riga1.add(lblDadoColore);
        JPanel riga2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 15)); 
        riga2.add(lblDadoPartenza);
        riga2.add(lblDadoDirezione);

        pannelloDadiCentro.add(riga1);
        pannelloDadiCentro.add(riga2);
        pannelloTabellone.add(pannelloDadiCentro, BorderLayout.CENTER);

        pannelloTabellone.revalidate();
        pannelloTabellone.repaint();
    }

    public void mostraTurno(Giocatore giocatore)
    {
        this.giocatoreAttuale = giocatore;
        lblTurno.setText("Turno di: " + giocatore.getNome());        
        timerGrafico.start();
    }

    public void mostraRisultatoDadi(RisultatoDadi dadi)
    {
        String testoDadi = String.format("<html><div style='text-align: center;'>Cerca: %s a %s %s<br>da partenza %s in senso %s</div></html>",
                dadi.getForma(),
                dadi.getFantasia(),
                dadi.getColoreAmeba(),
                dadi.getColorePartenza(),
                dadi.getDirezione()).toLowerCase();
                
        lblDadi.setText(testoDadi);
        
        impostaFacciaDado(lblDadoForma, "/img/dado_forma_" + dadi.getForma().name().toLowerCase() + ".jpeg");
        impostaFacciaDado(lblDadoFantasia, "/img/dado_fantasia_" + dadi.getFantasia().name().toLowerCase() + ".jpeg");
        impostaFacciaDado(lblDadoColore, "/img/dado_colore_" + dadi.getColoreAmeba().name().toLowerCase() + ".jpeg");
        impostaFacciaDado(lblDadoPartenza, "/img/dado_partenza_" + dadi.getColorePartenza().name().toLowerCase() + ".jpeg");

        lblDadoDirezione.setIcon(null); // Niente immagine
        lblDadoDirezione.setText("<html><h2 style='color: black;'>" + dadi.getDirezione().name() + "</h2></html>");
    }
    
    public void aggiornaTimer(long tempoCorrente)
    {
        lblTimer.setText("Timer in corso...");
    }

    public void mostraPenalita()
    {
        JOptionPane.showMessageDialog(this, 
                "SBAGLIATO! Hai ricevuto una penalità di 5 secondi.", 
                "Penalità", 
                JOptionPane.ERROR_MESSAGE);
    }

    public void mostraVincitore(Giocatore vincitore)
    {
        JOptionPane.showMessageDialog(this, 
                "PARTITA TERMINATA!\nIl vincitore è " + vincitore.getNome() + "\nTempo Totale: " + vincitore.getTempoTotale() + " ms", 
                "Fine Partita", 
                JOptionPane.INFORMATION_MESSAGE);
        
        System.exit(0);
    }

    private void impostaFacciaDado(JLabel label, String imagePath) 
    {
        URL imgUrl = getClass().getResource(imagePath);
        if (imgUrl != null)
        {
            ImageIcon icona = new ImageIcon(new ImageIcon(imgUrl).getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH));
            label.setIcon(icona);
            label.setText("");
        }
    }
}