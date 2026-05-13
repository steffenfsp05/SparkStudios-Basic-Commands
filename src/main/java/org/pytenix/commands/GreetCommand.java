package org.pytenix.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.pytenix.SimpleBasicPlugin;
import org.pytenix.config.ConfigPlaceholder;

import java.util.List;
import java.util.Random;

import static org.pytenix.util.MessageUtil.sendMessage;

public class GreetCommand implements CommandExecutor {


    final SimpleBasicPlugin plugin;
    final Random random;

    public GreetCommand(SimpleBasicPlugin plugin)
    {
        this.plugin = plugin;
        this.random = new Random();
    }


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {


        if(command.getName().equalsIgnoreCase("greet"))
        {
            if(strings.length == 1)
            {
                String name = strings[0];

                if(Bukkit.getPlayer(name) == null)
                {
                    sendMessage(commandSender, "The player '"+ name + "' is not online!", Color.RED);
                    return true;
                }

                final Player player = Bukkit.getPlayer(name);


                sendMessage(commandSender, "You greeted " + player.getName() + "!",Color.GREEN);
                sendMessage(player, greetMessage(new ConfigPlaceholder.PlaceholderInput(commandSender, player)));

                return true;

            } else
                sendHelp(commandSender);


        }


        return true;
    }


    public Component greetMessage(ConfigPlaceholder.PlaceholderInput placeholderInput)
    {
        final List<String> greetMessages = plugin.getConfigService().getConfiguration().getGreetMessages();

        String message = greetMessages.get(random.nextInt(greetMessages.size()-1));

        return ConfigPlaceholder.formatMessageToComponent(message, placeholderInput);

    }


    public void sendHelp(CommandSender sender) {
        sendMessage(sender, "/greet <Player> - Greets a player", Color.GREEN);
    }

}
