package fr.adracode.morescenarios.utils;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;

/**
 * Class utils to perform operation on strings
 *
 * @author adracode
 */
public class StringUtils {

    public static IChatBaseComponent getIChatBaseComponent(String toConvert){
        return IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + toConvert + "\"}");
    }

}
