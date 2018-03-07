package RandomTools;

import java.util.Random;

public class RandomCompoundNumber{
        private RandomNumber randomNumberFactory;
        private RandomListPicker randomListPicker;

        public RandomCompoundNumber(String file, String numberLength){
                this.randomListPicker = new RandomListPicker(file);
                this.randomNumberFactory = new RandomNumber(numberLength);
        }

        public String getNewNumber(){
                return randomListPicker.randomPick() + randomNumberFactory.getNewNumber();
        }
}
