package tower;

import javax.swing.JOptionPane;

/**
 * Pruebas de aceptación del Ciclo 4.
 * Evidencian visualmente los comportamientos más relevantes del simulador.
 * Cada prueba incluye esperas para apreciar la animación y pregunta al usuario
 * si acepta el resultado.
 *
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version 4.0
 */
public class TowerAtest {

    /**
     * Prueba de aceptación 1: OpenerCup elimina todas las tapas al entrar.
     *
     * <p>Escenario:
     * <ol>
     *   <li>Se crea una torre con 3 tazas normales.</li>
     *   <li>Se agregan tapas sueltas para las tazas 1, 2 y 3.</li>
     *   <li>Se cubre cada taza con su tapa (cover).</li>
     *   <li>Se inserta una OpenerCup (número 5).</li>
     *   <li>Se espera que todas las tapas hayan desaparecido.</li>
     * </ol>
     * </p>
     */
    public static void acceptanceTest1() {
        Tower tower = new Tower(9, 1000);

        // Paso 1: agregar tazas normales
        tower.pushCup("normal", 3);
        tower.pushCup("normal", 2);
        tower.pushCup("normal", 1);
        tower.makeVisible();
        sleep(1500);

        // Paso 2: agregar tapas y cubrir
        tower.pushLid("normal", 1);
        tower.pushLid("normal", 2);
        tower.pushLid("normal", 3);
        tower.cover();
        sleep(1500);

        // Paso 3: insertar OpenerCup
        tower.pushCup("opener", 5);
        sleep(1500);

        // Verificar
        int lidCount = 0;
        for (String[] item : tower.stackingItems()) {
            if (item[0].equals("lid")) lidCount++;
        }
        boolean passed = (lidCount == 0);

        int response = JOptionPane.showConfirmDialog(null,
            "Prueba de Aceptación 1: OpenerCup\n\n" +
            "Se insertó una OpenerCup en una torre con 3 tazas tapadas.\n" +
            "Resultado esperado: ninguna tapa en la torre.\n" +
            "Tapas restantes: " + lidCount + "\n\n" +
            (passed ? "✓ PRUEBA PASÓ" : "✗ PRUEBA FALLÓ") + "\n\n" +
            "¿Acepta el resultado?",
            "Prueba de Aceptación 1",
            JOptionPane.YES_NO_OPTION);

        tower.exit();

        if (response == JOptionPane.YES_OPTION && passed) {
            System.out.println("Aceptación 1: ACEPTADA");
        } else {
            System.out.println("Aceptación 1: NO ACEPTADA");
        }
    }

    /**
     * Prueba de aceptación 2: CrazyLid va a la base y StickyLid se pega al hacer popLid.
     *
     * <p>Escenario:
     * <ol>
     *   <li>Se crea una torre con 2 tazas normales (2 y 1).</li>
     *   <li>Se inserta una CrazyLid para la taza 1 → debe ir a la base.</li>
     *   <li>Se inserta una StickyLid para la taza 1 y se cubre.</li>
     *   <li>Se hace popLid → la StickyLid debe pegarse a la taza 2.</li>
     * </ol>
     * </p>
     */
    public static void acceptanceTest2() {
        Tower tower = new Tower(9, 1000);

        // Paso 1: agregar tazas
        tower.pushCup("normal", 2);
        tower.pushCup("normal", 1);
        tower.makeVisible();
        sleep(1500);

        // Paso 2: CrazyLid va a la base
        tower.pushLid("crazy", 1);
        sleep(1500);

        String[][] afterCrazy = tower.stackingItems();
        boolean crazyAtBase = afterCrazy.length > 0
            && afterCrazy[0][0].equals("lid")
            && afterCrazy[0][1].equals("1");
        sleep(1000);

        // Paso 3: StickyLid cubre cup 1 y hace popLid
        tower.pushLid("sticky", 1);
        tower.cover();
        sleep(1500);

        tower.popLid();
        sleep(1500);

        int[] lided = tower.lidedCups();
        boolean stickyAttached = lided.length == 1 && lided[0] == 2;

        boolean passed = crazyAtBase && stickyAttached;

        int response = JOptionPane.showConfirmDialog(null,
            "Prueba de Aceptación 2: CrazyLid + StickyLid\n\n" +
            "CrazyLid fue a la base: " + (crazyAtBase ? "✓ SÍ" : "✗ NO") + "\n" +
            "StickyLid se pegó a cup 2 tras popLid: " + (stickyAttached ? "✓ SÍ" : "✗ NO") + "\n\n" +
            (passed ? "✓ PRUEBA PASÓ" : "✗ PRUEBA FALLÓ") + "\n\n" +
            "¿Acepta el resultado?",
            "Prueba de Aceptación 2",
            JOptionPane.YES_NO_OPTION);

        tower.exit();

        if (response == JOptionPane.YES_OPTION && passed) {
            System.out.println("Aceptación 2: ACEPTADA");
        } else {
            System.out.println("Aceptación 2: NO ACEPTADA");
        }
    }

    private static void sleep(int ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { /* ignorado */ }
    }

    /**
     * Ejecuta ambas pruebas de aceptación.
     *
     * @param args argumentos de línea de comandos (no usados)
     */
    public static void main(String[] args) {
        acceptanceTest1();
        acceptanceTest2();
    }
}
