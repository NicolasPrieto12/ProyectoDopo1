package tower;

/**
 * Representa una tapa normal para una taza dentro de la torre.
 * Es la clase base para todos los tipos de tapa.
 *
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version 4.0
 */
public class Lid {

    private int cupNumber;
    private shapes.Rectangle visual;
    private int xPos;
    private int yPos;

    /**
     * Crea una tapa asociada al número de taza dado.
     *
     * @param cupNumber número de la taza asociada
     */
    public Lid(int cupNumber) {
        this.cupNumber = cupNumber;
        this.visual = new shapes.Rectangle();
        this.xPos = 70;
        this.yPos = 15;
    }

    /**
     * Establece la posición gráfica de la tapa.
     *
     * @param x posición horizontal en píxeles
     * @param y posición vertical en píxeles
     */
    public void setPosition(int x, int y) {
        visual.moveHorizontal(x - xPos);
        visual.moveVertical(y - yPos);
        xPos = x;
        yPos = y;
    }

    /**
     * Cambia el ancho de la tapa.
     *
     * @param width nuevo ancho en píxeles
     */
    public void setSize(int width) {
        visual.changeSize(5, width);
    }

    /**
     * Cambia el color de la tapa.
     *
     * @param color nombre del color
     */
    public void setColor(String color) {
        visual.changeColor(color);
    }

    /**
     * Retorna el número de la taza asociada.
     *
     * @return número de la taza
     */
    public int getCupNumber() {
        return cupNumber;
    }

    /**
     * Retorna el tipo de esta tapa.
     *
     * @return "normal"
     */
    public String getType() {
        return "normal";
    }

    /** Hace visible la tapa en el canvas. */
    public void makeVisible() {
        visual.makeVisible();
    }

    /** Hace invisible la tapa en el canvas. */
    public void makeInvisible() {
        visual.makeInvisible();
    }
}
