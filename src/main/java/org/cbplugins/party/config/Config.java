package org.cbplugins.party.config;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.cbplugins.party.Party;

import java.io.*;

public class Config {

    private Plugin plugin;
    private Configuration config;
    private String name;

    public Config(String name, Plugin plugin) {
        this.plugin = plugin;
        this.name = name;
        createIfNotExists();
        loadConfiguration();
    }

    public void createIfNotExists() {
        if(!plugin.getDataFolder().exists()) {
            if(!plugin.getDataFolder().mkdir()) {
                Party.getInstance().getLogger().warning("Could not create data folder. Please double check your file system permissions.");
            }
        }
        File file = new File(plugin.getDataFolder(), name + ".yml");
        if(!file.exists()) {
            try {
                if(!file.createNewFile()) {
                    Party.getInstance().getLogger().warning("Could not create config file. Please double check your file system permissions.");
                }
                FileWriter out = new FileWriter(file);
                InputStream is = Party.class.getResourceAsStream("/" + name + ".yml");
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line;
                while((line = br.readLine()) != null) {
                    out.write(line + "\n");
                }
                out.flush();
                is.close();
                isr.close();
                br.close();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void loadConfiguration() {
        try {
            this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(plugin.getDataFolder(), name + ".yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Configuration getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File(plugin.getDataFolder(), name + ".yml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
