package cpoo5.layout_evaluator;

import cpoo5.layout_evaluator.model.evaluator.LayoutEvaluator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Arrays;

import cpoo5.layout_evaluator.model.layout.KeyboardLayout;
import cpoo5.layout_evaluator.model.layout.KeyboardLayoutFactory;

class LayoutEvaluatorTest {

    @Test
    void testConstructorWithNullLayoutThrows() {
        assertThrows(IllegalArgumentException.class, () -> new LayoutEvaluator(null, List.of("abc")));
    }

    @Test
    void testConstructorWithNullNGramsThrows() {
        KeyboardLayout azerty = KeyboardLayoutFactory.createLayout("azerty");
        assertThrows(IllegalArgumentException.class, () -> new LayoutEvaluator(azerty, null));
    }

    @Test
    void testEvaluateOneNGramDetailed() {
        KeyboardLayout azerty = KeyboardLayoutFactory.createLayout("azerty");
        List<String> nGrams = Arrays.asList("abc");
        LayoutEvaluator evaluator = new LayoutEvaluator(azerty, nGrams);

        LayoutEvaluator.NGramEvaluationResult result = evaluator.evaluateOneNGramDetailed("abc");
        assertNotNull(result, "Résultat ne doit pas être null");
    }

}