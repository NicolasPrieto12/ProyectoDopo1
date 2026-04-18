package shapes;

/**
 * Clase abstracta base para todas las figuras geométricas del simulador.
 * Define el contrato común de visibilidad, color y movimiento.
 *
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version 4.0
 */
public abstract class Shape {

    protected int xPosition;
    protected int yPosition;
    protected String color;
    protected boolean isVisible;

    /**
     * Hace visible la figura en el canvas.
     */
    public abstract void makeVisible();

    /**
     * Hace invisible la figura en el canvas.
     */
    public abstract void makeInvisible();

    /**
     * Mueve la figura horizontalmente.
     *
     * @param distance distancia en píxeles (negativo = izquierda)
     */
    public abstract void moveHorizontal(int distance);

    /**
     * Mueve la figura verticalmente.
     *
     * @param distance distancia en píxeles (negativo = arriba)
     */
    public abstract void moveVertical(int distance);

    /**
     * Cambia el color de la figura.
     *
     * @param newColor nuevo color ("red", "blue", "green", "yellow", "magenta", "black")
     */
    public abstract void changeColor(String newColor);

    /**
     * Retorna la posición horizontal actual.
     *
     * @return posición x en píxeles
     */
    public int getXPosition() {
        return xPosition;
    }

    /**
     * Retorna la posición vertical actual.
     *
     * @return posición y en píxeles
     */
    public int getYPosition() {
        return yPosition;
    }

    /**
     * Indica si la figura es visible actualmente.
     *
     * @return true si es visible
     */
    public boolean isVisible() {
        return isVisible;
    }
}
