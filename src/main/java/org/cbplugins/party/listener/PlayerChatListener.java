package org.cbplugins.party.listener;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import org.cbplugins.party.Party;
import org.cbplugins.party.PartyManager;
import org.cbplugins.party.PlayerParty;

import java.util.Arrays;

public class PlayerChatListener implements Listener {
	
	@EventHandler
	public void onPlayerChat(ChatEvent e) {
		if(!(e.getSender() instanceof ProxiedPlayer)) {
			return;
		}
		ProxiedPlayer player = (ProxiedPlayer) e.getSender();
		if(PartyManager.getParty(player) != null) {
			PlayerParty party = PartyManager.getParty(player);
			if(e.getMessage().startsWith("@party")) {
				e.setCancelled(true);
				for(ProxiedPlayer p : party.getPlayers()) {
					if(p != player) {
						p.sendMessage(Party.getMessageManager().getString("Party-Chat-Format", Arrays.asList("%player%", "%message%"), Arrays.asList(player.getName(), e.getMessage().replace("@party", ""))));
					}
				}
				if(party.getLeader() != player) {
					party.getLeader().sendMessage(Party.getMessageManager().getString("Party-Chat-Format", Arrays.asList("%player%", "%message%"), Arrays.asList(player.getName(), e.getMessage().replace("@party", ""))));
				}
				player.sendMessage(Party.getMessageManager().getString("Party-Chat-Format", Arrays.asList("%player%", "%message%"), Arrays.asList(player.getName(), e.getMessage().replace("@party", ""))));
			}
		}
	}

}
