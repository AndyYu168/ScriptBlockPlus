package com.github.yuttyann.scriptblockplus.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.github.yuttyann.scriptblockplus.ScriptBlock;
import com.github.yuttyann.scriptblockplus.enums.Permission;
import com.github.yuttyann.scriptblockplus.event.ScriptBlockBreakEvent;
import com.github.yuttyann.scriptblockplus.file.SBConfig;
import com.github.yuttyann.scriptblockplus.script.ScriptRead;
import com.github.yuttyann.scriptblockplus.script.ScriptType;
import com.github.yuttyann.scriptblockplus.script.ScriptType.SBPermission;
import com.github.yuttyann.scriptblockplus.utils.ItemUtils;
import com.github.yuttyann.scriptblockplus.utils.Utils;

public class ScriptBreakListener extends IAssist {

	public ScriptBreakListener(ScriptBlock plugin) {
		super(plugin, ScriptType.BREAK);
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onBlockBreakEvent(BlockBreakEvent event) {
		Player player = event.getPlayer();
		ItemStack item = ItemUtils.getItemInMainHand(player);
		if (ItemUtils.isBlockSelector(player, item) && Permission.TOOL_BLOCKSELECTOR.has(player)
				|| ItemUtils.isScriptEditor(player, item) && Permission.TOOL_SCRIPTEDITOR.has(player)) {
			event.setCancelled(true);
			return;
		}
		Block block = event.getBlock();
		Location location = block.getLocation();
		if (mapManager.containsCoords(scriptType, location)) {
			ScriptBlockBreakEvent breakEvent = new ScriptBlockBreakEvent(player, block);
			Bukkit.getPluginManager().callEvent(breakEvent);
			if (breakEvent.isCancelled()) {
				return;
			}
			if (!SBPermission.has(player, ScriptType.BREAK, false)) {
				Utils.sendMessage(player, SBConfig.getNotPermissionMessage());
				return;
			}
			new ScriptRead(this, player, location).read(0);
		}
	}
}