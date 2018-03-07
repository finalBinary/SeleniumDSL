package SeleniumDSL;

import RandomTools.*;
import JsonTools.*;

import java.io.File;

import java.util.Map;
import java.util.HashMap;

interface ProcessingMethod {
	public String method();
}

public class ValueMapper{
	public static final String basePath = new File("").getAbsolutePath();
	public static final String JSON_MAP_CONFIG = basePath+"/src/main/resources/DSLConfigs/jsonMapConfig.json";
	public static final String RANDOM_COMPOUND_NUMBER = "#randomCompoundNumber";
	public static final String RANDOM_NUMBER = "#randomNumber";
	public static final String COMPOUND = "#compound";
	public static final String FILE = "#file";

	Map<String, ProcessingMethod> methodMap;
	RandomListPicker randomFirstName;
	Map<String, String> jsonMap;

	public ValueMapper(){
		methodMap = new HashMap<String, ProcessingMethod>();
		jsonMap = (new JsonMapper(JSON_MAP_CONFIG)).getJsonMap();
		initMap();
	}

	public Map<String, ProcessingMethod> getMap(){
		return methodMap;
	}

	public String parseValue(String value){
                for (String key : methodMap.keySet()) {
                        if(key.equals(value)){
                                return methodMap.get(key).method();
                        }
                }
                return value;
        }

	private void initMap(){
		for(String key : jsonMap.keySet()){
			 if(key.equals(RANDOM_COMPOUND_NUMBER)){
                                final  Map<String, String> jsonRandomCompoundMap = (new JsonMapper()).mapJsonFromString(jsonMap.get(key));
				final String fileName = jsonRandomCompoundMap.get(FILE);
				
				jsonRandomCompoundMap.remove(FILE);
                                if(jsonRandomCompoundMap.size() == 1){
                                        final Map.Entry<String,String> randomCompoundMapEntry = jsonRandomCompoundMap.entrySet().iterator().next();
                                        
						methodMap.put(randomCompoundMapEntry.getKey(), new ProcessingMethod() {
						RandomCompoundNumber randomCompoundNumber = new RandomCompoundNumber(fileName, randomCompoundMapEntry.getValue());
                                                public String method() { return randomCompoundNumber.getNewNumber();}
                                        });
                                }

			} else if(key.equals(RANDOM_NUMBER)){
                                final  Map<String, String> jsonRandomMap = (new JsonMapper()).mapJsonFromString(jsonMap.get(key));
                              	
				if(jsonRandomMap.size() == 1){
					final Map.Entry<String,String> randomMapEntry = jsonRandomMap.entrySet().iterator().next();
                                	methodMap.put(randomMapEntry.getKey(), new ProcessingMethod() {
						RandomNumber randomNumber = new RandomNumber(randomMapEntry.getValue());
                                		public String method() { return randomNumber.getNewNumber();}
                                	});
				}
                              
                        } else if(key.equals(COMPOUND)){
				final  Map<String, String> jsonCompoundMap = (new JsonMapper()).mapJsonFromString(jsonMap.get(key));
				final RandomListListPicker listListPicker = new RandomListListPicker(jsonCompoundMap.get(FILE));

				jsonCompoundMap.remove(FILE);
				for(String compoundKey : jsonCompoundMap.keySet()){
					methodMap.put(compoundKey, new ProcessingMethod() {
                                        	public String method() { return listListPicker.randomPick(jsonCompoundMap.get(compoundKey));}
                                	});
				}
						
			} else {
				methodMap.put(key, new ProcessingMethod() {
					RandomListPicker randomListPicker = new RandomListPicker(jsonMap.get(key));
 	  				public String method() { return randomListPicker.randomPick();}
				});
			}
		}
	}
}

