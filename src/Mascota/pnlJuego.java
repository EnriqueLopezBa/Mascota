package Mascota;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.Timer;

/**
 *
 * @author Enrique
 */
public class pnlJuego implements KeyListener {

    Dibujar puntero;
    int mover = 1;
    int salto = 1;
    int deslice = 1;
    JButton salir = new JButton("Salir", new ImageIcon(getClass().getResource("/Resources/close.png")));
    Accion accion = Accion.CORRIENDO;

    public enum Accion {
        CORRIENDO, SALTANDO, DESLIZANDO;
    }

    public pnlJuego(Dibujar puntero) {
        this.puntero = puntero;
        salir.setBounds(700, 50, 50, 90);
        salir = puntero.inicio.fondoTransparente(salir);

        puntero.add(salir);
        salir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                salir();
            }

        });
        hiloMascota.start();

        hilObstaculos.start();
    }

    private void salir() {
        puntero.removeAll();
        puntero.estado = 1;
        ejecutarHiloMascota = false;
        ejecutarHiloObstaculos = false;
        puntero.inicio.activarBotones();
        puntero.inicio.status.setPausado(false);
        puntero.inicio.status.setAburrimiento(puntero.inicio.status.getAburrimiento() + 5);
    }

    Point obstaculoAbajo = new Point(750, 480);
    Point obstaculoArriba = new Point(1200, 380);
    Point posMascota = new Point(70, 400);

    public void pintar(Graphics g) {
        //Generacion de obstaculos
        g.drawImage(new ImageIcon(getClass().getResource("/Resources/spike 1.png")).getImage(), obstaculoAbajo.x, obstaculoAbajo.y, 40, 40, puntero);
        g.drawImage(new ImageIcon(getClass().getResource("/Resources/spike 1.png")).getImage(), obstaculoArriba.x, obstaculoArriba.y, 40, 40, puntero);
        switch (accion) {
            case CORRIENDO:
                g.drawImage(new ImageIcon(getClass().getResource("/Resources/cat/Run (" + mover + ").png")).getImage(), posMascota.x, posMascota.y, 150, 120, puntero);
                break;
            case SALTANDO:
                g.drawImage(new ImageIcon(getClass().getResource("/Resources/cat/Jump (" + salto + ").png")).getImage(), posMascota.x, posMascota.y, 150, 120, puntero);
                break;
            case DESLIZANDO:
                g.drawImage(new ImageIcon(getClass().getResource("/Resources/cat/Slide (" + deslice + ").png")).getImage(), posMascota.x, posMascota.y, 150, 120, puntero);
                break;
        }

    }

    //TIMER ACCIONES
    int contSaltar = 0;
    Timer timerSalto = new Timer(130, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            salto = (salto + 1) % 9;
            if (salto == 0) {
                salto++;
            }
            if (contSaltar == 9) {
                posMascota.y = 400;
                posMascota.x = 70;
                ((Timer) e.getSource()).stop();
                accion = Accion.CORRIENDO;
                return;
            }
            if (contSaltar < 5) {
                posMascota.y -= 15;
                posMascota.x += 5;
            } else {
                posMascota.y += 15;
                posMascota.x -= 5;
            }
            contSaltar++;
        }
    });
    int contDeslizar = 0;
    Timer timerDeslizar = new Timer(130, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            deslice = (deslice + 1) % 9;
            if (deslice == 0) {
                deslice++;
            }
            if (contDeslizar == 9) {

                posMascota.x = 70;
                ((Timer) e.getSource()).stop();
                accion = Accion.CORRIENDO;
                return;
            }

            contDeslizar++;
        }
    });

    //HILOS MASCOTA
    boolean ejecutarHiloMascota = true;
    Thread hiloMascota = new Thread(new Runnable() {
        @Override
        public void run() {
            while (ejecutarHiloMascota) {
                try {
                    mover = (mover + 1) % 9;
                    if (mover == 0) {
                        mover++;
                    }
                    Thread.sleep(80);
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    });

    boolean ejecutarHiloObstaculos = true;
    Thread hilObstaculos = new Thread(new Runnable() {
        @Override
        public void run() {
            while (ejecutarHiloObstaculos) {
                try {
                    if (colision(obstaculoAbajo, accion) || colision(obstaculoArriba, accion)) {
                        int opcion = JOptionPane.showConfirmDialog(null, "GAME OVER\nREINTENTAR?");
                        if (opcion == 0) {
                            obstaculoAbajo.x = 750;
                            obstaculoArriba.x = 1200;
                        } else {
                            salir();
                        }
                    }
                    //OBSTACULO ABAJO
                    if (obstaculoAbajo.x < 0) {
                        int num = 0;
                        do {
                            num = (int) (Math.random() * (1200 - (obstaculoArriba.x - 300))) + (obstaculoArriba.x - 300);
                        } while (num < 750);
                        obstaculoAbajo.x = num;
                    } else {
                        obstaculoAbajo.x -= 50;
                    }
                    //OBSTACULO ARRIBA
                    if (obstaculoArriba.x < 0) {
                        int num = 0;
                        do {
                            num = (int) (Math.random() * (1200 - (obstaculoAbajo.x + 300))) + (obstaculoAbajo.x + 300);
                        } while (num < 750);
                        obstaculoArriba.x = num;
                    } else {
                        obstaculoArriba.x -= 50;
                    }

                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    });

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP) {
            if (!timerSalto.isRunning() && !timerDeslizar.isRunning()) {
                salto = 1;
                contSaltar = 0;
                timerSalto.start();
                accion = Accion.SALTANDO;

            }
        } else if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (!timerDeslizar.isRunning() && !timerSalto.isRunning()) {
                deslice = 1;
                contDeslizar = 0;
                timerDeslizar.start();
                accion = Accion.DESLIZANDO;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public boolean estaEnArea(int posXObstaculo, int posYObstaculo, Accion c) {
        int x2 = 0;
        int y2 = 0;
        if (c == Accion.CORRIENDO) {
            x2 = posMascota.x + 100;
            y2 = posMascota.y + 100;
        }
        if (c == Accion.SALTANDO) {
            x2 = posMascota.x + 63;
            y2 = posMascota.y + 100;
        }
        if (c == Accion.DESLIZANDO) {
            y2 = posMascota.y - 30;
            x2 = posMascota.x + 94;
        }
        if (posXObstaculo >= posMascota.x && posXObstaculo <= x2) {
            if (posYObstaculo >= posMascota.y && posYObstaculo <= y2) {
                return true;
            }
        }
        return false;
    }

    public boolean colision(Point objetoA, Accion c) {
        int x2ObjetoA = objetoA.x + 40;
        int y2ObjetoA = objetoA.y + 40;
        if (estaEnArea(x2ObjetoA, objetoA.y, c)) {
            return true;
        }
        if (estaEnArea(objetoA.x, y2ObjetoA, c)) {
            return true;
        }
        if (estaEnArea(objetoA.x, objetoA.y, c)) {
            System.out.println("");
            return true;
        }
        if (estaEnArea(x2ObjetoA, y2ObjetoA, c)) {
            return true;
        }
        return false;
    }

}
