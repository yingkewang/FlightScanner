package yingke;

import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

/**
 * @author Yingke
 *
 */
public class Util {
    
    public static String escapeJson(String json) {
        return json.replaceAll("\"", "%22").replaceAll(" ", "%20");
    }

    public static void printJsonElement(JsonElement elem, int indent) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            builder.append("\t");
        }

        if (elem.isJsonPrimitive()) {
            System.out.print(builder.toString());
            System.out.println(elem);
        }
        else if (elem.isJsonArray()) {
            System.out.print(builder.toString());
            JsonArray array = elem.getAsJsonArray();
            for (JsonElement arrayElem : array) {
                printJsonElement(arrayElem, indent);
            }
        }
        else {
            for (Entry<String, JsonElement> jsonObject : elem.getAsJsonObject().entrySet()) {
                System.out.print(builder.toString());
                System.out.println(jsonObject.getKey());
                printJsonElement(jsonObject.getValue(), indent+1);
            }
        }
    }

}
