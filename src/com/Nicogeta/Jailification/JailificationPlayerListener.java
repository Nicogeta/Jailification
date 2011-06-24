package com.Nicogeta.Jailification;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.inventory.ItemStack;


public class JailificationPlayerListener extends PlayerListener{

	final Jailification plugin;

	public JailificationPlayerListener (Jailification instance) {
		plugin = instance;
	}

	@SuppressWarnings("deprecation")
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		Entity entity = event.getRightClicked();
		int entityLocX = event.getRightClicked().getLocation().getBlockX();
		int entityLocY = event.getRightClicked().getLocation().getBlockY();
		int entityLocZ = event.getRightClicked().getLocation().getBlockZ();
		World world = event.getPlayer().getWorld();

		int materialInHand = 283;
		Material materialNeeded = Material.REDSTONE;
		boolean canJail = false;
		boolean noRedstoneNeeded = false;
		boolean onPlayers = false;
		boolean jailPlayersToo = false;
		int amountOfRessource = 1;

		if((Jailification.Permissions == null && player.isOnline()))
			canJail = true;
		else if(Jailification.Permissions != null && Jailification.Permissions.has(player, "Jailification.canJail"))
			canJail = true;

		if((Jailification.Permissions == null && player.isOp()))
			onPlayers = true;
		else if(Jailification.Permissions != null && Jailification.Permissions.has(player, "Jailification.onPlayers"))
			onPlayers = true;

		if((Jailification.Permissions == null && player.isOnline()))
			noRedstoneNeeded = true;
		else if(Jailification.Permissions != null 
				&& Jailification.Permissions.has(player, "Jailification.noRedstoneNeeded"))
			noRedstoneNeeded = true;	

		if(canJail) {
			if(!onPlayers) {
				if(!(entity instanceof Player)) {
					if(event.getPlayer().getItemInHand().getTypeId() == materialInHand) {
						if(!noRedstoneNeeded) {
							if(event.getPlayer().getInventory().contains(materialNeeded, amountOfRessource)) {
								player.getInventory().removeItem(new ItemStack(materialNeeded, 1));
								player.updateInventory();
								jailification(entityLocX, entityLocY, entityLocZ, player, world);
							}
						} else {
							jailification(entityLocX, entityLocY, entityLocZ, player, world);
						}
					}
				}
			} else if (onPlayers || jailPlayersToo) {
				if(event.getPlayer().getItemInHand().getTypeId() == materialInHand) {
					if(!noRedstoneNeeded) {
						if(event.getPlayer().getInventory().contains(materialNeeded, amountOfRessource)) {
							player.getInventory().removeItem(new ItemStack(materialNeeded, 1));
							player.updateInventory();
							jailification(entityLocX, entityLocY, entityLocZ, player, world);
						}
					} else {
						jailification(entityLocX, entityLocY, entityLocZ, player, world);
					}
				}
			}
		}
	}	

	public void jailification(int entityLocX, int entityLocY, int entityLocZ, Player player, World world) {
		Material blockCheckOne;
		Material blockCheckTwo;
		Material blockCheckThree;
		int jailX = entityLocX + 3;
		int jailY = entityLocY + 3;
		int jailZ = entityLocZ + 2;
		int xChanger = 0;
		int zChanger = 0;
		int yChanger = 0;
		for(int k = 0; k < 2; k++) {
			for(int i = 0; i < 5; i++){
				for(int j = 0; j < 3; j++){
					blockCheckOne = world.getBlockAt(jailX - xChanger, entityLocY + j, jailZ - i).getType();
					if (blockCheckOne == Material.AIR || blockCheckOne == Material.SNOW){
						world.getBlockAt(jailX - xChanger, entityLocY + j, jailZ - i).setTypeId(20);
					}
				}
			}
			xChanger = 5;
		}
		for(int n = 0; n < 2; n++) {
			for(int m = 0; m < 5; m++) {
				for(int l = 0; l < 3; l++) {
					blockCheckTwo = world.getBlockAt(jailX - m, entityLocY + l, jailZ - zChanger).getType();
					if(blockCheckTwo == Material.AIR || blockCheckTwo == Material.SNOW) {
						world.getBlockAt(jailX - m, entityLocY + l, jailZ - zChanger).setTypeId(20);
					}
				}
			}
			zChanger = 4;
		}
		for(int r = 0; r < 2; r++) {
			for(int q = 0; q < 6; q++) {
				for(int p = 0; p < 5; p++) {
					blockCheckThree = world.getBlockAt(jailX - q, jailY - yChanger, jailZ - p).getType();
					if(blockCheckThree == Material.AIR || blockCheckThree == Material.SNOW) {
						if(r > 0) {
							world.getBlockAt(jailX - q, jailY - yChanger, jailZ - p).setTypeId(20);
						} else {
							world.getBlockAt(jailX - q, jailY - yChanger, jailZ - p).setTypeId(1);
						}
					}
				}
			}
			yChanger = 4;
		}
	}
}