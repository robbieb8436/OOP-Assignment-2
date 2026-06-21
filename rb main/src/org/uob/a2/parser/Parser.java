package org.uob.a2.parser;

import org.uob.a2.*;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import org.uob.a2.parser.*;


public class Parser {
	public Command parse(String command) {
	        if (command == null || command.trim().isEmpty()) {
	            return new TickCommand(new java.util.ArrayList<>());
	        }
		
	        String[] tokens = command.trim().split(" ");
	        List<String> words = Arrays.asList(tokens);
	        String commandType = tokens[0].toLowerCase();
		
	        switch (commandType) {
			    case "build":
			    case "b":
			        return new BuildCommand(words);

			    case "info":
			    case "i":
			        return new InfoCommand(words);

			    case "help":
			    case "h":
			        return new HelpCommand(words);

			    case "quit":
			    case "q":
			        return new QuitCommand(words);
	
			    case "tick":
			    case "t":
			        return new TickCommand(words);

			    case "cheat":
				case "c":
			        return new CheatCommand(words);
	
			    case "graph":
			    case "g":
			        return new GraphCommand(words);

			    case "save":
			    case "s":
			        return new SaveCommand(words);

			    case "load":
			    case "l":
			        return new LoadCommand(words);

			    case "launch": 
			        return new LaunchCommand(words);

		    default:
		        return new InvalidCommand(words);
		}
	}
}