package org.panthercode.arctic.core.runtime.plugins;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by architect on 02.03.17.
 */
public class PluginManager {

    private HashMap<String, Plugin> plugins = new HashMap<>();

    public PluginManager() {
    }

    public boolean register(Plugin plugin) {
        return false;
    }

    public boolean registerAndActivate(Plugin plugin) {
        return false;
    }

    public boolean unregister(Plugin plugin) {
        return false;
    }

    public boolean contains(String name) {
        return false;
    }

    public void activate(String name) {

    }

    public void deactivate(String name) {

    }

    public Plugin getPlugin(String name) {
        return null;
    }

    public <T extends Plugin> Plugin getPlugin(String name, Class<T> clazz) {
        return null;
    }

    public InteractivePlugin getInteractivePlugin(String name) {
        return null;
    }

    public Set<Plugin> plugins() {
        return null;
    }

    public int size() {
        return 0;
    }
}
