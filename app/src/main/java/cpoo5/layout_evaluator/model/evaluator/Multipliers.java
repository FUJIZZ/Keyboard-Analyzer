package cpoo5.layout_evaluator.model.evaluator;

import com.google.gson.reflect.TypeToken;
import cpoo5.lib.JsonImporter; // Assurez-vous que l'import vers votre JsonImporter est correct
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Énumération regroupant les multiplicateurs (tag + valeur). 
 * La valeur peut être mise à jour via un fichier JSON.
 */
public enum Multipliers {

    SFB("SFB", 1.5),
    CISEAUX("Ciseaux", 1.3),
    LSB("LSB", 1.2),
    HAND_ALTERNATION("Hand Alternation", 0.8),
    SAME_HAND("Same Hand", 1.1),
    ROULEMENT("Roulement", 0.7),
    REDIRECTION("Redirection", 1.4),
    SKIPGRAMMES("Skipgrammes", 1.6),
    HIGH_DISTANCE("High Distance", 0.8, 6);

    private final String tag;
    private double value;
    private int keyNumber;

    Multipliers(String tag, double value) {
        this.tag = tag;
        this.value = value;
    }
    Multipliers(String tag, double value, int keyNumber) {
        this.tag = tag;
        this.value = value;
        this.keyNumber = keyNumber;
    }

    public String getTag() {
        return tag;
    }

    public double getValue() {
        return value;
    }

    public int getKeyNumber() {
        return keyNumber;
    }

 

    public void setValue(double value) {
        this.value = value;
    }

    public void setKeyNumber(int keyNumber) {
        this.keyNumber = keyNumber;
    }

    /**
     * Charge/Met à jour les valeurs des multiplicateurs depuis un fichier JSON
     *
     * @param jsonPath Chemin vers le fichier JSON.
     * @throws Exception En cas d'erreur de lecture/parsing du fichier JSON.
     */
    public static void loadValuesFromJson(String jsonPath) throws Exception {
        // Type pour Map<String, Object>
        Type typeOfMap = new TypeToken<Map<String, Object>>() {}.getType();

        // On récupère la Map via le JsonImporter
        Map<String, Object> data = JsonImporter.loadFromJson(jsonPath, typeOfMap);

        // On parcourt les valeurs possibles de l'énumération
        for (Multipliers m : values()) {
            if (data.containsKey(m.getTag())) {
                Object jsonValue = data.get(m.getTag());
                if (jsonValue instanceof Double) {
                    m.setValue((Double) jsonValue);
                } else if (jsonValue instanceof List) {
                    List<?> list = (List<?>) jsonValue;
                    if (list.size() == 2 && list.get(0) instanceof Number && list.get(1) instanceof Number) {
                        m.setValue(((Number) list.get(0)).doubleValue());
                        m.setKeyNumber(((Number) list.get(1)).intValue());
                    }
                }
            }
        }
    }

    @Override 
    public String toString() {
        return tag + " : " + value + " (keyNumber: " + keyNumber + ")";
    }
}
