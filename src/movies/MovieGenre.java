package movies;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Movie genre enum
 */
public enum MovieGenre implements Serializable {
    ACTION("action"),
    COMEDY("comedy"),
    ADVENTURE("adventure"),
    HORROR("horror"),
    SCIENCE_FICTION("scifi");

    private String genreName;

    /**
     * enum constuctor to create elem's name
     *
     * @param genreName name of genre
     */
    MovieGenre(String genreName) {
        this.genreName = genreName;
    }

    /**
     * get enum elem by name
     *
     * @param name elem name
     * @return enum elem
     */
    public static MovieGenre getByName(String name) {
        switch (name.toLowerCase()) {
            case "action":
                return ACTION;
            case "comedy":
                return COMEDY;
            case "adventure":
                return ADVENTURE;
            case "horror":
                return HORROR;
            case "scifi":
                return SCIENCE_FICTION;
            default:
                return null;

        }
    }

    /**
     * create Movie genre from user input
     *
     * @param in  input stream
     * @param out output stream
     * @return genre
     */
    public static MovieGenre getFromUserInput(InputStream in, PrintStream out) {
        MovieGenre res = null;
        String str;
        out.println("\tAvailable genres:");
        Arrays.stream(MovieGenre.values()).forEach(movieGenre -> out.println("\t\t" + movieGenre.getName()));
        Scanner scanner = new Scanner(in);
        while (res == null) {
            out.println("Enter movie genre: ");
            str = scanner.nextLine();
            res = getByName(str);
            if (str.isEmpty()) {
                return null;
            }
            if (res == null) {
                out.println("No such genre");
            }
        }
        return res;

    }

    /**
     * genre name getter
     *
     * @return genre name
     */
    public String getName() {
        return genreName.toUpperCase();
    }
}