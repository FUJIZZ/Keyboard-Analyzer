package cpoo5.layout_evaluator.model.layout;

import cpoo5.layout_evaluator.model.utils.*;
import cpoo5.layout_evaluator.model.key.Key;
import cpoo5.layout_evaluator.model.key.DeadKey;

import java.util.List;
import java.util.Map;

public interface KeyboardLayout {
    String getName();
    Map<Key, Map<Layer, Character>> getMappings();
    Geometry getGeometry();
    Key findKeyForCharacter(char character, Layer layer);
    List<Key> findKeysForCharacter(char character);
    Layer getLayerForCharacter(char character);
    Key findModifierKeyForLayer(Layer layer);
    Map<String, DeadKey> getDeadKeys();


    KeyboardLayout copy();
}

