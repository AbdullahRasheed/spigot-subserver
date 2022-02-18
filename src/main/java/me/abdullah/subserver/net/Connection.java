package me.abdullah.subserver.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.function.BiConsumer;

public class Connection {

    private boolean running;

    private final Socket socket;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;
    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());

        this.running = false;
    }

    public synchronized void begin(BiConsumer<Connection, Object> packetConsumer){
        if(running) throw new IllegalStateException("Can't begin a new connection thread while it is already running!");

        running = true;
        new Thread(() -> {
            while(running){
                try {
                    packetConsumer.accept(this, in.readObject());
                } catch (IOException | ClassNotFoundException e) {
                    close();
                }
            }
        }).start();
    }

    public synchronized void close(){
        running = false;
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendPacket(Object o){
        try {
            out.writeObject(o);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
