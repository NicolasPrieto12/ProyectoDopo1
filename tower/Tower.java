package tower;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Torre donde se apilan tazas y tapas de diferentes tipos.
 * Simula el problema del maratón de apilamiento de tazas (Stacking Cups).
 *
 * <p>Tipos de taza soportados: normal, opener, hierarchical.</p>
 * <p>Tipos de tapa soportados: normal, fearful, crazy, sticky.</p>
 *
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version 4.0
 */
public class Tower {

    /** Ancho de la torre en unidades. */
    public static final int TowerWidth = 9;

    private int width;
    private int maxHeight;
    private ArrayList<Cup> cups;
    private ArrayList<Lid> lids;
    private ArrayList<Object> towerItems;
    private boolean isVisible;
    private shapes.Rectangle axisX;
    private ArrayList<shapes.Rectangle> axisY;

    /**
     * Crea una torre con ancho y altura máxima dados.
     *
     * @param width     ancho de la torre
     * @param maxHeight altura máxima permitida
     */
    public Tower(int width, int maxHeight) {
        this.width = width;
        this.maxHeight = maxHeight;
        this.cups = new ArrayList<>();
        this.lids = new ArrayList<>();
        this.towerItems = new ArrayList<>();
        this.isVisible = false;
        this.axisY = new ArrayList<>();
        createAxis();
    }

    /**
     * Crea una torre con el número de tazas normales dado.
     * Las tazas se crean de tamaño (2i-1)*10 para i de 1 a cups.
     *
     * @param cupsCount número de tazas a crear
     */
    public Tower(int cupsCount) {
        this(9, 1000);
        for (int i = cupsCount; i >= 1; i--) {
            pushCup(i);
        }
    }

    private void createAxis() {
        axisX = new shapes.Rectangle();
        axisX.changeColor("black");
        axisX.changeSize(2, 300);
        axisX.moveHorizontal(-60);
        axisX.moveVertical(265);
        for (int i = 0; i <= 9; i++) {
            shapes.Rectangle mark = new shapes.Rectangle();
            mark.changeColor("black");
            mark.changeSize(2, 15);
            mark.moveHorizontal(-60);
            mark.moveVertical(-15 + (270 - i * 30));
            axisY.add(mark);
        }
    }

    /**
     * Inserta una taza normal con el número dado.
     * El ancho se calcula como (2*i - 1) * 10.
     *
     * @param i número identificador de la taza
     */
    public void pushCup(int i) {
        pushCup("normal", i);
    }

    /**
     * Inserta una taza del tipo especificado con el número dado.
     * El ancho se calcula como (2*i - 1) * 10.
     *
     * @param type tipo de taza: "normal", "opener" o "hierarchical"
     * @param i    número identificador de la taza
     */
    public void pushCup(String type, int i) {
        if (cups.size() >= maxHeight) return;
        Cup cup = createCup(type, i);
        cup.setSize(30, (2 * i - 1) * 10);
        applyDefaultColor(cup, i);

        if (type.equals("opener")) {
            removeBlockingLids();
        }

        if (type.equals("hierarchical")) {
            insertHierarchical((HierarchicalCup) cup);
            return;
        }

        cups.add(cup);
        towerItems.add(cup);
        if (isVisible) updatePositions();
    }

    private Cup createCup(String type, int number) {
        switch (type) {
            case "opener":       return new OpenerCup(number);
            case "hierarchical": return new HierarchicalCup(number);
            default:             return new Cup(number);
        }
    }

    private void applyDefaultColor(Cup cup, int number) {
        if (cup.getType().equals("normal")) {
            String[] palette = {"red", "green", "yellow", "blue", "magenta"};
            cup.setColor(palette[Math.min(number - 1, palette.length - 1)]);
        }
        // opener y hierarchical ya tienen su color propio
    }

    private void removeBlockingLids() {
        ArrayList<Object> toRemove = new ArrayList<>();
        for (Object obj : towerItems) {
            if (obj instanceof Lid) toRemove.add(obj);
        }
        for (Object obj : toRemove) {
            Lid lid = (Lid) obj;
            lid.makeInvisible();
            lids.remove(lid);
            towerItems.remove(lid);
        }
        // También quitar tapas adjuntas a tazas
        for (Cup c : cups) {
            if (c.hasLid()) {
                c.getLid().makeInvisible();
                c.removeLid();
            }
        }
    }

