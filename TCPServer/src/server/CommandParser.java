package server;


import movies.Movie;

import java.util.HashSet;
import java.util.NoSuchElementException;

import server.commands.*;

/**
 * Class that parse commands from user input string
 */
public class CommandParser {
    private final HashSet<String> AVAILABLE_COMMANDS;


    /**
     * Parser constructor
     *
     * @param commands    available command names
     */
    CommandParser(HashSet<String> commands) {
        this.AVAILABLE_COMMANDS = commands;
    }

    /**
     * Parse command from client Request object
     *
     * @param request user command request
     * @return command
     * @throws NoSuchElementException if there is no such command
     */
    public Command parse(Request request) throws NoSuchElementException{
        String commandName = request.getMethod();
        String text_arg = "";
        if (commandName.contains("=")){
            String [] splited = commandName.split("=", 2);
            commandName = splited[0];
            text_arg = splited[1];
        }
        Movie arg = request.getArg();

        Command command = null;

        if (AVAILABLE_COMMANDS.contains(commandName)) {
            switch (commandName) {
                case "get":
                    command = new LoadCommand();
                    break;
                case "add":
                    command = new AddCommand(arg);
                    break;
                case "info":
                    command = new InfoCommand();
                    break;
                case "delete":
                    command = new DeleteCommand(arg);
                    break;
                case "delete_by_id":
                    command = new DeleteByIdCommand(Long.parseLong(text_arg));
                    break;
                case "update":
                    command = new UpdateCommand(arg, Long.parseLong(text_arg));
                    break;
                case "clear":
                    command = new ClearCommand();
                    break;
                case "save":
                    command = new SaveCommand(text_arg);
                    break;
            }
        } else {
            throw new NoSuchElementException("No such command");
        }

        return command;
    }
}
