package cpoo5.layout_evaluator.model.key;

import java.util.Map;

import cpoo5.layout_evaluator.model.utils.Finger;

public final class DeadKey extends AbstractKey {
    private final Map<Character, Character> transformations; // Transformations possibles

    public DeadKey(String label, int row, int column, Finger finger, Map<Character, Character> transformations) {
        super(label, row, column, finger);
        this.transformations = transformations;
    }

    @Override
    public boolean isModifier() {
        return true; // Une DeadKey agit comme un modificateur
    }


    /**
     * Transforme un caractère en utilisant une table de transformations.
     *
     * @param input le caractère à transformer
     * @return le caractère transformé ou le caractère d'entrée si aucune transformation n'est trouvée
     */
    public Character transform(Character input) {
        return transformations.getOrDefault(input, input);
    }
}

