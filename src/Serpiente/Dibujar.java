package Serpiente;


import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.util.List;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JPanel;


/**
 *
 * @author Enrique
 */
public class Dibujar extends JPanel implements KeyListener{

   
    
    
    
    Point serpiente;
    Point comida;
    int direccion = KeyEvent.VK_RIGHT;
      List<Point> puntos = new ArrayList<>();
      
    public Dibujar(){
         
        setBounds(0, 0, 800, 600);
        setBackground(Color.WHITE);
        serpiente = new Point(getWidth()/2, getHeight()/2);
        puntos.add(serpiente);
        comida = new Point(new Random().nextInt(getWidth()),new Random().nextInt(getHeight()));
        FoodRandom();
        hilo.start();
       
    }
    
    public void FoodRandom(){
        comida.x = new Random().nextInt(getWidth());
        comida.y = new Random().nextInt(getHeight());
    }
    
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(new Color(102, 102, 255));
        for(Point p : puntos){
            g.fillRect(p.x,p.y, 10, 10);
        }
        
        g.setColor(Color.red);
        g.fillRect(comida.x, comida.y, 10, 10);
    }
    
    public boolean colision(){
       if((serpiente.x >= comida.x - 10) && (serpiente.x <= comida.x +10))
           if((serpiente.y >= comida.y - 10) && (serpiente.y <= comida.y +10))
               return true;
        return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
     
    }

    @Override
    public void keyPressed(KeyEvent e) {
         if(e.getKeyCode() == KeyEvent.VK_UP){
                    if(direccion != KeyEvent.VK_DOWN)
                        direccion = KeyEvent.VK_UP;
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN){
                    if(direccion != KeyEvent.VK_UP)
                        direccion = KeyEvent.VK_DOWN;
                }
                if(e.getKeyCode() == KeyEvent.VK_LEFT){
                    if(direccion != KeyEvent.VK_RIGHT)
                        direccion = KeyEvent.VK_LEFT;
                }
                if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                    if(direccion != KeyEvent.VK_LEFT)
                        direccion = KeyEvent.VK_RIGHT;
                }
    }

    @Override
    public void keyReleased(KeyEvent e) {
       
    }
 
        
    Thread hilo = new Thread(new Runnable() {
        @Override
        public void run() {
            while(true){
                try {
                    switch (direccion) {
                        case KeyEvent.VK_UP:
                            serpiente.y = serpiente.y - 10;
                            if(serpiente.y < 0)
                                serpiente.y = getHeight();
                            break;
                        case KeyEvent.VK_DOWN:
                            serpiente.y = serpiente.y + 10;
                            if(serpiente.y > getHeight())
                                serpiente.y = 0;
                            break;
                        case KeyEvent.VK_RIGHT:
                            serpiente.x = serpiente.x + 10;
                            if(serpiente.x > getWidth())
                                serpiente.x = 0;
                            break;
                        case KeyEvent.VK_LEFT:
                            serpiente.x = serpiente.x - 10;
                            if(serpiente.x < 0)
                                serpiente.x = getWidth();
                            break;
                        default:
                            break;
                    }
                    repaint();
                    puntos.add(0,new Point(serpiente.x,serpiente.y));
                    puntos.remove(puntos.size()-1);
                   if(colision()){
                       puntos.add(0,new Point(serpiente.x,serpiente.y));
                         FoodRandom();
                   }
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    });
    
}
