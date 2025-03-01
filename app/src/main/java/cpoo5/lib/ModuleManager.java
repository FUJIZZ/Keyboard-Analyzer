package cpoo5.lib;

import java.util.HashMap;
import java.util.Map;

public class ModuleManager {

    public interface Launcher {
        void launch();
    }

    public record Module(String name, Launcher launcher) {}

    private static final Map<String, Module> modules = new HashMap<>();

    /**
     * Enregistre un module avec une clé unique, un nom et un lanceur.
     *
     * @param key La clé unique pour identifier le module.
     * @param name Le nom du module.
     * @param launcher Le lanceur associé au module.
     */
    public void registerModule(String key, String name, Launcher launcher) {
        modules.put(key, new Module(name, launcher));
    }

    /**
     * Lance le module correspondant à la clé spécifiée.
     *
     * @param key la clé du module à lancer
     * @throws NullPointerException si le module correspondant à la clé est introuvable
     */
    public void launchModule(String key) {
        Module module = modules.get(key);
        if (module != null) {
            module.launcher().launch();
        } else {
            System.out.println("Module invalide : " + key);
        }
    }

    public Map<String, Module> getModules() {
        return modules;
    }
}
