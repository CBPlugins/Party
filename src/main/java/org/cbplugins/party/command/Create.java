package org.cbplugins.party.command;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import org.cbplugins.party.Party;
import org.cbplugins.party.PartyManager;

public class Create extends SubCommand {
	
	public Create() {
		super(Party.getMessageManager().getRawString("Commands.Create.Help"), "", "create");
	}
	
	public void onCommand(ProxiedPlayer p, String[] args) {
		if(PartyManager.getParty(p) != null) {
			p.sendMessage(Party.getMessageManager().getString("Commands.Create.AlreadyInParty"));
			return;
		}
		
		PartyManager.createParty(p);
		
		p.sendMessage(Party.getMessageManager().getString("Commands.Create.Created"));
		return;
	}

}
