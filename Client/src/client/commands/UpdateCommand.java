package client.commands;

import movies.Movie;
import server.Request;
import server.Response;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * Update Command
 */
public class UpdateCommand extends AbstractCommand {
    private long id;
    private String elem;
    private boolean isCorrectId;

    /**
     * Base constructor
     *
     * @param arg id and elem
     */
    public UpdateCommand(String arg) {
        String[] splitArg = arg.split(" ", 2);
        try {
            this.id = Long.parseLong(splitArg[0]);
            this.isCorrectId = true;
        } catch (NumberFormatException ex) {
            System.err.println("Incorrect id. Update failed");
            this.isCorrectId = false;
        }
        try {
            this.elem = splitArg[1];
        } catch (IndexOutOfBoundsException ex) {
            this.elem = "";
            System.err.println("Element is required");
        }
    }


    /**
     * Update elem by id
     *
     * @param channel main channel
     */
    @Override
    public void execute(SocketChannel channel) throws IOException {
        if (isCorrectId) {
            try {
                Movie movie = Movie.getFromUserInput(System.in, System.out, "");
                Request request = new Request("update=" + this.id, movie);
                Response response = sendRequest(request, channel.socket());
                if (response == null) {
                    System.err.println("Got null server response");
                } else {
                    System.out.println(response.getText());
                }

            } catch (IOException e) {
                throw e;
            } catch (Exception e) {
                System.err.println("Update failed");
                System.err.println(e.getMessage());
            }
        }
    }
}
