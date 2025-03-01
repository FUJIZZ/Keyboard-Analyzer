package cpoo5.layout_evaluator.model.key;

import cpoo5.layout_evaluator.model.utils.Finger;

public final class StandardKey extends AbstractKey {

    public StandardKey(String label, int row, int column, Finger finger) {
        super(label, row, column, finger);
    }

    @Override
    public boolean isModifier() {
        return false;
    }
    
}
