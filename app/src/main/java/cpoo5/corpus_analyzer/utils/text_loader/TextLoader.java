package cpoo5.corpus_analyzer.utils.text_loader;

import java.io.IOException;
import java.util.List;

public interface TextLoader {
    List<String> loadTexts(String directoryPath) ;
}