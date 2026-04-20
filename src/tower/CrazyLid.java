package tower;

/**
 * Tapa de tipo crazy (loca). En lugar de tapar a su taza compañera,
 * se ubica en la base de la torre al ser insertada.
 * Se distingue visualmente por su color verde lima (lime).
 *
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version 4.0
 */
public class CrazyLid extends Lid {

    /**
     * Crea una tapa crazy asociada al número de taza dado.
     * Su color distintivo es verde lima.
     *
     * @param cupNumber número de la taza asociada
     */
    public CrazyLid(int cupNumber) {
        super(cupNumber);
        setColor("green");
    }

    /**
     * {@inheritDoc}
     * Retorna "crazy" como tipo de esta tapa.
     */
    @Override
    public String getType() {
        return "crazy";
    }
}
