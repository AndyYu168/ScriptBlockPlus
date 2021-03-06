package com.github.yuttyann.scriptblockplus.script.option.vault;

import org.bukkit.entity.Player;

import com.github.yuttyann.scriptblockplus.file.SBConfig;
import com.github.yuttyann.scriptblockplus.player.PlayerData;
import com.github.yuttyann.scriptblockplus.script.ScriptRead;
import com.github.yuttyann.scriptblockplus.script.hook.HookPlugins;
import com.github.yuttyann.scriptblockplus.script.hook.VaultEconomy;
import com.github.yuttyann.scriptblockplus.script.option.BaseOption;
import com.github.yuttyann.scriptblockplus.script.option.Option;
import com.github.yuttyann.scriptblockplus.utils.Utils;

public class MoneyCost extends BaseOption {

	public static final String KEY_COST = PlayerData.createRandomId("MoneyCost");

	public MoneyCost() {
		super("moneycost", "$cost:");
	}

	@Override
	public Option newInstance() {
		return new MoneyCost();
	}

	@Override
	protected boolean isValid() throws Exception {
		VaultEconomy vaultEconomy = HookPlugins.getVaultEconomy();
		if (!vaultEconomy.isEnabled()) {
			throw new UnsupportedOperationException();
		}
		Player player = getPlayer();
		double cost = Double.parseDouble(getOptionValue());
		if (vaultEconomy.has(player, cost)) {
			vaultEconomy.withdrawPlayer(player, cost);

			ScriptRead scriptRead = getScriptRead();
			scriptRead.put(KEY_COST, cost + (scriptRead.has(KEY_COST) ? scriptRead.getDouble(KEY_COST) : 0.0D));
			return true;
		}
		double result = cost - vaultEconomy.getBalance(player);
		Utils.sendMessage(player, SBConfig.getErrorCostMessage(cost, result));
		return false;
	}
}