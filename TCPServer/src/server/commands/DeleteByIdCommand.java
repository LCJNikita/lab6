package server.commands;

import movies.MovieTreeSet;
import server.Response;

public class DeleteByIdCommand implements Command {
    private long id;

    /**
     * Delete by id command constructor
     * @param id element id
     */
    public DeleteByIdCommand(long id) {
        this.id = id;
    }

    /**
     * remove element by id
     * @param treeSet main collection manager
     * @return Response to client
     */
    @Override
    public Response execute(MovieTreeSet treeSet) {
        return new Response(treeSet.removeById(this.id) ? "Movie with id " + id + " was removed" : "Movie with id " + id + " was not removed", null);
    }
}
