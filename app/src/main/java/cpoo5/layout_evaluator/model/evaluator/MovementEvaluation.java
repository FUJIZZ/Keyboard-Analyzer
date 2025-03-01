package cpoo5.layout_evaluator.model.evaluator;

import java.util.ArrayList;
import java.util.List;

/**
 * Stocke les informations de calcul pour un bigram/trigram donné
 */
public class MovementEvaluation {
    private double baseDistance;            // Distance brute entre deux touches (ou plus)
    private final List<Double> multipliers; // Liste des multiplicateurs (1.5, 1.3, etc.)
    private double finalScore;              // Résultat = baseDistance * (produit des multipliers)
    private final List<String> movementTags; // Ex: ["SFB", "Ciseaux", "LSB", ...]
    private double KeyNumber;

    public MovementEvaluation() {
        this.multipliers = new ArrayList<>();
        this.movementTags = new ArrayList<>();
    }

    public double getBaseDistance() {
        return baseDistance;
    }

    public void setBaseDistance(double baseDistance) {
        this.baseDistance = baseDistance;
    }

    public List<Double> getMultipliers() {
        return multipliers;
    }

    public double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(double finalScore) {
        this.finalScore = finalScore;
    }

    public List<String> getMovementTags() {
        return movementTags;
    }
    public double getKeyNumber() {
        return KeyNumber;
    }

    public void setKeyNumber(double KeyNumber) {
        this.KeyNumber = KeyNumber;
    }

 
    /**
     * Ajoute un multiplicateur à la liste des multiplicateurs et un tag de mouvement à la liste des tags de mouvement.
     *
     * @param multiplier l'objet Multipliers à ajouter, contenant la valeur du multiplicateur et le tag associé.
     */
    public void addMultiplier(Multipliers multiplier) {
        this.multipliers.add(multiplier.getValue());
        this.movementTags.add(multiplier.getTag());
    }


    /**
     * Ajoute un multiplicateur et un tag de mouvement à leurs listes respectives.
     *
     * @param multiplier Le multiplicateur à ajouter.
     * @param tag Le tag associé au mouvement à ajouter.
     */
    public void addMultiplier(double multiplier, String tag) {
        this.multipliers.add(multiplier);
        this.movementTags.add(tag);
    }
    
    @Override
    public String toString() {
        return String.format(
                "MovementEvaluation{distance=%.2f, multipliers=%s, finalScore=%.2f, tags=%s, KeyNumber=%.2f}",
                baseDistance,
                multipliers,
                finalScore,
                movementTags,
                KeyNumber
        );
    }
}
