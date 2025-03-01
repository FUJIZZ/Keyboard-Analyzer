package cpoo5.corpus_analyzer.model.analyzer;


// Record pour représenter un n-gram avec son compteur
public record NgramCount(String ngram, int count) {

    public NgramCount {
        if (ngram == null || ngram.isEmpty()) {
            throw new IllegalArgumentException("ngram cannot be null or empty");
        }
        if (count < 0) {
            throw new IllegalArgumentException("count cannot be negative");
        }
    }
}
