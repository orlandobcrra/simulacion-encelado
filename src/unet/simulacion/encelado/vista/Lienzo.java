/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unet.simulacion.encelado.vista;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.ImageIcon;
import unet.simulacion.encelado.modelo.Chorro;
import unet.simulacion.encelado.modelo.Simulacion;

/**
 *
 * @author Personal
 */
public class Lienzo extends javax.swing.JPanel {

    private Simulacion simulacion;
    private ImageIcon chorroImagen = new ImageIcon("./imagenes/chorro.png");
    private ImageIcon fondoImagen = new ImageIcon("./imagenes/fondo1.gif");
    private ImageIcon lunaImagen = new ImageIcon("./imagenes/encelado.jpg");
    private ImageIcon naveImagen = new ImageIcon("./imagenes/satelite2.png");
    private ImageIcon explosionImagen = new ImageIcon("./imagenes/explosion2.png");

    /**
     * Creates new form NewJPanel
     */
    public Lienzo(Simulacion simulacion) {
        initComponents();
        this.simulacion = simulacion;
    }
//    int p = simulacion.p.nextInt(this.getWidth());
//    int hm = deAlturaEnKmAPixel(60 + simulacion.h.nextInt(31));
    int p = 500;
    int hm = 70;
    int ha = 0;
    int t = 10;
    int fac = 1;

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        //-- fondo negro
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        //-- imagen de la luna
        g.drawImage(lunaImagen.getImage(),
                0, this.getHeight() - Simulacion.getPiso() - 50, this.getWidth(), Simulacion.getPiso() + 50, this);

        if (simulacion != null) {

            //-- chorros
            g.setColor(Color.WHITE);
            //System.out.println("cantidad de chorros" + simulacion.getChorros().size());
            synchronized (simulacion.getChorros()) {
                for (Chorro chorro : simulacion.getChorros()) {
                    if (chorro.getPosicion() == 0) {
                        chorro.setDatos(this.getWidth(), simulacion.getTiempoActual1());
                    }
                    if (!chorro.isVivo()) {
                        continue;
                    }
                    g.drawImage(chorroImagen.getImage(),
                            chorro.getPosicion(),
                            deAlturaEnMtAPixel(chorro.getAlturaActual()),
                            20, dePosEnMtAPixel(chorro.getAlturaActual()), this);
                }
            }
            //-- chorro

            //-- imagen de la nave
            //System.out.println(simulacion.getNave().getAltura());
            int pixelAltura = deAlturaEnMtAPixel(simulacion.getNave().getAltura());
            int pixelPosicion = dePosEnMtAPixel(simulacion.getNave().getPosicion());
            simulacion.getNave().setPosicionPixel(pixelPosicion);
            if (simulacion.getNave().getAltura() >= 0) {
                // centrar la imagen
                pixelAltura -= naveImagen.getIconHeight() / 2;
                //dibujo la nave            
                g.drawImage(naveImagen.getImage(),
                        pixelPosicion,
                        pixelAltura, this);

//                if (pixelPosicion + naveImagen.getIconWidth() >= this.getWidth()) {
//                    g.drawImage(naveImagen.getImage(),
//                            pixelPosicion - this.getWidth(),
//                            pixelAltura, this);
//                }

            } else { // si la altura < 0
                g.drawImage(explosionImagen.getImage(),
                        pixelPosicion - ((explosionImagen.getIconWidth() - naveImagen.getIconWidth()) / 2),
                        deAlturaEnMtAPixel(0) - (explosionImagen.getIconHeight() / 2), this);
            }
        }

        g.setColor(Color.WHITE);
        //-- regla
        for (int alturaKM = 0;; alturaKM++) {
            int pixel = deAlturaEnKmAPixel(alturaKM);
            if (alturaKM % 10 == 0) {
                g.drawLine(0, pixel, 20, pixel);
                g.drawString(alturaKM + " Km", 22, pixel);
            } else if (alturaKM % 5 == 0) {
                g.drawLine(0, pixel, 17, pixel);
            } else {
                g.drawLine(0, pixel, 10, pixel);
            }
            // fin de la pantalla superior
            if (pixel <= 0) {
                break;
            }
        }

        //-- punto de posicion de la nave en la regla
        g.setColor(Color.red);
        int pixel = deAlturaEnMtAPixel(simulacion.getNave().getAltura());
        g.drawLine(0, pixel, 20, pixel);
    }

    public int deAlturaEnKmAPixel(int km) {
        return (this.getHeight() - Simulacion.getPiso()) - (Simulacion.pixelPorKm * km);
    }

    private int deAlturaEnMtAPixel(int mt) {
        return (this.getHeight() - Simulacion.getPiso()) - ((Simulacion.pixelPorKm * mt) / 1000);
    }

    private int dePosEnMtAPixel(int mt) {
        return ((Simulacion.pixelPorKm * mt) / 1000);
    }

    public int getAnchoNave() {
        return naveImagen.getIconWidth();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 105, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 87, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
