package org.pytenix.commands;

import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.pytenix.SimpleBasicPlugin;
import org.pytenix.util.MessageUtil;

import javax.xml.crypto.Data;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimecheckCommand implements CommandExecutor {


    final SimpleBasicPlugin plugin;
    final SimpleDateFormat simpleDateFormat;

    public TimecheckCommand(SimpleBasicPlugin plugin)
    {
        this.plugin = plugin;
        this.simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {


        if(command.getName().equalsIgnoreCase("timecheck"))
        {

            Date date = new Date(System.currentTimeMillis());
            MessageUtil.sendMessage(commandSender, "Current Time: " + simpleDateFormat.format(date), Color.YELLOW);

        }

        return true;

    }
}
