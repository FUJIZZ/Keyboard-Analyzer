package cpoo5.corpus_analyzer;

import cpoo5.corpus_analyzer.model.analyzer.NgramCount;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class NgramCountTest {

    @Test
    void testValidNgramCount() {
        NgramCount nc = new NgramCount("test", 5);
        assertEquals("test", nc.ngram());
        assertEquals(5, nc.count());
    }

    @Test
    void testInvalidNgramThrows() {
        assertThrows(IllegalArgumentException.class, () -> new NgramCount("", 1));
        assertThrows(IllegalArgumentException.class, () -> new NgramCount(null, 1));
    }

    @Test
    void testNegativeCountThrows() {
        assertThrows(IllegalArgumentException.class, () -> new NgramCount("test", -1));
    }
}
