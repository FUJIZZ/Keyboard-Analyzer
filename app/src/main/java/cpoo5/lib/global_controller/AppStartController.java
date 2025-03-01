package cpoo5.lib.global_controller;

import java.util.function.Predicate;

import cpoo5.lib.ModuleManager;

public class AppStartController extends Controller {

    private final ModuleManager moduleManager;

    public AppStartController(ModuleManager moduleManager) {

        this.moduleManager = moduleManager;
    }

    public String getUserChoice() {
        displayMessage("Modules disponibles :");
        for (String key : moduleManager.getModules().keySet()) {
            displayMessage(key + ": " + moduleManager.getModules().get(key).name());
        }

        Predicate<String> isValidChoice = choice -> moduleManager.getModules().containsKey(choice);

        return getValidatedUserInput("Choisissez un module :", isValidChoice, "Choix invalide. Veuillez réessayer.");
    }

    @Override
    public void execute() {
        String choice = getUserChoice();
        moduleManager.launchModule(choice);
    }
}