package cpoo5.layout_evaluator.model.layout;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cpoo5.layout_evaluator.model.key.DeadKey;
import cpoo5.layout_evaluator.model.key.Key;
import cpoo5.layout_evaluator.model.utils.*;


public class DefaultKeyboardLayout implements KeyboardLayout {
    private final String name;
    private final Geometry geometry;
    private final Map<Key, Map<Layer, Character>> mappings;
    private final Map<Layer, Key> modifiers;
    private final Map<String, DeadKey> deadKeys;

    public DefaultKeyboardLayout(String name, Geometry geometry, Map<Key, Map<Layer, Character>> mappings, Map<Layer, Key> modifiers, Map<String, DeadKey> deadKeys) {
        this.name = name;
        this.geometry = geometry;
        this.mappings = mappings;
        this.modifiers = modifiers;
        this.deadKeys = deadKeys;
    }

    /**
     * Trouve la touche correspondant à un caractère donné dans une couche spécifique.
     *
     * @param character le caractère pour lequel trouver la touche
     * @param layer la couche dans laquelle chercher la touche
     * @return la touche correspondant au caractère dans la couche spécifiée, ou null si aucune touche ne correspond
     */
    @Override
    public Key findKeyForCharacter(char character, Layer layer) {
        return getMappings().entrySet().stream()
            .filter(entry -> {
                Character mappedChar = entry.getValue().get(layer);
                return mappedChar != null && mappedChar == character;
            })
            .map(Map.Entry::getKey)
            .findFirst()
            .orElse(null);
    }


    /**
     * Trouve les touches correspondant à un caractère donné.
     *
     * @param character le caractère pour lequel trouver les touches
     * @return une liste de touches qui correspondent au caractère donné
     */
    @Override
    public List<Key> findKeysForCharacter(char character) {
        return getMappings().entrySet().stream()
            .filter(entry -> entry.getValue().values().contains(character))
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    /**
     * Retourne la couche (Layer) associée à un caractère donné.
     *
     * @param character le caractère pour lequel la couche doit être trouvée
     * @return la couche (Layer) contenant le caractère spécifié, ou null si le caractère n'est pas trouvé
     */
    public Layer getLayerForCharacter(char character) {
        return getMappings().entrySet().stream()
            .filter(entry -> entry.getValue().values().contains(character))
            .map(entry -> entry.getValue().entrySet().stream()
                .filter(e -> e.getValue() == character)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null))
            .findFirst()
            .orElse(null);
    }
    
    @Override
    public Key findModifierKeyForLayer(Layer layer) {
        return modifiers.get(layer);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Map<Key, Map<Layer, Character>> getMappings() {
        return mappings;
    }

    @Override
    public Geometry getGeometry() {
        return geometry;
    }

    @Override
    public Map<String, DeadKey> getDeadKeys() {
        return deadKeys;
    }

    @Override
    public KeyboardLayout copy() {
        return new DefaultKeyboardLayout(name, geometry, mappings, modifiers, deadKeys);
    }
    
}

