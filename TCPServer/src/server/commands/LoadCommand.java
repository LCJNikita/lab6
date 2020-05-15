package server.commands;

import movies.MovieTreeSet;
import server.Response;

/**
 * Load command
 */
public class LoadCommand implements Command {

    /**
     * get all elements from collection manager
     * @param treeSet main collection manager
     * @return Response to client
     */
    @Override
    public Response execute(MovieTreeSet treeSet) {
        return new Response("Load all elements of collection", treeSet.toArrayList());
    }
}
