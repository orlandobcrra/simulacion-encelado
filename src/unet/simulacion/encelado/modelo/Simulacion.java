package unet.simulacion.encelado.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Personal
 */
public class Simulacion {

    /**
     * La nave espacial
     */
    private Nave nave;
    /**
     * Las gritas que funcionan como servidores para los chorros
     */
    private List<Chorro> chorros = new ArrayList<Chorro>();
    /**
     * Tiempo en segundos de la simulacion
     */
    public final int tiempoTotal;
    /**
     * relacion de tiempo un segundo se representa en ?
     */
    private int relacionMiliSeg = 50;
    /**
     * Tiempo actual en segundos de la simulacion
     */
    private int tiempoActual;
    /**
     * La simulacion esta pausada?
     */
    private boolean pausa;
    /**
     * La simulacion se acabo?
     */
    private boolean fin;
    /**
     * Cantidad de Pixel por Kilometro
     */
    public static final byte pixelPorKm = 6;
    /**
     * A cuantos pixeles desde el final de la pantalla comienza el piso
     */
    public static final short piso = 150;
    /**
     * Gravedad en mts/(seg)2
     */
    public static final double gravedad = 0.114;//9.8;//

    public Simulacion(int tiempoSimulacion) {
        tiempoActual = 0;
        pausa = false;
        fin = false;
        tiempoTotal = tiempoSimulacion;
        Chorro.calcularTiempoProximoChorro();
    }

    public Integer getTiempoActual1() {
        return tiempoActual;
    }
    boolean salio = false;

    /**
     * Cada segundo que pasa en la simulacion se hace la llamda a este metedo.
     * Es el encargado de notificar a la nave y a los chorros que un segundo de
     * timpo ha pasado y deven realizar los calculos.
     *
     * @param tiempoActual tiempo actual de la simulacion
     */
    public void setTiempoActual(Integer tiempoActual) {
        this.tiempoActual = tiempoActual;

        nave.calculo();
        if (Chorro.tiempoProximoChorro <= 0) {// !salio) {
            synchronized (chorros) {
                chorros.add(new Chorro(this));
                Chorro.calcularTiempoProximoChorro();
            }
            salio = true;
        }
        Chorro.tiempoProximoChorro--;
        for (Chorro chorro : chorros) {
            chorro.calcular();
            if (nave.getPoscicionPixel() == chorro.getPosicion()
                    //&& nave.getPoscicionPixel()+100 <= chorro.getPosicion()
                    && nave.getAltura() <= chorro.getAlturaActual()) {
                nave.setRecogio(true);
            }
        }

    }

    public Nave getNave() {
        return nave;
    }

    public Boolean getPausa() {
        return pausa;
    }

    public void setPausa(boolean pausa) {
        this.pausa = pausa;
    }

    public Boolean getFin() {
        return fin;
    }

    public void setFin(boolean fin) {
        this.fin = fin;
    }

    public static Short getPiso() {
        return piso;
    }

    public void setNave(Nave nave) {
        this.nave = nave;
    }

    public int getRelacionMiliSeg() {
        return relacionMiliSeg;
    }

    public void setRelacionMiliSeg(int relacionMiliSeg) {
        this.relacionMiliSeg = relacionMiliSeg;
    }

    public List<Chorro> getChorros() {
        return chorros;
    }
}
