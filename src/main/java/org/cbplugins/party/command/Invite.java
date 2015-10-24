package org.cbplugins.party.command;

import org.cbplugins.party.Party;
import org.cbplugins.party.PartyManager;
import org.cbplugins.party.PlayerParty;

import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.Arrays;

public class Invite extends SubCommand {
	
	public Invite() {
		super(Party.getMessageManager().getRawString("Commands.Invite.Help"), Party.getMessageManager().getRawString("Commands.Invite.Usage"), "invite");
	}
	
	public void onCommand(ProxiedPlayer p, String[] args) {
		if(args.length == 0) {
			p.sendMessage(Party.getMessageManager().getString("Commands.Invite.NoPlayer"));
			return;
		}
		
		if(PartyManager.getParty(p) == null) {
			p.sendMessage(Party.getMessageManager().getString("Commands.Invite.NoParty"));
			return;
		}
		
		PlayerParty party = PartyManager.getParty(p);
		
		if(!party.isLeader(p)) {
			p.sendMessage(Party.getMessageManager().getString("Commands.Invite.NotLeader"));
			return;
		}
		
		ProxiedPlayer pl = Party.getInstance().getProxy().getPlayer(args[0]);
		
		if(pl == null) {
			p.sendMessage(Party.getMessageManager().getString("Commands.Invite.NotOnline"));
			return;
		}
		
		party.invite(pl);
		
		p.sendMessage(Party.getMessageManager().getString("Commands.Invite.Invited", Arrays.asList("%player%"), Arrays.asList(pl.getName())));
	}

}
