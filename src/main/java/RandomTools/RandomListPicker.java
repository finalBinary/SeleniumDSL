package RandomTools;

import JsonTools.*;
import java.util.Random;

public class RandomListPicker{
        String[] values;
        private Random randomGenerator;


        public RandomListPicker(String filePath){
                JsonHandler jsonHandler = new JsonHandler(filePath);

                long seed = System.currentTimeMillis();
                randomGenerator = new Random(seed);

                this.values = jsonHandler.getJson(String[].class);
        }

        public String randomPick(){
                return values[randomGenerator.nextInt(values.length-1)];
        }
}
