package com.Nicogeta.Jailification;

import java.util.logging.Logger;

import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.Nicogeta.Jailification.Jailification;
import com.Nicogeta.Jailification.JailificationPlayerListener;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class Jailification extends JavaPlugin {

	private static final Logger log = Logger.getLogger("Minecraft");
	public static PermissionHandler Permissions;
	private final JailificationPlayerListener playerListener = new JailificationPlayerListener(this);

	public void onEnable() {
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Event.Type.PLAYER_INTERACT_ENTITY, playerListener, Priority.Normal, this);
		setupPermissions();
		log.info("Jailification ENABLE (by Nicogeta)");
		log.info("Jailification ver 0.1");
	}

	private void setupPermissions() {
		Plugin test = this.getServer().getPluginManager().getPlugin("Permissions");

		if (Jailification.Permissions == null) 
		{
			if (test != null) {
				Jailification.Permissions = ((Permissions)test).getHandler();
				log.info("Jailification has detected Permissions!");
			} else {
				log.info("Jailification has not detected Permissions.");
			}
		}
	}

	public void onDisable() {
		log.info("Jailification DISABLE");
	}



}
