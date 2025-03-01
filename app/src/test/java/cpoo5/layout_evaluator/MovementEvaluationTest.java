package cpoo5.layout_evaluator;

import cpoo5.layout_evaluator.model.evaluator.MovementEvaluation;
import cpoo5.layout_evaluator.model.evaluator.Multipliers;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class MovementEvaluationTest {

    @Test
    void testAddMultiplier() {
        MovementEvaluation eval = new MovementEvaluation();
        eval.addMultiplier(Multipliers.SFB);
        assertTrue(eval.getMovementTags().contains("SFB"), "Should contain 'SFB' tag");
        assertEquals(1, eval.getMultipliers().size(), "One multiplier should be added");
    }

    @Test
    void testSetFinalScore() {
        MovementEvaluation eval = new MovementEvaluation();
        eval.setFinalScore(10.0);
        assertEquals(10.0, eval.getFinalScore(), 0.0001, "Final score should be 10");
    }
}