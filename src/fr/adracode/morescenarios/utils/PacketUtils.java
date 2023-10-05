package fr.adracode.morescenarios.utils;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;

import java.lang.reflect.Field;

/**
 * Class utils to easily send packets to players
 *
 * @author adracode
 */
public class PacketUtils {

    public static Packet packetActionBar(String message){
        IChatBaseComponent messageLegacy = StringUtils.getIChatBaseComponent(message);
        PacketPlayOutChat packet = new PacketPlayOutChat();
        try {
            setField(packet, packet.getClass().getDeclaredField("a"), messageLegacy);
            setField(packet, packet.getClass().getDeclaredField("b"), (byte) 2);
        } catch(IllegalAccessException | NoSuchFieldException e){
            e.printStackTrace();
        }
        return packet;
    }

    public static Packet packetTitle(PacketPlayOutTitle.EnumTitleAction action, String message, int fadeIn, int stay, int fadeOut){
        IChatBaseComponent messageLegacy = StringUtils.getIChatBaseComponent(message);
        PacketPlayOutTitle packet = new PacketPlayOutTitle();
        try {
            setField(packet, packet.getClass().getDeclaredField("a"), action);
            setField(packet, packet.getClass().getDeclaredField("b"), messageLegacy);
            setField(packet, packet.getClass().getDeclaredField("c"), fadeIn);
            setField(packet, packet.getClass().getDeclaredField("d"), stay);
            setField(packet, packet.getClass().getDeclaredField("e"), fadeOut);
        } catch(IllegalAccessException | NoSuchFieldException e){
            e.printStackTrace();
        }
        return packet;
    }

    private static void setField(Object packet, Field field, Object value) throws IllegalAccessException{
        field.setAccessible(true);
        field.set(packet, value);
        field.setAccessible(false);
    }

}
