package org.cbplugins.party.command;

import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import org.cbplugins.party.Party;
import org.cbplugins.party.PartyManager;
import org.cbplugins.party.PlayerParty;

import java.util.Arrays;

public class List extends SubCommand {
	
	public List() {
		super(Party.getMessageManager().getRawString("Commands.List.Help"), "", "list");
	}
	
	public void onCommand(ProxiedPlayer p, String[] args) {
		if(PartyManager.getParty(p) == null) {
			p.sendMessage(new TextComponent(Party.getMessageManager().getString("Commands.List.NoParty")));
			return;
		}
		
		PlayerParty party = PartyManager.getParty(p);
		
		String leader = Party.getMessageManager().getRawString("Commands.List.Leader", Arrays.asList("%leader%"), Arrays.asList(party.getLeader().getName()));
		String players = Party.getMessageManager().getRawString("Commands.List.Players", Arrays.asList("%player%"), Arrays.asList(""));
		
		if(!party.getPlayers().isEmpty()) {
			for(ProxiedPlayer pp : party.getPlayers()) {
				players += pp.getName() + ", ";
			}
			players = players.substring(0, players.lastIndexOf(", "));
		}else {
			players += "Empty";
		}
		
		
		p.sendMessage(new TextComponent(Party.getMessageManager().getRawString("Commands.List.Header")));
		
		p.sendMessage(new TextComponent(leader));
		p.sendMessage(new TextComponent(players));
		
		p.sendMessage(new TextComponent(Party.getMessageManager().getRawString("Commands.List.Footer")));
		return;
	}

}
