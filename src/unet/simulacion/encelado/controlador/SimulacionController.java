package unet.simulacion.encelado.controlador;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import unet.simulacion.encelado.modelo.Simulacion;
import unet.simulacion.encelado.vista.Lienzo;
import unet.simulacion.encelado.vista.VentanaPrincipal;

/**
 *
 * @author Personal
 */
public class SimulacionController extends KeyAdapter implements ActionListener, ChangeListener {

    private final Simulacion simulacion;
    private VentanaPrincipal frame;
    private Lienzo lienzo;

    public SimulacionController(Simulacion s) {
        this.simulacion = s;

        lienzo = new Lienzo(simulacion);
        frame = new VentanaPrincipal(this, this, this, simulacion);
        //frame.setLayout(new BorderLayout());
        frame.getDibujo().add(lienzo, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Simulacion Encedalo");
        frame.setSize(1800, 1000);
        //frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        simulacion.setPausa(frame.getPausa().isSelected());

        new Thread() {

            @Override
            public void run() {
                int segundo = 0;
                while (true) {
                    if (segundo > simulacion.tiempoTotal) {
                        simulacion.setFin(true);
                        break;
                    }
                    if (!simulacion.getPausa()) {
                        //simulacion.setTiempoActual(segundo * simulacion.sleep);
                        simulacion.setTiempoActual(segundo);

                        lienzo.repaint();
                        frame.update();

                        if (simulacion.getNave().getAltura() <= 0) {
                            simulacion.setFin(true);
                            System.out.println("FIN");
                            break;
                        }

                        if (simulacion.getNave().getPesoConbustible() <= 0) {
                            simulacion.getNave().setEmpujeNegativo(0);
                            simulacion.getNave().setEmpujePositivo(0);
                            simulacion.getNave().setEmpujeYPositivo(0);
                        }

                        //i += simulacion.getRelacionMiliSeg();
                        segundo++;

//                        if (simulacion.getNave().getPoscicionPixel() >= lienzo.getWidth()) {
//                            simulacion.getNave().setPosicion(0);
//                        }
//                        else if (simulacion.getNave().getPoscicionPixel() <= lienzo.getAnchoNave() * -1) {
//                            simulacion.getNave().setPosicion(lienzo.getWidth() - lienzo.getAnchoNave() - 1);
//                        }
                    }
                    try {
                        Thread.sleep(simulacion.getRelacionMiliSeg());
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }.start();
    }

    public void keyPressed(java.awt.event.KeyEvent evt) {
        System.out.println(evt.getKeyCode());
        if (!(simulacion.getPausa() || simulacion.getFin())) {
            if (evt.getKeyCode() == KeyEvent.VK_I) {
                simulacion.getNave().setEmpujeYPositivo(
                        simulacion.getNave().getEmpujeYPositivo() + 50);
            } else if (evt.getKeyCode() == KeyEvent.VK_K) {
                simulacion.getNave().setEmpujeYPositivo(
                        simulacion.getNave().getEmpujeYPositivo() - 50);
            } else if (evt.getKeyCode() == KeyEvent.VK_O) {
                simulacion.getNave().setEmpujePositivo(
                        simulacion.getNave().getEmpujePositivo() + 1);
            } else if (evt.getKeyCode() == KeyEvent.VK_L) {
                simulacion.getNave().setEmpujePositivo(
                        simulacion.getNave().getEmpujePositivo() - 1);
            } else if (evt.getKeyCode() == KeyEvent.VK_U) {
                simulacion.getNave().setEmpujeNegativo(
                        simulacion.getNave().getEmpujeNegativo() + 1);
            } else if (evt.getKeyCode() == KeyEvent.VK_J) {
                simulacion.getNave().setEmpujeNegativo(
                        simulacion.getNave().getEmpujeNegativo() - 1);
            } else if (evt.getKeyCode() == KeyEvent.VK_9) {
                simulacion.setRelacionMiliSeg(
                        simulacion.getRelacionMiliSeg() + 1);
            } else if (evt.getKeyCode() == KeyEvent.VK_8) {
                simulacion.setRelacionMiliSeg(
                        simulacion.getRelacionMiliSeg() - 1);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == frame.getPausa()) {
            JCheckBoxMenuItem item = (JCheckBoxMenuItem) e.getSource();
            simulacion.setPausa(item.isSelected());
            frame.update();
        }
    }

    @Override
    public void stateChanged(ChangeEvent ce) {
        if (frame != null) {
            if (ce.getSource() == frame.getTiempoRelacion()) {
                JSlider slider = (JSlider) ce.getSource();
                simulacion.setRelacionMiliSeg(slider.getValue());
            } else if (ce.getSource() == frame.getEmpujeYPos()) {
                JSpinner spinner = (JSpinner) ce.getSource();
                simulacion.getNave().setEmpujeYPositivo((int) spinner.getValue());
            } else if (ce.getSource() == frame.getEmpujeYNeg()) {
                JSpinner spinner = (JSpinner) ce.getSource();
                simulacion.getNave().setEmpujeYNegativo((int) spinner.getValue());
            } else if (ce.getSource() == frame.getEmpujeXPos()) {
                JSpinner spinner = (JSpinner) ce.getSource();
                simulacion.getNave().setEmpujePositivo((int) spinner.getValue());
            } else if (ce.getSource() == frame.getEmpujeXNeg()) {
                JSpinner spinner = (JSpinner) ce.getSource();
                simulacion.getNave().setEmpujeNegativo((int) spinner.getValue());
            }
        }
    }
}
