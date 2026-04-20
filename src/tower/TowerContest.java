package tower;

/**
 * Resuelve y simula el problema de la maratón de Stacking Cups.
 * La lógica de resolución es independiente de la simulación visual.
 *
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version 5.0
 */
public final class TowerContest {

    private static volatile Tower currentTower = null;

    private TowerContest() {
        // clase utilitaria
    }

    /**
     * Determina si es posible construir una torre de altura h con n tazas.
     *
     * @param n número de tazas disponibles (1 a n)
     * @param h altura objetivo de la torre
     * @return "POSSIBLE" si es posible, "IMPOSSIBLE" si no lo es
     */
    public static String solve(int n, int h) {
        if (h < 1 || h > 2 * n) {
            return "IMPOSSIBLE";
        }
        if (h % 2 == 1) {
            return "POSSIBLE";
        }
        if (n >= h / 2) {
            return "POSSIBLE";
        }
        return "IMPOSSIBLE";
    }

    /**
     * Simula visualmente la solución usando Tower.
     *
     * @param n número de tazas disponibles (1 a n)
     * @param h altura objetivo de la torre
     */
    public static void simulate(int n, int h) {
        if (currentTower != null) {
            currentTower.exit();
            currentTower = null;
        }

        if ("IMPOSSIBLE".equals(solve(n, h))) {
            System.out.println("IMPOSSIBLE - No se puede simular una solución que no existe.");
            return;
        }

        if (h > 30) {
            System.out.println("POSSIBLE - Altura demasiado grande para graficar.");
            return;
        }

        currentTower = new Tower(n, h);
        int cupsNeeded = (h % 2 == 1) ? (h + 1) / 2 : h / 2;
        int maxWidth = Math.min(250, cupsNeeded * 20);

        for (int i = cupsNeeded; i >= 1; i--) {
            int width = (cupsNeeded == 1) ? maxWidth
                : (int) ((2.0 * i - 1) / (2.0 * cupsNeeded - 1) * maxWidth);
            if (width < 10) {
                width = 10;
            }
            currentTower.pushCup(i);
        }

        if (h % 2 == 0) {
            currentTower.pushLid(cupsNeeded);
        }

        currentTower.makeVisible();
        System.out.println("POSSIBLE - Solución simulada con altura " + currentTower.height());
    }
}
