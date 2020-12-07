package Mascota;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JPanel;

/**
 *
 * @author Enrique
 */
public class pnlEscoger {

    Dibujar puntero;
 JButton seleccionar = new JButton("SELECCIONAR");
    JButton seleccionar2 = new JButton("SELECCIONAR");
    JButton seleccionar3 = new JButton("SELECCIONAR");
    
    public pnlEscoger(Dibujar puntero) {
        this.puntero = puntero;
        seleccionar.setBounds(380, 395, 130, 30);
        seleccionar2.setBounds(200, 395, 130, 30);
        seleccionar3.setBounds(550, 395, 130, 30);
        puntero.add(seleccionar);
        puntero.add(seleccionar2);
        puntero.add(seleccionar3);
         seleccionar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                puntero.estado = 1;
                puntero.inicio = new pnlInicio(puntero);
                puntero.inicio.activarBotones();
                puntero.remove(seleccionar);
                puntero.remove(seleccionar2);
                puntero.remove(seleccionar3);
             
            }
            
        });
        hilo.start();

    }
    Graphics2D g2;
   static boolean iniciar = false;
    public void pintar(Graphics g) {
        g2 = (Graphics2D) g;
        g2.setColor(Color.blue);
        g2.drawImage(new ImageIcon(getClass().getResource("/Resources/fondo.jpg")).getImage(), 40, 50, puntero);
        g2.drawImage(new ImageIcon(getClass().getResource("/Resources/cat/Idle (" + i + ").png")).getImage(), 350, 270, 150, 120, puntero);
        g2.drawImage(new ImageIcon(getClass().getResource("/Resources/dog/Idle (" + i + ").png")).getImage(), 200, 270, 150, 120, puntero);
        g2.drawImage(new ImageIcon(getClass().getResource("/Resources/dino/Idle (" + i + ").png")).getImage(), 540, 270, 150, 120, puntero);
        
    }

    
    int i = 1;
    Thread hilo = new Thread(new Runnable() {
        @Override
        public void run() {

            while (true) {
            
                 
                try {
                   
                        if(g2 != null){
                        i  = (i+1) % 11;
                        if(i == 0)i++;
                        Thread.sleep(38);
                         
                        }
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                }
               
            }

        }
    });

}
