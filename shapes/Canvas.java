package shapes;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * Canvas es una clase que permite dibujar figuras geométricas en una ventana.
 * Modificación del Canvas general, adaptada para el proyecto shapes.
 *
 * @author Bruce Quig
 * @author Michael Kolling
 * @version 4.0
 */
public class Canvas {

    private static Canvas canvasSingleton;

    /**
     * Método factory para obtener el singleton del canvas.
     *
     * @return instancia única del canvas
     */
    public static Canvas getCanvas() {
        if (canvasSingleton == null) {
            canvasSingleton = new Canvas("BlueJ Shapes Demo", 300, 300, Color.white);
        }
        canvasSingleton.setVisible(true);
        return canvasSingleton;
    }

    private JFrame frame;
    private CanvasPane canvas;
    private Graphics2D graphic;
    private Color backgroundColour;
    private Image canvasImage;
    private List<Object> objects;
    private HashMap<Object, ShapeDescription> shapes;

    private Canvas(String title, int width, int height, Color bgColour) {
        frame = new JFrame();
        canvas = new CanvasPane();
        frame.setContentPane(canvas);
        frame.setTitle(title);
        canvas.setPreferredSize(new Dimension(width, height));
        backgroundColour = bgColour;
        frame.pack();
        objects = new ArrayList<Object>();
        shapes = new HashMap<Object, ShapeDescription>();
    }

    /**
     * Controla la visibilidad del canvas.
     *
     * @param visible true para mostrar
     */
    public void setVisible(boolean visible) {
        if (graphic == null) {
            Dimension size = canvas.getSize();
            canvasImage = canvas.createImage(size.width, size.height);
            graphic = (Graphics2D) canvasImage.getGraphics();
            graphic.setColor(backgroundColour);
            graphic.fillRect(0, 0, size.width, size.height);
            graphic.setColor(Color.black);
        }
        frame.setVisible(visible);
    }

    /**
     * Dibuja una figura en el canvas.
     *
     * @param referenceObject objeto de referencia para identificar la figura
     * @param color           color de la figura
     * @param shape           figura AWT a dibujar
     */
    public void draw(Object referenceObject, String color, java.awt.Shape shape) {
        objects.remove(referenceObject);
        objects.add(referenceObject);
        shapes.put(referenceObject, new ShapeDescription(shape, color));
        redraw();
    }

    /**
     * Borra una figura del canvas.
     *
     * @param referenceObject objeto de referencia de la figura a borrar
     */
    public void erase(Object referenceObject) {
        objects.remove(referenceObject);
        shapes.remove(referenceObject);
        redraw();
    }

    /**
     * Establece el color de primer plano del canvas.
     *
     * @param colorString nombre del color
     */
    public void setForegroundColor(String colorString) {
        if      (colorString.equals("red"))      graphic.setColor(Color.red);
        else if (colorString.equals("black"))    graphic.setColor(Color.black);
        else if (colorString.equals("blue"))     graphic.setColor(Color.blue);
        else if (colorString.equals("yellow"))   graphic.setColor(Color.yellow);
        else if (colorString.equals("green"))    graphic.setColor(Color.green);
        else if (colorString.equals("magenta"))  graphic.setColor(Color.magenta);
        else if (colorString.equals("white"))    graphic.setColor(Color.white);
        else if (colorString.equals("orange"))   graphic.setColor(Color.orange);
        else if (colorString.equals("cyan"))     graphic.setColor(Color.cyan);
        else if (colorString.equals("pink"))     graphic.setColor(Color.pink);
        else                                     graphic.setColor(Color.black);
    }

    /**
     * Espera un número de milisegundos (útil para animaciones).
     *
     * @param milliseconds tiempo de espera
     */
    public void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (Exception e) {
            // ignorado intencionalmente
        }
    }

    private void redraw() {
        eraseCanvas();
        for (Iterator i = objects.iterator(); i.hasNext();) {
            shapes.get(i.next()).draw(graphic);
        }
        canvas.repaint();
    }

    private void eraseCanvas() {
        Color original = graphic.getColor();
        graphic.setColor(backgroundColour);
        Dimension size = canvas.getSize();
        graphic.fill(new java.awt.Rectangle(0, 0, size.width, size.height));
        graphic.setColor(original);
    }

    private class CanvasPane extends JPanel {
        public void paint(Graphics g) {
            g.drawImage(canvasImage, 0, 0, null);
        }
    }

    private class ShapeDescription {
        private java.awt.Shape shape;
        private String colorString;

        public ShapeDescription(java.awt.Shape shape, String color) {
            this.shape = shape;
            this.colorString = color;
        }

        public void draw(Graphics2D graphic) {
            setForegroundColor(colorString);
            graphic.draw(shape);
            graphic.fill(shape);
        }
    }
}
