package movies;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Scanner;


/**
 * movies.Movie class
 */
public class Movie implements Comparable, Serializable {
    private static long nextId = 0;
    private long id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long oscarsCount; //Значение поля должно быть больше 0
    private Long totalBoxOffice; //Поле не может быть null, Значение поля должно быть больше 0
    private MovieGenre genre; //Поле может быть null
    private MpaaRating mpaaRating; //Поле может быть null
    private Person screenwriter;

    /**
     * Constructor for loading from files (include creation date)
     *
     * @param name           movie name, can't be empty or null
     * @param coor           movie coordinates, can't be null
     * @param oscarsCount    oscars quantity, need to be upper than zero
     * @param totalBoxOffice total box office, can't be null, need to be upper than zero
     * @param genre          film genre (can be null)
     * @param rating         film mpaa rating (can be null)
     * @param screenwriter   film screenwriter (can be null)
     * @param creationDate   creation date
     * @throws NullPointerException if got null object
     */
    public Movie(String name,
                 Coordinates coor,
                 long oscarsCount,
                 Long totalBoxOffice,
                 MovieGenre genre,
                 MpaaRating rating,
                 Person screenwriter,
                 LocalDate creationDate) throws NullPointerException, NumberFormatException {
        if ((name == null) || (name.isEmpty())) {
            throw new NullPointerException("Name can't be null or empty");
        }
        if (coor == null) {
            throw new NullPointerException("Coordinates can't be null");
        }
        if (totalBoxOffice == null) {
            throw new NullPointerException("Genre can't be null");
        }
        if (totalBoxOffice <= 0) {
            throw new NumberFormatException("totalBoxOffice need to be higher than zero");
        }
        if (oscarsCount <= 0) {
            throw new NumberFormatException("oscarsCount need to be higher than zero");
        }
        this.id = nextId;
        if (creationDate == null) {
            this.creationDate = java.time.LocalDate.now();
        } else {
            this.creationDate = creationDate;
        }
        nextId++;
        this.name = name;
        this.coordinates = coor;
        this.genre = genre;
        this.mpaaRating = rating;
        this.oscarsCount = oscarsCount;
        this.screenwriter = screenwriter;
        this.totalBoxOffice = totalBoxOffice;
    }

    public Movie(String name,
                 Coordinates coor,
                 long oscarsCount,
                 Long totalBoxOffice,
                 MovieGenre genre,
                 MpaaRating rating,
                 Person screenwriter) throws NullPointerException, NumberFormatException {
        this(name, coor, oscarsCount, totalBoxOffice, genre, rating, screenwriter, null);
    }

    /**
     * Mpaa rating getter
     *
     * @return Mpaa rating value
     */
    public MpaaRating getMpaaRating() {
        return mpaaRating;
    }

    /**
     * OscarsCount getter
     *
     * @return OscarsCount value
     */
    public long getOscarsCount() {
        return oscarsCount;
    }

    /**
     * id getter
     *
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * id setter
     *
     * @param id id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Compare with other movies.Movie by name
     *
     * @param o other movies.Movie
     * @return 1 if this grater than other, -1 if less, 0 if equals
     */
    @Override
    public int compareTo(Object o) {
        return this.name.compareTo(((Movie) o).name);
    }

    /**
     * compare movies by oscars count
     *
     * @param o other movie
     * @return 1 if this grater than other, -1 if less, 0 if equals
     */
    public int compareOscarsCountTo(Movie o) {
        return (int) (this.getOscarsCount() - o.getOscarsCount());
    }

