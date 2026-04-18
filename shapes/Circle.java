package shapes;

import java.awt.geom.Ellipse2D;

/**
 * Círculo que puede manipularse y dibujarse en el canvas.
 * Extiende ShapeBase aprovechando herencia para reutilizar estado común.
 *
 * @author Michael Kolling and David J. Barnes (Modified)
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version 4.0
 */
public class Circle extends ShapeBase {

    public static final double PI = 3.1416;

    private int diameter;

    /**
     * Crea un círculo en posición y color por defecto.
     */
    public Circle() {
        diameter = 30;
        xPosition = 20;
        yPosition = 15;
        color = "blue";
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
     * Cambia el diámetro del círculo.
     *
     * @param newDiameter nuevo diámetro en píxeles
     */
    public void changeSize(int newDiameter) {
        erase();
        diameter = newDiameter;
        draw();
    }

    /**
     * Retorna el diámetro actual.
     *
     * @return diámetro en píxeles
     */
    public int getDiameter() {
        return diameter;
    }

    private void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color,
                new Ellipse2D.Double(xPosition, yPosition, diameter, diameter));
            canvas.wait(10);
        }
    }

    private void erase() {
        if (isVisible) {
            Canvas.getCanvas().erase(this);
        }
    }
}
