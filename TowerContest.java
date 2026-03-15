/**
 * Clase que resuelve el problema de la maratón de Stacking Cups.
 * Esta clase NO usa Tower para resolver, solo para simular.
 * 
 * @autores Nicolás Prieto y Sebastian Peña
 * @version 3
 */
public class TowerContest {
    
    /**
     * Resuelve el problema de la maratón.
     * Determina si es posible construir una torre de altura h con n tazas.
     * 
     * @param n número de tazas disponibles (1 a n)
     * @param h altura objetivo de la torre
     * @return "POSSIBLE" si es posible, "IMPOSSIBLE" si no lo es
     */
    public static String solve(int n, int h) {
        // La altura mínima posible es 1 (una tapa suelta)
        // La altura máxima posible es 2*n - 1 (todas las tazas apiladas sin tapas)
        
        if (h < 1 || h > 2 * n) {
            return "IMPOSSIBLE";
        }
        
        // Si h es impar, siempre es posible usando (h+1)/2 tazas sin tapas
        if (h % 2 == 1) {
            return "POSSIBLE";
        }
        
        // Si h es par, necesitamos h/2 tazas con tapas
        if (n >= h / 2) {
            return "POSSIBLE";
        }
        
        return "IMPOSSIBLE";
    }
    
    /**
     * Simula visualmente la solución del problema usando Tower.
     * 
     * @param n número de tazas disponibles (1 a n)
     * @param h altura objetivo de la torre
     */
    private static Tower currentTower = null;
    
    public static void simulate(int n, int h) {
        // Limpiar la torre anterior del canvas
        if (currentTower != null) {
            currentTower.exit();
            currentTower = null;
        }
        
        String result = solve(n, h);
        
        if (result.equals("IMPOSSIBLE")) {
            System.out.println("IMPOSSIBLE - No se puede simular una solución que no existe.");
            return;
        }
        
        // Verificar si es posible graficar (altura razonable)
        if (h > 30) {
            System.out.println("POSSIBLE - Pero la altura es demasiado grande para graficar.");
            return;
        }
        
        // Crear la torre para simular
        currentTower = new Tower(n, h);
        
        int cupsNeeded = (h % 2 == 1) ? (h + 1) / 2 : h / 2;
        // Escalar el ancho para que quepan en el canvas (max 250px)
        int maxWidth = Math.min(250, cupsNeeded * 20);
        
        for (int i = cupsNeeded; i >= 1; i--) {
            int width = (cupsNeeded == 1) ? maxWidth :
                (int)((2.0 * i - 1) / (2.0 * cupsNeeded - 1) * maxWidth);
            if (width < 10) width = 10;
            currentTower.pushCup(i, width);
        }
        
        if (h % 2 == 0) {
            currentTower.pushLidOnTop();
        }
        
        currentTower.makeVisible();
        System.out.println("POSSIBLE - Solución simulada con altura " + currentTower.height());
    }
}
