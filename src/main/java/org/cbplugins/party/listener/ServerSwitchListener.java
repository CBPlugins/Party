package org.cbplugins.party.listener;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import org.cbplugins.party.Party;
import org.cbplugins.party.PartyManager;
import org.cbplugins.party.PlayerParty;

import java.util.Arrays;

public class ServerSwitchListener implements Listener {
	
	@EventHandler
	public void onServerSwitch(ServerSwitchEvent e) {
		ProxiedPlayer player = e.getPlayer();
		if(PartyManager.getParty(player) != null) {
			PlayerParty party = PartyManager.getParty(player);
			if(party.isLeader(player)) {
				if(Party.getConfig().getConfig().getStringList("ExcludedServers").contains(party.getServer().getName())) return;
				for(ProxiedPlayer p : party.getPlayers()) {
					p.connect(party.getServer());
					p.sendMessage(Party.getMessageManager().getString("Party-Switch-Server", Arrays.asList("%server%"), Arrays.asList(party.getServer().getName())));
				}
				player.sendMessage(Party.getMessageManager().getString("Party-Switch-Server", Arrays.asList("%server%"), Arrays.asList(party.getServer().getName())));
			}
		}
	}

}
