package com.github.yuttyann.scriptblockplus.script.option.time;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import com.github.yuttyann.scriptblockplus.ScriptBlock;
import com.github.yuttyann.scriptblockplus.script.ScriptType;

public final class TimeData {

	protected int second;

	private final int scriptIndex;
	private final boolean isOldCooldown;
	private final UUID uuid;
	private final String fullCoords;
	private final ScriptType scriptType;

	TimeData(int second, int scriptIndex, String fullCoords) {
		this(second, scriptIndex, true, fullCoords, null, null);
	}

	TimeData(int second, int scriptIndex, String fullCoords, UUID uuid, ScriptType scriptType) {
		this(second, scriptIndex, false, fullCoords, uuid, scriptType);
	}

	private TimeData(int second, int scriptIndex, boolean isOldCooldown, String fullCoords, UUID uuid, ScriptType scriptType) {
		this.second = second;
		this.scriptIndex = scriptIndex;
		this.isOldCooldown = isOldCooldown;
		this.fullCoords = fullCoords;
		this.uuid = uuid;
		this.scriptType = scriptType;
	}

	public int getSecond() {
		return second;
	}

	public int getScriptIndex() {
		return scriptIndex;
	}

	public boolean isOldCooldown() {
		return isOldCooldown;
	}

	public UUID getUniqueId() {
		return uuid;
	}

	public String getFullCoords() {
		return fullCoords;
	}

	public ScriptType getScriptType() {
		return scriptType;
	}

	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("second", second);
		map.put("scriptindex", scriptIndex);
		map.put("isoldcooldown", isOldCooldown);
		map.put("fullcoords", fullCoords);
		if (!isOldCooldown) {
			map.put("uuid", uuid);
			map.put("scripttype", scriptType);
		}
		return map;
	}

	public static void deserialize(Map<String, Object> args) {
		int second = (int) args.get("second");
		int scriptIndex = (int) args.get("scriptindex");
		boolean isOldCooldown = (boolean) args.get("isoldcooldown");
		String fullCoords = (String) args.get("fullcoords");
		UUID uuid = (UUID) args.get("uuid");
		ScriptType scriptType = (ScriptType) args.get("scripttype");
		TimeData timeData = new TimeData(second, scriptIndex, isOldCooldown, fullCoords, uuid, scriptType);
		if (timeData.isOldCooldown) {
			new OldCooldown().deserialize(timeData);
		} else {
			new Cooldown().deserialize(timeData);
		}
	}

	@Override
	public int hashCode() {
		return hashCode(scriptIndex, isOldCooldown, fullCoords, uuid, scriptType);
	}

	static int hashCode(int scriptIndex, boolean isOldCooldown, String fullCoords, UUID uuid, ScriptType scriptType) {
		int hash = 1;
		hash = hash * 31 + Integer.hashCode(scriptIndex);
		hash = hash * 31 + Boolean.hashCode(isOldCooldown);
		hash = hash * 31 + Objects.requireNonNull(fullCoords).hashCode();
		hash = uuid == null ? hash : hash * 31 + uuid.hashCode();
		hash = scriptType == null ? hash : hash * 31 + scriptType.hashCode();
		return hash;
	}

	static int getSecond(int hash) {
		for (TimeData timeData : ScriptBlock.getInstance().getMapManager().getCooldowns()) {
			if (hash == timeData.hashCode()) {
				return timeData.second;
			}
		}
		return -1;
	}
}