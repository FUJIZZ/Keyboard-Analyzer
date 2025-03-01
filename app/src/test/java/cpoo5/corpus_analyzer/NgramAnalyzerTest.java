package cpoo5.corpus_analyzer;


import cpoo5.corpus_analyzer.model.analyzer.NgramAnalyzer;
import cpoo5.corpus_analyzer.model.analyzer.NgramCount;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class NgramAnalyzerTest {

    @Test
    void testExtractNgramsSingleWord() {
        NgramAnalyzer analyzer = new NgramAnalyzer("test");
        List<NgramCount> result = analyzer
            .extractNgrams("hello", 2);
        assertEquals(4, result.size(), "Doit extraire 4 bigrammes de 'hello'");
    }

    @Test
    void testExtractNgramsEmptyString() {
        NgramAnalyzer analyzer = new NgramAnalyzer("test");
        List<NgramCount> result = analyzer
            .extractNgrams("", 2);
        assertTrue(result.isEmpty(), "String vide n'a pas de n-grammes");
    }

    @Test
    void testGlobalNgramCountsNotNull() {
        NgramAnalyzer analyzer = new NgramAnalyzer("test");
        assertNotNull(analyzer, "NgramAnalyzer doit être créée sans exception");
    }
}