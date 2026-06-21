package org.uob.a2.parser;

import org.uob.a2.engine.*;
import org.uob.a2.model.*; 
import org.uob.a2.parser.*;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class GraphCommand extends Command {

    public GraphCommand(List<String> words) {
        super(words);
    }

    @Override
    public String execute(Context ctx) {
		if (words.size() < 2) {
            return "Usage: graph <resource_type>. Example: graph GOLD_BAR";
        }
        
        String resourceTypeString = words.get(1);

		// call the engine graph method
        return ctx.getEngine().graph(resourceTypeString);
    }
}