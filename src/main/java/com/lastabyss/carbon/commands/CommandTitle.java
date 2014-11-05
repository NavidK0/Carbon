package com.lastabyss.carbon.commands;

import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.spigotmc.ProtocolInjector;

import java.util.ArrayList;

/**
 *
 * @author matthijs2704
 */
public class CommandTitle extends Command {

    public CommandTitle() {
        super("title", "Vanilla 1.8 title command", "/title <player> <title|subtitle|clear|reset|times> ...", new ArrayList<String>());
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
    	if (sender instanceof Player) {
			if (!sender.isOp()) {
				return false;
			}
    	}
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /title <player> <title|subtitle|clear|reset|times> ...");
            return true;
        }

        ArrayList<Player> targets = new ArrayList<Player>();
        if (args[0].equalsIgnoreCase("@a")) {
        	targets.addAll(Bukkit.getOnlinePlayers());
        }
        Player player = getOnlinePlayer(args[0]);
        if (player == null && targets.isEmpty()) {
            sender.sendMessage(ChatColor.RED + args[0] + " is not a valid player or is offline.");
            return true;
        }

        targets.add(player);
        for (Player target : targets) {
            if (args[1].equalsIgnoreCase("times")) {
                int fadeIn, stay, fadeOut;

                if (args.length < 5) {
                    sender.sendMessage(ChatColor.RED + "Usage: /title <player> times <fadeIn> <stay> <fadeOut>");
                    return true;
                }

                if (isInteger(args[2])) {
                    fadeIn = Integer.parseInt(args[2]);
                } else {
                    sender.sendMessage(ChatColor.RED + args[2] + " is not a valid number");
                    return true;
                }

                if (isInteger(args[3])) {
                    stay = Integer.parseInt(args[3]);
                } else {
                    sender.sendMessage(ChatColor.RED + args[3] + " is not a valid number");
                    return true;
                }

                if (isInteger(args[4])) {
                    fadeOut = Integer.parseInt(args[4]);
                } else {
                    sender.sendMessage(ChatColor.RED + args[4] + " is not a valid number");
                    return true;
                }

                sendTimes(fadeIn, stay, fadeOut, target);
                sender.sendMessage(ChatColor.GREEN + "Title command successfully executed");
            } else if (args[1].equalsIgnoreCase("title")) {
                if (args.length < 3) {
                    sender.sendMessage(ChatColor.RED + "Usage: /title <player> title <raw json title|text>");
                    return true;
                }

                sendTitle(args, 2, target);
                sender.sendMessage(ChatColor.GREEN + "Title command successfully executed");
            } else if (args[1].equalsIgnoreCase("subtitle")) {
                if (args.length < 3) {
                    sender.sendMessage(ChatColor.RED + "Usage: /title <player> subtitle <raw json title|text>");
                    return true;
                }

                sendSubtitle(args, 2, target);
                sender.sendMessage(ChatColor.GREEN + "Title command successfully executed");

            } else if (args[1].equalsIgnoreCase("clear")) {

                //sendTimes(0,0,0,player);
                sendClear(target);
                sender.sendMessage(ChatColor.GREEN + "Title command successfully executed");

            } else if (args[1].equalsIgnoreCase("reset")) {

                sendReset(target);
                sender.sendMessage(ChatColor.GREEN + "Title command successfully executed");

            }
        }
        return true;
    }
    private void sendClear(Player player) {
        //((CraftPlayer)player).getHandle().playerConnection.sendPacket(new ProtocolInjector.PacketTitle(ProtocolInjector.PacketTitle.Action.CLEAR));
        IChatBaseComponent subtitle = ChatSerializer.a(TextConverter.convert(""));
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(new ProtocolInjector.PacketTitle(ProtocolInjector.PacketTitle.Action.TITLE, subtitle));
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(new ProtocolInjector.PacketTitle(ProtocolInjector.PacketTitle.Action.SUBTITLE, subtitle));


    }

    private void sendReset(Player player) {
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(new ProtocolInjector.PacketTitle(ProtocolInjector.PacketTitle.Action.RESET));

    }

    private void sendTimes(int fadeIn, int stay, int fadeOut, Player player) {
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(new ProtocolInjector.PacketTitle(ProtocolInjector.PacketTitle.Action.TIMES, fadeIn, stay, fadeOut));
    }

    void sendTitle(String[] args, int start, Player p) {
        StringBuilder sb = new StringBuilder(args[start]);
        for (int i = start + 1; args.length > i; i++)
            sb.append(" " + args[i]);

        String raw = sb.toString();
        IChatBaseComponent subtitle = null;
        if (raw.startsWith("{") && raw.endsWith("}")) {
            subtitle = ChatSerializer.a(raw);
        }else{
            String titleStr = ChatColor.translateAlternateColorCodes('&', raw);
            subtitle = ChatSerializer.a(TextConverter.convert(titleStr));
        }
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(new ProtocolInjector.PacketTitle(ProtocolInjector.PacketTitle.Action.TITLE, subtitle));

    }

    void sendSubtitle(String[] args, int start, Player p) {
        StringBuilder sb = new StringBuilder(args[start]);
        for (int i = start + 1; args.length > i; i++)
            sb.append(" " + args[i]);

        String raw = sb.toString();
        IChatBaseComponent subtitle = null;
        if (raw.startsWith("{") && raw.endsWith("}")) {
            subtitle = ChatSerializer.a(raw);
        }else{
            String titleStr = ChatColor.translateAlternateColorCodes('&', raw);
            subtitle = ChatSerializer.a(TextConverter.convert(titleStr));
        }
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(new ProtocolInjector.PacketTitle(ProtocolInjector.PacketTitle.Action.SUBTITLE, subtitle));

    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    private Player getOnlinePlayer(String name){
        for (Player p : Bukkit.getOnlinePlayers()){
            if (p.getName().equalsIgnoreCase(name)){
                return p;
            }
        }
        return null;
    }
}
/*
 * Creates raw json from plain text
 */
class TextConverter {
    public static String convert(String text) {
        if (text == null || text.length() == 0) {
            return "\"\"";
        }

        char c;
        int i;
        int len = text.length();
        StringBuilder sb = new StringBuilder(len + 4);
        String t;

        sb.append('"');
        for (i = 0; i < len; i += 1) {
            c = text.charAt(i);
            switch (c) {
                case '\\':
                case '"':
                    sb.append('\\');
                    sb.append(c);
                    break;
                case '/':
                    sb.append('\\');
                    sb.append(c);
                    break;
                case '\b':
                    sb.append("\\b");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\f':
                    sb.append("\\f");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                default:
                    if (c < ' ') {
                        t = "000" + Integer.toHexString(c);
                        sb.append("\\u" + t.substring(t.length() - 4));
                    } else {
                        sb.append(c);
                    }
            }
        }
        sb.append('"');
        return sb.toString();
    }


}