package cpoo5.layout_evaluator.model.layout;

import cpoo5.layout_evaluator.model.key.DeadKey;
import cpoo5.layout_evaluator.model.key.Key;
import cpoo5.layout_evaluator.model.utils.*;

import java.util.HashMap;
import java.util.Map;

public class KeyboardLayoutBuilder {
    private String name;
    private Geometry geometry;
    private Map<Key, Map<Layer, Character>> mappings = new HashMap<>();
    private Map<Layer, Key> modifiers = new HashMap<>();
    private Map<String, DeadKey> deadKeyMap = new HashMap<>();

    public KeyboardLayoutBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public KeyboardLayoutBuilder withGeometry(Geometry geometry) {
        this.geometry = geometry;
        return this;
    }

    public KeyboardLayoutBuilder addMapping(Map<Key, Map<Layer, Character>> mappings) {
        this.mappings = mappings;
        return this;
    }

    public KeyboardLayoutBuilder addModifiers(Map<Layer, Key> modifiers) {
        this.modifiers = modifiers;
        return this;
    }

    public KeyboardLayoutBuilder addDeadKeys(Map<String, DeadKey> deadKeys) {
        this.deadKeyMap = deadKeys;
        return this;
    }

    public KeyboardLayout build() {
        return new DefaultKeyboardLayout(name, geometry, mappings, modifiers, deadKeyMap);
    }
}