    /**
     * get movies.Movie object from JSON
     *
     * @param jsonStr json string
     * @return movies.Movie obj
     * @throws ParseException        if got incorrect json
     * @throws NullPointerException  if got null object
     * @throws NumberFormatException if incorrect number
     */
    public static Movie getFromJson(String jsonStr)
            throws ParseException, NullPointerException, NumberFormatException {
        JSONParser parcer;
        JSONObject object = null;
        parcer = new JSONParser();

        object = (JSONObject) parcer.parse(jsonStr);
        String name = (String) object.get("name");

        Long oscarsCount = (Long) object.get("oscars_count");
        Long totalBoxOffice = (Long) object.get("total_box_office");
        MovieGenre genre;
        MpaaRating rating;
        LocalDate creationDate;
        Coordinates coordinates = Coordinates.getFromJsonObject((JSONObject) object.get("coordinates"));
        Person screenwriter;
        try {
            creationDate = LocalDate.parse((String) object.get("creation_date"));
        } catch (NullPointerException e){
            creationDate = null;
        }
        try {
            genre = MovieGenre.getByName((String) object.get("genre"));
        } catch (NullPointerException ex) {
            genre = null;
        }
        try {
            rating = MpaaRating.getByName((String) object.get("mpaa_rating"));
        } catch (NullPointerException ex) {
            rating = null;
        }
        try {
            screenwriter = Person.getFromJsonObject((JSONObject) object.get("screenwriter"));
        } catch (NullPointerException ex) {
            screenwriter = null;
        }

        return new Movie(name, coordinates, oscarsCount, totalBoxOffice, genre, rating, screenwriter, creationDate);
    }

    /**
     * get movie from user input
     *
     * @param in      input stream
     * @param out     output print stream
     * @param jsonStr json command arg
     * @return movie
     * @throws ParseException        invalid json
     * @throws NullPointerException  null value
     * @throws NumberFormatException incorrect number value
     */
    public static Movie getFromUserInput(InputStream in, PrintStream out, String jsonStr)
            throws ParseException, NullPointerException, NumberFormatException {
        JSONObject object = null;


        Scanner ins = new Scanner(in);
        String name = "";
        Long oscarsCount = 0L;
        Long totalBoxOffice = 0L;

        while (name.isEmpty()){
            System.out.println("Name can't be empty");
            System.out.print("Enter film name:");
            name = ins.nextLine();
        }

        while (oscarsCount <=0) {
            try {
                System.out.println("oscars_count > 0 is required");
                System.out.print("Enter oscars count:");
                oscarsCount = Long.parseLong(ins.nextLine());
            } catch (NumberFormatException ex){
                System.out.println("Number error");
            }
        }

        while (totalBoxOffice <=0) {
            try {
                System.out.println("total_box_office > 0 is required");
                System.out.print("Enter total_box_office:");

                totalBoxOffice = Long.parseLong(ins.nextLine());
            } catch (NumberFormatException ex){
                System.out.println("Number error");
            }
        }

        Coordinates coordinates = Coordinates.getFromUserInput(in, out);
        MovieGenre genre = MovieGenre.getFromUserInput(in, out);
        MpaaRating rating = MpaaRating.getFromUserInput(in, out);
        Person screenwriter = Person.getFromUserInput(in, out, "Enter screenwriter data");

        return new Movie(name, coordinates, oscarsCount, totalBoxOffice, genre, rating, screenwriter);
    }


    /**
     * creates JSON Object from movie obj
     *
     * @return JSON Object
     */
    public JSONObject toJSONObject() {
        JSONObject outputJson = new JSONObject();
        outputJson.put("id", this.id);
        outputJson.put("name", this.name);
        outputJson.put("coordinates", this.coordinates.toJSONObject());
        outputJson.put("creation_date", this.creationDate.toString());
        outputJson.put("oscars_count", this.oscarsCount);
        outputJson.put("total_box_office", this.totalBoxOffice);
        if (this.genre != null) {
            outputJson.put("genre", this.genre.getName());
        } else {
            outputJson.put("genre", null);
        }
        if (this.mpaaRating != null) {
            outputJson.put("mpaa_rating", this.mpaaRating.getName());
        } else {
            outputJson.put("mpaa_rating", null);
        }
        if (this.screenwriter != null) {
            outputJson.put("screenwriter", this.screenwriter.toJSONObject());
        } else {
            outputJson.put("screenwriter", null);
        }
        return outputJson;
    }

    /**
     * to string json
     *
     * @return string json
     */
    @Override
    public String toString() {
        return this.toJSONObject().toString();
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}