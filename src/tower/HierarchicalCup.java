package tower;

/**
 * Taza de tipo hierarchical. Al entrar a la torre va desplazando todos los
 * objetos de menor tamaño (número menor). Si logra llegar al fondo de la torre,
 * no se deja quitar (es inamovible desde el fondo).
 * Se distingue visualmente por su color cyan.
 *
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version 4.0
 */
public class HierarchicalCup extends Cup {

    private boolean atBottom;

    /**
     * Crea una taza hierarchical con el número identificador dado.
     * Su color distintivo es cyan.
     *
     * @param number número identificador de la taza
     */
    public HierarchicalCup(int number) {
        super(number);
        setColor("cyan");
        this.atBottom = false;
    }

    /**
     * Marca esta taza como ubicada en el fondo de la torre.
     * Una vez en el fondo, no puede ser removida.
     *
     * @param atBottom true si está en el fondo
     */
    public void setAtBottom(boolean atBottom) {
        this.atBottom = atBottom;
    }

    /**
     * Indica si esta taza está en el fondo de la torre.
     *
     * @return true si está en el fondo y no puede removerse
     */
    public boolean isAtBottom() {
        return atBottom;
    }

    /**
     * {@inheritDoc}
     * Retorna "hierarchical" como tipo de esta taza.
     */
    @Override
    public String getType() {
        return "hierarchical";
    }
}