    private void insertHierarchical(HierarchicalCup cup) {
        // Desplazar (mover al final) todos los objetos de número menor
        ArrayList<Object> smaller = new ArrayList<>();
        ArrayList<Object> rest = new ArrayList<>();
        for (Object obj : towerItems) {
            if (obj instanceof Cup && ((Cup) obj).getNumber() < cup.getNumber()) {
                smaller.add(obj);
            } else {
                rest.add(obj);
            }
        }
        towerItems.clear();
        towerItems.addAll(rest);
        towerItems.add(cup);
        towerItems.addAll(smaller);
        cups.add(cup);

        // Si llegó al fondo (posición 0 en towerItems), marcarla
        if (!towerItems.isEmpty() && towerItems.get(0) == cup) {
            cup.setAtBottom(true);
        }
        if (isVisible) updatePositions();
    }

    /**
     * Inserta una tapa normal para la taza con el número dado.
     *
     * @param i número de la taza a tapar
     */
    public void pushLid(int i) {
        pushLid("normal", i);
    }

    /**
     * Inserta una tapa del tipo especificado para la taza con el número dado.
     *
     * @param type tipo de tapa: "normal", "fearful", "crazy" o "sticky"
     * @param i    número de la taza asociada
     */
    public void pushLid(String type, int i) {
        Lid lid = createLid(type, i);

        if (type.equals("fearful")) {
            // Solo entra si su taza compañera está en la torre
            if (!cupIsInTower(i)) return;
        }

        if (type.equals("crazy")) {
            // Se ubica en la base (posición 0 de towerItems)
            lids.add(lid);
            towerItems.add(0, lid);
            if (isVisible) updatePositions();
            return;
        }

        lids.add(lid);
        towerItems.add(lid);
        if (isVisible) updatePositions();
    }

    private Lid createLid(String type, int number) {
        switch (type) {
            case "fearful": return new FearfulLid(number);
            case "crazy":   return new CrazyLid(number);
            case "sticky":  return new StickyLid(number);
            default:        return new Lid(number);
        }
    }

    private boolean cupIsInTower(int number) {
        for (Cup c : cups) {
            if (c.getNumber() == number) return true;
        }
        return false;
    }

    /**
     * Quita la taza del tope de la torre.
     * Si tiene tapa, la tapa pasa a ser suelta.
     */
    public void popCup() {
        if (cups.isEmpty()) return;
        Cup top = cups.get(cups.size() - 1);
        if (top instanceof HierarchicalCup && ((HierarchicalCup) top).isAtBottom()) return;
        if (top.hasLid()) lids.add(top.removeLid());
        top.makeInvisible();
        cups.remove(cups.size() - 1);
        towerItems.remove(top);
        if (isVisible) updatePositions();
    }

    /**
     * Quita la taza con el número dado de la torre.
     *
     * @param number número de la taza a quitar
     */
    public void removeCup(int number) {
        for (int i = 0; i < cups.size(); i++) {
            Cup cup = cups.get(i);
            if (cup.getNumber() == number) {
                if (cup instanceof HierarchicalCup && ((HierarchicalCup) cup).isAtBottom()) return;
                if (cup.hasLid()) lids.add(cup.removeLid());
                cup.makeInvisible();
                cups.remove(i);
                towerItems.remove(cup);
                if (isVisible) updatePositions();
                return;
            }
        }
    }

    /**
     * Quita la tapa del tope de la torre.
     * Si es sticky, se adhiere a la siguiente taza disponible.
     */
    public void popLid() {
        if (cups.isEmpty()) return;
        Cup top = cups.get(cups.size() - 1);
        if (!top.hasLid()) return;

        Lid lid = top.removeLid();
        int originalCupNumber = lid.getCupNumber();

        if (lid instanceof StickyLid) {
            // Se pega a la primera taza disponible distinta a la original
            for (int i = cups.size() - 1; i >= 0; i--) {
                Cup candidate = cups.get(i);
                if (candidate.getNumber() != originalCupNumber && !candidate.hasLid()) {
                    candidate.putLid(lid);
                    if (isVisible) updatePositions();
                    return;
                }
            }
        }

        lid.makeInvisible();
        lids.add(lid);
        if (isVisible) updatePositions();
    }

