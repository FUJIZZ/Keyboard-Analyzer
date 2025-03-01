package cpoo5.corpus_analyzer;

import cpoo5.corpus_analyzer.controller.AnalyzerController;


import cpoo5.lib.ModuleManager.Launcher;

public class AnalyzerLauncher implements Launcher {




    @Override
    public void launch() {

        AnalyzerController controller = new AnalyzerController();
        controller.selectCorpus();
    }
}
