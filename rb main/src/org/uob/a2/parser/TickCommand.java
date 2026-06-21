package org.uob.a2.parser;

import org.uob.a2.engine.*;
import org.uob.a2.model.*; 
import org.uob.a2.parser.*;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class TickCommand extends Command {

    public TickCommand(List<String> words) {
        super(words);
    }
	
    @Override
    public String execute(Context ctx) {
        return ctx.getEngine().nextTick();
    }
}