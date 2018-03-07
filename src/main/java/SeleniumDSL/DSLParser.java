package SeleniumDSL;

import SeleniumDSLUI.*;

import java.util.List;
import java.util.ArrayList;

import org.openqa.selenium.WebElement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

public class DSLParser{

	static final String basePath = new File("").getAbsolutePath();
	private static final String file = basePath+"/src/main/resources/test.txt";
	private SeleniumHandler seleniumHandler;
	private ValueMapper valueMap = new ValueMapper();
        private Program prog = new Program();

	// Syntax Definition
	private final String TARGET = "target";
	private final String SEPARATOR = "\\.";
	private final String OPENING_ARGUMENT = "(";
	private final String CLOSING_ARGUMENT = ")";
	private final String VARIABLE = "var";
	private final String INPUT_COMMAND = "input";
	private final String LABEL_COMMAND = "label";
	private final String VALUE_COMMAND = "value";
	private final String KEY_COMMAND = "key";
	private final String WHILE = "while";
	private final String END_WHILE = "end while";
	private final String IF = "if";
	private final String END_IF = "end if";
	private final String WAIT_FOR_INPUT = "waitForInput";
	private final String SAVE_TO_FILE = "saveToFile";
	private final String ASSIGN = "=";
	private final String COMPARE = "==";
	private final String STRING_ENCLOSURE = "\"";


	public DSLParser(SeleniumHandler seleniumHandler){
		this.seleniumHandler = seleniumHandler;
	}

	private String getArgument(String statement){
		int openIndex = statement.indexOf(OPENING_ARGUMENT);
		int openCount = 0;

		for(int i = openIndex+1; i<statement.length(); i++){
			if(statement.substring(i, i+1).equals(CLOSING_ARGUMENT) && openCount == 0){
				return statement.substring(openIndex+1, i);
			} else if(statement.substring(i, i+1).equals(CLOSING_ARGUMENT)){
				openCount--;
			} else if(statement.substring(i, i+1).equals(OPENING_ARGUMENT)){
				openCount++;
			}
		}
		return null;
	}

	public void run(){
		prog.execute();
	}

	public void parse(){
		prog.add(parseFileTo(null));
	}

	public List<Executable> parseFileTo(String stopLine){
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			return parseTo(stopLine, br);
		} catch(IOException IOEx){
			IOEx.printStackTrace();
		}

