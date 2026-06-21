package org.uob.a2.parser;

import org.uob.a2.engine.*;
import org.uob.a2.model.*; 
import org.uob.a2.parser.*;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class InvalidCommand extends Command {

    public InvalidCommand(List<String> words) {
        super(words);
    }

    @Override
    public String execute(Context ctx) {
		return "Invalid command " + words.get(0) + "! See 'help' for valid commands.";
    }
}