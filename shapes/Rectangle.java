package shapes;

/**
 * Rectángulo que puede manipularse y dibujarse en el canvas.
 * Extiende ShapeBase aprovechando herencia para reutilizar estado común.
 *
 * @author Michael Kolling and David J. Barnes (Modified)
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version 4.0
 */
public class Rectangle extends ShapeBase {

    public static final int EDGES = 4;

    private int height;
    private int width;

    /**
     * Crea un rectángulo en posición y color por defecto.
     */
    public Rectangle() {
        height = 30;
        width = 40;
        xPosition = 70;
        yPosition = 15;
        color = "magenta";
        isVisible = false;
    }

    /** {@inheritDoc} */
    @Override
    public void makeVisible() {
        isVisible = true;
        draw();
    }

    /** {@inheritDoc} */
    @Override
    public void makeInvisible() {
        erase();
        isVisible = false;
    }

    /** {@inheritDoc} */
    @Override
    public void moveHorizontal(int distance) {
        erase();
        xPosition += distance;
        draw();
    }

    /** {@inheritDoc} */
    @Override
    public void moveVertical(int distance) {
        erase();
        yPosition += distance;
        draw();
    }

    /** {@inheritDoc} */
    @Override
    public void changeColor(String newColor) {
        color = newColor;
        draw();
    }

    /**
     * Cambia el tamaño del rectángulo.
     *
     * @param newHeight nueva altura en píxeles
     * @param newWidth  nuevo ancho en píxeles
     */
    public void changeSize(int newHeight, int newWidth) {
        erase();
        height = newHeight;
        width = newWidth;
        draw();
    }

    /**
     * Retorna el ancho actual.
     *
     * @return ancho en píxeles
     */
    public int getWidth() {
        return width;
    }

    /**
     * Retorna la altura actual.
     *
     * @return altura en píxeles
     */
    public int getHeight() {
        return height;
    }

    private void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color,
                new java.awt.Rectangle(xPosition, yPosition, width, height));
            canvas.wait(10);
        }
    }

    private void erase() {
        if (isVisible) {
            Canvas.getCanvas().erase(this);
        }
    }
}
