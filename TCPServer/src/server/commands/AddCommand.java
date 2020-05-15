package server.commands;

import movies.Movie;
import movies.MovieTreeSet;
import server.Response;

/**
 * Add command
 */
public class AddCommand implements Command {
    private Movie movie;

    /**
     * Add command constructor
     * @param movie movie to be added
     */
    public AddCommand(Movie movie) {
        this.movie = movie;
        this.movie.setCreationDate(java.time.LocalDate.now());
    }

    /**
     * add movie to main collection
     * @param treeSet main collection manager
     * @return Response to client
     */
    @Override
    public Response execute(MovieTreeSet treeSet) {
        return new Response(treeSet.add(this.movie) ? "Movie was added" : "Movie was not added", null);
    }
}
