package net.runelite.client.plugins.oneclick;

import java.util.HashMap;
import net.runelite.api.ItemID;
import net.runelite.api.MenuEntry;
import net.runelite.api.MenuOpcode;

public class HerbTar implements ClickMode
{

	private static final HashMap<Integer, String> HERB_IDS = new HashMap<>()
	{{
		put(ItemID.GUAM_LEAF, "Guam leaf");
		put(ItemID.HARRALANDER, "Harralander");
		put(ItemID.MARRENTILL, "Marrentill");
		put(ItemID.TARROMIN, "Tarromin");
	}};
	private final OneClickPlugin plugin;

	HerbTar(OneClickPlugin plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean isEntryValid(MenuEntry entry)
	{
		return entry.getTarget() != null
			&& !entry.getTarget().isEmpty()
			&& entry.getMenuOpcode() == MenuOpcode.ITEM_USE
			&& HERB_IDS.containsKey(entry.getIdentifier())
			&& plugin.findItem(ItemID.SWAMP_TAR).isPresent();
	}

	@Override
	public void modifyEntry(MenuEntry entry)
	{
		var dartName = HERB_IDS.get(entry.getIdentifier());
		if (dartName != null)
		{
			entry.setTarget("<col=ff9040>Swamp tar<col=ffffff> -> <col=ff9040>" + dartName);
			entry.setForceLeftClick(true);
		}
	}

	@Override
	public boolean isClickValid(MenuEntry entry)
	{
		return entry.getTarget() != null
			&& !entry.getTarget().isEmpty()
			&& entry.getMenuOpcode() == MenuOpcode.ITEM_USE
			&& HERB_IDS.containsKey(entry.getIdentifier())
			&& plugin.findItem(ItemID.SWAMP_TAR).isPresent();
	}

	@Override
	public void modifyClick(MenuEntry entry)
	{
		var dartName = HERB_IDS.get(entry.getIdentifier());
		if (dartName != null)
		{
			if (plugin.selectItem(ItemID.SWAMP_TAR))
			{
				entry.setTarget("<col=ff9040>Swamp tar<col=ffffff> -> <col=ff9040>" + dartName);
				entry.setForceLeftClick(true);
				entry.setOpcode(MenuOpcode.ITEM_USE_ON_WIDGET_ITEM.getId());
			}
		}
	}
}
