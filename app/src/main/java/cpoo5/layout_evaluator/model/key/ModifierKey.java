package cpoo5.layout_evaluator.model.key;

import cpoo5.layout_evaluator.model.utils.Finger;

public final class ModifierKey extends AbstractKey {

    public ModifierKey(String label, int row, int column, Finger finger) {
        super(label, row, column, finger);
    }

    @Override
    public boolean isModifier() {
        return true; // Agit comme modificateur
    }
}
