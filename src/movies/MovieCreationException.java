package movies;

import java.io.Serializable;

public class MovieCreationException extends Exception implements Serializable {

    public MovieCreationException(String message){
        super(message);
    }
}
