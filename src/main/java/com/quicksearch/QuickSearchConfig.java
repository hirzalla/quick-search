package com.quicksearch;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("quicksearch")
public interface QuickSearchConfig extends Config {
	@ConfigItem(
			keyName = "enableWiki",
			name = "Enable Wiki Search",
			description = "Enables OSRS Wiki search (!osrs or !wiki)"
	)
	default boolean enableWiki() {
		return true;
	}

	@ConfigItem(
			keyName = "enableYoutube",
			name = "Enable YouTube Search",
			description = "Enables YouTube search (!yt or !youtube)"
	)
	default boolean enableYoutube() {
		return true;
	}

	@ConfigItem(
			keyName = "enableKick",
			name = "Enable Kick Search",
			description = "Enables Kick search (!kick)"
	)
	default boolean enableKick() {
		return true;
	}

	@ConfigItem(
			keyName = "enableTwitch",
			name = "Enable Twitch Search",
			description = "Enables Twitch search (!twitch or !ttv)"
	)
	default boolean enableTwitch() {
		return true;
	}
}