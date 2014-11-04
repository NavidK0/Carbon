package com.lastabyss.carbon.commands;

import com.lastabyss.carbon.utils.particlelib.ParticleEffect;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Wombosvideo
 */
public class CommandParticle extends Command {

	public CommandParticle() {
		super("particle", "Vanilla 1.8 particle command", "/particle <name> <x> <y> <z> <xd> <yd> <zd> <speed> [count] [mode]", new ArrayList<String>());
	}

	private float parseFloat(CommandSender sender, boolean color, String value) {
		float val = -1.0F;
		try {
			val = Float.parseFloat(value.endsWith("F") ? value : value + "F");
		} catch (Exception ex) {
			if (color) {
				sender.sendMessage("" + ChatColor.RED + "'" + value + "' is not a valid number");
			} else {
				sender.sendMessage("'" + value + "' is not a valid number");
			}
		}
		return val;
	}

	private int parseInt(CommandSender sender, boolean color, String value) {
		int val = -1;
		try {
			val = Integer.parseInt(value);
		} catch (Exception ex) {
			if (color) {
				sender.sendMessage("" + ChatColor.RED + "'" + value + "' is not a valid number");
			} else {
				sender.sendMessage("'" + value + "' is not a valid number");
			}
		}
		return val;
	}

	private double parseDouble(CommandSender sender, boolean color, String value) {
		double val = -1;
		try {
			val = Double.parseDouble(value);
		} catch (Exception ex) {
			if (color) {
				sender.sendMessage("" + ChatColor.RED + "'" + value + "' is not a valid number");
			} else {
				sender.sendMessage("'" + value + "' is not a valid number");
			}
		}
		return val;
	}

	// No support for any errors occuring while executing this method
	private Location getLocation(CommandSender sender, boolean color, Location location, String x, String y, String z) {
		boolean xRelative = x.startsWith("~");
		boolean yRelative = y.startsWith("~");
		boolean zRelative = z.startsWith("~");
		double dX = parseDouble(sender, color, x.replaceAll("~", ""));
		double dY = parseDouble(sender, color, y.replaceAll("~", ""));
		double dZ = parseDouble(sender, color, z.replaceAll("~", ""));
		Location finalloc = location;
		if (xRelative) {
			finalloc = finalloc.add(dX, 0D, 0D);
		} else {
			finalloc.setX(dX);
		}
		if (yRelative) {
			finalloc = finalloc.add(0D, dY, 0D);
		} else {
			finalloc.setY(dY);
		}
		if (zRelative) {
			finalloc = finalloc.add(0D, 0D, dZ);
		} else {
			finalloc.setZ(dZ);
		}
		return finalloc;
	}

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		Location location = null;
		boolean color = false;
		if (sender instanceof Player) {
			if (!sender.isOp()) {
				return false;
			}
			location = ((Player) sender).getLocation();
			color = true;
		} else if (sender instanceof BlockCommandSender) {
			location = ((BlockCommandSender) sender).getBlock().getLocation().add(0.5D, 0.5D, 0.5D);
		} else {
			sender.sendMessage("This command needs to be sent by an ingame command executor.");
			return true;
		}
		if (args.length >= 8) {
			String name = args[0];
			String x = args[1];
			String y = args[2];
			String z = args[3];
			Location finalLocation = getLocation(sender, color, location, x, y, z);
			String xd = args[4];
			String yd = args[5];
			String zd = args[6];
			String speed = args[7];
			String amount = args.length > 8 ? args[8] : "1";
			float fXd = parseFloat(sender, color, xd);
			float fYd = parseFloat(sender, color, yd);
			float fZd = parseFloat(sender, color, zd);
			float fSpeed = parseFloat(sender, color, speed);
			int iAmount = parseInt(sender, color, amount);
			if (fXd != -1.0F && fYd != -1.0F && fZd != -1.0F && iAmount != -1) {
				try {
					ParticleEffect.fromName(name).display(fXd, fYd, fZd, fSpeed, iAmount, finalLocation, 32.0D);
					return true;
				} catch (Exception ex) {
					if (color) {
						sender.sendMessage("" + ChatColor.RED + "An internal error occurred while attempting to perform this command");
					} else {
						sender.sendMessage("An internal error occurred while attempting to perform this command");
					}
				}
			}
		}
		return false;
	}

}
