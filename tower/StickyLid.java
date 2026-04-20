package tower;

/**
 * Tapa de tipo sticky (pegajosa). Tipo nuevo propuesto por el equipo.
 * Comportamiento especial: al ser removida de su taza, en lugar de salir
 * de la torre, se adhiere automáticamente a la taza que quede en el tope.
 * Si no hay otra taza disponible, sale normalmente.
 * Se distingue visualmente por su color morado (magenta oscuro).
 *
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version 4.0
 */
public class StickyLid extends Lid {

    /**
     * Crea una tapa sticky asociada al número de taza dado.
     * Su color distintivo es magenta.
     *
     * @param cupNumber número de la taza asociada
     */
    public StickyLid(int cupNumber) {
        super(cupNumber);
        setColor("magenta");
    }

    /**
     * {@inheritDoc}
     * Retorna "sticky" como tipo de esta tapa.
     */
    @Override
    public String getType() {
        return "sticky";
    }
}
