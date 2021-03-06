package com.github.yuttyann.scriptblockplus.event;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

import com.github.yuttyann.scriptblockplus.utils.ItemUtils;

public abstract class ScriptBlockEvent extends PlayerEvent implements Cancellable {

	private static final HandlerList HANDLERS = new HandlerList();

	protected Block block;
	protected Location location;
	protected ItemStack mainHand;
	protected ItemStack offHand;

	public ScriptBlockEvent(Player player) {
		super(player);
	}

	public ScriptBlockEvent(Player player, Block block) {
		this(player, block, block.getLocation());
	}

	public ScriptBlockEvent(Player player, Block block, Location location) {
		super(player);
		this.block = block;
		this.location = location;
		this.mainHand = ItemUtils.getItemInMainHand(player);
		this.offHand = ItemUtils.getItemInOffHand(player);
	}

	public final Block getBlock() {
		return block;
	}

	public final Location getLocation() {
		return location;
	}

	public ItemStack getItemInMainHand() {
		return mainHand;
	}

	public ItemStack getItemInOffHand() {
		return offHand;
	}

	public ItemStack getItem(boolean isMainHand) {
		return isMainHand ? getItemInMainHand() : getItemInOffHand();
	}

	@Override
	public abstract boolean isCancelled();

	@Override
	public abstract void setCancelled(boolean cancel);

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}
}