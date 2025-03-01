package cpoo5.corpus_analyzer.utils.text_loader;

public class TextLoaderFactory {
    /**
     * Retourne une instance de TextLoader en fonction du type spécifié.
     *
     * @param type le type de TextLoader à retourner (par exemple, "directory").
     * @return une instance de TextLoader correspondant au type spécifié.
     * @throws IllegalArgumentException si le type spécifié est inconnu.
     */
    public static TextLoader getTextLoader(String type) {
        if ("directory".equalsIgnoreCase(type)) {
            return new DirectoryTextLoader();
        }
        // D'autres loader peuvent être ajoutés ici
        throw new IllegalArgumentException("Unknown loader type");
    }
}