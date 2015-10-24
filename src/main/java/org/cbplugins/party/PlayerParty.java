package org.cbplugins.party;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PlayerParty {

	private ProxiedPlayer leader;
	
	private List<ProxiedPlayer> players;
	
	private List<ProxiedPlayer> invitations;
	
	public PlayerParty(ProxiedPlayer leader) {
		this.leader = leader;
		this.players = new ArrayList<>();
		this.invitations = new ArrayList<>();
	}
	
	public boolean isLeader(ProxiedPlayer player) {
		return this.leader == player;
	}
	
	public List<ProxiedPlayer> getPlayers() {
		return players;
	}
	
	public ProxiedPlayer getLeader() {
		return leader;
	}
	
	public boolean isInParty(ProxiedPlayer player) {
		if(isLeader(player)) {
			return true;
		}else if(players.contains(player)) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean addPlayer(ProxiedPlayer player) {
		if(!players.contains(player) && invitations.contains(player) && players.size() < Party.getConfig().getConfig().getInt("MaxPerParty")) {
			players.add(player);
			invitations.remove(player);
			return true;
		}else {
			return false;
		}
	}
	
	public boolean removePlayer(ProxiedPlayer player) {
		if(players.contains(player)) {
			players.remove(player);
			return true;
		}else {
			return false;
		}
	}
	
	public ServerInfo getServer() {
		return leader.getServer().getInfo();
	}
	
	public void invite(final ProxiedPlayer p) {
		invitations.add(p);
		p.sendMessage(Party.getMessageManager().getString("Party-Invite", Arrays.asList("%player%"), Arrays.asList(leader.getName())));
		p.sendMessage(Party.getMessageManager().getString("Party-Join-Help", Arrays.asList("%leader%"), Arrays.asList(getLeader().getName())));
		Party.getInstance().getProxy().getScheduler().schedule(Party.getInstance(), new Runnable() {
			public void run() {
				if(invitations.contains(p)) {
					invitations.remove(p);
					p.sendMessage(Party.getMessageManager().getString("Party-Invitation-Expired"));
					leader.sendMessage(Party.getMessageManager().getString("Invitation-Expired", Arrays.asList("%player%"), Arrays.asList(p.getName())));
				}
			}
		}, 30L, TimeUnit.SECONDS);
	}
}
