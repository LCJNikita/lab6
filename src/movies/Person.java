package movies;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.InputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Person class
 */
public class Person implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private long height; //Значение поля должно быть больше 0
    private float weight; //Значение поля должно быть больше 0

    /**
     * movies.Person constructor
     *
     * @param name   person name
     * @param height person height
     * @param weight person weight
     * @throws NumberFormatException if height or weight is less or equals zero
     * @throws NullPointerException  if name is null or empty
     */
    public Person(String name,
                  long height,
                  float weight)
            throws NumberFormatException, NullPointerException {
        if ((name == null) || (name.isEmpty())) {
            throw new NullPointerException("Name can't be null or empty");
        }
        if (height <= 0) {
            throw new NumberFormatException("Height need to be greater than zero");
        }
        if (weight <= 0) {
            throw new NumberFormatException("Weight need to be greater than zero");
        }
        this.name = name;
        this.height = height;
        this.weight = weight;
    }

    /**
     * get Person from JSON object
     *
     * @param object JSON obj
     * @return Person
     * @throws NumberFormatException
     * @throws NullPointerException  if got null obj
     */
    public static Person getFromJsonObject(JSONObject object)
            throws NumberFormatException, NullPointerException {
        return new Person((String) object.get("name"),
                (Long) object.get("height"),
                Float.parseFloat(((Double) object.get("weight")).toString()));
    }

    /**
     * get Person from JSON string
     *
     * @param jsonStr json str
     * @return person
     * @throws NumberFormatException invalid number
     * @throws NullPointerException  if get Person from JSON object
     * @throws ParseException        invalid json
     */
    public static Person getFromJsonString(String jsonStr)
            throws NumberFormatException, NullPointerException, ParseException {
        JSONParser parcer;
        JSONObject object;
        parcer = new JSONParser();
        object = (JSONObject) parcer.parse(jsonStr);
        return getFromJsonObject(object);
    }

    /**
     * create Person from user input
     *
     * @param in            input stream
     * @param out           output PrintWriter
     * @param firstInputMsg message, than will be displayed before user input
     * @return Person
     */
    public static Person getFromUserInput(InputStream in, PrintStream out, String firstInputMsg) {
        String val = "";
        String name = "";
        long height = 0L;
        float weight = 0F;
        Scanner scanner = new Scanner(in);
        out.println(firstInputMsg);
        out.println("Do you want to enter person data (Y yes, N no): ");
        while (!Arrays.asList("y", "yes", "no", "n").contains(val)) {
            val = scanner.nextLine().toLowerCase();
        }
        if (val.equals("no") || val.equals("n")) {
            return null;
        }
        while (name.isEmpty()) {
            out.println("Enter person name: ");
            name = scanner.nextLine();
            if (name.isEmpty()) {
                out.println("Name can't be empty.");
            }
        }
        while (height <= 0) {
            out.println("Type person height: ");
            try {
                height = Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException ex) {
                out.println("Type valid long value.");
            }
        }
        while (weight <= 0) {
            out.println("Type person weight: ");
            try {
                weight = Float.parseFloat(scanner.nextLine());
            } catch (NumberFormatException ex) {
                out.println("Type valid decimal value.");
            }
        }
        return new Person(name, height, weight);
    }

    /**
     * weight getter
     *
     * @return weight
     */
    public float getWeight() {
        return weight;
    }

    /**
     * height getter
     *
     * @return height
     */
    public long getHeight() {
        return height;
    }

    /**
     * name getter
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Create JSON Object from fields
     *
     * @return JSON Object
     */
    public JSONObject toJSONObject() {
        JSONObject outputJson = new JSONObject();
        outputJson.put("name", this.name);
        outputJson.put("height", this.height);
        outputJson.put("weight", this.weight);
        return outputJson;
    }
}
