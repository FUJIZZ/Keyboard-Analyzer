package cpoo5.layout_evaluator.model.utils;

import java.util.List;


import cpoo5.lib.JsonImporter;

/**
 * Classe pour importer les analyses d'Ngram depuis un fichier JSON.
 */
public class JsonAnalyzeImporter extends JsonImporter {

    // Structures pour le JSON
    public static class NgramAnalysisJson {
        public ResultJson result;
        public String corpus;
    }

    public static class ResultJson {
        public List<NgramJson> ngrams;
    }

    public static class NgramJson {
        public String ngram;
        public int count;
    }

    /**
     * Charge une analyse d'Ngram à partir d'un fichier JSON.
     *
     * @param path Le chemin vers le fichier JSON.
     * @return L'objet {@link NgramAnalysisJson} importé.
     * @throws Exception Si une erreur survient lors de la lecture ou du parsing.
     */ 
    public static NgramAnalysisJson loadFromJson(String path) throws Exception {
        return loadFromJson(path, NgramAnalysisJson.class);
    }
}
