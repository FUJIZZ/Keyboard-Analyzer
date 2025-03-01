package cpoo5.layout_evaluator.model.key;

import cpoo5.layout_evaluator.model.utils.Finger;

/**
 * Classe abstraite représentant une touche de clavier.
 * Implémente l'interface Key.
 * 
 * @see Key
 */
public abstract class AbstractKey implements Key {
    private final String label;
    private final int row;
    private final int column;
    private final Finger finger;

    /**
     * Constructeur de la classe AbstractKey.
     *
     * @param label  Le label de la touche.
     * @param row    La ligne où se trouve la touche.
     * @param column La colonne où se trouve la touche.
     * @param finger Le doigt associé à la touche.
     */
    public AbstractKey(String label, int row, int column, Finger finger) {
        this.label = label;
        this.row = row;
        this.column = column;
        this.finger = finger;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public Finger getFinger() {
        return finger;
    }

    // La méthode isModifier sera définie dans les sous-classes
    @Override
    public abstract boolean isModifier();

    @Override
    public String toString() {
        return "Key{" +
                "label='" + label + '\'' +
                ", row=" + row +
                ", column=" + column +
                ", finger=" + finger +
                '}';
    }
}

