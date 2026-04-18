package tower;

/**
 * Taza de tipo opener. Al entrar a la torre, elimina todas las tapas
 * que le impiden el paso (tapas ubicadas por encima de su posición de inserción).
 * Se distingue visualmente por su color naranja.
 *
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version 4.0
 */
public class OpenerCup extends Cup {

    /**
     * Crea una taza opener con el número identificador dado.
     * Su color distintivo es naranja.
     *
     * @param number número identificador de la taza
     */
    public OpenerCup(int number) {
        super(number);
        setColor("orange");
    }

    /**
     * {@inheritDoc}
     * Retorna "opener" como tipo de esta taza.
     */
    @Override
    public String getType() {
        return "opener";
    }
}
