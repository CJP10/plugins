package net.runelite.client.plugins.oneclick;

import net.runelite.api.ItemID;
import net.runelite.api.MenuEntry;
import net.runelite.api.MenuOpcode;

public class Wines implements ClickMode
{

	private final OneClickPlugin plugin;

	Wines(OneClickPlugin plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean isEntryValid(MenuEntry entry)
	{
		return entry.getTarget() != null
			&& !entry.getTarget().isEmpty()
			&& entry.getMenuOpcode() == MenuOpcode.ITEM_USE
			&& plugin.findItem(ItemID.GRAPES).isPresent()
			&& plugin.findItem(ItemID.JUG_OF_WATER).isPresent();
	}

	@Override
	public void modifyEntry(MenuEntry entry)
	{
		if (entry.getIdentifier() == ItemID.GRAPES)
		{
			entry.setTarget("<col=ff9040>Jug of water<col=ffffff> -> <col=ff9040>Grapes");
			entry.setForceLeftClick(true);
		}
		else if (entry.getIdentifier() == ItemID.JUG_OF_WATER)
		{
			entry.setTarget("<col=ff9040>Grapes<col=ffffff> -> <col=ff9040>Jug of water");
			entry.setForceLeftClick(true);
		}
	}

	@Override
	public boolean isClickValid(MenuEntry entry)
	{
		return entry.getTarget() != null
			&& !entry.getTarget().isEmpty()
			&& entry.getMenuOpcode() == MenuOpcode.ITEM_USE
			&& plugin.findItem(ItemID.GRAPES).isPresent()
			&& plugin.findItem(ItemID.JUG_OF_WATER).isPresent();
	}

	@Override
	public void modifyClick(MenuEntry entry)
	{
		if (entry.getIdentifier() == ItemID.GRAPES)
		{
			if (plugin.selectItem(ItemID.JUG_OF_WATER))
			{
				entry.setTarget("<col=ff9040>Jug of water<col=ffffff> -> <col=ff9040>Grapes");
				entry.setForceLeftClick(true);
				entry.setOpcode(MenuOpcode.ITEM_USE_ON_WIDGET_ITEM.getId());
			}
		}
		else if (entry.getIdentifier() == ItemID.JUG_OF_WATER)
		{
			if (plugin.selectItem(ItemID.GRAPES))
			{
				entry.setTarget("<col=ff9040>Grapes<col=ffffff> -> <col=ff9040>Jug of water");
				entry.setForceLeftClick(true);
				entry.setOpcode(MenuOpcode.ITEM_USE_ON_WIDGET_ITEM.getId());
			}
		}
	}
}
