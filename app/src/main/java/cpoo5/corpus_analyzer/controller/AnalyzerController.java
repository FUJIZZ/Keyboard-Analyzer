package cpoo5.corpus_analyzer.controller;

import cpoo5.lib.global_controller.Controller;
import cpoo5.lib.IOHandlers.CombinedIOManager;

import java.util.Arrays;
import java.util.List;

import cpoo5.corpus_analyzer.model.analyzer.NgramAnalyzer;

/**
 * La classe AnalyzerController étend la classe Controller et permet de sélectionner un corpus
 * et de créer un NgramAnalyzer pour le corpus sélectionné.
 * 
 */
public class AnalyzerController extends Controller {
    private String selectedCorpus;

    /**
     * Sélectionne un corpus et crée un NgramAnalyzer.
     * Affiche les options disponibles et valide le choix de l'utilisateur.
     * Retourne un NgramAnalyzer pour le corpus sélectionné.
     * 
     * @return NgramAnalyzer pour le corpus sélectionné
     */
    public NgramAnalyzer selectCorpus() {
        displayMessage("Choisissez un corpus :");
        displayMessage("1: anglais");
        displayMessage("2: francais");
        displayMessage("3: code_java");
        List<String> validChoices = Arrays.asList("1", "2", "3");

        // Utiliser la méthode généralisée avec validation
        String userChoice = getValidatedUserInput("Faites un choix:",
                validChoices::contains,
                "Choix non valide. Veuillez sélectionner 1, 2 ou 3.");
        switch (userChoice) {
            case "1":
                selectedCorpus = "anglais";
                break;
            case "2":
                selectedCorpus = "francais";
                break;
            case "3":
                selectedCorpus = "code_java";
                break;
            default:
                displayMessage("Choix non valide, corpus par défaut: anglais");
                selectedCorpus = "anglais";
        }
        displayMessage("Vous avez choisi le corpus: " + selectedCorpus);

        NgramAnalyzer ngramAnalyzer = new NgramAnalyzer(selectedCorpus);
        displayMessage("Analyse Ngram pour le corpus " + selectedCorpus + " créé  dans le répertoire output");
        //wait 1.5 seconds
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return ngramAnalyzer;
    }

    @Override
    public void execute() {
        selectCorpus();
    }
}
