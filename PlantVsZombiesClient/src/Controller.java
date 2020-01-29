import org.json.simple.JSONObject;

import java.io.IOException;

public interface Controller {
    void initJsonInput(JSONObject jsonObject) throws IOException;

    void initializeReOpen() throws IOException;
}
