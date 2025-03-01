package cpoo5.layout_evaluator;

import cpoo5.lib.ModuleManager.Launcher;
import cpoo5.layout_evaluator.controller.EvaluatorController;

public class EvaluatorLauncher implements Launcher {

    @Override
    public void launch() {
        EvaluatorController controller = new EvaluatorController();
        controller.execute();
    }

}
