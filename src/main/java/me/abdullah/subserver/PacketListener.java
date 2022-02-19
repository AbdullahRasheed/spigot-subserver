package me.abdullah.subserver;

import me.abdullah.concurrent.ExecutorThreads;
import me.abdullah.csl.db.Caches;
import me.abdullah.subserver.net.Connection;
import me.abdullah.subserver.net.packets.PlayerConnectPacket;
import me.abdullah.subserver.net.packets.PlayerConnectionInfoPacket;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;

public class PacketListener implements BiConsumer<Connection, Object> {

    private final Map<Class<?>, BiConsumer<Connection, Object>> handlers;

    private final Subserver subserver;
    public PacketListener(Subserver subserver){
        this.subserver = subserver;
        this.handlers = new HashMap<>();

        registerPacket(PlayerConnectPacket.class, this::handlePlayerConnectPacket);
    }

    @Override
    public void accept(Connection connection, Object o) {
        handlers.get(o.getClass()).accept(connection, o);
    }

    public void registerPacket(Class<?> clazz, BiConsumer<Connection, Object> handler){
        handlers.put(clazz, handler);
    }

    public void handlePlayerConnectPacket(Connection connection, Object o){

        PlayerConnectPacket packet = (PlayerConnectPacket) o;

        UUID uuid = new UUID(packet.most, packet.least);
        subserver.addConnection(uuid, connection);

        packet.name = Bukkit.getPlayer(uuid).getName().getBytes();

        subserver.sendGlobalPacket(packet);
        subserver.sendPacket(connection, new PlayerConnectionInfoPacket());

        ExecutorThreads.getCachedThreadService().submit(() -> Caches.getPlayerCache().retrieveAndStore(uuid));

    }
}