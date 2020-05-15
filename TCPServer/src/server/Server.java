package server;

import movies.MovieTreeSet;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Server
 */
public class Server {
    private MovieTreeSet treeSet;
    private ServerSocket serverSocket;
    private String fileName;
    private Logger logger;
    private CommandParser parser;

    /**
     * Server constructor
     *
     * @param port     server port to bind
     * @param fileName filename to save
     * @throws IOException can be caused by network
     */
    public Server(int port, String fileName) throws IOException {
        serverSocket = new ServerSocket(port, 0, InetAddress.getLocalHost());
        this.fileName = fileName;
        this.treeSet = new MovieTreeSet(this.fileName);
        this.logger = Logger.getLogger(this.getClass().getName());
        this.parser = new CommandParser(new HashSet<>(Arrays.asList("get", "add", "info", "delete", "delete_by_id", "clear", "save")));

        this.logger.info("Server created");
        this.logger.info("IP: " + serverSocket.getLocalSocketAddress());
    }


    /**
     * listen for client requests
     *
     * @throws IOException can be caused by network
     */
    public void listen() throws IOException {
        this.logger.info("Start listening for requests");

        while (true) {
            Socket socket = serverSocket.accept();
            this.logger.info("Get new connection from: " + socket.getInetAddress() + ":" + socket.getPort());
            ResponseThread thread = new ResponseThread(socket, this.parser, this.treeSet, this.logger);
            thread.start();
        }
    }

    /**
     * main server method
     *
     * @param args command line args
     */
    public static void main(String[] args) {
        int port = 1234;

        //By arguments
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            //By input stream
            Scanner scanner = new Scanner(System.in);
            System.out.print("Port: ");
            port = scanner.nextInt();
        }

        try {
            Server server = new Server(port, "collection.json");
            server.listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
