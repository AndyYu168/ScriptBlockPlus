package com.github.yuttyann.scriptblockplus.player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.github.yuttyann.scriptblockplus.region.CuboidRegion;
import com.github.yuttyann.scriptblockplus.region.Region;
import com.github.yuttyann.scriptblockplus.script.SBClipboard;

public abstract class PlayerData implements SBPlayer {

	private static final String KEY_REGION = createRandomId("CuboidRegion");
	private static final String KEY_CLIPBOARD = createRandomId("ClipBoard");
	private static final String KEY_SCRIPTLINE = createRandomId("ScriptLine");
	private static final String KEY_CLICKACTION = createRandomId("ClickAction");
	private static final String KEY_OLDFULLCOORDS = createRandomId("OldFullCoords");


	private final ObjectMap objectMap = new ObjMap();

	PlayerData() {}

	@Override
	public Region getRegion() {
		CuboidRegion region = getObjectMap().get(KEY_REGION);
		if (region == null) {
			getObjectMap().put(KEY_REGION, region = new CuboidRegion());
		}
		return region;
	}

	@Override
	public ObjectMap getObjectMap() {
		return objectMap;
	}

	private class ObjMap implements ObjectMap {

		private final Map<String, Object> objectMap;

		private ObjMap() {
			this.objectMap = new HashMap<>();
		}

		@Override
		public void put(String key, Object value) {
			objectMap.put(key, value);
		}

		@Override
		public byte getByte(String key) {
			return get(key, Byte.class);
		}

		@Override
		public short getShort(String key) {
			return get(key, Short.class);
		}

		@Override
		public int getInt(String key) {
			return get(key, Integer.class);
		}

		@Override
		public long getLong(String key) {
			return get(key, Byte.class);
		}

		@Override
		public char getChar(String key) {
			return get(key, Character.class);
		}

		@Override
		public float getFloat(String key) {
			return get(key, Float.class);
		}

		@Override
		public double getDouble(String key) {
			return get(key, Double.class);
		}

		@Override
		public boolean getBoolean(String key) {
			return get(key, Boolean.class);
		}

		@Override
		public String getString(String key) {
			return get(key, String.class);
		}

		@Override
		public <T> T get(String key) {
			return (T) objectMap.get(key);
		}

		@Override
		public <T> T get(String key, Class<T> classOfT) {
			return classOfT == null ? get(key) : classOfT.cast(get(key));
		}

		@Override
		public <T> T remove(String key) {
			return (T) objectMap.remove(key);
		}

		@Override
		public <T> T remove(String key, Class<T> classOfT) {
			return classOfT == null ? remove(key) : classOfT.cast(remove(key));
		}

		@Override
		public boolean has(String key) {
			return get(key) != null;
		}

		@Override
		public boolean containsKey(String key) {
			return objectMap.containsKey(key);
		}

		@Override
		public boolean containsValue(Object value) {
			return objectMap.containsValue(value);
		}

		@Override
		public void clear() {
			objectMap.clear();
		}
	}

	@Override
	public void setClipboard(SBClipboard clipboard) {
		getObjectMap().put(KEY_CLIPBOARD, clipboard);
	}

	@Override
	public SBClipboard getClipboard() {
		return getObjectMap().get(KEY_CLIPBOARD);
	}

	@Override
	public boolean hasClipboard() {
		return getObjectMap().has(KEY_CLIPBOARD);
	}

	@Override
	public void setScriptLine(String scriptLine) {
		getObjectMap().put(KEY_SCRIPTLINE, scriptLine);
	}

	@Override
	public String getScriptLine() {
		return getObjectMap().getString(KEY_SCRIPTLINE);
	}

	@Override
	public boolean hasScriptLine() {
		return getObjectMap().has(KEY_SCRIPTLINE);
	}

	@Override
	public void setActionType(String actionType) {
		getObjectMap().put(KEY_CLICKACTION, actionType);
	}

	@Override
	public String getActionType() {
		return getObjectMap().getString(KEY_CLICKACTION);
	}

	@Override
	public boolean hasActionType() {
		return getObjectMap().has(KEY_CLICKACTION);
	}

	@Override
	public void setOldFullCoords(String fullCoords) {
		getObjectMap().put(KEY_OLDFULLCOORDS, fullCoords);
	}

	@Override
	public String getOldFullCoords() {
		return getObjectMap().getString(KEY_OLDFULLCOORDS);
	}

	@Override
	public boolean hasOldFullCoords() {
		return getObjectMap().has(KEY_OLDFULLCOORDS);
	}

	public static String createRandomId(String key) {
		return key + "_" + UUID.randomUUID();
	}
}