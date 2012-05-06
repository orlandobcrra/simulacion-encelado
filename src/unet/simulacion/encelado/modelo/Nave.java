package unet.simulacion.encelado.modelo;

/**
 *
 * @author Personal
 */
public class Nave {

    /**
     * Simulacion. para conocer valores como el tiempo actual de la simulacion
     */
    private Simulacion simulacion;
    /**
     * Altura inicial de la nave
     */
    private final int alturaInicial;
    /**
     * Posicion de la nave en mts
     */
    private punto posicion;
    /**
     * Velocidad inicial en mts/seg
     */
    private double velocidadInicialY = 0.0;
    /**
     * Velocidad actual en y en mts/seg
     */
    private double velocidadActualY = 0.0;
    /**
     * Velocidad actual en x en mts/seg
     */
    private double velocidadActualX = 0.0;
    /**
     * Peso de la nave sin combustible en Kg
     */
    public final int pesoCombustibleInicial;
    /**
     * Peso del combustible en Kg
     */
    private double pesoConbustible;
    /**
     * Peso de la nave sin combustible en Kg
     */
    private final Integer pesoNave;
    /**
     * La nave esta en modo automatico
     */
    private boolean automatico;
    /**
     * Empuje del eje Y
     */
    private int empujeYPositivo = 0;
    /**
     * Empuje del eje Y
     */
    private int empujeYNegativo = 0;
    /**
     * Velocidad de los gases en Y en m/s
     */
    private float uYPositivo;
    /**
     * Velocidad de los gases en Y en m/s
     */
    private float uYNegativo;
    /**
     * Cantidad de combustible quemado Kg/s
     */
    private float dYPositivo;
    /**
     * Cantidad de combustible quemado Kg/s
     */
    private float dYNegativo;
    /**
     * Empuje del positivo del eje x
     */
    private int empujeXPositivo = 0;
    /**
     * Velocidad de los gases en Y en m/s
     */
    private float uXPositivo;
    /**
     * Cantidad de combustible quemado Kg/s
     */
    private float dXPositivo;
    /**
     * Empuje del negativo del eje x
     */
    private int empujeXNegativo = 0;
    /**
     * Velocidad de los gases en Y en m/s
     */
    private float uXNegativo;
    /**
     * Cantidad de combustible quemado Kg/s
     */
    private float dXNegativo;
    /**
     * Recogio particulas
     */
    private boolean recogio = false;

    /**
     *
     * @param simulacion
     */
    public Nave(Simulacion simulacion, int pesoNave, int pesoCombustible,
            int alturaInicial) {
        automatico = false;
        this.simulacion = simulacion;
        this.pesoNave = pesoNave;
        this.pesoCombustibleInicial = pesoCombustible;
        this.pesoConbustible = (double) pesoCombustible;
        this.alturaInicial = alturaInicial;
        posicion = new punto(15_000, alturaInicial);
        //empuje1 = 900_000.0;
        reCalcular();
    }

    /**
     * Recalcular los valores de U y D segun el nuevo empuje. Para los 4
     * motores, superior, inferior, derecho e izquierdo.
     */
    private void reCalcular() {
        dYPositivo = empujeYPositivo * .3f / 900;
        uYPositivo = empujeYPositivo * 3000 / 900;
        dYNegativo = empujeYNegativo * .3f / 900;
        uYNegativo = empujeYNegativo * 3000 / 900;
        dXPositivo = empujeXPositivo * .3f / 900;
        uXPositivo = empujeXPositivo * 3000 / 900;
        dXNegativo = empujeXNegativo * .3f / 900;
        uXNegativo = empujeXNegativo * 3000 / 900;
        System.out.println("Cambio empuje");
    }

    public int getAltura() {
        return (int) posicion.altura;
    }

    public void setPosicionPixel(int p) {
        posicion.posEnPixel = p;
    }

    public int getPoscicionPixel() {
        return posicion.posEnPixel;
    }

    public int getPosicion() {
        return (int) posicion.posicion;
    }

    public double getVelocidadActualY() {
        return velocidadActualY;
    }

    public double getVelocidadActualX() {
        return velocidadActualX;
    }

    public int getEmpujeYPositivo() {
        return empujeYPositivo;
    }

