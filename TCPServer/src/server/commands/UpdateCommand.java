package server.commands;

import movies.Movie;
import movies.MovieTreeSet;
import server.Response;

/**
 * Update Command
 */
public class UpdateCommand implements Command{
    private Movie movie;
    private long id;

    /**
     * Update command constructor
     * @param movie new elem
     * @param id id of old elem
     */
    public UpdateCommand(Movie movie, long id) {
        this.movie = movie;
        this.id = id;
    }

    /**
     * Replace elem with id with new data
     * @param treeSet main collection manager
     * @return Response to client
     */
    @Override
    public Response execute(MovieTreeSet treeSet) {
        return new Response(treeSet.update(this.id, this.movie) ? "Movie was updated" : "Movie was not updated", null);
    }
}
