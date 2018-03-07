package JsonTools;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import java.io.FileReader;

public class JsonHandler{

	private GsonBuilder builder;
	private JsonReader jsonReader;
	private String path;

	public JsonHandler(String filePath){
		this.builder = new GsonBuilder();
		readJsonFile(filePath);
		this.path = filePath;
	}

	public JsonHandler(){
		 this.builder = new GsonBuilder();
	}

	public void registerTypeAdapter(Class cls, Object obj){
		this.builder.registerTypeAdapter(cls, obj);
	}

	public void readJsonFile(String filePath){
		try{
			this.jsonReader = new JsonReader(new FileReader(filePath));
		} catch(Exception e){
			System.out.println("ERROR");
			e.printStackTrace();
			System.exit(1);
		}
	}

	private <T> T getJsonFromString(String jsonString, Class<T> cls){
		T jsonObject = builder.create().fromJson(jsonString, cls);
		return jsonObject;
	}

	public <T> T getJsonFromString(String jsonString, Type type){
                T jsonObject = builder.create().fromJson(jsonString, type);
                return jsonObject;
        }

	private <T> T getJsonFromReader(JsonReader reader, Class<T> cls){
		T jsonObject = builder.create().fromJson(jsonReader, cls);
		return jsonObject;
	}

	private <T> T getJsonFromReader(JsonReader reader, Type type){
                T jsonObject = builder.create().fromJson(jsonReader, type);
                return jsonObject;
        }

	public <T> T getJsonFromFile(String filePath, Class<T> cls){
		readJsonFile(filePath);
		return getJsonFromReader(jsonReader, cls);
	}

	public <T> T getJson(Class<T> cls){
		readJsonFile(path);
		return getJsonFromReader(jsonReader, cls);
	}

	public <T> T getJson(Type type){
                readJsonFile(path);
                return getJsonFromReader(jsonReader, type);
        }
}
