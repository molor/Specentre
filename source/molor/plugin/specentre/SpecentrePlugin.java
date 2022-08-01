package molor.plugin.specentre;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;

public class SpecentrePlugin extends JavaPlugin {
	@Override
	public void onEnable() {
		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this, ListenerPriority.MONITOR, PacketType.Play.Server.VIEW_CENTRE) {
			@Override
			public void onPacketSending(PacketEvent theEvent) {
				if (theEvent.isCancelled())
					return;

				Player thePlayer =
					theEvent.getPlayer();
				Entity theView =
					thePlayer.getSpectatorTarget();

				// See:
				// https://bugs.mojang.com/browse/MC-148993

				if (theView == null)
					return; // not a spectator or didn't grab camera of other entity

				Location toLocation =
					theView.getLocation();
				PacketContainer thePacket =
					new PacketContainer(PacketType.Play.Server.ENTITY_TELEPORT);

				thePacket.getIntegers().write(0, thePlayer.getEntityId());
				thePacket.getDoubles().write(0, toLocation.getX());
				thePacket.getDoubles().write(1, toLocation.getY());
				thePacket.getDoubles().write(2, toLocation.getZ());
				thePacket.getBytes().write(0, (byte) toLocation.getYaw());
				thePacket.getBytes().write(1, (byte) toLocation.getPitch());
				thePacket.getBooleans().write(0, false);

				try {
					ProtocolLibrary.getProtocolManager().sendServerPacket(thePlayer, thePacket);
				}
				catch (Exception theException) {
					theException.printStackTrace();
				}
			}
		});
	}

	@Override
	public void onDisable() {
		ProtocolLibrary.getProtocolManager().removePacketListeners(this);
	}
}
