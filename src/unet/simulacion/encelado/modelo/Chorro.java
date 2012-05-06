package unet.simulacion.encelado.modelo;

import java.util.Random;

/**
 *
 * @author Personal
 */
public class Chorro {

    /**
     * Random para la altura del chorro
     */
    private static Random h = new Random();
    /**
     * Random para la posicion del chorro en la luna
     */
    private static Random p = new Random();
    /**
     * Tiempo para la salida del proximo chorro
     */
    public static int tiempoProximoChorro;
    /**
     * Tiempo calculado para la salida del chorro
     */
    public static int tiempoProximoChorroMx;
    /**
     * Velocidad inicial en mts/seg
     */
    private double velocidadInicialY;
    /**
     * Velocidad actual en y en mts/seg
     */
    private double velocidadActualY;
    /**
     * Altura actual del chorro
     */
    private int alturaActual;
    /**
     * Altura maxima del chorro
     */
    private int alturaMx;
    /**
     * Posicion del chorro en la luna
     */
    private int posicion;
    /**
     * Tiempo inicial de salida de chorro
     */
    private int tiempoInicial;
    /**
     * Si el chorro aun vive
     */
    private boolean vivo = true;
    /**
     * Simulacion. Para conocer el tiempo actual
     */
    private Simulacion simulacion;
    
    private static Random velocidad = new Random();

    /**
     * Crea una instancia de Chorro con su altura ya calculada.
     */
    public Chorro(Simulacion simulacion) {
        this.alturaMx = (60 + h.nextInt(31)) * 1000;
        this.alturaActual = 0;
        this.simulacion = simulacion;
        this.tiempoInicial = simulacion.getTiempoActual1();
        this.velocidadActualY = 0;
        this.velocidadInicialY = 118 + velocidad.nextInt(143 - 118);
        //143 LLEGA hasta 90 km
        //118 llegas hasta 60 KM
    }

    public int getAlturaActual() {
        return alturaActual;
    }

    public int getPosicion() {
        return posicion;
    }

    public boolean isVivo() {
        return vivo;
    }

    /**
     * Calculo de velocidad y altura del chorro.
     * Segun formulas de lanzamiento vertical.
     */
    public void calcular() {
        velocidadActualY = velocidadInicialY
                - (Simulacion.gravedad
                * (simulacion.getTiempoActual1() - tiempoInicial));
        alturaActual = alturaActual + (int) velocidadActualY;
        if (alturaActual < 0) {
            vivo = false;
        }
    }

    /**
     * Calculo de la posicion del chorro en la luna
     *
     * @param ancho de la pantalla visible
     */
    public void setDatos(int ancho, int tiempoActual) {
        posicion = p.nextInt(ancho);
        tiempoInicial = tiempoActual;
    }

    /**
     * Se calcula el tiempo de llegada de un chorro segun la distribución
     * Exponencial. Con media 10 seg
     */
    public static final void calcularTiempoProximoChorro() {
        tiempoProximoChorroMx = (int) (-10 * (Math.log(Math.random())));
        tiempoProximoChorro = tiempoProximoChorroMx;
    }
}
