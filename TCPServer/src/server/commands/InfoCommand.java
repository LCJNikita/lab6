package server.commands;

import movies.MovieTreeSet;
import server.Response;

/**
 * Info command
 */
public class InfoCommand implements Command {
    /**
     * get info about collection from main manager
     * @param treeSet main collection manager
     * @return Response to client
     */
    @Override
    public Response execute(MovieTreeSet treeSet) {
        return new Response(treeSet.getInfo(), null);
    }
}