		return null;
	}

	public List<Executable> parseTo(String stopLine, BufferedReader br){
		List<Executable> parsedProgram = new ArrayList<Executable>();
		System.out.println("parsing");
		try {
    			String line;
    			while ((line = br.readLine()) != null && !line.equals(stopLine)) {
       				// process the line.
				if(line.startsWith(TARGET)){
					Executable exe = parseStatement(line);
					if(exe != null){
						System.out.println("adding exe");
						parsedProgram.add(exe);
					};
				} 
				
				else if(line.startsWith(WHILE)){
					System.out.println(line);
					Executable whileCond = parseExpression(getArgument(line));

					Program whileContent = new Program();
					whileContent.add(parseTo(END_WHILE, br));

					Executable whileLoop = new Executable(){
						public String execute(){
							System.out.println("Executing while");
							while(whileCond.execute().equals("true")){
								System.out.println("in while");
								whileContent.execute();
							}
							return null;
						}
					};

					parsedProgram.add(whileLoop);
				}

				else if(line.startsWith(IF)){
					System.out.println(line);
					Executable ifCond = parseExpression(getArgument(line));

					Program ifContent = new Program();
					ifContent.add(parseTo(END_IF, br));

					Executable ifBlock = new Executable(){
						public String execute(){
							System.out.println("Executing if");
							if(ifCond.execute().equals("true")){
								System.out.println("in if");
								ifContent.execute();
							}
							return null;
						}
					};

					parsedProgram.add(ifBlock);
				}

				else if(line.startsWith(WAIT_FOR_INPUT)){
					String input = getArgument(line);
					System.out.println("--- waitForInput: "+input);
					Executable wait = new Executable(){
						public Object execute(){
							System.out.println("waiting...");
							InputWaiter.waitForInput(input);
							return null;
					}
					};
					parsedProgram.add(wait);
				}

				else if(line.startsWith(SAVE_TO_FILE)){
                                        String[] argument = getArgument(line).split(",");
					if(argument.length < 2){
						System.out.println("ERROR: saveToFile not caled with (file name, data)");
						return null;
					}
					String fileName = argument[0].trim();
					String data = (String) parseExpression(argument[1].trim()).execute();
                                        System.out.println("--- saving: "+data);
                                        Executable wait = new Executable(){
                                                public String execute(){
                                                        System.out.println("saving...");
                                                        FileSaver.saveToFile(fileName, data);
                                                        return data;
                                        }
                                        };
                                        parsedProgram.add(wait);
                                }

    			}

		} catch(IOException IOEx){
			IOEx.printStackTrace();
		}

		return parsedProgram;
	}

	public Executable listExecuter(List<Executable> commands){
		return new Executable(){
			public Object execute(){
				for(int i=0; i<commands.size()-1; i++){
					commands.get(i).execute();
				}
				if(commands.size() > 0){
					return commands.get(commands.size()-1).execute();
				}
				return null;
			}
		};
	}

	public Executable parseStatement(String statement){
		List<Executable> commands = new ArrayList<Executable>();
		if(statement.startsWith(TARGET)){
			System.out.println("in statement if");
			System.out.println(statement);
			String[] targetAndCommand = statement.split(SEPARATOR);

			String target_Path = getArgument(targetAndCommand[0]);
			WebElement target = seleniumHandler.getElement(target_Path);
			System.out.println(targetAndCommand.length);

			if(targetAndCommand.length > 1){

				for(int commandIndex=1; commandIndex < targetAndCommand.length; commandIndex++){

			        String commandToken = targetAndCommand[commandIndex];
			        String command = commandToken.split("\\(")[0];
			        String value = valueMap.parseValue(getArgument(commandToken));
				
			        if(command.equals(INPUT_COMMAND)){
					System.out.println("retuning fillInput: "+value);
			                commands.add(new Executable(){
			                                public Object execute(){
								System.out.println("sending value: "+value);
								target.sendKeys(value);
								return target;
			                                }
			                        });
			        } else if(command.equals(KEY_COMMAND)){
			                commands.add(new Executable(){
			                                public Object execute(){
			                                        target.sendKeys(value);
								return target;
			                                }
			                        });
			        } else if(command.equals(LABEL_COMMAND)){
			                commands.add(new Executable(){
			                                public String execute(){
								return target.getText();
			                                }
			                        });
					return listExecuter(commands);
			        } else if(command.equals(VALUE_COMMAND)){
                                        commands.add(new Executable(){
                                                        public String execute(){
								return target.getAttribute("value");
                                                        }
                                                });
					return listExecuter(commands);
                                }

				}
			
			} else if(targetAndCommand.length > 0){
			       
			}

		} else if(statement.startsWith(STRING_ENCLOSURE) && statement.endsWith(STRING_ENCLOSURE)){
			return new Executable(){
				public String execute(){
					return statement.replaceAll("^\"+", "").replaceAll("\"+$", "");
				}
			};
		}

		return listExecuter(commands);
	}

	public Executable parseExpression(String expression){
		System.out.println("parseExpression: "+expression);
		if(expression.contains(COMPARE)){
			System.out.println("in compare expression");
			String[] toCompare = expression.split(COMPARE);
			Executable leftCompare = parseStatement(toCompare[0]);
			Executable rightCompare = parseStatement(toCompare[1]);

			return new Executable(){
				public String execute(){
					System.out.println("leftComp: "+ leftCompare.execute());
					System.out.println("rightComp: "+ rightCompare.execute());
					if(leftCompare.execute().equals(rightCompare.execute())){
						return "true";
					} else {
						return "false";
					}
				}
			};
		}

		return parseStatement(expression);
	}
}
