import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Clase de pruebas para TowerContest.
 * 
 * @autores Nicolás Prieto y Sebastian Peña
 * @version 3
 */
public class TowerContestTest {
    
    @Test
    public void testSolveHeightOne() {
        assertEquals("POSSIBLE", TowerContest.solve(1, 1));
    }
    
    @Test
    public void testSolveHeightZero() {
        assertEquals("IMPOSSIBLE", TowerContest.solve(5, 0));
    }
    
    @Test
    public void testSolveHeightTooLarge() {
        assertEquals("IMPOSSIBLE", TowerContest.solve(3, 10));
    }
    
    @Test
    public void testSolveOddHeight() {
        // Alturas impares siempre son posibles si están en el rango
        assertEquals("POSSIBLE", TowerContest.solve(5, 3));
        assertEquals("POSSIBLE", TowerContest.solve(5, 5));
        assertEquals("POSSIBLE", TowerContest.solve(5, 7));
        assertEquals("POSSIBLE", TowerContest.solve(5, 9));
    }
    
    @Test
    public void testSolveEvenHeightPossible() {
        // Altura par posible cuando n >= h/2
        assertEquals("POSSIBLE", TowerContest.solve(5, 2));
        assertEquals("POSSIBLE", TowerContest.solve(5, 4));
        assertEquals("POSSIBLE", TowerContest.solve(5, 6));
        assertEquals("POSSIBLE", TowerContest.solve(5, 8));
    }
    
    @Test
    public void testSolveEvenHeightImpossible() {
        // Altura par imposible cuando n < h/2
        assertEquals("IMPOSSIBLE", TowerContest.solve(2, 6));
        assertEquals("IMPOSSIBLE", TowerContest.solve(3, 8));
    }
    
    @Test
    public void testSolveMaxHeight() {
        // Altura máxima con tapas es 2*n, sin tapas es 2*n-1
        assertEquals("POSSIBLE", TowerContest.solve(5, 9));
        assertEquals("POSSIBLE", TowerContest.solve(5, 10));
        assertEquals("IMPOSSIBLE", TowerContest.solve(5, 11));
    }
    
    @Test
    public void testSolveExampleFromMarathon() {
        // Ejemplos del problema de la maratón
        assertEquals("POSSIBLE", TowerContest.solve(3, 4));
        assertEquals("IMPOSSIBLE", TowerContest.solve(3, 6));
    }
    
    @Test
    public void testSolveEdgeCases() {
        assertEquals("POSSIBLE", TowerContest.solve(1, 1));
        assertEquals("IMPOSSIBLE", TowerContest.solve(1, 2));
        assertEquals("IMPOSSIBLE", TowerContest.solve(1, 3));
    }
    
    @Test
    public void testSimulateDoesNotThrowException() {
        // Verificar que simulate no lance excepciones
        assertDoesNotThrow(() -> TowerContest.simulate(3, 4));
        assertDoesNotThrow(() -> TowerContest.simulate(3, 6));
        assertDoesNotThrow(() -> TowerContest.simulate(5, 9));
    }
}
