package me.abdullah.subserver;

import me.abdullah.Panthera;
import me.abdullah.subserver.net.Connection;
import me.abdullah.subserver.net.Server;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Subserver {

    private final Map<UUID, Connection> connections;

    private final Server server;
    public Subserver(int port) throws IOException {
        this.server = new Server(port);
        this.server.setPacketListener(new PacketListener(this));

        this.connections = new HashMap<>();
    }

    public void sendPacket(Connection connection, Object o){
        connection.sendPacket(o);
    }

    public void sendGlobalPacket(Object o){
        connections.values().forEach((connection) -> connection.sendPacket(o));
    }

    public synchronized void begin(){
        server.begin();
    }

    public synchronized void close(){
        connections.values().forEach(Connection::close);
        server.close();
    }

    public Connection getConnection(UUID uuid){
        return connections.get(uuid);
    }

    public void addConnection(UUID uuid, Connection connection){
        connections.put(uuid, connection);
    }

    public void removeConnection(UUID uuid){
        connections.remove(uuid);
    }

    public static Subserver get(){
        return Panthera.getPanthera().getSubserver();
    }
}
