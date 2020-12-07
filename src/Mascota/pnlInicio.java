package Mascota;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.text.StyleConstants;

public class pnlInicio {

    Dibujar puntero;
    StatusPet status = new StatusPet();
        int posMascota = 180;

    Graphics g2;
    int estado = 0;
    
    JButton comida = new JButton("Comer",new ImageIcon(getClass().getResource("/Resources/comer.png")));
    JButton sed = new JButton("Agua",new ImageIcon(getClass().getResource("/Resources/agua.png")));
    JButton limpieza = new JButton("Limpiar",new ImageIcon(getClass().getResource("/Resources/limpiar.png")));
    JButton jugar = new JButton("Jugar",new ImageIcon(getClass().getResource("/Resources/jugar.png")));

    
    
    public pnlInicio(Dibujar puntero) {
     
        this.puntero = puntero;
        hiloMovimiento.start();
        comida.setBounds(50, 40, 50, 80);
        sed.setBounds(150, 40, 50, 80);
        limpieza.setBounds(250, 40, 50, 80);
        jugar.setBounds(350, 40, 50, 80);
        comida = fondoTransparente(comida);
        sed = fondoTransparente(sed);
        limpieza = fondoTransparente(limpieza);
        jugar = fondoTransparente(jugar);

    }
    
    public JButton fondoTransparente(JButton a){
        a.setBorder(null);
        a.setContentAreaFilled(false);
        a.setBorderPainted(false);
        a.setVerticalTextPosition(SwingConstants.BOTTOM);
        a.setHorizontalTextPosition(SwingConstants.CENTER);
        a.setForeground(Color.WHITE);
        a.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return a;
    }

    public void activarBotones(){
         puntero.add(comida);
         puntero.add(sed);
         puntero.add(limpieza);
         puntero.add(jugar);

        comida.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(status.getHambre() != 100){
                    status.setHambre(status.getHambre()+5);
                     status.updateBars();
                }
                else
                    showMessageDialog(null, "Estoy lleno");
            }
            
        });
        sed.addMouseListener(new MouseAdapter() {
             @Override
             public void mouseClicked(MouseEvent e) {
                 if(status.getSed() != 100){
                     status.setSed(status.getSed()+5);
                     status.updateBars();
                 }
                 else
                     showMessageDialog(null, "No tengo sed");
             }
            
        });
        limpieza.addMouseListener(new MouseAdapter() {
             @Override
             public void mouseClicked(MouseEvent e) {
                 if(status.getLimpieza() != 100){
                     status.setLimpieza(status.getLimpieza() + 5);
                      status.updateBars();
                 }
                 else
                     showMessageDialog(null, "Ya estoy limpio");
             }
            
        });
        jugar.addMouseListener(new MouseAdapter() {
             @Override
             public void mouseClicked(MouseEvent e) {
                 puntero.removeAll();
                 puntero.juego = new pnlJuego(puntero);
                 puntero.addKeyListener(puntero.juego);
                 puntero.estado = 2;
                 status.setPausado(true);
                 
             }
            
        });
    }
    boolean onlyOne = false;
    public void pintar(Graphics g) {
        g2 = g;
        //DIBUJAR CARPETA
        g.drawImage(new ImageIcon(getClass().getResource("/Resources/carpet8.png")).getImage(), 70, 500, 750, 128, puntero);

        //DIBUJAR MASCOTA
        if(!onlyOne)
        g.drawImage(new ImageIcon(getClass().getResource("/Resources/cat/Walk (" + i + ").png")).getImage(), posMascota, 430, 150, 120, puntero);
        
        //SI SU SALUD LLEGA A 0
        if(onlyOne){
        g.drawImage(new ImageIcon(getClass().getResource("/Resources/cat/Dead (" + i + ").png")).getImage(), posMascota, 430, 150, 120, puntero);
        }

        g.setColor(Color.white);

        //DIBUJAR BARRAS
        g.drawString("HAMBRE", 750, 50);
        puntero.add(status.getBarraHambre());
        g.drawString("SED", 765, 100);
        puntero.add(status.getBarraSed());
        g.drawString("SALUD", 750, 150);
        puntero.add(status.getBarraSalud());
        g.drawString("LIMPIEZA", 740, 200);
        puntero.add(status.getBarraLimpieza());
        g.drawString("ABURRIMIENTO", 700, 250);
        puntero.add(status.getBarraAburrimiento());
        //FIN DIBUJADO DE BARRAS

    }

    int i = 1;
    boolean entrar = true;
    boolean ejecutar = true;
    Thread hiloMovimiento = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                 if(status.getSalud() == 0){
                     if(!onlyOne){
                               onlyOne = true;
                    ejecutar = false;
                     i = (i + 1) % 11;
                        if (i == 0) {
                        i++;
                     }
                        if(i == 10)
                            onlyOne = false;
                     }
                     
              
                    return;
                }
                if(ejecutar){
                  
                try {
                    if (g2 != null) {
                        switch (estado) {
                            case 0:
                                if (posMascota < 450) {
                                    posMascota += 5;
                                    i = (i + 1) % 11;
                                    if (i == 0) {
                                        i++;
                                    }
                                } else {
                                    entrar = true;
                                    estado = 1;
                                }
                                break;
                            case 1:
                                if (posMascota > 180) {
                                    if(entrar){
                                       i = 10;
                                       entrar = false;
                                   }
                                   i++;
                                   if(i == 21)
                                       i = 11;
                                    posMascota -= 5;
                                   
                                } else {
                                    estado = 0;
                                }
                                break;
                        }
                       
                    }
                    Thread.sleep(100);

                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                }
                }else
                try {
                   
                    Thread.sleep(300);
                } catch (InterruptedException ex) {
                    Logger.getLogger(pnlInicio.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }

        }
    });

}
