package cpoo5.layout_evaluator.model.utils;

import java.util.List;
import java.util.Map;

import cpoo5.lib.JsonImporter;

/**
 * Classe pour importer des layouts de type JsonLayout.
 */
public class JsonLayoutImporter  extends JsonImporter {
    // Structures pour le JSON
    public static class JsonLayout {
        public String name;
        public GeometryJson geometry;
        public List<MappingJson> mappings;
        public Map<String, String> modifiers;
        public List<DeadKeyJson> deadkeys;
    }

    public static class GeometryJson {
        public int rows;
        public int columns;
        public List<KeyJson> keys;
    }

    public static class KeyJson {
        public String label;
        public int row;
        public int column;
        public String finger;
        public boolean modifier;
    }

    public static class MappingJson {
        public String keyLabel;
        public Map<String, String> layerMapping; // ex: { "NORMAL": "a", "SHIFT": "A" }
    }

    public static class DeadKeyJson {
        public String label;
        public Map<String, String> transformations;
    }

    /**
     * Charge un JsonLayout à partir d'un fichier JSON.
     *
     * @param path Le chemin vers le fichier JSON.
     * @return L'objet JsonLayout importé.
     * @throws Exception Si une erreur survient lors de la lecture ou du parsing.
     */
    public static JsonLayout loadFromJson(String path) throws Exception {
        return loadFromJson(path, JsonLayout.class);
    }
}
