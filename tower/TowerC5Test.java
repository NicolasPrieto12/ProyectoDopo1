package tower;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Pruebas unitarias del Ciclo 5.
 * Objetivo: superar 75% de cobertura en todas las clases de dominio.
 * Se cubren métodos no ejercitados en ciclos anteriores.
 *
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version 5.0
 */
public class TowerC5Test {

    // ---- Lid ----

    @Test
    public void lidGetTypeShouldReturnNormal() {
        Lid lid = new Lid(1);
        assertEquals("normal", lid.getType());
    }

    @Test
    public void lidSetColorAndMakeVisible() {
        Lid lid = new Lid(2);
        lid.setColor("red");
        lid.setSize(40);
        lid.makeVisible();
        lid.makeInvisible();
        assertEquals(2, lid.getCupNumber());
    }

    @Test
    public void lidSetPositionUpdatesCorrectly() {
        Lid lid = new Lid(3);
        lid.setPosition(100, 100);
        lid.setPosition(150, 150);
        assertEquals(3, lid.getCupNumber());
    }

    @Test
    public void fearfulLidGetTypeShouldReturnFearful() {
        FearfulLid lid = new FearfulLid(1);
        assertEquals("fearful", lid.getType());
    }

    @Test
    public void crazyLidGetTypeShouldReturnCrazy() {
        CrazyLid lid = new CrazyLid(1);
        assertEquals("crazy", lid.getType());
    }

    @Test
    public void stickyLidGetTypeShouldReturnSticky() {
        StickyLid lid = new StickyLid(1);
        assertEquals("sticky", lid.getType());
    }

    // ---- Cup ----

    @Test
    public void cupGetTypeShouldReturnNormal() {
        Cup cup = new Cup(1);
        assertEquals("normal", cup.getType());
    }

    @Test
    public void cupGetWidthAfterSetSize() {
        Cup cup = new Cup(2);
        cup.setSize(30, 50);
        assertEquals(50, cup.getWidth());
    }

    @Test
    public void cupGetXPosAndYPos() {
        Cup cup = new Cup(1);
        cup.setPosition(100, 200);
        assertEquals(100, cup.getXPos());
        assertEquals(200, cup.getYPos());
    }

    @Test
    public void cupMakeVisibleAndInvisible() {
        Cup cup = new Cup(1);
        cup.setColor("blue");
        cup.makeVisible();
        cup.makeInvisible();
        assertEquals(1, cup.getNumber());
    }

    @Test
    public void openerCupGetTypeShouldReturnOpener() {
        OpenerCup cup = new OpenerCup(1);
        assertEquals("opener", cup.getType());
    }

    @Test
    public void hierarchicalCupAtBottomDefaultFalse() {
        HierarchicalCup cup = new HierarchicalCup(1);
        assertFalse(cup.isAtBottom());
    }

    @Test
    public void hierarchicalCupSetAtBottom() {
        HierarchicalCup cup = new HierarchicalCup(2);
        cup.setAtBottom(true);
        assertTrue(cup.isAtBottom());
    }

    // ---- Tower ----

    @Test
    public void reverseTowerInvertsOrder() {
        Tower t = new Tower(3);
        t.reverseTower();
        String[][] items = t.stackingItems();
        assertEquals("cup", items[0][0]);
        assertEquals("1", items[0][1]);
    }

    @Test
    public void removeLidRemovesLooseLid() {
        Tower t = new Tower(9, 1000);
        t.pushCup(2);
        t.pushLid(2);
        t.removeLid(2);
        for (String[] item : t.stackingItems()) {
            assertFalse(item[0].equals("lid") && item[1].equals("2"));
        }
    }

    @Test
    public void coverWithMultipleLids() {
        Tower t = new Tower(9, 1000);
        t.pushCup(1);
        t.pushCup(2);
        t.pushCup(3);
        t.pushLid(1);
        t.pushLid(2);
        t.pushLid(3);
        t.cover();
        assertEquals(3, t.lidedCups().length);
    }

    @Test
    public void swapToReduceWithLidFirst() {
        Tower t = new Tower(9, 1000);
        t.pushCup(3);
        t.pushLid(3);
        // Poner lid al frente manualmente via swap
        t.swap(new String[]{"cup", "3"}, new String[]{"lid", "3"});
        String[][] suggestion = t.swapToReduce();
        assertNotNull(suggestion);
    }

    @Test
    public void popCupFromEmptyTowerDoesNothing() {
        Tower t = new Tower(9, 1000);
        t.popCup(); // no debe lanzar excepción
        assertEquals(0, t.stackingItems().length);
    }

    @Test
    public void popLidFromCupWithoutLidDoesNothing() {
        Tower t = new Tower(9, 1000);
        t.pushCup(1);
        t.popLid(); // no debe lanzar excepción
        assertEquals(0, t.lidedCups().length);
    }

    @Test
    public void heightWithLidAtBase() {
        Tower t = new Tower(9, 1000);
        t.pushLid(1);
        assertEquals(1, t.height());
    }

    @Test
    public void towerContestSolvePossible() {
        assertEquals("POSSIBLE", TowerContest.solve(3, 4));
    }

    @Test
    public void towerContestSolveImpossible() {
        assertEquals("IMPOSSIBLE", TowerContest.solve(1, 3));
    }

    @Test
    public void towerContestSimulateImpossible() {
        // No debe lanzar excepción
        TowerContest.simulate(1, 5);
    }

    @Test
    public void towerContestSimulatePossibleOdd() {
        TowerContest.simulate(3, 3);
    }

    @Test
    public void towerContestSimulatePossibleEven() {
        TowerContest.simulate(3, 4);
    }

    @Test
    public void towerContestSimulateTooLarge() {
        TowerContest.simulate(20, 31);
    }
}
