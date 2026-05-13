package org.pytenix.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.function.Function;

@Getter @AllArgsConstructor @NoArgsConstructor

public enum ConfigPlaceholder {

    TARGET_NAME("%name%", placeholderInput -> placeholderInput.targetPlayer.getName()),
    SOURCE_NAME("%sourcename%", placeholderInput -> placeholderInput.sourcePlayer.getName());


    String placeholder;
    Function<PlaceholderInput, String> function;



    public record PlaceholderInput(CommandSender sourcePlayer, Player targetPlayer) {}


    public static String formatMessageToText(String rawMessage, PlaceholderInput placeholderInput) {
        for (ConfigPlaceholder value : ConfigPlaceholder.values()) {
            rawMessage = rawMessage.replace("%" + value.placeholder + "%", value.getFunction().apply(placeholderInput));
        }
        return rawMessage;
    }

    public static Component formatMessageToComponent(String rawMessage, PlaceholderInput placeholderInput)
    {
        return LegacyComponentSerializer.legacySection().deserialize(formatMessageToText(rawMessage,placeholderInput));
    }

}
