package server.commands;

import movies.MovieTreeSet;
import server.Response;

public interface Command {
    Response execute(MovieTreeSet treeSet);
}
