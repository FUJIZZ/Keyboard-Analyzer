package cpoo5.layout_evaluator.model.layout;

import cpoo5.layout_evaluator.model.key.*;
import cpoo5.layout_evaluator.model.utils.*;
import java.util.*;

public class KeyboardLayoutFactory {

    /**
     * Crée un agencement de clavier à partir d'un fichier JSON spécifié par son nom.
     *
     * @param layoutName Le nom du fichier JSON contenant les données de l'agencement (sans l'extension .json).
     * @return Un objet KeyboardLayout représentant l'agencement de clavier.
     * @throws RuntimeException Si une erreur survient lors de la création de l'agencement.
     */
    public static KeyboardLayout createLayout(String layoutName) {
        try {
            // Charger les données JSON
            JsonLayoutImporter.JsonLayout layoutData = JsonLayoutImporter
                    .loadFromJson("src/main/resources/layout/" + layoutName + ".json");

            // Construire l'index des touches
            Map<String, Key> labelToKey = buildKeyIndex(layoutData);

            // Créer les structures nécessaires
            Geometry geometry = createGeometry(layoutData, labelToKey);
            Map<Key, Map<Layer, Character>> mappings = createMappings(layoutData, labelToKey);
            Map<Layer, Key> modifiers = loadModifiers(layoutData, labelToKey);
            Map<String, DeadKey> deadKeys = buildDeadKeyMap(layoutData);

            // Construire et retourner le layout
            return new KeyboardLayoutBuilder()
                    .withName(layoutData.name)
                    .withGeometry(geometry)
                    .addMapping(mappings)
                    .addModifiers(modifiers)
                    .addDeadKeys(deadKeys)
                    .build();

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création du layout " + layoutName, e);
        }
    }

    private static Map<String, Key> buildKeyIndex(JsonLayoutImporter.JsonLayout layoutData) {
        Map<String, Key> labelToKey = new HashMap<>();
        for (JsonLayoutImporter.KeyJson kj : layoutData.geometry.keys) {
            Key builtKey = kj.modifier
                    ? new ModifierKey(kj.label, kj.row, kj.column, Finger.valueOf(kj.finger))
                    : new StandardKey(kj.label, kj.row, kj.column, Finger.valueOf(kj.finger));
            labelToKey.put(kj.label, builtKey);
        }
        return labelToKey;
    }

    /**
     * Crée une instance de la classe Geometry à partir des données de mise en page JSON
     *
     * @param layoutData Les données de mise en page importées depuis un fichier JSON.
     * @param labelToKey Une carte associant des étiquettes (labels) à des instances de la classe Key.
     * @return Une nouvelle instance de la classe Geometry initialisée avec les rangées, colonnes 
     *         et l'ensemble des touches fournies.
     */
    private static Geometry createGeometry(JsonLayoutImporter.JsonLayout layoutData, Map<String, Key> labelToKey) {
        return new Geometry(
                layoutData.geometry.rows,
                layoutData.geometry.columns,
                new HashSet<>(labelToKey.values())
        );
    }

    /**
     * Crée une map des mappages de touches à partir des données de layout JSON et d'une map de labels de touches.
     *
     * @param layoutData Les données de layout importées depuis un fichier JSON.
     * @param labelToKey Une map associant les labels de touches à leurs objets Key correspondants.
     * @return Une map où chaque Key est associée à une map de Layer et de Character.
     */
    private static Map<Key, Map<Layer, Character>> createMappings(JsonLayoutImporter.JsonLayout layoutData, Map<String, Key> labelToKey) {
        Map<Key, Map<Layer, Character>> mappings = new HashMap<>();
        for (JsonLayoutImporter.MappingJson mj : layoutData.mappings) {
            Key keyObj = labelToKey.get(mj.keyLabel);
            if (keyObj != null) {
                Map<Layer, Character> layerMap = new HashMap<>();
                for (Map.Entry<String, String> layerEntry : mj.layerMapping.entrySet()) {
                    layerMap.put(Layer.valueOf(layerEntry.getKey()), layerEntry.getValue().charAt(0));
                }
                mappings.put(keyObj, layerMap);
            }
        }
        return mappings;
    }

    /**
     * Construit une carte des touches mortes (DeadKey) à partir des données de mise en page JSON fournies.
     *
     * @param layoutData Les données de mise en page JSON importées contenant les informations sur les touches mortes.
     * @return Une carte (Map) où les clés sont des étiquettes de touches mortes (String) et les valeurs sont des objets DeadKey.
     */
    private static Map<String, DeadKey> buildDeadKeyMap(JsonLayoutImporter.JsonLayout layoutData) {
        Map<String, DeadKey> result = new HashMap<>();
        if (layoutData.deadkeys != null) {
            for (JsonLayoutImporter.DeadKeyJson dkJson : layoutData.deadkeys) {
                Map<Character, Character> transf = new HashMap<>();
                if (dkJson.transformations != null) {
                    dkJson.transformations.forEach((base, accented) ->
                        transf.put(base.charAt(0), accented.charAt(0))
                    );
                }
                result.put(dkJson.label, new DeadKey(dkJson.label, -1, -1, Finger.PINKY, transf));
            }
        }
        return result;
    }

    /**
     * Charge les modificateurs à partir des données de mise en page JSON et les associe aux touches correspondantes.
     *
     * @param layoutData Les données de mise en page JSON importées.
     * @param labelToKey Une map associant les étiquettes de touches aux objets Key correspondants.
     * @return Une map associant chaque couche (Layer) à la touche (Key) correspondante.
     */
    private static Map<Layer, Key> loadModifiers(JsonLayoutImporter.JsonLayout layoutData, Map<String, Key> labelToKey) {
        Map<Layer, Key> modifiers = new HashMap<>();
        if (layoutData.modifiers != null) {
            layoutData.modifiers.forEach((layer, keyLabel) -> {
                Layer layerEnum = Layer.valueOf(layer);
                Key key = labelToKey.get(keyLabel);
                if (key != null) {
                    modifiers.put(layerEnum, key);
                }
            });
        }
        return modifiers;
    }
}
