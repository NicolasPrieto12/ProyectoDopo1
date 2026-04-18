package tower;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Pruebas unitarias del Ciclo 4.
 * Cubren los nuevos tipos de taza y tapa, y los métodos actualizados de Tower.
 *
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version 4.0
 */
public class TowerC4Test {

    private Tower tower;

    @Before
    public void setUp() {
        tower = new Tower(4);
    }

    // ---- OpenerCup ----

    @Test
    public void openerCupShouldRemoveLooseLidsOnEntry() {
        tower.pushLid(1);
        tower.pushLid(2);
        tower.pushCup("opener", 5);

        String[][] items = tower.stackingItems();
        for (String[] item : items) {
            assertFalse("No debe quedar ninguna tapa suelta tras insertar opener",
                item[0].equals("lid"));
        }
    }

    @Test
    public void openerCupShouldRemoveAttachedLidsOnEntry() {
        tower.pushLid("normal", 3);
        // Cubrir la taza 3 con su tapa
        tower.cover();
        tower.pushCup("opener", 5);

        assertEquals("Ninguna taza debe tener tapa tras insertar opener", 0, tower.lidedCups().length);
    }

    @Test
    public void openerCupShouldHaveTypeOpener() {
        tower.pushCup("opener", 5);
        String[][] items = tower.stackingItems();
        // Buscar la cup 5
        boolean found = false;
        for (String[] item : items) {
            if (item[0].equals("cup") && item[1].equals("5")) found = true;
        }
        assertTrue("La taza opener debe estar en la torre", found);
    }

    // ---- HierarchicalCup ----

    @Test
    public void hierarchicalCupShouldDisplaceSmallerCups() {
        // Torre tiene cups 4,3,2,1 (de base a tope)
        // Insertar hierarchical 3 debe desplazar cups 1 y 2
        tower.pushCup("hierarchical", 3);
        String[][] items = tower.stackingItems();
        // La cup hierarchical 3 debe estar antes que cups 1 y 2
        int posH = -1, pos1 = -1, pos2 = -1;
        for (int i = 0; i < items.length; i++) {
            if (items[i][0].equals("cup")) {
                if (items[i][1].equals("3") && posH == -1) posH = i; // primera cup 3
                if (items[i][1].equals("1")) pos1 = i;
                if (items[i][1].equals("2")) pos2 = i;
            }
        }
        assertTrue("HierarchicalCup 3 debe estar antes que cup 1", posH < pos1);
        assertTrue("HierarchicalCup 3 debe estar antes que cup 2", posH < pos2);
    }

    @Test
    public void hierarchicalCupAtBottomShouldNotBeRemoved() {
        Tower t = new Tower(1);
        t.pushCup("hierarchical", 1);
        // La cup hierarchical 1 debería estar en el fondo
        t.popCup();
        String[][] items = t.stackingItems();
        boolean stillThere = false;
        for (String[] item : items) {
            if (item[0].equals("cup") && item[1].equals("1")) stillThere = true;
        }
        assertTrue("HierarchicalCup en el fondo no debe poder removerse", stillThere);
    }

    // ---- FearfulLid ----

    @Test
    public void fearfulLidShouldNotEnterIfCupNotInTower() {
        Tower t = new Tower(9, 1000);
        // No hay ninguna taza en la torre
        t.pushLid("fearful", 1);
        assertEquals("FearfulLid no debe entrar si su taza no está", 0, t.stackingItems().length);
    }

    @Test
    public void fearfulLidShouldEnterIfCupIsInTower() {
        tower.pushLid("fearful", 1);
        String[][] items = tower.stackingItems();
        boolean found = false;
        for (String[] item : items) {
            if (item[0].equals("lid") && item[1].equals("1")) found = true;
        }
        assertTrue("FearfulLid debe entrar si su taza está en la torre", found);
    }

    // ---- CrazyLid ----

    @Test
    public void crazyLidShouldGoToBase() {
        tower.pushLid("crazy", 2);
        String[][] items = tower.stackingItems();
        assertEquals("CrazyLid debe estar en la posición 0 (base)", "lid", items[0][0]);
        assertEquals("CrazyLid debe ser la tapa 2", "2", items[0][1]);
    }

    // ---- StickyLid ----

    @Test
    public void stickyLidShouldAttachToNextCupOnPop() {
        Tower t = new Tower(2);
        // cups: 2, 1 (de base a tope)
        t.pushLid("sticky", 1); // tapa sticky para cup 1
        t.cover(); // cubre cup 1 con la sticky lid
        t.popLid(); // al salir, debe pegarse a cup 2
        assertEquals("Cup 2 debe tener la sticky lid tras popLid", 1, t.lidedCups().length);
        assertEquals("La taza tapada debe ser la 2", 2, t.lidedCups()[0]);
    }

    // ---- Tower métodos generales ----

    @Test
    public void pushCupNormalShouldAddCupToTower() {
        Tower t = new Tower(9, 1000);
        t.pushCup("normal", 3);
        String[][] items = t.stackingItems();
        assertEquals(1, items.length);
        assertEquals("cup", items[0][0]);
        assertEquals("3", items[0][1]);
    }

    @Test
    public void pushLidNormalShouldAddLidToTower() {
        Tower t = new Tower(9, 1000);
        t.pushCup(1);
        t.pushLid("normal", 1);
        String[][] items = t.stackingItems();
        boolean found = false;
        for (String[] item : items) {
            if (item[0].equals("lid") && item[1].equals("1")) found = true;
        }
        assertTrue("Tapa normal debe estar en la torre", found);
    }

    @Test
    public void okShouldReturnTrueForOrderedTower() {
        assertTrue("Torre ordenada debe retornar ok=true", tower.ok());
    }

    @Test
    public void okShouldReturnFalseForDisorderedTower() {
        tower.swap(new String[]{"cup", "4"}, new String[]{"cup", "1"});
        assertFalse("Torre desordenada debe retornar ok=false", tower.ok());
    }

    @Test
    public void heightShouldReflectBaseElement() {
        // Base es cup 4, altura = 2*4-1 = 7
        assertEquals(7, tower.height());
    }
}
