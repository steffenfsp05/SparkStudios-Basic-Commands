package org.pytenix.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.entity.Player;

import java.util.function.Function;

@Getter @AllArgsConstructor @NoArgsConstructor
public enum ConfigPlaceholder {

    TARGET_NAME("%name%", placeholderInput -> placeholderInput.targetPlayer.getName()),
    SOURCE_NAME("%sourcename%", placeholderInput -> placeholderInput.sourcePlayer.getName());


    String placeholder;
    Function<PlaceholderInput, String> function;



    public record PlaceholderInput(Player sourcePlayer, Player targetPlayer) {}


    public static String FormatMessage(String rawMessage, PlaceholderInput placeholderInput) {
        for (ConfigPlaceholder value : ConfigPlaceholder.values()) {
            rawMessage = rawMessage.replace("%" + value.placeholder + "%", value.getFunction().apply(placeholderInput));
        }
        return rawMessage;
    }

}
