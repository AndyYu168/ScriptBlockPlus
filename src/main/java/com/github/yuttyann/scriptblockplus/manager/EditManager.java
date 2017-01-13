package com.github.yuttyann.scriptblockplus.manager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import com.github.yuttyann.scriptblockplus.file.Messages;
import com.github.yuttyann.scriptblockplus.file.Yaml;
import com.github.yuttyann.scriptblockplus.manager.MetadataManager.Edit;
import com.github.yuttyann.scriptblockplus.manager.OptionManager.ScriptType;
import com.github.yuttyann.scriptblockplus.util.BlockLocation;
import com.github.yuttyann.scriptblockplus.util.Utils;

public class EditManager {

	private ScriptType scriptType;
	private Yaml scriptFile;
	private String scriptPath;
	private List<String> scripts;
	private BlockLocation location;

	public EditManager(Yaml scripts, BlockLocation location) {
		this.scriptFile = scripts;
		this.location = location;
	}

	public ScriptType getScriptType() {
		if (scriptType == null) {
			if (scriptFile.getFile().getName().startsWith("interact")) {
				scriptType = ScriptType.INTERACT;
			} else {
				scriptType = ScriptType.WALK;
			}
		}
		return scriptType;
	}

	public String getScriptPath() {
		if (scriptPath == null) {
			scriptPath = location.getWorld().getName() + "." + location.getCoords();
		}
		return scriptPath;
	}

	public void save() {
		scriptFile.save();
	}

	public void scriptCopy(Player player) {
		MetadataManager.removeAllMetadata(player);
		if (!scriptFile.contains(getScriptPath() + ".Author")) {
			Utils.sendPluginMessage(player, Messages.getErrorScriptFileCheckMessage());
			return;
		}
		scripts = scriptFile.getStringList(getScriptPath() + ".Scripts");
		Edit.removeAllMetadata(player);
		Edit.setMetadata(player, getScriptType(), this);
		Utils.sendPluginMessage(player, Messages.getScriptCopyMessage(scriptType));
		Utils.sendPluginMessage(Messages.getConsoleScriptCopyMessage(player, scriptType, location.getWorld(), location.getCoords()));
	}

	public void scriptPaste(Player player, BlockLocation location) {
		MetadataManager.removeAllMetadata(player);
		String coords = location.getCoords();
		String scriptPath = location.getWorld().getName() + "." + coords;
		scriptFile.set(scriptPath + ".Author", player.getUniqueId().toString());
		scriptFile.set(scriptPath + ".LastEdit", Utils.getDateFormat("yyyy/MM/dd HH:mm:ss"));
		scriptFile.set(scriptPath + ".Scripts", new ArrayList<String>(scripts));
		scriptFile.save();
		String fullCoords = location.getFullCoords();
		switch (scriptType) {
		case INTERACT:
			if (!MapManager.getInteractCoords().contains(fullCoords)) {
				MapManager.getInteractCoords().add(fullCoords);
			}
			break;
		case WALK:
			if (!MapManager.getWalkCoords().contains(fullCoords)) {
				MapManager.getWalkCoords().add(fullCoords);
			}
			break;
		}
		Utils.sendPluginMessage(player, Messages.getScriptPasteMessage(scriptType));
		Utils.sendPluginMessage(Messages.getConsoleScriptPasteMessage(player, scriptType, location.getWorld(), coords));
	}

	//WorldEdit用に軽量化
	public void scriptWEPaste(Player player, BlockLocation location) {
		String scriptPath = location.getWorld().getName() + "." + location.getCoords();
		scriptFile.set(scriptPath + ".Author", player.getUniqueId().toString());
		scriptFile.set(scriptPath + ".LastEdit", Utils.getDateFormat("yyyy/MM/dd HH:mm:ss"));
		scriptFile.set(scriptPath + ".Scripts", new ArrayList<String>(scripts));
		String fullCoords = location.getFullCoords();
		switch (scriptType) {
		case INTERACT:
			if (!MapManager.getInteractCoords().contains(fullCoords)) {
				MapManager.getInteractCoords().add(fullCoords);
			}
			break;
		case WALK:
			if (!MapManager.getWalkCoords().contains(fullCoords)) {
				MapManager.getWalkCoords().add(fullCoords);
			}
			break;
		}
	}
}
