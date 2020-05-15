package server.commands;

import movies.Movie;
import movies.MovieTreeSet;
import server.Response;

/**
 * Delete command
 */
public class DeleteCommand implements Command {
    private Movie movie;

    /**
     * Delete command constructor
     *
     * @param movie movie to be deleted
     */
    public DeleteCommand(Movie movie) {
        this.movie = movie;
    }

    /**
     * delete element from main collection
     * @param treeSet main collection manager
     * @return Response to client
     */
    @Override
    public Response execute(MovieTreeSet treeSet) {
        return new Response(treeSet.remove(this.movie) ? "Movie was removed" : "Movie was not removed", null);
    }
}
