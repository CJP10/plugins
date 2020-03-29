package net.runelite.client.plugins.oneclick;

import java.util.HashMap;
import net.runelite.api.ItemID;
import net.runelite.api.MenuEntry;
import net.runelite.api.MenuOpcode;

public class Firemaking implements ClickMode
{

	private static final HashMap<Integer, String> LOG_IDS = new HashMap<>()
	{{
		put(ItemID.LOGS, "Logs");
		put(ItemID.ACHEY_TREE_LOGS, "Achey tree logs");
		put(ItemID.OAK_LOGS, "Oak logs");
		put(ItemID.WILLOW_LOGS, "Willow logs");
		put(ItemID.TEAK_LOGS, "Teak logs");
		put(ItemID.ARCTIC_PINE_LOGS, "Arctic pine logs");
		put(ItemID.MAPLE_LOGS, "Maple logs");
		put(ItemID.MAHOGANY_LOGS, "Mahogany logs");
		put(ItemID.YEW_LOGS, "Yew logs");
		put(ItemID.MAGIC_LOGS, "Magic logs");
		put(ItemID.REDWOOD_LOGS, "Redwood logs");
	}};
	private final OneClickPlugin plugin;

	Firemaking(OneClickPlugin plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean isEntryValid(MenuEntry entry)
	{
		return entry.getTarget() != null
			&& !entry.getTarget().isEmpty()
			&& entry.getMenuOpcode() == MenuOpcode.ITEM_USE
			&& LOG_IDS.containsKey(entry.getIdentifier())
			&& plugin.findItem(ItemID.TINDERBOX).isPresent();
	}

	@Override
	public void modifyEntry(MenuEntry entry)
	{
		var logName = LOG_IDS.get(entry.getIdentifier());
		if (logName != null)
		{
			entry.setTarget("<col=ff9040>Tinderbox<col=ffffff> -> <col=ff9040>" + logName);
			entry.setForceLeftClick(true);
		}
	}

	@Override
	public boolean isClickValid(MenuEntry entry)
	{
		return entry.getTarget() != null
			&& !entry.getTarget().isEmpty()
			&& entry.getMenuOpcode() == MenuOpcode.ITEM_USE
			&& LOG_IDS.containsKey(entry.getIdentifier())
			&& plugin.findItem(ItemID.TINDERBOX).isPresent();
	}

	@Override
	public void modifyClick(MenuEntry entry)
	{
		var logName = LOG_IDS.get(entry.getIdentifier());
		if (logName != null)
		{
			if (plugin.selectItem(ItemID.TINDERBOX))
			{
				entry.setTarget("<col=ff9040>Tinderbox<col=ffffff> -> <col=ff9040>" + logName);
				entry.setForceLeftClick(true);
				entry.setOpcode(MenuOpcode.ITEM_USE_ON_WIDGET_ITEM.getId());
			}

		}
	}
}
