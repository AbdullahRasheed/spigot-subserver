package me.abdullah.subserver.net.packets;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.UUID;

public class PlayerConnectionInfoPacket implements Serializable {
    public UUID[] uuids;
    public byte[][] names;

    public PlayerConnectionInfoPacket(UUID[] uuids, byte[][] names){
        this.uuids = uuids;
        this.names = names;
    }

    public PlayerConnectionInfoPacket(){
        int size = Bukkit.getOnlinePlayers().size();
        Player[] onlinePlayers = Bukkit.getOnlinePlayers().toArray(new Player[size]);

        uuids = new UUID[size];
        names = new byte[size][];
        for (int i = 0; i < onlinePlayers.length; i++) {
            uuids[i] = onlinePlayers[i].getUniqueId();
            names[i] = onlinePlayers[i].getName().getBytes();
        }
    }
}
