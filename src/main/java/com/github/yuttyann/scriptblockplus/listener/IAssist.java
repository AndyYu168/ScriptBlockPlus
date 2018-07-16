package com.github.yuttyann.scriptblockplus.listener;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import com.github.yuttyann.scriptblockplus.ScriptBlock;
import com.github.yuttyann.scriptblockplus.enums.ScriptType;
import com.github.yuttyann.scriptblockplus.manager.MapManager;

public class IAssist implements Listener {

	protected final Plugin plugin;
	protected final ScriptType scriptType;
	protected final MapManager mapManager;

	public IAssist(IAssist iAssist) {
		this.plugin = iAssist.getPlugin();
		this.scriptType = iAssist.getScriptType();
		this.mapManager = iAssist.getMapManager();
	}

	public IAssist(ScriptBlock plugin, ScriptType scriptType) {
		this.plugin = plugin;
		this.scriptType = scriptType;
		this.mapManager = plugin == null ? null : plugin.getMapManager();
	}

	public Plugin getPlugin() {
		return plugin;
	}

	public ScriptType getScriptType() {
		return scriptType;
	}

	public MapManager getMapManager() {
		return mapManager;
	}
}