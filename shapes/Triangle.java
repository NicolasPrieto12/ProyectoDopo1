package shapes;

import java.awt.Polygon;

/**
 * Triángulo que puede manipularse y dibujarse en el canvas.
 * Extiende Shape aprovechando herencia para reutilizar estado común.
 *
 * @author Michael Kolling and David J. Barnes (Modified)
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version 4.0
 */
public class Triangle extends Shape {

    public static final int VERTICES = 3;

    private int height;
    private int width;

    /**
     * Crea un triángulo en posición y color por defecto.
     */
    public Triangle() {
        height = 30;
        width = 40;
        xPosition = 140;
        yPosition = 15;
        color = "green";
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
     * Cambia el tamaño del triángulo.
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

    private void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            int[] xpoints = {xPosition, xPosition + (width / 2), xPosition - (width / 2)};
            int[] ypoints = {yPosition, yPosition + height, yPosition + height};
            canvas.draw(this, color, new Polygon(xpoints, ypoints, 3));
            canvas.wait(10);
        }
    }

    private void erase() {
        if (isVisible) {
            Canvas.getCanvas().erase(this);
        }
    }
}
