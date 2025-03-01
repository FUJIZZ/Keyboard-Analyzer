package cpoo5.corpus_analyzer.model.analyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cpoo5.corpus_analyzer.utils.text_loader.TextLoader;
import cpoo5.corpus_analyzer.utils.text_loader.TextLoaderFactory;

public class NgramAnalyzer {

    private static final String CORPUS_DIRECTORY_PATH = "src/main/resources/corpus/";
    private static final String CORPUS_OUTPUT_PATH = "src/main/java/cpoo5/output/";

    private final String corpusChoice; // Choix du corpus à analyser

    private final Map<String, Integer> globalNgramCounts = new ConcurrentHashMap<>(); // Dictionnaire ngram -> total occurrences

    /**
     * Initialise un nouvel analyseur d'Ngram avec le corpus spécifié.
     *
     * @param corpusChoice Le choix du corpus à analyser.
     */
    public NgramAnalyzer(String corpusChoice) {
        this.corpusChoice = corpusChoice;
        TextLoader loader = TextLoaderFactory.getTextLoader("directory");
        List<String> textList = loader.loadTexts(CORPUS_DIRECTORY_PATH + this.corpusChoice);

        // Transforme chaque texte en flux d'NgramCount (pour n=1..3) en mode parallèle
        List<NgramCount> allNgrams = textList
            .parallelStream()  // traite en parallèle
            .flatMap(txt -> Stream.of(1, 2, 3)
                .flatMap(n -> extractNgrams(txt, n).stream()))
            .toList();

        // Construit un dictionnaire global ngram -> total occurrences
        allNgrams.forEach(nc -> {
            globalNgramCounts.merge(nc.ngram(), nc.count(), Integer::sum);
        });

        // Enfin, on écrit tout en JSON
        writeNgramsToJson("ngrams_output.json");
    }

    /**
     * Extrait les n-grammes d'un texte donné.
     *
     * @param text Le texte à analyser.
     * @param n La taille des n-grammes à extraire.
     * @return Une liste de {@link NgramCount} représentant les n-grammes et leur fréquence.
     */
    public List<NgramCount> extractNgrams(String text, int n) {
        // Transforme le texte en tokens (liste de mots)
        return Arrays.stream(text.split("\\s+"))
            .flatMap(token ->
                IntStream.rangeClosed(0, token.length() - n) // Crée un flux d'entiers de 0 à token.length - n
                         .mapToObj(i -> new NgramCount(token.substring(i, i + n), 1)) // Crée un NgramCount pour chaque n-gram
            )
            .toList();
    }

    /**
     * Écrit les n-grammes globaux dans un fichier JSON avec la structure spécifiée.
     *
     * @param outputFilename Le nom du fichier de sortie.
     */
    private void writeNgramsToJson(String outputFilename) {
        // Convertir la map en une liste d'objets NgramCount
        List<NgramCount> ngramEntries = globalNgramCounts.entrySet()
            .stream()
            .map(entry -> new NgramCount(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());

        // Créer les objets Result et Output pour structurer le JSON
        Result result = new Result(ngramEntries);
        Output output = new Output(result, corpusChoice);

        // Sérialiser en JSON avec mise en forme
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(output);

        // Écrire le JSON dans le fichier
        try {
            Files.writeString(Paths.get(CORPUS_OUTPUT_PATH + outputFilename), json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Classes internes pour la structure JSON
    private record Result(List<NgramCount> ngrams) {}

    /**
     * Enregistrement représentant la sortie de l'analyseur N-gram.
     *
     * @param result Le résultat de l'analyse.
     * @param corpus Le corpus analysé.
     */
    private record Output(Result result, String corpus) {}

}
