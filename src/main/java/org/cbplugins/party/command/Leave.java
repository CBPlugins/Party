package org.cbplugins.party.command;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import org.cbplugins.party.Party;
import org.cbplugins.party.PartyManager;
import org.cbplugins.party.PlayerParty;

import java.util.Arrays;

public class Leave extends SubCommand {
	
	public Leave() {
		super(Party.getMessageManager().getRawString("Commands.Leave.Help"), "", "leave");
	}
	
	public void onCommand(ProxiedPlayer p, String[] args) {
		if(PartyManager.getParty(p) == null) {
			p.sendMessage(Party.getMessageManager().getString("Commands.Leave.NoParty"));
			return;
		}
		
		PlayerParty party = PartyManager.getParty(p);
		
		if(party.isLeader(p)) {
			for(ProxiedPlayer pp : party.getPlayers()) {
				pp.sendMessage(Party.getMessageManager().getString("Commands.Leave.Dissolved"));
			}
			p.sendMessage(Party.getMessageManager().getString("Commands.Leave.Dissolve"));
			PartyManager.deleteParty(party);
			return;
		}else {
			if(party.removePlayer(p)) {
				p.sendMessage(Party.getMessageManager().getString("Commands.Leave.Leave"));
				for(ProxiedPlayer pp : party.getPlayers()) {
					pp.sendMessage(Party.getMessageManager().getString("Commands.Leave.Left", Arrays.asList("%player%"), Arrays.asList(p.getName())));
				}
				party.getLeader().sendMessage(Party.getMessageManager().getString("Commands.Leave.Left", Arrays.asList("%player%"), Arrays.asList(p.getName())));
				return;
			}else {
				p.sendMessage(Party.getMessageManager().getString("Commands.Leave.Error"));
				return;
			}
		}
	}

}
