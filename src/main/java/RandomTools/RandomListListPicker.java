package RandomTools;

import JsonTools.*;
import java.util.Random;

public class RandomListListPicker{

        String[][] values;
        private Random randomGenerator;
        private int randomNumber;
        private int counter = 0;

        public RandomListListPicker(String filePath){
                JsonHandler jsonHandler = new JsonHandler(filePath);

                long seed = System.currentTimeMillis();
                randomGenerator = new Random(seed);

                this.values = jsonHandler.getJson(String[][].class);
                this.randomNumber = randomGenerator.nextInt(values.length-1);
        }

        public String randomPick(String index){
                try{
                        return randomPick(Integer.parseInt(index));
                } catch(NumberFormatException nfe){
                        return "";
                }
        }

        public String randomPick(int index){
                if(index > values[0].length - 1){
                        return "";
                }

                if(counter > values[0].length - 1){
                        this.randomNumber = randomGenerator.nextInt(values.length-1);
                        counter = 0;
                }
                counter++;

                return values[randomNumber][index];
        }
}
