package net.runelite.client.plugins.oneclick;

import net.runelite.api.ItemID;
import net.runelite.api.MenuEntry;
import net.runelite.api.MenuOpcode;

public class Fertilizer implements ClickMode
{
	private final OneClickPlugin plugin;

	Fertilizer(OneClickPlugin plugin)
	{
		this.plugin = plugin;
	}

	@Override
	public boolean isEntryValid(MenuEntry entry)
	{
		return entry.getTarget() != null
			&& !entry.getTarget().isEmpty()
			&& entry.getMenuOpcode() == MenuOpcode.ITEM_USE
			&& (plugin.findItem(ItemID.SALTPETRE).isPresent() || plugin.findItem(ItemID.COMPOST).isPresent());
	}

	@Override
	public void modifyEntry(MenuEntry entry)
	{
		if (entry.getIdentifier() == ItemID.COMPOST)
		{
			entry.setTarget("<col=ff9040>Saltpetre<col=ffffff> -> <col=ff9040>Compost");
			entry.setForceLeftClick(true);
		}
		else if (entry.getIdentifier() == ItemID.SALTPETRE)
		{
			entry.setTarget("<col=ff9040>Compost<col=ffffff> -> <col=ff9040>Saltpetre");
			entry.setForceLeftClick(true);
		}
	}

	@Override
	public boolean isClickValid(MenuEntry entry)
	{
		return entry.getTarget() != null
			&& !entry.getTarget().isEmpty()
			&& entry.getMenuOpcode() == MenuOpcode.ITEM_USE
			&& (plugin.findItem(ItemID.GRAPES).isPresent() || plugin.findItem(ItemID.JUG_OF_WATER).isPresent());
	}

	@Override
	public void modifyClick(MenuEntry entry)
	{
		if (entry.getIdentifier() == ItemID.COMPOST)
		{
			if (plugin.selectItem(ItemID.SALTPETRE))
			{
				entry.setTarget("<col=ff9040>Saltpetre<col=ffffff> -> <col=ff9040>Compost");
				entry.setForceLeftClick(true);
				entry.setOpcode(MenuOpcode.ITEM_USE_ON_WIDGET_ITEM.getId());
			}
		}
		else if (entry.getIdentifier() == ItemID.SALTPETRE)
		{
			if (plugin.selectItem(ItemID.COMPOST))
			{
				entry.setTarget("<col=ff9040>Compost<col=ffffff> -> <col=ff9040>Saltpetre");
				entry.setForceLeftClick(true);
				entry.setOpcode(MenuOpcode.ITEM_USE_ON_WIDGET_ITEM.getId());
			}
		}
	}
}
