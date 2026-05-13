package org.pytenix.config;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;
import org.pytenix.SimpleBasicPlugin;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.io.*;

public class ConfigService {


    private final Yaml yaml;
    private final String fileName;


    @Getter
    Configuration configuration;

    private final SimpleBasicPlugin plugin;

    public ConfigService(SimpleBasicPlugin plugin, File dataFolder) {

        this.plugin = plugin;

        this.fileName = new File(dataFolder, "config.yaml").getAbsolutePath();

        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setIndent(2);
        options.setPrettyFlow(true);

        Representer representer = new Representer(options);
        representer.addClassTag(Object.class, Tag.MAP);

        LoaderOptions loaderOptions = new LoaderOptions();
        loaderOptions.setTagInspector(tag -> true);
        this.yaml = new Yaml(new Constructor(Object.class, loaderOptions), representer, options);


        if (!existsConfig())
            saveConfig(Configuration.defaultConfiguration());

        this.configuration = loadConfig();

        if (this.configuration == null) {
            plugin.getLogger().severe("Error loading configuration from file, loading default configuration!");
            this.configuration = Configuration.defaultConfiguration();
        }
    }

    public void reloadConfig() {
        Configuration reloaded = loadConfig();
        if (reloaded != null) {
            this.configuration = reloaded;
        } else {
            plugin.getLogger().severe("Error while reloading the config");
        }
    }

    public boolean existsConfig() {
        return new File(fileName).exists();
    }

    public void saveConfig(Configuration configuration) {
        if (!saveFile(configuration, new File(fileName)))
            plugin.getLogger().severe("Error saving configuration!");
    }

    public @Nullable Configuration loadConfig() {
        return loadFile(Configuration.class, new File(fileName));
    }


    public boolean saveFile(Object data, File file) {
        try (Writer writer = new FileWriter(file)) {

            if (data instanceof Configuration)
                writeComments(writer);

            yaml.dump(data, writer);
            return true;
        } catch (IOException e) {
            plugin.getLogger().severe("Error while saving the config: " + e.getMessage());
            return false;
        }
    }


    public void writeComments(Writer writer) throws IOException {
        writer.write("# =======================================================\n");
        writer.write("# SimpleBasicCommands Configuration\n");
        writer.write("# =======================================================\n");
        writer.write("# \n");
        writer.write("# Available Placeholders for 'greetMessages':\n");
        writer.write("# %name%           - The original name of the targetted Player\n");
        writer.write("# %sourcename%      - The original name of the source Player\n");
        writer.write("# \n");
        writer.write("# =======================================================\n\n");
    }

    public <T> @Nullable T loadFile(Class<T> clazz, File file) {
        if (!file.exists())
            return null;


        try (InputStream inputStream = new FileInputStream(file)) {
            LoaderOptions loaderOptions = new LoaderOptions();
            loaderOptions.setTagInspector(tag -> true);
            Object loaded = new Yaml(new Constructor(clazz, loaderOptions)).load(inputStream);
            return clazz.cast(loaded);
        } catch (Exception e) {
            plugin.getLogger().severe("[Config] Error while loading " + file.getName() + ": " + e.getMessage());
            return null;
        }
    }
}