    public void setEmpujeYPositivo(int empuje) {
        this.empujeYPositivo = empuje;
        reCalcular();
    }

    public int getEmpujeYNegativo() {
        return empujeYNegativo;
    }

    public void setEmpujeYNegativo(int empuje) {
        this.empujeYNegativo = empuje;
        reCalcular();
    }

    public int getEmpujePositivo() {
        return empujeXPositivo;
    }

    public void setEmpujePositivo(int empuje) {
        this.empujeXPositivo = empuje;
        reCalcular();
    }

    public int getEmpujeNegativo() {
        return empujeXNegativo;
    }

    public void setEmpujeNegativo(int empuje) {
        this.empujeXNegativo = empuje;
        reCalcular();
    }

    public boolean getAutomatico() {
        return automatico;
    }

    public void setAutomatico(boolean automatico) {
        this.automatico = automatico;
    }

    public int getPesoConbustible() {
        return (int) pesoConbustible;
    }

    public boolean isRecogio() {
        return recogio;
    }

    public void setRecogio(boolean recogio) {
        this.recogio = recogio;
    }
    //TODO documentar
    boolean seAcaboElCombustible = false;
    boolean yaEntro = false;
    int tiempoSeAcaboCombustible = 0;

    public void setPosicion(int p) {
        posicion.posicion = p;
    }

    public void calculo() {

        //calculo de valores iniciales
        double masaInicial = pesoCombustibleInicial + pesoNave;
        double masaActual = pesoNave + pesoConbustible;
        int t = simulacion.getTiempoActual1();///simulacion.relacionMiliSeg;

        //si se acaba el combustible
        if (seAcaboElCombustible && !yaEntro) {
            //no puede tener empuje
            empujeYPositivo = 0;
            empujeXPositivo = 0;
            //calculo de U y D
            reCalcular();
            yaEntro = true;
            //valores cuando ya no tiene combustible ni empuje
            tiempoSeAcaboCombustible = t;
            velocidadInicialY = velocidadActualY;
        }

        // si no se ha acabado el combustible
        if (!seAcaboElCombustible) {
            //calcula el nuevo peso del combustible
            pesoConbustible = pesoConbustible - dYPositivo
                    - dYNegativo - dXPositivo - dXNegativo;
            //si se acabo
            if (pesoConbustible <= 0) {
                pesoConbustible = 0.0;
                seAcaboElCombustible = true;
            }
        }

//        velocidadActualY = velocidadInicialY
//                - Simulacion.gravedad * (t - tiempoSeAcaboCombustible)
//                + uYPositivo * Math.log(
//                (masaInicial)
//                / (masaActual));

        // velocidad en el eje Y.
        // tomando en cuenta la gravedad, el empuje y la masa

        velocidadActualY = velocidadActualY
                - Simulacion.gravedad
                + ((empujeYPositivo - empujeYNegativo) / masaActual);

//        velocidadActualY = velocidadInicialY
//                - Simulacion.gravedad * (t - tiempoSeAcaboCombustible)
//                + ((uYPositivo - uYNegativo) * Math.log(masaInicial / masaActual));
        //+ empujeYPositivo / masaActual
        //- empujeYNegativo / masaActual;


        // calculo de la altura en base a la altura altual en mts 
        // y su velocidad calculada en el eje Y en mts/seg
        posicion.altura = posicion.altura + (velocidadActualY * 1);

        // velocidad en el eje X.
        // solo tomamos en cuenta el empuje ya que no hay resistencia
        velocidadActualX = velocidadActualX + uXPositivo - uXNegativo;

        // calculo de la posicion en base a su posicion actual en mts
        // y su velocidad calculada en el eje X en mts/seg
        posicion.posicion = posicion.posicion + velocidadActualX;

//        System.out.println("tiempo= " + t + " velocidad= " + velocidadActualY + " h= " + posicion.altura);
//        System.out.println("tiempo= " + t + " h= " + h);
    }
}

class punto {

    public double altura;
    public double posicion;
    public int posEnPixel;

    public punto(double posicion, double altura) {
        this.altura = altura;
        this.posicion = posicion;
    }
//    public void setLocation(double altura, double posicion) {
//        this.altura = altura;
//        this.posicion = posicion;
//    }
}
