package client.commands;


import java.io.IOException;
import java.nio.channels.SocketChannel;

public interface Command {
    void execute(SocketChannel channel) throws IOException;
}
