package SeleniumDSL;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

class Program implements Executable{
	List<Executable> commands = new ArrayList<Executable>();

	public void add(Executable exe){
		commands.add(exe);
	}

	public void add(List<Executable> exeList){
		commands.addAll(exeList);
	}

	public Object execute(){
		for(Executable command : commands){
			System.out.println("executing");
			command.execute();
		}
		return null;		
	}
}
