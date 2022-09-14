package me.abdullah.subserver.net;

import org.bukkit.Bukkit;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.BiConsumer;

public class Server {

    private boolean running;

    private final ServerSocket serverSocket;
    private BiConsumer<Connection, Object> packetConsumer;
    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
        this.packetConsumer = (connection, o) -> {};

        this.running = false;

        Bukkit.getLogger().info("Subserver registered on port " + port);
    }

    public synchronized void begin(){
        if(running) throw new IllegalStateException("Can't begin a server that is already running!");

        running = true;
        new Thread(() -> {
            Bukkit.getLogger().info("Subserver has started");
            while(running){
                try {
                    Socket socket = serverSocket.accept();
                    socket.setTcpNoDelay(true);
                    new Connection(socket).begin(packetConsumer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public synchronized void close(){
        running = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setPacketListener(BiConsumer<Connection, Object> packetConsumer){
        this.packetConsumer = packetConsumer;
    }
}
