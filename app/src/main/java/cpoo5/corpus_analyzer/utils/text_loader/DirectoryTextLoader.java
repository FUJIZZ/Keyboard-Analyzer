package cpoo5.corpus_analyzer.utils.text_loader;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class DirectoryTextLoader implements TextLoader {
    /**
     * Charge les textes à partir d'un répertoire spécifié.
     *
     * @param directoryPath le chemin du répertoire contenant les fichiers texte
     * @return une liste de chaînes de caractères représentant le contenu des fichiers texte dans le répertoire

     */
    @Override
    public List<String> loadTexts(String directoryPath) {
        List<String> texts = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(directoryPath))) {
            for (Path entry : stream) {
                String content = new String(Files.readAllBytes(entry));
                texts.add(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return texts;
    }
}