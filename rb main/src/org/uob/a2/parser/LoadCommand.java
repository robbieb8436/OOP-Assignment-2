package org.uob.a2.parser;

import org.uob.a2.engine.*;
import org.uob.a2.model.*; 
import org.uob.a2.parser.*;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class LoadCommand extends Command {

    public LoadCommand(List<String> words) {
        super(words);
    }

    @Override
    public String execute(Context ctx) {
		// check for a filename given
		if (words.size() < 2) {
            return "Usage: load <filename>";
        }
        
        String filename = words.get(1);
		
        return ctx.getEngine().load(filename);
    }
}