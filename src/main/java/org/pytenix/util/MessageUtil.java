package org.pytenix.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Color;
import org.bukkit.command.CommandSender;

public class MessageUtil {


    public static TextColor toColor(Color color) {
        return TextColor.color(color.asRGB());
    }


    public static void sendMessage(CommandSender sender, Component message) {
        sender.sendMessage((message));
    }

    public static void sendMessage(CommandSender sender, String message, Color color) {
        sender.sendMessage(Component.text(message).color(toColor(color)));
    }

}
