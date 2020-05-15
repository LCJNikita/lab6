package server;

import movies.Movie;

import java.io.Serializable;

/**
 * Request to server
 */
public class Request implements Serializable {
    private String method;
    private Movie arg;

    /**
     * Request constructor
     *
     * @param method method name
     * @param movie  movie argument
     */
    public Request(String method, Movie movie) {
        this.method = method;
        this.arg = movie;
    }

    /**
     * method name getter
     * @return method name
     */
    public String getMethod() {
        return method;
    }

    /**
     * get movie argument
     * @return movie arg
     */
    public Movie getArg() {
        return arg;
    }

    /**
     *
     * @return string interpritation of request
     */
    @Override
    public String toString() {
        return "Request{" +
                "method='" + method + '\'' +
                ", arg=" + (arg != null ? arg.toString() : "null") +
                '}';
    }
}
