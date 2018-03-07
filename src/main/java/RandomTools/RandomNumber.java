package RandomTools;

import java.util.Random;

public class RandomNumber{
        private Random randomGenerator;
        private int numberLength;

        public RandomNumber(int numberLength){
                this.numberLength = numberLength;
                long seed = System.currentTimeMillis();
                randomGenerator = new Random(seed);
        }

        public RandomNumber(String numberLength){
                try{
                        this.numberLength = Integer.parseInt(numberLength);
                } catch(NumberFormatException nfe){
                        this.numberLength = 0;
                }
                long seed = System.currentTimeMillis();
                randomGenerator = new Random(seed);
        }

        public String getNewNumber(){
                String numberString = "";
                for(int i=0; i<numberLength; i++){
                        numberString += randomGenerator.nextInt(9);
                }
                return numberString;
        }
}
