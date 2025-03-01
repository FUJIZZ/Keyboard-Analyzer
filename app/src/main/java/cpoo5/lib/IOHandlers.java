package cpoo5.lib;

import java.util.Scanner;


public final class IOHandlers {

 
    /**
     * Enumération représentant différents styles ANSI pour la coloration et le formatage du texte dans la console.
     * Chaque style est associé à un code ANSI spécifique.
     * 
     * <ul>
     *   <li>{@link #RESET} - Réinitialise tous les attributs</li>
     *   <li>{@link #BOLD} - Texte en gras</li>
     *   <li>{@link #UNDERLINE} - Texte souligné</li>
     *   <li>{@link #RED} - Texte rouge</li>
     *   <li>{@link #GREEN} - Texte vert</li>
     *   <li>{@link #YELLOW} - Texte jaune</li>
     *   <li>{@link #BLUE} - Texte bleu</li>
     *   <li>{@link #PURPLE} - Texte violet</li>
     *   <li>{@link #CYAN} - Texte cyan</li>
     *   <li>{@link #WHITE} - Texte blanc</li>
     *   <li>{@link #BG_RED} - Fond rouge</li>
     *   <li>{@link #BG_GREEN} - Fond vert</li>
     * </ul>
     * 
     * Chaque instance de cette énumération contient un code ANSI qui peut être récupéré via la méthode {@link #getCode()}.
     */
    public enum AnsiStyle {
        RESET("\u001B[0m"),
        BOLD("\u001B[1m"),
        UNDERLINE("\u001B[4m"),
        RED("\u001B[31m"),
        GREEN("\u001B[32m"),
        YELLOW("\u001B[33m"),
        BLUE("\u001B[34m"),
        PURPLE("\u001B[35m"),
        CYAN("\u001B[36m"),
        WHITE("\u001B[37m"),
        BG_RED("\u001B[41m"),
        BG_GREEN("\u001B[42m");

        private final String code;

        AnsiStyle(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    /**
     * Interface privée pour gérer les entrées utilisateur.
     * Fournit une méthode pour obtenir l'entrée utilisateur avec une invite.
     */
    private interface InputManager {
        /**
         * Demande à l'utilisateur une entrée.
         *
         * @param prompt Le message à afficher pour inviter l'utilisateur à entrer une valeur.
         * @return La chaîne de caractères entrée par l'utilisateur.
         */
        String getUserInput(String prompt);
    }

    /**
     * Interface OutputManager pour gérer l'affichage des messages.
     */
    private interface OutputManager {
        /**
         * Affiche un message simple.
         *
         * @param message Le message à afficher.
         */
        void displayMessage(String message);

        /**
         * Affiche un message avec des styles ANSI.
         *
         * @param message Le message à afficher.
         * @param styles  Les styles ANSI à appliquer au message.
         */
        void displayStyledMessage(String message, AnsiStyle... styles);
    }

    
    private static class ConsoleInputManager implements InputManager {
        private final Scanner scanner;

        public ConsoleInputManager() {
            this.scanner = new Scanner(System.in);
        }

        /**
         * Demande à l'utilisateur une entrée .
         *
         * @param prompt Le message à afficher pour inviter l'utilisateur à entrer une donnée.
         * @return La chaîne de caractères entrée par l'utilisateur.
         */
        @Override
        public String getUserInput(String prompt) {
            System.out.print(prompt);
            return scanner.nextLine();
        }
    }

    
    private static class ConsoleOutputManager implements OutputManager {
        /**
         * Affiche un message sur la console.
         *
         * @param message Le message à afficher.
         */
        @Override
        public void displayMessage(String message) {
            System.out.println(message);
        }

        /**
         * Affiche un message avec des styles ANSI spécifiés.
         *
         * @param message Le message à afficher.
         * @param styles  Les styles ANSI à appliquer au message.
         */
        @Override
        public void displayStyledMessage(String message, AnsiStyle... styles) {
            StringBuilder styledMessage = new StringBuilder();
            for (AnsiStyle style : styles) {
                styledMessage.append(style.getCode());
            }
            styledMessage.append(message).append(AnsiStyle.RESET.getCode());
            System.out.println(styledMessage);
        }
    }

    // Gestion combinée
    public static class CombinedIOManager implements InputManager, OutputManager {
        private final InputManager inputManager;
        private final OutputManager outputManager;

        public CombinedIOManager(InputManager inputManager, OutputManager outputManager) {
            this.inputManager = inputManager;
            this.outputManager = outputManager;
        }

        @Override
        public void displayMessage(String message) {
            outputManager.displayMessage(message);
        }

        @Override
        public String getUserInput(String prompt) {
            return inputManager.getUserInput(prompt);
        }

        @Override
        public void displayStyledMessage(String message, AnsiStyle... styles) {
            outputManager.displayStyledMessage(message, styles);
        }

        public void clear() {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }

        public void eraseLastLine() {
            System.out.print("\033[1A"); // Remonter d'une ligne
            System.out.print("\033[2K"); // Effacer la ligne
            System.out.flush();
        }
    }

    // Méthode statique pour créer une instance combinée
    public static CombinedIOManager createCombinedIOManager() {
        return new CombinedIOManager(new ConsoleInputManager(), new ConsoleOutputManager());
    }
}
