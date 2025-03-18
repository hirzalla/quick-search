package com.quicksearch;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("quicksearch")
public interface QuickSearchConfig extends Config {
	@ConfigItem(
		keyName = "enableWiki",
		name = "Enable Wiki Search",
		description = "Toggle OSRS Wiki search on or off"
	)
	default boolean enableWiki() {
		return true;
	}

	@ConfigItem(
		keyName = "enableYoutube",
		name = "Enable YouTube Search",
		description = "Toggle YouTube search on or off"
	)
	default boolean enableYoutube() {
		return true;
	}

	@ConfigItem(
		keyName = "enableKick",
		name = "Enable Kick Search",
		description = "Toggle Kick search on or off"
	)
	default boolean enableKick() {
		return true;
	}

	@ConfigItem(
		keyName = "enableTwitch",
		name = "Enable Twitch Search",
		description = "Toggle Twitch search on or off"
	)
	default boolean enableTwitch() {
		return true;
	}
}