package cpoo5.layout_evaluator;

import cpoo5.layout_evaluator.model.evaluator.ScoreCalculator;
import cpoo5.layout_evaluator.model.evaluator.MovementEvaluation;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import cpoo5.layout_evaluator.model.key.Key;
import cpoo5.layout_evaluator.model.layout.KeyboardLayout;
import cpoo5.layout_evaluator.model.layout.KeyboardLayoutFactory;

class ScoreCalculatorTest {

    @Test
    void testConstructorThrowsOnNullLayout() {
        assertThrows(IllegalArgumentException.class, () -> new ScoreCalculator(null));
    }

    @Test
    void testComputeDistance() {
        KeyboardLayout layout = KeyboardLayoutFactory.createLayout("azerty");
        ScoreCalculator sc = new ScoreCalculator(layout);
        Key a = layout.getGeometry().findKeyAt(1, 3);
        Key z = layout.getGeometry().findKeyAt(1, 4);
        double distance = sc.computeDistance(a, z);
        assertEquals(1.0, distance, 0.0001, "La distance doit être sqrt(1^2 + 1^2)");
    }

    @Test
    void testEvaluateBigramNotNull() {
        KeyboardLayout layout = KeyboardLayoutFactory.createLayout("azerty");
        ScoreCalculator sc = new ScoreCalculator(layout);
        Key a = layout.getGeometry().findKeyAt(1, 3);
        Key z = layout.getGeometry().findKeyAt(1, 4);
        MovementEvaluation eval = sc.evaluateBigram(a, z);
        assertNotNull(eval, "L'évaluation ne doit pas être null");
    }

}