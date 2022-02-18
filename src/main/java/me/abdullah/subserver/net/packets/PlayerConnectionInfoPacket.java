package me.abdullah.subserver.net.packets;

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

    }
}
