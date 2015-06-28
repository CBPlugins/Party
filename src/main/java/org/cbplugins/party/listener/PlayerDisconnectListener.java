package org.cbplugins.party.listener;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import org.cbplugins.party.Party;
import org.cbplugins.party.PartyManager;
import org.cbplugins.party.PlayerParty;

import java.util.Arrays;

public class PlayerDisconnectListener implements Listener {
	
	@EventHandler
	public void onPlayerDisconnect(PlayerDisconnectEvent e) {
		ProxiedPlayer player = e.getPlayer();
		
		if(PartyManager.getParty(player) != null) {
			PlayerParty party = PartyManager.getParty(player);
			if(party.isLeader(player)) {
				for(ProxiedPlayer p : party.getPlayers()) {
					p.sendMessage(Party.getMessageManager().getString("Party-Left-Leader"));
				}
				PartyManager.deleteParty(party);
			}else {
				party.removePlayer(player);
				for(ProxiedPlayer p : party.getPlayers()) {
					p.sendMessage(Party.getMessageManager().getString("Party-Left-Player", Arrays.asList("%player%"), Arrays.asList(player.getName())));
				}
				party.getLeader().sendMessage(Party.getMessageManager().getString("Party-Left-Player", Arrays.asList("%player%"), Arrays.asList(player.getName())));
			}
		}
	}

}
