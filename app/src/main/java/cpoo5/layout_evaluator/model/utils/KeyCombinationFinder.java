package cpoo5.layout_evaluator.model.utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cpoo5.layout_evaluator.model.key.DeadKey;
import cpoo5.layout_evaluator.model.key.Key;
import cpoo5.layout_evaluator.model.layout.DefaultKeyboardLayout;
import cpoo5.layout_evaluator.model.layout.KeyboardLayout;

public class KeyCombinationFinder {

    private final KeyboardLayout keyboardLayout;

    public KeyCombinationFinder(KeyboardLayout keyboardLayout) {
        this.keyboardLayout = keyboardLayout;
    }

    /**
     * Retourne la combinaison de touches nécessaires pour produire un caractère donné.
     *
     * @param character Le caractère pour lequel on veut obtenir la combinaison.
     * @return Une liste de touches nécessaires pour produire le caractère.
     * @throws IllegalArgumentException Si le caractère n'est pas trouvé dans le layout.
     */
    public List<Key> getCombinationForCharacter(char character) {
        List<Key> combination = new ArrayList<>();
        Layer layer = keyboardLayout.getLayerForCharacter(character);

        if (layer == null) {
            // Cas où le layout ne connaît pas directement le caractère => on tente toutes les DeadKeys
            for (Map.Entry<String, DeadKey> entry : ((DefaultKeyboardLayout) keyboardLayout).getDeadKeys().entrySet()) {
                DeadKey dk = entry.getValue();
                // Parcours de a à z
                for (char c = 'a'; c <= 'z'; c++) {
                    // Test en minuscule
                    if (dk.transform(c) == character) {
                        applyDeadKeyCombination(combination, dk, c);
                        return combination;
                    }
                    // Test en majuscule
                    char upper = Character.toUpperCase(c);
                    if (dk.transform(upper) == character) {
                        applyDeadKeyCombination(combination, dk, upper);
                        return combination;
                    }
                }
            }
            // Aucune DeadKey n'a permis de produire le caractère
            throw new IllegalArgumentException("Caractère '" + character + "' introuvable même via DeadKey.");
        }

        // Cas normal (caractère géré directement par le layout)
        addModifierLayertoCombination(combination, layer);
        Key mainKey = keyboardLayout.findKeyForCharacter(character, layer);
        if (mainKey == null) {
            throw new IllegalArgumentException("Key for character '" + character + "' not found.");
        }
        combination.add(mainKey);
        return combination;
    }


    /**
     * Applique une combinaison de touches avec une touche morte (DeadKey).
     *
     * @param combination La liste des touches à laquelle ajouter la combinaison.
     * @param dk La touche morte à appliquer.
     * @param baseChar Le caractère de base (en minuscule ou majuscule) à combiner avec la touche morte.
     * @throws IllegalArgumentException Si la lettre de base ne peut pas être invoquée.
     */
    private void applyDeadKeyCombination(List<Key> combination, DeadKey dk, char baseChar) {
        // 1ère frappe : la DeadKey
        Layer deadKeyLayer = keyboardLayout.getLayerForCharacter(dk.getLabel().charAt(0));
        addModifierLayertoCombination(combination, deadKeyLayer);
        Key deadKey = keyboardLayout.findKeyForCharacter(dk.getLabel().charAt(0), deadKeyLayer);
        combination.add(deadKey);

        // 2ème frappe : la touche de base (c en minuscule ou majuscule)
        Layer baseLayer = keyboardLayout.getLayerForCharacter(baseChar);
        addModifierLayertoCombination(combination, baseLayer);
        Key inputKey = keyboardLayout.findKeyForCharacter(baseChar, baseLayer == null ? Layer.NORMAL : baseLayer);
        if (inputKey == null) {
            throw new IllegalArgumentException("Impossible d’invoquer la lettre base '" + baseChar + "'");
        }
        combination.add(inputKey);
    }


    /**
     * Ajoute une couche modificateur à la combinaison si la couche spécifiée n'est pas nulle et différente de la couche normale.
     *
     * @param combination la liste de clés représentant la combinaison actuelle
     * @param layer la couche à ajouter à la combinaison
     */
    private void addModifierLayertoCombination(List<Key> combination, Layer layer) {
        if (layer != null && layer != Layer.NORMAL) {
            Key modifierKey = keyboardLayout.findModifierKeyForLayer(layer);
            if (modifierKey != null) {
                combination.add(modifierKey);
            }
        }
    }
}
