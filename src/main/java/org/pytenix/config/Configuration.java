package org.pytenix.config;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Configuration implements Serializable {


    List<String> greetMessages;


    public static Configuration defaultConfiguration() {
        return new Configuration(
                new ArrayList<>(List.of(
                        "§e%source_name% greeted you!",
                        "§eHello %name%!",
                        "§eGreetings from %source_name%, §a%name%!"))
                );
    }

}
