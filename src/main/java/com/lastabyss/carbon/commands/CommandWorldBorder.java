package com.lastabyss.carbon.commands;


import java.util.ArrayList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Thanks minelazz! Modified this a bit.
 * @author minelazz
 */
public class CommandWorldBorder extends Command {
    public static WorldBorderCommand cmd = new WorldBorderCommand();
    
    public CommandWorldBorder() {
        super("worldborder", "Vanilla 1.8 border command.", "/worldborder <args>", new ArrayList<String>());
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof Player) {
        Player p = (Player) sender;
        if (p.isOp())
	cmd.execute(new ICommandConv(p), args);
        }
	return true;
    }
}
		