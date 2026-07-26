package rusplugins.neonukkitx.plugin.internal;

import rusplugins.neonukkitx.plugin.PluginDescription;
import rusplugins.neonukkitx.plugin.PluginLoadOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Описание внутреннего модуля ядра NeoNukkitX.
 * Создаётся программно, без plugin.yml.
 */
public class InternalPluginDescription extends PluginDescription {

    public InternalPluginDescription(String name, String version, String description, List<String> authors, String website) {
        super(buildMap(name, version, description, authors, website));
    }

    private static java.util.Map<String, Object> buildMap(String name, String version, String description, List<String> authors, String website) {
        java.util.Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("version", version);
        map.put("main", "internal.dummy.Main");
        map.put("api", new ArrayList<String>() {{ add("1.0.0"); }});
        map.put("description", description);
        map.put("load", "STARTUP");
        if (authors != null && !authors.isEmpty()) {
            map.put("authors", authors);
        }
        if (website != null) {
            map.put("website", website);
        }
        return map;
    }

    @Override
    public PluginLoadOrder getOrder() {
        return PluginLoadOrder.STARTUP;
    }
}
