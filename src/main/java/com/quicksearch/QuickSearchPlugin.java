package com.quicksearch;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.api.events.ChatMessage;
import net.runelite.client.util.LinkBrowser;
import java.util.Map;
import java.util.HashMap;

@Slf4j
@PluginDescriptor(
		name = "Quick Search",
		description = "Use in-game commands to search various platforms",
		tags = {"search", "quick", "wiki", "osrs", "youtube", "yt", "kick", "twitch"}
)
public class QuickSearchPlugin extends Plugin {
	private static final String COMMAND_PREFIX = "search";
	private static final Map<String, String> URL_PATTERNS = new HashMap<>();

	@Inject
	private Client client;

	@Inject
	private QuickSearchConfig config;

	@Override
	protected void startUp() {
		initializeUrlPatterns();
	}

	private void initializeUrlPatterns() {
		// Wiki
		URL_PATTERNS.put("wiki", "https://oldschool.runescape.wiki/w/%s");
		URL_PATTERNS.put("osrs", "https://oldschool.runescape.wiki/w/%s");
		// YouTube
		URL_PATTERNS.put("youtube", "https://www.youtube.com/results?search_query=%s");
		URL_PATTERNS.put("yt", "https://www.youtube.com/results?search_query=%s");
		// Twitch
		URL_PATTERNS.put("twitch", "https://www.twitch.tv/search?term=%s");
		URL_PATTERNS.put("ttv", "https://www.twitch.tv/search?term=%s");
		// Kick
		URL_PATTERNS.put("kick", "https://kick.com/search?query=%s");
		// Reddit
		// URL_PATTERNS.put("reddit", "https://www.reddit.com/search/?q=%s");
		// URL_PATTERNS.put("r", "https://www.reddit.com/search/?q=%s");
	}

	@Subscribe
	public void onChatMessage(ChatMessage event) {
		String message = event.getMessage();
		if (!message.toLowerCase().startsWith("!" + COMMAND_PREFIX + " ")) {
			return;
		}

		String[] parts = message.split(" ", 3);
		if (parts.length < 3) {
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Usage: !search {{ platform }} {{ query }}", null);
			return;
		}

		String platform = parts[1].toLowerCase();
		String query = parts[2];

		if (!URL_PATTERNS.containsKey(platform)) {
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Unsupported platform", null);
			return;
		}

		if (!isPlatformEnabled(platform)) {
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", platform + " search is disabled", null);
			return;
		}

		String separator = (platform.equals("wiki") || platform.equals("osrs")) ? "_" : "+";
		String url = String.format(URL_PATTERNS.get(platform), query.replace(" ", separator));

		LinkBrowser.browse(url);
	}

	private boolean isPlatformEnabled(String platform) {
		switch (platform) {
			case "wiki":
			case "osrs":
				return config.enableWiki();
			case "youtube":
			case "yt":
				return config.enableYoutube();
			case "kick":
				return config.enableKick();
			case "twitch":
			case "ttv":
				return config.enableTwitch();
			default:
				return false;
		}
	}

	@Provides
	QuickSearchConfig provideConfig(ConfigManager configManager) {
		return configManager.getConfig(QuickSearchConfig.class);
	}
}