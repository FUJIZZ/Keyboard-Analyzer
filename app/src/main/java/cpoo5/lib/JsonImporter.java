package cpoo5.lib;

import com.google.gson.Gson;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Classe utilitaire pour charger des objets Java depuis un fichier JSON ou un flux.
 */
public abstract class JsonImporter {

    private static final Gson GSON = new Gson();



    /**
     * Charge un objet de type T à partir d'un fichier JSON.
     *
     * @param <T>  Le type de l'objet à charger.
     * @param path Le chemin vers le fichier JSON.
     * @param type La classe de type T.
     * @return L'objet importé de type T.
     * @throws Exception Si une erreur survient lors de la lecture ou du parsing.
     */
    public static <T> T loadFromJson(String path, Class<T> type) throws Exception {
        try (Reader reader = Files.newBufferedReader(Paths.get(path))) {
            return GSON.fromJson(reader, type);
        }
    }

    /**
     * Charge un objet de type T à partir d'un fichier JSON.
     *
     * @param <T>     Le type de l'objet à charger.
     * @param path    Le chemin vers le fichier JSON.
     * @param typeOfT Le {@link Type} décrivant T (peut être un type générique).
     * @return L'objet importé de type T.
     * @throws Exception Si une erreur survient lors de la lecture ou du parsing.
     */
    public static <T> T loadFromJson(String path, Type typeOfT) throws Exception {
        try (Reader reader = Files.newBufferedReader(Paths.get(path))) {
            return GSON.fromJson(reader, typeOfT);
        }
    }

    /**
     * Charge un objet de type T à partir d'un Reader JSON.
     *
     * @param <T>     Le type de l'objet à charger.
     * @param reader  Le flux {@link Reader} contenant le JSON.
     * @param typeOfT Le {@link Type} décrivant T (peut être un type générique).
     * @return L'objet importé de type T.
     * @throws Exception Si une erreur survient lors du parsing.
     */
    public static <T> T loadFromJson(Reader reader, Type typeOfT) throws Exception {
        return GSON.fromJson(reader, typeOfT);
    }
}
