package SeleniumDSLUI;

import java.io.PrintWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;

public class FileSaver{

	public static void saveToFile(String fileName, String data){
		try (PrintWriter out = new PrintWriter(new FileOutputStream(new File(fileName),true))) {
		    out.println(data);
		} catch(FileNotFoundException exIO) {
			exIO.printStackTrace();
		}	
	}
}
