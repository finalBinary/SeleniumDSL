package SeleniumDSLUI;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class InputWaiter {
        public static void waitForInput(String input){
                InputStream is = null;
                BufferedReader br = null;

                try {

                        is = System.in;
                        br = new BufferedReader(new InputStreamReader(is));

                        String line = null;

                        while ((line = br.readLine()) != null) {
                                if (line.equalsIgnoreCase(input)) {
                                        break;
                                }
                                System.out.println("Line entered : " + line);
                        }

                } catch (IOException ioe) {

                        System.out.println("Exception while reading input " + ioe);

                } finally {

                        try {
                                if (br != null && false) {
                                        br.close();
                                }
                        }catch (IOException ioe) {
                                System.out.println("Error while closing stream: " + ioe);
                        }
                }
        }

        public static void waitForInput(){
                System.out.println("in wait");
        }
}

