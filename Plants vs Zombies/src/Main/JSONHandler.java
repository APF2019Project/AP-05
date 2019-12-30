package Main;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class JSONHandler {
    private JSONObject jsonObject;

    JSONHandler(File file) throws Exception {
        JSONParser jsonParser = new JSONParser();
        jsonObject = (JSONObject) jsonParser.parse(new FileReader(file));
    }

    JSONHandler(String string) throws Exception {
        JSONParser jsonParser = new JSONParser();
        jsonObject = (JSONObject) jsonParser.parse(string);
    }

    private Object getFromJSONObject(FieldNames key) throws Exception {
        if (jsonObject.containsKey(key.name())) {
            return jsonObject.get(key.name());
        } else {
            throw new Exception("Key:" + key.name() + " doesn't exist in JsonObject");
        }
    }

    int getInt(FieldNames key) throws Exception {
        return ((Long) getFromJSONObject(key)).intValue();
    }

    String getString(FieldNames key) throws Exception {
        return (String) getFromJSONObject(key);
    }


    boolean getBoolean(FieldNames key) throws Exception {
        return (boolean) getFromJSONObject(key);
    }

    JSONHandler getJSONHandler(FieldNames key) throws Exception {
        return new JSONHandler(getString(key));
    }

}
