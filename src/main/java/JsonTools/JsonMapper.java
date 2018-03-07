package JsonTools;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import java.util.Map;
import java.util.HashMap;

public class JsonMapper{

        Map<String, String> jsonMap;
        String jsonMapConfig;
        Type mapType = new TypeToken<Map<String, String>>(){}.getType();

        public JsonMapper(String jsonMapConfig){
                this.jsonMapConfig = jsonMapConfig;

                JsonHandler jsonHandler = new JsonHandler(jsonMapConfig);

                this.jsonMap= jsonHandler.getJson(mapType);
        }

        public JsonMapper(){

        }

        public Map<String, String> mapJsonFromString(String json){
                JsonHandler jsonHandler = new JsonHandler();
                return jsonHandler.getJsonFromString(json, mapType);
        }

        public Map<String, String> getJsonMap(){
                return jsonMap;
        }

}
