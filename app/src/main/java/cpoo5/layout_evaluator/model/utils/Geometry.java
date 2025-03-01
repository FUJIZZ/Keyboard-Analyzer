package cpoo5.layout_evaluator.model.utils;

import java.util.Set;

import cpoo5.layout_evaluator.model.key.Key;

public record Geometry(int rows, int columns, Set<Key> keys) {
    
    public Key findKeyAt(int row, int column) {
        return keys.stream()
            .filter(key -> key.getRow() == row && key.getColumn() == column)
            .findFirst()
            .orElse(null);
    }
}
