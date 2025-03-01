package cpoo5.layout_evaluator.model.evaluator;

import cpoo5.layout_evaluator.model.layout.KeyboardLayout;
import cpoo5.layout_evaluator.model.key.Key;
import cpoo5.layout_evaluator.model.utils.KeyCombinationFinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LayoutEvaluator {

    private final KeyboardLayout keyboardLayout;
    private final KeyCombinationFinder keyCombinationFinder;
    private final List<String> nGrams;
    private final ScoreCalculator scoreCalculator;

    public LayoutEvaluator(KeyboardLayout keyboardLayout, List<String> nGrams) {
        if (keyboardLayout == null) {
            throw new IllegalArgumentException("KeyboardLayout ne peut pas être null");
        }
        if (nGrams == null) {
            throw new IllegalArgumentException("nGrams ne peut pas être null");
        }
        this.keyboardLayout = keyboardLayout;
        this.keyCombinationFinder = new KeyCombinationFinder(keyboardLayout);
        this.nGrams = nGrams;
        this.scoreCalculator = new ScoreCalculator(keyboardLayout);
    }

    /**
     * Évalue un n-gramme donné en détail en calculant les scores de bigrammes et trigrammes.
     *
     * @param nGram Le n-gramme à évaluer.
     * @return Un objet NGramEvaluationResult contenant le n-gramme, le score total et les évaluations détaillées des mouvements.
     */
    public NGramEvaluationResult evaluateOneNGramDetailed(String nGram) {
        List<Key> keys = nGram.chars()
                .mapToObj(c -> {
                    try {
                        List<Key> combination = keyCombinationFinder.getCombinationForCharacter((char) c);
                        return combination.isEmpty() ? null : combination.get(combination.size() - 1);
                    } catch (IllegalArgumentException e) {
                        //System.err.println("Caractère non trouvé : " + (char) c);
                        return null;
                    }
                })
                .filter(key -> key != null)
                .toList();

        List<MovementEvaluation> detailedMovements = new ArrayList<>();
        double totalScore = 0.0;

        for (int i = 0; i < keys.size() - 1; i++) {
            Key k1 = keys.get(i);
            Key k2 = keys.get(i + 1);

            MovementEvaluation bigramEval = scoreCalculator.evaluateBigram(k1, k2);
            detailedMovements.add(bigramEval);
            totalScore += bigramEval.getFinalScore();

            if (i < keys.size() - 2) {
                Key k3 = keys.get(i + 2);
                MovementEvaluation trigramEval = scoreCalculator.evaluateTrigram(k1, k2, k3);
                detailedMovements.add(trigramEval);
                totalScore += trigramEval.getFinalScore();
            }
        }

        return new NGramEvaluationResult(nGram, totalScore, detailedMovements);
    }

  
    /**
     * Évalue tous les n-grammes présents dans la liste nGrams.
     * Pour chaque n-gramme, appelle la méthode evaluateOneNGramDetailed
     * et affiche le résultat de l'évaluation.
     */
    public List<NGramEvaluationResult> evaluateAll() {
        List<NGramEvaluationResult> results = new ArrayList<>();
        for (String nGram : nGrams) {
            NGramEvaluationResult result = evaluateOneNGramDetailed(nGram);
            results.add(result);
        }
        return results;
    }

    /**
     * Évalue tous les N-grammes et retourne les résultats sous forme de map.
     * 
     * 
     * @return Une map où les clés sont les N-grammes (String) et les valeurs sont
     *         les scores totaux (Double) de chaque N-gramme.
     */
    public Map<String, Double> evaluateAllNGramsAsMap() {
        Map<String, Double> results = new HashMap<>();

        for (String nGram : nGrams) {

            NGramEvaluationResult result = evaluateOneNGramDetailed(nGram);
     

            results.put(nGram, result.totalScore);
        }

        return results;
    }


    public record NGramEvaluationResult(String nGram, double totalScore, List<MovementEvaluation> details) {
        @Override
        public String toString() {
            return String.format(
                    "NGram: '%s' | Total Score: %.2f\nDetails:\n  %s\n",
                    nGram,
                    totalScore,
                    String.join("\n  ",
                            details.stream().map(MovementEvaluation::toString).toList()));
        }
    }
}
