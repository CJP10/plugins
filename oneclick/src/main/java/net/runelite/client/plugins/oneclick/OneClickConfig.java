package net.runelite.client.plugins.oneclick;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("oneclick")
public interface OneClickConfig extends Config
{
	@ConfigItem(
		position = 0,
		keyName = "darts",
		name = "Darts",
		description = "Enable one click dart making!"
	)
	default boolean darts()
	{
		return false;
	}

	@ConfigItem(
		position = 1,
		keyName = "firemaking",
		name = "Fireamking",
		description = "Enable one click log lighting!"
	)
	default boolean firemaking()
	{
		return false;
	}

	@ConfigItem(
		position = 2,
		keyName = "wines",
		name = "Wines",
		description = "Enable one click wine making!"
	)
	default boolean wines()
	{
		return false;
	}
}
