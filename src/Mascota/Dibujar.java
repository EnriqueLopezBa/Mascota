package Mascota;

import java.awt.Color;
import java.awt.Graphics;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

public class Dibujar extends JPanel {

    int estado = 0;
    pnlEscoger escoger = new pnlEscoger(this);
    pnlInicio inicio;
    pnlJuego juego;

    public Dibujar() {

        this.setFocusable(true);
        this.requestFocusInWindow();
        setBounds(0, 0, 800, 600);
        setBackground(Color.WHITE);
        setLayout(null);
        hilo.start();

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
        if (estado == 0) {
            escoger.pintar(g);
        }
        if (estado == 1) {
            setBackground(new Color(0, 153, 204));
            inicio.pintar(g);
        }
        if (estado == 2) {
            setBackground(new Color(153, 102, 255));

            juego.pintar(g);

        }

    }

    Thread hilo = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    repaint();

                    Thread.sleep(20);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Dibujar.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    });

}
