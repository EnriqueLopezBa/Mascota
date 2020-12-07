package Mascota;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Random;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

/**
 *
 * @author Enrique
 */
public class StatusPet {

   private int hambre, sed, salud, limpieza, aburrimiento;
   private JProgressBar barraHambre, barraSed, barraSalud, barraLimpieza, barraAburrimiento;
   private boolean pausado = false;
   final int tiempoMaximo = 100;
   final int tiempoMinmo = 50;
    public StatusPet() {
        hambre = sed = salud = limpieza = aburrimiento = 100;
        iniciarStatus();
        barraHambre = new JProgressBar(SwingConstants.HORIZONTAL, 0, 100);
        barraHambre.setBounds(750, 50, 50, 20);
        barraSed = new JProgressBar(SwingConstants.HORIZONTAL, 0, 100);
        barraSed.setBounds(750, 100, 50, 20);
        barraSalud = new JProgressBar(SwingConstants.HORIZONTAL, 0, 100);
        barraSalud.setBounds(750, 150, 50, 20);
        barraLimpieza = new JProgressBar(SwingConstants.HORIZONTAL, 0, 100);
        barraLimpieza.setBounds(750, 200, 50, 20);
        barraAburrimiento = new JProgressBar(SwingConstants.HORIZONTAL, 0, 100);
        barraAburrimiento.setBounds(750, 250, 50, 20);
        barraHambre = modifyDesignBar(barraHambre);
        barraSed = modifyDesignBar(barraSed);
        barraLimpieza = modifyDesignBar(barraLimpieza);
        barraSalud = modifyDesignBar(barraSalud);
        barraAburrimiento = modifyDesignBar(barraAburrimiento);
        updateBars();

      
    }
    
    private JProgressBar modifyDesignBar(JProgressBar p){
        p.setStringPainted(true);
        p.setForeground(Color.white);
        return p;
    }

    public void iniciarStatus(){
          hiloHambre.start();
        hiloSed.start();
        hiloSalud.start();
        hiloLimpieza.start();
        hiloAburrimiento.start();
    }
    
    public void updateBars() {
        barraHambre.setString(hambre+"");
        barraSed.setString(sed+"");
        barraSalud.setString(salud+"");
        barraLimpieza.setString(limpieza+"");
        barraAburrimiento.setString(aburrimiento+"");
        barraHambre.setValue(hambre);
        barraSed.setValue(sed);
        barraSalud.setValue(salud);
        barraLimpieza.setValue(limpieza);
        barraAburrimiento.setValue(aburrimiento);
    }

    private void reducirSalud(){
        if(getSalud() > 0)
            setSalud(getSalud()-1);
    }
    
   private Thread hiloHambre = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    
                    Thread.sleep((int) (Math.random() * (tiempoMaximo - tiempoMinmo) + tiempoMinmo));
                    if(!pausado){
                    if (hambre != 0) {
                        hambre -= 5;
                    }else{
                        reducirSalud();
                    }
                    updateBars();
                    }
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    });

   private Thread hiloSed = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    
                    Thread.sleep((int) (Math.random() * (tiempoMaximo - tiempoMinmo) + tiempoMinmo));
                    if(!pausado){
                    if (sed != 0) {
                        sed -= 5;
                     
                    }else{
                        reducirSalud();
                    }
                       updateBars();
                    }
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    });

   private Thread hiloSalud = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    
                    Thread.sleep((int) (Math.random() * (15000 - 5000) + 5000));
                    if(!pausado){
                    if (new Random().nextBoolean()) {
                        if (salud != 0) {
                            salud -= 5;
                        }
                         updateBars();
                    }
                    }
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    });
  private  Thread hiloLimpieza = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep((int) (Math.random() * (tiempoMaximo - tiempoMinmo) + tiempoMinmo));
                    if(!pausado){
                    if (limpieza != 0) {
                        limpieza -= 5;
                             
                    }else{
                        reducirSalud();
                    }
               updateBars();
                    }
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    });
  private  Thread hiloAburrimiento = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep((int) (Math.random() * (tiempoMaximo - tiempoMinmo) + tiempoMinmo));
                    if(!pausado){
                    if (aburrimiento != 0) {
                        aburrimiento -= 5;
    
                    }else{
                        reducirSalud();
                    }
                      updateBars();
                    }
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    });
  
      public void setPausado(boolean pausado) {
        this.pausado = pausado;
    }

    public JProgressBar getBarraHambre() {
        return barraHambre;
    }

    public JProgressBar getBarraSed() {
        return barraSed;
    }

    public JProgressBar getBarraSalud() {
        return barraSalud;
    }

    public JProgressBar getBarraLimpieza() {
        return barraLimpieza;
    }

    public JProgressBar getBarraAburrimiento() {
        return barraAburrimiento;
    }

    public int getHambre() {
        return hambre;
    }

    public void setHambre(int hambre) {
        this.hambre = hambre;
    }

    public int getSed() {
        return sed;
    }

    public void setSed(int sed) {
        this.sed = sed;
    }

    public int getSalud() {
        return salud;
    }

    public void setSalud(int salud) {
        this.salud = salud;
    }

    public int getLimpieza() {
        return limpieza;
    }

    public void setLimpieza(int limpieza) {
        this.limpieza = limpieza;
    }

    public int getAburrimiento() {
        return aburrimiento;
    }

    public void setAburrimiento(int aburrimiento) {
        this.aburrimiento = aburrimiento;
    }

}
