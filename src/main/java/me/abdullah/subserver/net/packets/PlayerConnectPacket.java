package me.abdullah.subserver.net.packets;

import java.io.Serializable;

// Max size: 32 bytes
public class PlayerConnectPacket implements Serializable {
    public long least;
    public long most;
    public byte[] name;
}