    /**
     * Quita la tapa suelta asociada a la taza con el número dado.
     *
     * @param cupNumber número de la taza cuya tapa suelta se quita
     */
    public void removeLid(int cupNumber) {
        for (int i = 0; i < lids.size(); i++) {
            if (lids.get(i).getCupNumber() == cupNumber) {
                lids.get(i).makeInvisible();
                towerItems.remove(lids.get(i));
                lids.remove(i);
                if (isVisible) updatePositions();
                return;
            }
        }
    }

    /**
     * Ordena la torre de mayor a menor (base a tope).
     */
    public void orderTower() {
        ArrayList<Cup> sorted = new ArrayList<>(cups);
        sorted.sort((a, b) -> Integer.compare(b.getNumber(), a.getNumber()));
        towerItems.clear();
        cups.clear();
        for (Cup c : sorted) {
            cups.add(c);
            towerItems.add(c);
        }
        for (Lid l : lids) towerItems.add(l);
        if (isVisible) updatePositions();
    }

    /**
     * Invierte el orden de todos los elementos de la torre.
     */
    public void reverseTower() {
        Collections.reverse(towerItems);
        if (isVisible) updatePositions();
    }

    /**
     * Intercambia dos elementos de la torre identificados por tipo y número.
     *
     * @param o1 identificador del primer elemento, ej. {"cup","4"}
     * @param o2 identificador del segundo elemento, ej. {"lid","2"}
     */
    public void swap(String[] o1, String[] o2) {
        int i1 = findIndex(o1);
        int i2 = findIndex(o2);
        if (i1 == -1 || i2 == -1) return;
        Collections.swap(towerItems, i1, i2);
        // Sincronizar lista cups con el nuevo orden de towerItems
        cups.clear();
        for (Object obj : towerItems) {
            if (obj instanceof Cup) cups.add((Cup) obj);
        }
        if (isVisible) updatePositions();
    }

    /**
     * Cubre cada taza con su tapa suelta correspondiente si existe.
     */
    public void cover() {
        ArrayList<Lid> toRemove = new ArrayList<>();
        for (Object obj : towerItems) {
            if (obj instanceof Cup) {
                Cup cup = (Cup) obj;
                if (!cup.hasLid()) {
                    Lid lid = findLooseLid(cup.getNumber());
                    if (lid != null) {
                        cup.putLid(lid);
                        lids.remove(lid);
                        toRemove.add(lid);
                    }
                }
            }
        }
        for (Lid l : toRemove) towerItems.remove(l);
        if (isVisible) updatePositions();
    }

    /**
     * Retorna la altura de la torre (tamaño del elemento base).
     *
     * @return altura en unidades
     */
    public int height() {
        if (towerItems.isEmpty()) return 0;
        Object first = towerItems.get(0);
        if (first instanceof Cup) return 2 * ((Cup) first).getNumber() - 1;
        return 1;
    }

    /**
     * Retorna los números de las tazas que tienen tapa.
     *
     * @return arreglo con los números de tazas tapadas
     */
    public int[] lidedCups() {
        ArrayList<Integer> temp = new ArrayList<>();
        for (Cup c : cups) {
            if (c.hasLid()) temp.add(c.getNumber());
        }
        int[] result = new int[temp.size()];
        for (int i = 0; i < temp.size(); i++) result[i] = temp.get(i);
        return result;
    }

    /**
     * Retorna todos los elementos de la torre como arreglo de identificadores.
     * Cada elemento es {"cup"/"lid", "número"}.
     *
     * @return arreglo de identificadores
     */
    public String[][] stackingItems() {
        String[][] result = new String[towerItems.size()][2];
        for (int i = 0; i < towerItems.size(); i++) result[i] = getIdentifier(i);
        return result;
    }

