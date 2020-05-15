package server.commands;

import movies.MovieTreeSet;
import server.Response;

/**
 * Clear command
 */
public class ClearCommand implements Command{
    /**
     * Clear main collection
     * @param treeSet main collection manager
     * @return Response to client
     */
    @Override
    public Response execute(MovieTreeSet treeSet) {
        treeSet.clear();
        return new Response("Collection on server was cleared", null);
    }
}
