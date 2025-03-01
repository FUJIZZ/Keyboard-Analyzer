package cpoo5.layout_evaluator.model.evaluator;

import cpoo5.layout_evaluator.model.key.Key;
import cpoo5.layout_evaluator.model.layout.KeyboardLayout;

public final class ScoreCalculator {



    private final KeyboardLayout keyboardLayout;

    public ScoreCalculator(KeyboardLayout keyboardLayout) {
        try {
            Multipliers.loadValuesFromJson("src/main/resources/movement_multipliers.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (keyboardLayout == null) {
            throw new IllegalArgumentException("KeyboardLayout ne peut pas être null");
        }
        this.keyboardLayout = keyboardLayout;
    }

  
    /**
     * Calcule la distance euclidienne entre deux touches du clavier.
     *
     * @param k1 La première touche.
     * @param k2 La deuxième touche.
     * @return La distance euclidienne entre les deux touches.
     */
    public double computeDistance(Key k1, Key k2) {
        double dr = k1.getRow() - k2.getRow();
        double dc = k1.getColumn() - k2.getColumn();
        return Math.sqrt(dr * dr + dc * dc);
    }


    /**
     * Évalue un bigramme en termes de mouvement entre deux touches.
     *
     * @param k1 La première touche du bigramme.
     * @param k2 La deuxième touche du bigramme.
     * @return Une instance de MovementEvaluation contenant les détails de l'évaluation.
     *
     * Le processus d'évaluation inclut :
     * 1. Calcul de la distance de base entre les deux touches.
     * 2. Application de multiplicateurs basés sur divers critères de mouvement :
     *    - Distance élevée
     *    - Utilisation du même doigt
     *    - Mouvement en ciseaux
     *    - Extension latérale 
     *    - Alternance de main
     *    - Utilisation de la même main
     *    - Roulement 
     * 3. Calcul du score final en multipliant la distance par tous les multiplicateurs appliqués.
     */
    public MovementEvaluation evaluateBigram(Key k1, Key k2) {
        MovementEvaluation evaluation = new MovementEvaluation();
        
        // Calcul de la distance de base
        double distance = computeDistance(k1, k2);
        evaluation.setBaseDistance(distance);

        if( distance > Multipliers.HIGH_DISTANCE.getKeyNumber()){

            evaluation.addMultiplier(Multipliers.HIGH_DISTANCE);
        }

        //  Vérifications de mouvements pour affecter les multiplicateurs
        if (k1.getFinger() == k2.getFinger()) {
            evaluation.addMultiplier(Multipliers.SFB);
        }
        // Ciseaux (si row et col différents)
        if (k1.getRow() != k2.getRow() && k1.getColumn() != k2.getColumn()) {
            evaluation.addMultiplier(Multipliers.CISEAUX);
        }
        // Extension latérale (LSB) : ex. si l'une des touches est hors zone "home" 
        if (!isInHomePosition(k1) || !isInHomePosition(k2)) {
            evaluation.addMultiplier(Multipliers.LSB);
        }
        // Alternance de main
        if (isLeftHand(k1) != isLeftHand(k2)) {
            evaluation.addMultiplier(Multipliers.HAND_ALTERNATION);
        } else {
            evaluation.addMultiplier(Multipliers.SAME_HAND);
        }
        // Roulement (ex. index finger ordinal plus grand → plus petit) 
        // Logique simplifiée ici
        if (k1.getFinger().ordinal() > k2.getFinger().ordinal()) {
            // Bonus => multiplier < 1
            evaluation.addMultiplier(Multipliers.ROULEMENT);
        }

        // 3) Calcul du score final : on multiplie la distance par tous les multiplicateurs
        double finalScore = distance;
        for (double m : evaluation.getMultipliers()) {
            finalScore *= m;
        }
        evaluation.setFinalScore(finalScore);

        return evaluation;
    }

    
     
    /**
     * Évalue un trigramme de touches en calculant une distance de base et en appliquant des multiplicateurs.
     *
     * @param k1 La première touche du trigramme.
     * @param k2 La deuxième touche du trigramme.
     * @param k3 La troisième touche du trigramme.
     * @return Une instance de MovementEvaluation contenant la distance de base, les multiplicateurs appliqués et le score final.
     */
    public MovementEvaluation evaluateTrigram(Key k1, Key k2, Key k3) {
        MovementEvaluation evaluation = new MovementEvaluation();

        // On peut par exemple définir la distance trigramme comme la somme des distances

        double distance12 = computeDistance(k1, k2);
        double distance23 = computeDistance(k2, k3);
        double sumDistance = distance12 + distance23;
        evaluation.setBaseDistance(sumDistance);

        //  redirection : si k1.finger == k3.finger != k2.finger
        if (k1.getFinger() == k3.getFinger() && k1.getFinger() != k2.getFinger()) {
            evaluation.addMultiplier(Multipliers.REDIRECTION);
        }

      
        double finalScore = sumDistance;
        for (double m : evaluation.getMultipliers()) {
            finalScore *= m;
        }
        evaluation.setFinalScore(finalScore);

        return evaluation;
    }

    private boolean isLeftHand(Key key) {
        return key.getColumn() < keyboardLayout.getGeometry().columns() / 2;
    }

    private boolean isInHomePosition(Key key) {
     
        // On considère que row == 1 et col dans [3..6] => c'est la zone "home"
        return (key.getRow() == 1);
    }
}