    /**
     * Sugiere un intercambio que reduciría la altura de la torre.
     * Los objetos se identifican por su tipo y número.
     *
     * @return par de identificadores a intercambiar, o null si no hay mejora posible
     */
    public String[][] swapToReduce() {
        if (towerItems.isEmpty()) return null;
        Object first = towerItems.get(0);

        if (first instanceof Lid) {
            for (int i = 1; i < towerItems.size(); i++) {
                if (towerItems.get(i) instanceof Cup)
                    return new String[][]{getIdentifier(0), getIdentifier(i)};
            }
        }

        if (first instanceof Cup) {
            int firstNum = ((Cup) first).getNumber();
            for (int i = 1; i < towerItems.size(); i++) {
                Object obj = towerItems.get(i);
                if (obj instanceof Cup && ((Cup) obj).getNumber() < firstNum)
                    return new String[][]{getIdentifier(0), getIdentifier(i)};
            }
            for (int i = 1; i < towerItems.size(); i++) {
                if (towerItems.get(i) instanceof Lid)
                    return new String[][]{getIdentifier(0), getIdentifier(i)};
            }
        }
        return null;
    }

    /**
     * Hace visible la torre en el canvas.
     */
    public void makeVisible() {
        isVisible = true;
        axisX.makeVisible();
        for (shapes.Rectangle mark : axisY) mark.makeVisible();
        updatePositions();
    }

    /**
     * Hace invisible la torre en el canvas.
     */
    public void makeInvisible() {
        isVisible = false;
        axisX.makeInvisible();
        for (shapes.Rectangle mark : axisY) mark.makeInvisible();
        for (Cup c : cups) {
            c.makeInvisible();
            if (c.hasLid()) c.getLid().makeInvisible();
        }
        for (Lid l : lids) l.makeInvisible();
    }

    /**
     * Libera todos los recursos de la torre.
     */
    public void exit() {
        makeInvisible();
        cups.clear();
        lids.clear();
        towerItems.clear();
    }

    /**
     * Verifica si la torre está en estado válido (tazas de mayor a menor).
     *
     * @return true si está ordenada correctamente
     */
    public boolean ok() {
        for (int i = 0; i < cups.size() - 1; i++) {
            if (cups.get(i).getNumber() < cups.get(i + 1).getNumber()) return false;
        }
        return true;
    }

    // ---- métodos privados de apoyo ----

    private int findIndex(String[] id) {
        String type = id[0];
        int number = Integer.parseInt(id[1]);
        for (int i = 0; i < towerItems.size(); i++) {
            Object obj = towerItems.get(i);
            if (type.equals("cup") && obj instanceof Cup && ((Cup) obj).getNumber() == number) return i;
            if (type.equals("lid") && obj instanceof Lid && ((Lid) obj).getCupNumber() == number) return i;
        }
        return -1;
    }

    private Lid findLooseLid(int number) {
        for (Lid l : lids) {
            if (l.getCupNumber() == number) return l;
        }
        return null;
    }

    private String[] getIdentifier(int index) {
        Object obj = towerItems.get(index);
        if (obj instanceof Cup) return new String[]{"cup", "" + ((Cup) obj).getNumber()};
        return new String[]{"lid", "" + ((Lid) obj).getCupNumber()};
    }

    private void updatePositions() {
        int totalSlots = 0;
        for (Object obj : towerItems) {
            totalSlots++;
            if (obj instanceof Cup && ((Cup) obj).hasLid()) totalSlots++;
        }
        if (totalSlots == 0) return;

        int slotHeight = Math.min(30, 250 / totalSlots);
        int baseY = 270;
        int stackIndex = 0;

        for (Object obj : towerItems) {
            if (obj instanceof Cup) {
                Cup cup = (Cup) obj;
                int cupX = 45 + (width * 10 - cup.getWidth()) / 2;
                int cupY = baseY - (stackIndex * slotHeight);
                cup.setSize(slotHeight, cup.getWidth());
                cup.setPosition(cupX, cupY);
                if (isVisible) cup.makeVisible();
                stackIndex++;
                if (cup.hasLid()) {
                    Lid lid = cup.getLid();
                    lid.setPosition(cupX, baseY - (stackIndex * slotHeight));
                    lid.setSize(cup.getWidth());
                    if (isVisible) lid.makeVisible();
                    stackIndex++;
                }
            } else if (obj instanceof Lid) {
                Lid lid = (Lid) obj;
                lid.setPosition(45 + (width * 10 - 30) / 2, baseY - (stackIndex * slotHeight));
                lid.setSize(30);
                if (isVisible) lid.makeVisible();
                stackIndex++;
            }
        }
    }
}
