package org.pytenix;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.pytenix.commands.GreetCommand;
import org.pytenix.commands.TimecheckCommand;
import org.pytenix.config.ConfigService;
import org.pytenix.config.Configuration;

public class SimpleBasicPlugin extends JavaPlugin {


    @Getter
    ConfigService configService;


    @Override
    public void onEnable() {

        if (!getDataFolder().exists())
            getDataFolder().mkdirs();

        this.configService = new ConfigService(this, getDataFolder());


        registerCommands();

    }


    private void registerCommands() {
        this.getCommand("greet").setExecutor(new GreetCommand(this));
        this.getCommand("timecheck").setExecutor(new TimecheckCommand(this));
    }




}
