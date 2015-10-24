package org.cbplugins.party.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import org.cbplugins.party.Party;

public class PartyCommand extends Command {

	private List<SubCommand> cmds = new ArrayList<>();
	
	public PartyCommand() {
		super("party");
		cmds.add(new Invite());
		cmds.add(new Join());
		cmds.add(new Leave());
		cmds.add(new Kick());
		cmds.add(new org.cbplugins.party.command.List());
		cmds.add(new Create());
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) {
		if(!(sender instanceof ProxiedPlayer)) {
			sender.sendMessage(Party.getMessageManager().getString("Commands.MustBePlayer"));
			return;
		}
		
		ProxiedPlayer p = (ProxiedPlayer)sender;
		
		if(args.length == 0) {
			p.sendMessage(Party.getMessageManager().getString("Commands.Header"));
			for(SubCommand sc : cmds) {
				p.sendMessage(Party.getMessageManager().getString("Commands.Format", Arrays.asList("%aliases%", "%usage%", "%message%"), Arrays.asList(aliases(sc), sc.getUsage(), sc.getMessage())));
			}
			p.sendMessage(Party.getMessageManager().getString("Commands.Footer"));
			return;
		}
		
		SubCommand sc = getCommand(args[0]);
		
		if(sc == null) {
			p.sendMessage(Party.getMessageManager().getString("Commands.NotExists"));
			return;
		}
		
		Vector<String> a = new Vector<>(Arrays.asList(args));
		a.remove(0);
		args = a.toArray(new String[a.size()]);
		
		sc.onCommand(p, args);
	}
	
	private String aliases(SubCommand sc) {
		String fin = "";
		
		for(String a : sc.getAliases()) {
			fin += a + " | ";
		}
		
		return fin.substring(0, fin.lastIndexOf(" | "));
	}
	
	private SubCommand getCommand(String name) {
		for(SubCommand sc : cmds) {
			if(sc.getClass().getSimpleName().equalsIgnoreCase(name)) return sc;
			for(String alias : sc.getAliases()) if(alias.equalsIgnoreCase(name)) return sc;
		}
		return null;
	}

}
