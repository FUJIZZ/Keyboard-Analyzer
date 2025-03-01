package cpoo5.lib.global_controller;
import java.util.function.Predicate;

import cpoo5.lib.IOHandlers;
import cpoo5.lib.IOHandlers.AnsiStyle;
import cpoo5.lib.IOHandlers.CombinedIOManager;



/**
 * La classe abstraite Controller fournit des méthodes utilitaires pour gérer les entrées et sorties utilisateur
 * et pour valider les entrées utilisateur. Elle doit être étendue par des classes concrètes qui implémentent la méthode
 * abstraite {@link #execute()}.
 */
public abstract class Controller {
    private static final CombinedIOManager ioManager = IOHandlers.createCombinedIOManager();


    /**
     * Affiche un message en utilisant le gestionnaire d'entrée/sortie.
     *
     * @param message Le message à afficher.
     */
    protected void displayMessage(String message) {
        ioManager.displayMessage(message);
    }

    /**
     * Demande une entrée utilisateur avec un message d'invite.
     *
     * @param prompt Le message d'invite affiché à l'utilisateur.
     * @return La chaîne de caractères saisie par l'utilisateur.
     */
    private String getUserInput(String prompt) {
        return ioManager.getUserInput(prompt);
    }

    /**
     * Affiche un message stylisé en utilisant les styles ANSI spécifiés.
     *
     * @param message Le message à afficher.
     * @param styles  Les styles ANSI à appliquer au message.
     */
    protected void displayStyledMessage(String message, AnsiStyle... styles) {
        ioManager.displayStyledMessage(message, styles);
    }

    

   
    /**
     * Demande à l'utilisateur une entrée valide en fonction d'un prédicat de validation.
     * 
     * @param prompt Le message à afficher pour demander l'entrée de l'utilisateur.
     * @param validator Un prédicat qui teste si l'entrée de l'utilisateur est valide.
     * @param errorMsg Le message d'erreur à afficher si l'entrée de l'utilisateur n'est pas valide.
     * @return L'entrée validée de l'utilisateur.
     */
    protected String getValidatedUserInput(String prompt, Predicate<String> validator, String errorMsg) {
        String input;
        while (true) {
            input = getUserInput(prompt);
            if (validator.test(input)) {
                break;
            } else {
                displayMessage(errorMsg);
                try {
                    // Attendre 2 secondes (2000 millisecondes)
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    // Réinitialiser l'état d'interruption
                    Thread.currentThread().interrupt();
                    displayMessage("Une interruption s'est produite. Veuillez réessayer.");
                }

                ioManager.eraseLastLine();
                ioManager.eraseLastLine();
            }
        }
        ioManager.clear();  
        return input;
    }



    /**
     * Méthode abstraite à implémenter pour exécuter une action spécifique.
     */
    public abstract void execute();
}
