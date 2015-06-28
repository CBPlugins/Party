package org.cbplugins.party.command;

import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import org.cbplugins.party.Party;
import org.cbplugins.party.PartyManager;
import org.cbplugins.party.PlayerParty;

import java.util.Arrays;

public class Kick extends SubCommand {
	
	public Kick() {
		super(Party.getMessageManager().getRawString("Commands.Kick.Help"), Party.getMessageManager().getRawString("Commands.Kick.Usage"), "kick");
	}
	
	public void onCommand(ProxiedPlayer p, String[] args) {
		if(args.length == 0) {
			p.sendMessage(Party.getMessageManager().getString("Commands.Kick.NoPlayer"));
			return;
		}
		
		if(PartyManager.getParty(p) == null) {
			p.sendMessage(Party.getMessageManager().getString("Commands.Kick.NoParty"));
			return;
		}
		
		PlayerParty party = PartyManager.getParty(p);
		
		if(!party.isLeader(p)) {
			p.sendMessage(Party.getMessageManager().getString("Commands.Kick.NotLeader"));
			return;
		}
		
		ProxiedPlayer pl = BungeeCord.getInstance().getPlayer(args[0]);
		
		if(pl == null) {
			p.sendMessage(Party.getMessageManager().getString("Commands.Kick.NotOnline"));
			return;
		}
		
		if(party.removePlayer(pl)) {
			p.sendMessage(Party.getMessageManager().getString("Commands.Kick.Kicked", Arrays.asList("%player%"), Arrays.asList(pl.getName())));
			for(ProxiedPlayer pp : party.getPlayers()) {
				pp.sendMessage(Party.getMessageManager().getString("Commands.Kick.Player-Kicked", Arrays.asList("%player%"), Arrays.asList(pl.getName())));
			}
			pl.sendMessage(Party.getMessageManager().getString("Commands.Kick.Kick"));
			return;
		}else {
			p.sendMessage(Party.getMessageManager().getString("Commands.Kick.Error"));
			return;
		}
	}

}
