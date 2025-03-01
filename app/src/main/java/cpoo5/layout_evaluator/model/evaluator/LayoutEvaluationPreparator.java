package cpoo5.layout_evaluator.model.evaluator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import cpoo5.layout_evaluator.model.layout.KeyboardLayout;
import cpoo5.layout_evaluator.model.layout.KeyboardLayoutFactory;
import cpoo5.layout_evaluator.model.utils.JsonAnalyzeImporter;
import cpoo5.layout_evaluator.model.utils.JsonAnalyzeImporter.NgramAnalysisJson;


public class LayoutEvaluationPreparator {

    private String layoutName;
    private KeyboardLayout layout;
    private List<String> ngrams;
    private String corpusName;
    private Map<String, Integer> ngramsMap;

    /**
     * Constructeur principal.
     *
     * @param layoutName Nom du layout (ex: "azerty", "qwerty", "devorak_fr").
     */
    public LayoutEvaluationPreparator(String layoutName) {
        this.layoutName = layoutName;
    }

    /**
     * Prépare tous les éléments pour l'évaluation :
     *  - Création du clavier via KeyboardLayoutFactory
     *  - Chargement des n-grammes à partir du JSON
     * 
     * @param jsonFilePath Chemin du fichier JSON contenant l'analyse des n-grammes.
     * @throws Exception si le chargement du JSON échoue ou si le contenu est invalide.
     */
    public void prepare(String jsonFilePath) throws Exception {
        // Création du KeyboardLayout en fonction du nom
        layout = KeyboardLayoutFactory.createLayout(layoutName);

        // Chargement du JSON contenant l'analyse des n-grammes
        NgramAnalysisJson loadedNgrams = JsonAnalyzeImporter.loadFromJson(jsonFilePath);
        if (loadedNgrams == null) {
            throw new Exception("Erreur: impossible de charger le fichier JSON (NgramAnalysisJson est null).");
        }

        // Stockage des n-grammes et du nom de corpus
        ngramsMap = loadedNgrams.result.ngrams.stream()
            .collect(Collectors.toMap(ng -> ng.ngram, ng -> ng.count));
        ngrams = new ArrayList<>(ngramsMap.keySet());
        corpusName = loadedNgrams.corpus;
    }

    /**
     * Retourne un LayoutEvaluator prêt à l'emploi,
     * construit à partir du KeyboardLayout et de la liste de n-grammes.
     *
     * @return un LayoutEvaluator initialisé.
     * @throws IllegalStateException si la préparation n'a pas été effectuée avant.
     */
    public LayoutEvaluator getLayoutEvaluator() {
        if (layout == null || ngrams == null) {
            throw new IllegalStateException("Les données n'ont pas été préparées. " +
                                            "Appelez d'abord la méthode prepare().");
        }
        // On renvoie un LayoutEvaluator fonctionnel
        return new LayoutEvaluator(layout.copy(), ngrams);
    }

    /**
     * @return Le nom du corpus, si présent dans le JSON chargé.
     */
    public String getCorpusName() {
        return corpusName;
    }

    /**
     * @return Le clavier instancié.
     */
    public KeyboardLayout getLayout() {
        return layout;
    }

    /**
     * @return Le nom du layout (ex: "azerty").
     */
    public String getLayoutName() {
        return layoutName;
    }

    /**
     * @return La liste de n-grammes chargée depuis le JSON.
     */
    public List<String> getNgrams() {
        return ngrams;
    }

    /**
     * @return La map de n-grammes et leurs fréquences chargée depuis le JSON.
     */
    public Map<String, Integer> getNgramsMap() {
        return ngramsMap;
    }
}
