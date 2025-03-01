package cpoo5.layout_evaluator.model.key;

import cpoo5.layout_evaluator.model.utils.Finger;

/**
 * Interface représentant une touche de clavier.
 */
public interface Key {

    /**
     * Retourne le nom de la touche.
     *
     * @return le nom de la touche
     */
    String getLabel(); // Nom de la touche

    /**
     * Retourne le numéro de rangée de la touche.
     *
     * @return le numéro de rangée
     */
    int getRow(); // Numéro de rangée 

    /**
     * Retourne le numéro de colonne de la touche.
     *
     * @return le numéro de colonne
     */
    int getColumn(); // Numéro de colonne

    /**
     * Retourne le doigt utilisé pour appuyer sur la touche.
     *
     * @return le doigt utilisé
     */
    Finger getFinger(); // Doigt utilisé

    /**
     * Indique si la touche est un modificateur.
     *
     * @return true si la touche est un modificateur, sinon false
     */
    boolean isModifier(); // Indique si la touche est un modificateur
}
