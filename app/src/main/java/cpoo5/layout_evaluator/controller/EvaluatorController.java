package cpoo5.layout_evaluator.controller;


import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cpoo5.layout_evaluator.model.evaluator.LayoutEvaluationPreparator;
import cpoo5.layout_evaluator.model.evaluator.LayoutEvaluator;
import cpoo5.layout_evaluator.model.evaluator.LayoutEvaluator.NGramEvaluationResult;
import cpoo5.lib.IOHandlers.AnsiStyle;

import cpoo5.lib.global_controller.Controller;

public class EvaluatorController extends Controller {

    private String selectedLayout;

    private void selectLayout() {
        displayMessage("\nChoisissez un layout :");
        displayMessage("1: azerty");
        displayMessage("2: qwerty");
        displayMessage("3: devorak_fr");
        List<String> validChoices = Arrays.asList("1", "2", "3");

        String userChoice = getValidatedUserInput("Faites un choix:",
                validChoices::contains,
                "Choix non valide. Veuillez sélectionner 1, 2 ou 3.");
        switch (userChoice) {
            case "1":
                selectedLayout = "azerty";
                break;
            case "2":
                selectedLayout = "qwerty";
                break;
            case "3":
                selectedLayout = "devorak_fr";
                break;
            default:
                displayMessage("Choix non valide, layout par défaut: azerty");
                selectedLayout = "azerty";
        }
        displayMessage("Vous avez choisi le layout: " + selectedLayout);

        // Utilisation de la nouvelle classe
        LayoutEvaluationPreparator preparator = new LayoutEvaluationPreparator(selectedLayout);
        try {
            preparator.prepare("src/main/java/cpoo5/output/ngrams_output.json");
            displayMessage("Clavier " + preparator.getLayout().getName() + " créé");

            // Récupération du LayoutEvaluator
            LayoutEvaluator evaluator = preparator.getLayoutEvaluator();

            // Évaluation
            Map<String, Integer> nGramsMap = preparator.getNgramsMap();
            List<NGramEvaluationResult> results = evaluator.evaluateAll();

            // Filtre des résultats pour les nGrams de longueur <= 3
            List<NGramEvaluationResult> filteredResults = results.stream()
                .filter(result -> result.nGram().length() <= 3)
                .collect(Collectors.toList());
    
            // Calcul du total des fréquences pour ces n-grams
            double totalFrequencies = filteredResults.stream()
                .mapToDouble(result -> nGramsMap.getOrDefault(result.nGram(), 0))
                .sum();
    
            // Calcul de la somme pondérée des scores
            double weightedSum = filteredResults.stream()
                .mapToDouble(result -> result.totalScore() * nGramsMap.getOrDefault(result.nGram(), 0))
                .sum();
    
            // Calcul de la moyenne pondérée
            double averageScoreResults = (totalFrequencies == 0.0)
                ? 0.0
                : weightedSum / totalFrequencies;

            displayMessage("Moyenne du score pour le layout "
                    + selectedLayout + " : " + averageScoreResults
                    + " avec le corpus " + preparator.getCorpusName());
            displayMessage("\n");

        } catch (Exception e) {
            displayStyledMessage(
                    "Un fichier d'analyse des n-grams doit être fourni. (Lancer le corpus_analyzer)",
                    AnsiStyle.RED, AnsiStyle.BOLD);
        }
    }

    @Override
    public void execute() {
        selectLayout();
    }
}
