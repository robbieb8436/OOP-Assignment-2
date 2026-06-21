package org.uob.a2.parser;

import org.uob.a2.engine.*;
import org.uob.a2.model.*; 
import org.uob.a2.parser.*;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;


public class CheatCommand extends Command {

    private static final int CHEAT_AMOUNT = 10000;

    public CheatCommand(List<String> words) {
        super(words);
    }

    @Override
    public String execute(Context ctx) {
        SimulationState state = ctx.getSimulationState();

		// string builder to make the list of items better
        StringBuilder sb = new StringBuilder();
        sb.append("--- CHEAT ACTIVATED ---\n");
        sb.append("10000 of the following resources added:\n");

        // iterate over every defined ResourceType
        for (ResourceType resource : ResourceType.values()) {
            state.addResource(resource, CHEAT_AMOUNT);
            sb.append("* ").append(resource.name()).append("\n");
        }
        
        sb.append("-----------------------\n");
        sb.append("You are now fully stocked for testing your builds and conversions.");
        
        return sb.toString();
    }
}