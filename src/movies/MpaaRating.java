package movies;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Film age rating enum
 */
public enum MpaaRating implements Serializable {
    G("G"),
    PG("PG"),
    PG_13("PG_13"),
    R("R"),
    NC_17("NC_17");

    private String name;

    /**
     * enum constuctor to create elem's name
     *
     * @param name name of rating
     */
    MpaaRating(String name) {
        this.name = name;
    }

    /**
     * get enum elem by name
     *
     * @param name elem name
     * @return enum elem
     */
    public static MpaaRating getByName(String name) {
        switch (name.toUpperCase()) {
            case "G":
                return G;
            case "PG":
                return PG;
            case "PG_13":
                return PG_13;
            case "R":
                return R;
            case "NC_17":
                return NC_17;
            default:
                return null;

        }
    }

    /**
     * rating name getter
     *
     * @return rating name
     */
    public String getName() {
        return name.toUpperCase();
    }

    /**
     * create Mpaa rating from user input
     *
     * @param in  input stream
     * @param out output print writer
     * @return mpaa rating
     */
    public static MpaaRating getFromUserInput(InputStream in, PrintStream out) {
        MpaaRating res = null;
        String str;
        out.println("\tAvailable rating:");
        Arrays.stream(MpaaRating.values()).forEach(rating -> out.println("\t\t" + rating.getName()));
        Scanner scanner = new Scanner(in);
        while (res == null) {
            out.println("Enter Mpaa rating: ");
            str = scanner.nextLine();
            res = getByName(str);
            if (str.isEmpty()) {
                return null;
            }
            if (res == null) {
                out.println("No such rating type");
            }
        }
        return res;
    }
}