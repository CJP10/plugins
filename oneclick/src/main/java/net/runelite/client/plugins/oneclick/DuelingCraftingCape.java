package net.runelite.client.plugins.oneclick;

import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.InventoryID;
import net.runelite.api.ItemID;
import net.runelite.api.MenuEntry;
import net.runelite.api.kit.KitType;

@Slf4j
public class DuelingCraftingCape implements ClickMode
{
	static List<Integer> RINGS = Arrays.asList(ItemID.RING_OF_DUELING1, ItemID.RING_OF_DUELING2, ItemID.RING_OF_DUELING3, ItemID.RING_OF_DUELING4, ItemID.RING_OF_DUELING5, ItemID.RING_OF_DUELING6, ItemID.RING_OF_DUELING7, ItemID.RING_OF_DUELING8);

	private final Client client;

	public DuelingCraftingCape(OneClickPlugin oneClickPlugin)
	{
		this.client = oneClickPlugin.getClient();
	}

	@Override
	public boolean isEntryValid(MenuEntry entry)
	{
		return false;
	}

	@Override
	public void modifyEntry(MenuEntry entry)
	{

	}

	@Override
	public boolean isClickValid(MenuEntry entry)
	{
		return entry.getTarget().contains("Crafting cape") && entry.getOpcode() == 57 && entry.getOption().equals("Teleport");
	}

	@Override
	public void modifyClick(MenuEntry entry)
	{
		var itemContainer = client.getItemContainer(InventoryID.EQUIPMENT);
		if (client.getLocalPlayer() != null && itemContainer != null)
		{
			var ring = itemContainer.getItem(KitType.RING.getIndex());
			if (client.getLocalPlayer().getWorldLocation().getRegionID() == 11571 && ring != null)
			{
				if (RINGS.contains(ring.getId()))
				{
					entry.setOption("Duel Arena");
					entry.setIdentifier(2);
					entry.setOpcode(57);
					entry.setParam0(-1);
					entry.setParam1(25362455);
					entry.setForceLeftClick(false);
				}
			}
		}
	}
}
