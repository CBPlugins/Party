package org.cbplugins.party;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.plugin.Plugin;
import org.cbplugins.party.command.PartyCommand;
import org.cbplugins.party.config.Config;
import org.cbplugins.party.listener.PlayerChatListener;
import org.cbplugins.party.listener.PlayerDisconnectListener;
import org.cbplugins.party.listener.ServerSwitchListener;

public class Party extends Plugin {

	/**
	 * Instance of the plugin
	 */
	private static Party instance;

	/**
	 * Instance of the config file
	 */
	private static Config config;

	/**
	 * Instance of the message file
	 */
	private Config messages;

	/**
	 * Instance of the MessageManager
	 */
	private static MessageManager messageManager;

	/**
	 * Method will be called when the plugin is enabling
	 */
	@Override
	public void onEnable() {
		instance = this;
		config = new Config("config", this);
		messages = new Config("messages", this);
		messageManager = new MessageManager(messages);
		BungeeCord.getInstance().getPluginManager().registerCommand(this, new PartyCommand());
		BungeeCord.getInstance().getPluginManager().registerListener(this, new PlayerChatListener());
		BungeeCord.getInstance().getPluginManager().registerListener(this, new PlayerDisconnectListener());
		BungeeCord.getInstance().getPluginManager().registerListener(this, new ServerSwitchListener());
	}

	/**
	 * Method will be called when the plugin is disabling
	 */
	@Override
	public void onDisable() {
		
	}

	public static Config getConfig() {
		return config;
	}

	/**
	 * Getter for the instance of the plugin
	 * @return Instance of the plugin
	 */
	public static Party getInstance() {
		return instance;
	}

	/**
	 * Getter for the instance of the MessageManager
	 * @return Instance of the MessageManager
	 */
	public static MessageManager getMessageManager() {
		return messageManager;
	}
}
