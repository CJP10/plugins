package net.runelite.client.plugins.oneclick;

import net.runelite.api.MenuEntry;

public interface ClickMode
{
	boolean isEntryValid(MenuEntry entry);

	void modifyEntry(MenuEntry entry);

	boolean isClickValid(MenuEntry entry);

	void modifyClick(MenuEntry entry);
}
