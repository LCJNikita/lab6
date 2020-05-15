package server;

import movies.Movie;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Response from server to client
 */
public class Response implements Serializable {
    private String text;
    private ArrayList<Movie> movies;

    /**
     * Response constructor
     *
     * @param text   text response data
     * @param movies movies collection
     */
    public Response(String text, ArrayList<Movie> movies) {
        this.text = text;
        this.movies = movies;
    }

    /**
     *
     * @return movies collection from response
     */
    public ArrayList<Movie> getMovies() {
        return movies;
    }

    /**
     * get text data grom response
     * @return text data
     */
    public String getText() {
        return text;
    }

    /**
     *
     * @return String interpretation
     */
    @Override
    public String toString() {
        return "Response{" +
                "text='" + text + '\'' +
                ", movies=" + movies +
                '}';
    }
}
