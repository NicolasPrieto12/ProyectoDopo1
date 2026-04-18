package tower;

/**
 * Representa una taza normal que puede apilarse dentro de la torre.
 * Es la clase base para todos los tipos de taza.
 *
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version 4.0
 */
public class Cup {

    private int number;
    private shapes.Rectangle base;
    private shapes.Rectangle leftWall;
    private shapes.Rectangle rightWall;
    private boolean hasLid;
    private Lid lid;
    private int xPos;
    private int yPos;
    private int cupWidth;
    private int cupHeight;

    /**
     * Crea una taza con el número identificador dado.
     *
     * @param number número identificador de la taza
     */
    public Cup(int number) {
        this.number = number;
        this.hasLid = false;
        this.cupHeight = 30;
        this.cupWidth = 40;
        this.xPos = 70;
        this.yPos = 15;
        this.base = new shapes.Rectangle();
        this.leftWall = new shapes.Rectangle();
        leftWall.moveVertical(-cupHeight);
        this.rightWall = new shapes.Rectangle();
        rightWall.moveHorizontal(cupWidth - 10);
        rightWall.moveVertical(-cupHeight);
    }

    /**
     * Establece la posición gráfica de la taza.
     *
     * @param x posición horizontal en píxeles
     * @param y posición vertical en píxeles
     */
    public void setPosition(int x, int y) {
        int dx = x - xPos;
        int dy = y - yPos;
        xPos = x;
        yPos = y;
        base.moveHorizontal(dx);
        base.moveVertical(dy);
        leftWall.moveHorizontal(dx);
        leftWall.moveVertical(dy);
        rightWall.moveHorizontal(dx);
        rightWall.moveVertical(dy);
    }

    /**
     * Cambia el color de la taza.
     *
     * @param color nombre del color
     */
    public void setColor(String color) {
        base.changeColor(color);
        leftWall.changeColor(color);
        rightWall.changeColor(color);
    }

    /**
     * Cambia el tamaño de la taza.
     *
     * @param height nueva altura en píxeles
     * @param width  nuevo ancho en píxeles
     */
    public void setSize(int height, int width) {
        int oldHeight = this.cupHeight;
        int oldWidth = this.cupWidth;
        this.cupWidth = width;
        this.cupHeight = height;
        base.changeSize(10, width);
        leftWall.changeSize(height, 10);
        rightWall.changeSize(height, 10);
        leftWall.moveVertical(oldHeight - height);
        rightWall.moveHorizontal(width - oldWidth);
        rightWall.moveVertical(oldHeight - height);
    }

    /**
     * Retorna el número identificador de la taza.
     *
     * @return número de la taza
     */
    public int getNumber() {
        return number;
    }

    /**
     * Indica si la taza tiene tapa asociada.
     *
     * @return true si tiene tapa
     */
    public boolean hasLid() {
        return hasLid;
    }

    /**
     * Asocia una tapa a esta taza.
     *
     * @param lid tapa a asociar
     */
    public void putLid(Lid lid) {
        this.lid = lid;
        this.hasLid = true;
    }

    /**
     * Quita y retorna la tapa asociada.
     *
     * @return la tapa removida
     */
    public Lid removeLid() {
        Lid temp = this.lid;
        this.lid = null;
        this.hasLid = false;
        return temp;
    }

    /**
     * Retorna la tapa asociada sin quitarla.
     *
     * @return la tapa actual, o null si no tiene
     */
    public Lid getLid() {
        return lid;
    }

    /**
     * Retorna el ancho actual de la taza.
     *
     * @return ancho en píxeles
     */
    public int getWidth() {
        return cupWidth;
    }

    /**
     * Retorna la posición vertical actual.
     *
     * @return posición y en píxeles
     */
    public int getYPos() {
        return yPos;
    }

    /**
     * Retorna la posición horizontal actual.
     *
     * @return posición x en píxeles
     */
    public int getXPos() {
        return xPos;
    }

    /**
     * Retorna el tipo de esta taza.
     *
     * @return "normal"
     */
    public String getType() {
        return "normal";
    }

    /** Hace visible la taza en el canvas. */
    public void makeVisible() {
        base.makeVisible();
        leftWall.makeVisible();
        rightWall.makeVisible();
    }

    /** Hace invisible la taza en el canvas. */
    public void makeInvisible() {
        base.makeInvisible();
        leftWall.makeInvisible();
        rightWall.makeInvisible();
    }
}
