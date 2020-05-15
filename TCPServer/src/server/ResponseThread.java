package server;

import movies.MovieTreeSet;
import server.commands.Command;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

/**
 * Server thread to serve client
 */
public class ResponseThread extends Thread {

    private Socket clientSocket;
    private CommandParser parser;
    private final Logger logger;
    private final MovieTreeSet treeSet;


    /**
     * Response thread constructor
     *
     * @param clientSocket client socket
     * @param parser       command parser
     * @param treeSet      main collection manager
     * @param logger       server logger
     */
    public ResponseThread(Socket clientSocket,
                          CommandParser parser,
                          MovieTreeSet treeSet,
                          Logger logger) {
        super();
        this.clientSocket = clientSocket;
        this.parser = parser;
        this.treeSet = treeSet;
        this.logger = logger;
        this.logger.info("New response thread created");

    }

    /**
     * main thread method to work with client
     */
    public void run() {
        try (InputStream is = clientSocket.getInputStream();
             ObjectInputStream in = new ObjectInputStream(is);

             OutputStream os = clientSocket.getOutputStream();
             ObjectOutputStream out = new ObjectOutputStream(os)) {

            while (!clientSocket.isClosed()) {
                Request request = (Request) in.readObject();

                synchronized (this.logger) {
                    this.logger.info("Get new response from client: " + clientSocket.getInetAddress());
                    this.logger.info("Request: " + request.toString());
                }

                Command command = null;
                Response response = null;

                try {
                    command = parser.parse(request);
                } catch (NoSuchElementException e) {
                    this.logger.severe(e.getMessage());
                    response = new Response("No such command", null);
                } catch (Exception e) {
                    this.logger.severe(e.toString());
                    response = new Response("Server cant recognize command or args", null);
                }

                if (command != null) {
                    synchronized (this.treeSet) {
                        response = command.execute(this.treeSet);
                    }
                }
                logger.info("Send response: " + response.toString());
                out.writeObject(response);
            }
            this.logger.info("End connection");
            this.logger.info("Save collection to file");
            this.treeSet.save();

        } catch (EOFException | SocketException e) {
            this.logger.info("End connection from: " + clientSocket.getInetAddress());
            this.logger.info("Save collection to file");
            this.treeSet.save();
        } catch (IOException | ClassNotFoundException e) {
            this.logger.severe(e.getMessage());
            e.printStackTrace();
            this.logger.info("Save collection to file");
            this.treeSet.save();
        }
    }
}
