package Main;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class JSONHandler {
    private JSONObject jsonObject;
    private File file;

    JSONHandler(File file) throws Exception {
        JSONParser jsonParser = new JSONParser();
        this.file = file;
        FileReader fileReader = new FileReader(file);
        jsonObject = (JSONObject) jsonParser.parse(fileReader);
        fileReader.close();
    }

    JSONHandler(String string) throws Exception {
        JSONParser jsonParser = new JSONParser();
        jsonObject = (JSONObject) jsonParser.parse(string);
    }

    Object getFromJSONObject(FieldNames key) throws Exception {
        if (jsonObject.containsKey(key.name())) {
            return jsonObject.get(key.name());
        } else {
            throw new Exception("Key:" + key.name() + " doesn't exist in JsonObject");
        }
    }

    public int getInt(FieldNames key) throws Exception {
        return ((Long) getFromJSONObject(key)).intValue();
    }

    public String getString(FieldNames key) throws Exception {
        return (String) getFromJSONObject(key);
    }


    public boolean getBoolean(FieldNames key) throws Exception {
        return (boolean) getFromJSONObject(key);
    }

    void put(FieldNames key, Object value){
        jsonObject.put(key.name(), value);
    }

    void set(FieldNames key, Object value) throws Exception {
        if(file==null){
            throw new Exception("File is null");
        }
        jsonObject.put(key.name(), value);
        //System.out.println(jsonObject.toJSONString());
        FileWriter fileWriter=new FileWriter(file);
        fileWriter.write(jsonObject.toJSONString());
        fileWriter.close();
    }
}
