package org.cbplugins.party;

import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PartyManager {

	/**
	 * List of all the parties
	 */
	private static List<PlayerParty> parties = new ArrayList<>();

	/**
	 * Get the party from a player
	 * @param player The player from the party
	 * @return the party
	 */
	public static PlayerParty getParty(ProxiedPlayer player) {
		for(PlayerParty party : parties) {
			if(party.isInParty(player)) {
				return party;
			}
		}
		return null;
	}

	/**
	 * Creates a party for a player
	 * @param player Leader of the party
	 * @return Boolean whether can create the party or not
	 */
	public static boolean createParty(ProxiedPlayer player) {
		if(getParty(player) == null) {
			parties.add(new PlayerParty(player));
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Deletes the party from a player
	 * @param player Party from the specified player
	 * @return Whether the party could be deleted or not
	 */
	public static boolean deleteParty(ProxiedPlayer player) {
		if(getParty(player) != null) {
			if(getParty(player).isLeader(player)) {
				for(ProxiedPlayer p : getParty(player).getPlayers()) {
					getParty(player).removePlayer(p);
				}
				parties.remove(getParty(player));
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}

	/**
	 * Getter for all the parties
	 * @return List with all parties
	 */
	public static List<PlayerParty> getParties() {
		return parties;
	}

	/**
	 * Deletes the specified party
	 * @param party Party that should be deleted
	 */
	public static void deleteParty(PlayerParty party) {
		if(party != null) {
			for(int i = 0; i < party.getPlayers().size(); i++) {
				if(party.getPlayers().get(i) != null) {
					party.getPlayers().remove(i);
				}
			}
			parties.remove(party);
		}
	}
	
}
