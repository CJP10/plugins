package net.runelite.client.plugins.oneclick;

import com.google.inject.Inject;
import com.google.inject.Provides;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import net.runelite.api.Client;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.PluginType;
import org.apache.commons.lang3.tuple.Pair;
import org.pf4j.Extension;

@Extension
@PluginDescriptor(
	name = "One Click",
	enabledByDefault = false,
	description = "Do actions in one click",
	tags = {"one", "click"},
	type = PluginType.UTILITY
)
public class OneClickPlugin extends Plugin
{

	@Inject
	private Client client;

	@Inject
	private OneClickConfig config;
	private ArrayList<ClickMode> modes = new ArrayList<>();

	@Provides
	OneClickConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(OneClickConfig.class);
	}

	@Override
	protected void startUp()
	{
		updateConfig();
	}

	@Subscribe
	private void onConfigChanged(ConfigChanged event)
	{
		updateConfig();
	}

	@Subscribe
	public void onMenuEntryAdded(MenuEntryAdded event)
	{
		for (ClickMode mode : modes)
		{
			if (mode.isEntryValid(event))
			{
				mode.modifyEntry(event);
				event.setModified();
				break;
			}
		}
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked event)
	{
		for (ClickMode mode : modes)
		{
			if (mode.isClickValid(event))
			{
				mode.modifyClick(event);
				break;
			}
		}
	}

	private void updateConfig()
	{
		modes.clear();

		if (config.darts())
		{
			modes.add(new Darts(this));
		}

		if (config.firemaking())
		{
			modes.add(new Firemaking(this));
		}

		if (config.wines())
		{
			modes.add(new Wines(this));
		}

		if (config.fertilizer())
		{
			modes.add(new Fertilizer(this));
		}

		if (config.herbTar())
		{
			modes.add(new HerbTar(this));
		}
	}

	public Optional<Pair<Integer, Integer>> findItem(int id)
	{
		final Widget inventoryWidget = client.getWidget(WidgetInfo.INVENTORY);
		final List<WidgetItem> itemList = (List<WidgetItem>) inventoryWidget.getWidgetItems();

		for (int i = itemList.size() - 1; i >= 0; i--)
		{
			final WidgetItem item = itemList.get(i);
			if (item.getId() == id)
			{
				return Optional.of(Pair.of(item.getId(), item.getIndex()));
			}
		}

		return Optional.empty();
	}

	public boolean selectItem(int id)
	{
		var item = findItem(id);
		if (item.isPresent())
		{
			var index = item.get().getRight();
			client.setSelectedItemWidget(WidgetInfo.INVENTORY.getId());
			client.setSelectedItemSlot(index);
			client.setSelectedItemID(id);
			return true;
		}

		return false;
	}
}