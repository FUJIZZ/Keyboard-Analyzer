package cpoo5;

import cpoo5.lib.global_controller.AppStartController;
import cpoo5.corpus_analyzer.AnalyzerLauncher;
import cpoo5.lib.ModuleManager;
import cpoo5.layout_evaluator.EvaluatorLauncher;

public class App {
    public static void main(String[] args) {
        

        ModuleManager moduleManager = new ModuleManager();
        moduleManager.registerModule("1", "Analyzer", new AnalyzerLauncher());
        moduleManager.registerModule("2", "Evaluator", new EvaluatorLauncher());
        moduleManager.registerModule("3", "Exit", () -> System.exit(0));

        AppStartController controller = new AppStartController( moduleManager);
        while (true) {

            String choice = controller.getUserChoice();

            moduleManager.launchModule(choice);
        }
    }
}