package tower;

/**
 * Tapa de tipo fearful (miedosa). Tiene dos comportamientos especiales:
 * - Si su taza compañera no está en la torre, no entra.
 * - Si está tapando a su taza compañera, no sale.
 * Se distingue visualmente por su color rosa (pink).
 *
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version 4.0
 */
public class FearfulLid extends Lid {

    /**
     * Crea una tapa fearful asociada al número de taza dado.
     * Su color distintivo es rosa.
     *
     * @param cupNumber número de la taza asociada
     */
    public FearfulLid(int cupNumber) {
        super(cupNumber);
        setColor("pink");
    }

    /**
     * {@inheritDoc}
     * Retorna "fearful" como tipo de esta tapa.
     */
    @Override
    public String getType() {
        return "fearful";
    }
}
