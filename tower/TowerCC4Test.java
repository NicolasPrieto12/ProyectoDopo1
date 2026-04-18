package tower;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Pruebas de casos comunes del Ciclo 4.
 * Creación colectiva siguiendo el protocolo definido.
 * Cada caso prueba un escenario específico de los nuevos tipos.
 *
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version 4.0
 */
public class TowerCC4Test {

    /**
     * CC4-01: Torre vacía tiene altura 0.
     */
    @Test
    public void testCC401_emptyTowerHeightIsZero() {
        Tower t = new Tower(9, 1000);
        assertEquals(0, t.height());
    }

    /**
     * CC4-02: pushCup normal agrega la taza correctamente.
     */
    @Test
    public void testCC402_pushNormalCupAddsToTower() {
        Tower t = new Tower(9, 1000);
        t.pushCup("normal", 2);
        assertEquals(1, t.stackingItems().length);
        assertEquals("cup", t.stackingItems()[0][0]);
    }

    /**
     * CC4-03: pushCup opener elimina tapas sueltas existentes.
     */
    @Test
    public void testCC403_openerRemovesLooseLids() {
        Tower t = new Tower(9, 1000);
        t.pushCup(1);
        t.pushLid(1);
        t.pushCup("opener", 3);
        for (String[] item : t.stackingItems()) {
            assertFalse("No debe haber tapas sueltas", item[0].equals("lid"));
        }
    }

    /**
     * CC4-04: pushCup hierarchical desplaza tazas menores.
     */
    @Test
    public void testCC404_hierarchicalDisplacesSmaller() {
        Tower t = new Tower(9, 1000);
        t.pushCup(1);
        t.pushCup(2);
        t.pushCup("hierarchical", 3);
        String[][] items = t.stackingItems();
        // cup 3 debe aparecer antes que cup 1 y cup 2
        int pos3 = -1, pos1 = -1;
        for (int i = 0; i < items.length; i++) {
            if (items[i][0].equals("cup") && items[i][1].equals("3") && pos3 == -1) pos3 = i;
            if (items[i][0].equals("cup") && items[i][1].equals("1")) pos1 = i;
        }
        assertTrue("HierarchicalCup 3 debe estar antes que cup 1", pos3 < pos1);
    }

    /**
     * CC4-05: FearfulLid no entra si su taza no está.
     */
    @Test
    public void testCC405_fearfulLidBlockedWithoutCup() {
        Tower t = new Tower(9, 1000);
        t.pushLid("fearful", 5);
        assertEquals(0, t.stackingItems().length);
    }

    /**
     * CC4-06: FearfulLid entra si su taza está en la torre.
     */
    @Test
    public void testCC406_fearfulLidEntersWithCup() {
        Tower t = new Tower(9, 1000);
        t.pushCup(2);
        t.pushLid("fearful", 2);
        assertEquals(2, t.stackingItems().length);
    }

    /**
     * CC4-07: CrazyLid se ubica en la base.
     */
    @Test
    public void testCC407_crazyLidGoesToBase() {
        Tower t = new Tower(9, 1000);
        t.pushCup(1);
        t.pushCup(2);
        t.pushLid("crazy", 1);
        assertEquals("lid", t.stackingItems()[0][0]);
    }

    /**
     * CC4-08: StickyLid se pega a la siguiente taza al hacer popLid.
     */
    @Test
    public void testCC408_stickyLidAttachesOnPop() {
        Tower t = new Tower(9, 1000);
        t.pushCup(2);
        t.pushCup(1);
        t.pushLid("sticky", 1);
        t.cover();
        t.popLid();
        assertEquals(1, t.lidedCups().length);
        assertEquals(2, t.lidedCups()[0]);
    }

    /**
     * CC4-09: cover() asocia tapas sueltas a sus tazas.
     */
    @Test
    public void testCC409_coverAssociatesLooseLids() {
        Tower t = new Tower(9, 1000);
        t.pushCup(3);
        t.pushLid(3);
        t.cover();
        assertEquals(1, t.lidedCups().length);
        assertEquals(3, t.lidedCups()[0]);
    }

    /**
     * CC4-10: removeCup quita la taza correcta.
     */
    @Test
    public void testCC410_removeCupRemovesCorrectCup() {
        Tower t = new Tower(3);
        t.removeCup(2);
        for (String[] item : t.stackingItems()) {
            assertFalse("Cup 2 no debe estar en la torre", item[0].equals("cup") && item[1].equals("2"));
        }
    }

    /**
     * CC4-11: orderTower ordena de mayor a menor.
     */
    @Test
    public void testCC411_orderTowerSortsDescending() {
        Tower t = new Tower(9, 1000);
        t.pushCup(1);
        t.pushCup(3);
        t.pushCup(2);
        t.orderTower();
        assertTrue("Torre debe estar ordenada tras orderTower", t.ok());
    }

    /**
     * CC4-12: swapToReduce retorna null con un solo elemento.
     */
    @Test
    public void testCC412_swapToReduceNullWithOneElement() {
        Tower t = new Tower(9, 1000);
        t.pushCup(1);
        assertNull(t.swapToReduce());
    }
}
