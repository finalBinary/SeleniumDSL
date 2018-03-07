package SeleniumDSLMain;

import SeleniumDSL.*;
import SeleniumDSLUI.*;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumTester {
	public static void main(String[] args) {

		SeleniumHandler seleniumHandler = new SeleniumHandler();
		
		DSLParser parser = new DSLParser(seleniumHandler);
		parser.parse();
		parser.run();
	}
}
