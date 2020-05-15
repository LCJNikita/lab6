package movies;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Scanner;

/**
 * movies.Coordinates class
 */
public class Coordinates implements Serializable {
    private int x;
    private float y; //Максимальное значение поля: 623
    private final static int MAX_Y_VALUE = 623;

    /**
     * movies.Coordinates constructor
     *
     * @param x X coor value
     * @param y Y coor value
     * @throws NumberFormatException Y can't be upper 623
     */
    public Coordinates(int x, float y) throws NumberFormatException {
        if (y > MAX_Y_VALUE) {
            throw new NumberFormatException(String.format("y need to be less than %d", MAX_Y_VALUE));
        }
        this.x = x;
        this.y = y;
    }

    /**
     * Create Coordinates object from json object
     *
     * @param object JSON Object
     * @return coor
     * @throws NumberFormatException if got incorrect number
     * @throws NullPointerException  if got null
     */
    public static Coordinates getFromJsonObject(JSONObject object)
            throws NumberFormatException, NullPointerException {
        return new Coordinates(Integer.parseInt(((Long) object.get("x")).toString()),
                Float.parseFloat(((Double) object.get("y")).toString()));
    }

    /**
     * Create Coordinates object from json string
     *
     * @param jsonStr json str
     * @return Coordinates object
     * @throws NumberFormatException if there is an invalid value in json
     * @throws ParseException        invalid json
     * @throws NullPointerException  if got null obj
     */
    public static Coordinates getFromJsonString(String jsonStr)
            throws NumberFormatException, ParseException, NullPointerException {
        JSONParser parcer;
        JSONObject object;
        parcer = new JSONParser();
        object = (JSONObject) parcer.parse(jsonStr);
        return getFromJsonObject(object);
    }

    /**
     * get coordinates ftom user input
     *
     * @param in  input stream
     * @param out output PrintWriter
     * @return coordinates
     */
    public static Coordinates getFromUserInput(InputStream in, PrintStream out) {
        int x = 0;
        float y = 0F;
        NumberFormatException exception = new NumberFormatException();
        Scanner scanner = new Scanner(in);
        out.println("Enter coordinates: ");
        while (exception != null) {
            try {
                out.println("Enter X coordinate: ");
                x = Integer.parseInt(scanner.nextLine());
                exception = null;
            } catch (NumberFormatException ex) {
                out.println("Wrong X value (integer)");
                exception = ex;
            }
        }
        exception = new NumberFormatException();
        while (exception != null || y > MAX_Y_VALUE) {
            try {
                out.println("Enter Y coordinate: ");
                y = Float.parseFloat(scanner.nextLine());
                if (y > MAX_Y_VALUE) {
                    out.println("Y need to be under " + MAX_Y_VALUE);
                }
                exception = null;
            } catch (NumberFormatException ex) {
                out.println("Wrong Y value (decimal)");
                exception = ex;
            }
        }
        return new Coordinates(x, y);

    }

    /**
     * Y getter
     *
     * @return y coordinates value
     */
    public float getY() {
        return y;
    }

    /**
     * X getter
     *
     * @return x coordinates value
     */
    public int getX() {
        return x;
    }

    /**
     * MAX Y value getter
     *
     * @return max value, that Y can to be
     */
    public static int getMaxYValue() {
        return MAX_Y_VALUE;
    }

    /**
     * Create JSON Object from fields
     *
     * @return JSON Object
     */
    public JSONObject toJSONObject() {
        JSONObject outputJson = new JSONObject();
        outputJson.put("x", this.x);
        outputJson.put("y", this.y);
        return outputJson;
    }
}
