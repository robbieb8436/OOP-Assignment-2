package org.uob.a2.parser;

import org.uob.a2.engine.*;
import org.uob.a2.model.*; 
import org.uob.a2.parser.*;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class SaveCommand extends Command {

    public SaveCommand(List<String> words) {
        super(words);
    }

    @Override
    public String execute(Context ctx) {
		// check a filename is given
		if (words.size() < 2) {
            return "Usage: save <filename>";
        }
        
        String filename = words.get(1);
        
        return ctx.getEngine().save(filename);
    }
}