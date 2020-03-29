package net.runelite.client.plugins.oneclick;

import java.util.HashMap;
import net.runelite.api.ItemID;
import net.runelite.api.MenuEntry;
import net.runelite.api.MenuOpcode;

public class Darts implements ClickMode
{

	private static final HashMap<Integer, String> DART_IDS = new HashMap<>()
	{{
		put(ItemID.BRONZE_DART_TIP, "Bronze dart tip");
		put(ItemID.IRON_DART_TIP, "Iron dart tip");
		put(ItemID.STEEL_DART_TIP, "Steel dart tip");
		put(ItemID.MITHRIL_DART_TIP, "Mithril dart tip");
		put(ItemID.ADAMANT_DART_TIP, "Adamant dart tip");
		put(ItemID.RUNE_DART_TIP, "Rune dart tip");
		put(ItemID.DRAGON_DART_TIP, "Dragon dart tip");
	}};
	private final OneClickPlugin plugin;

	Darts(OneClickPlugin plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean isEntryValid(MenuEntry entry)
	{
		return entry.getTarget() != null
			&& !entry.getTarget().isEmpty()
			&& entry.getMenuOpcode() == MenuOpcode.ITEM_USE
			&& DART_IDS.containsKey(entry.getIdentifier())
			&& plugin.findItem(ItemID.FEATHER).isPresent();
	}

	@Override
	public void modifyEntry(MenuEntry entry)
	{
		var dartName = DART_IDS.get(entry.getIdentifier());
		if (dartName != null)
		{
			entry.setTarget("<col=ff9040>Feather<col=ffffff> -> <col=ff9040>" + dartName);
			entry.setForceLeftClick(true);
		}
	}

	@Override
	public boolean isClickValid(MenuEntry entry)
	{
		return entry.getTarget() != null
			&& !entry.getTarget().isEmpty()
			&& entry.getMenuOpcode() == MenuOpcode.ITEM_USE
			&& DART_IDS.containsKey(entry.getIdentifier())
			&& plugin.findItem(ItemID.FEATHER).isPresent();
	}

	@Override
	public void modifyClick(MenuEntry entry)
	{
		var dartName = DART_IDS.get(entry.getIdentifier());
		if (dartName != null)
		{
			if (plugin.selectItem(ItemID.FEATHER))
			{
				entry.setTarget("<col=ff9040>Feather<col=ffffff> -> <col=ff9040>" + dartName);
				entry.setForceLeftClick(true);
				entry.setOpcode(MenuOpcode.ITEM_USE_ON_WIDGET_ITEM.getId());
			}
		}
	}
}
