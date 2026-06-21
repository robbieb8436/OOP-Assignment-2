package org.uob.a2.parser;

import org.uob.a2.engine.*;
import org.uob.a2.model.*; 
import org.uob.a2.parser.*;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import org.uob.a2.model.HyperspaceDrive;

public class LaunchCommand extends Command {

    public LaunchCommand(List<String> words) {
        super(words);
    }

    @Override
    public String execute(Context ctx) {
		SimulationState state = ctx.getSimulationState();
        
        // ensure a name argument
        if (words.size() < 2) {
            return "Usage: launch <Hyperspace Drive Name>";
        }
        
        String targetName = words.get(1);

        // iterate through the consumers to get  the specified one
        for (Entity entity : state.getConsumers()) {
            if (entity instanceof HyperspaceDrive && entity.getName().equals(targetName)) {
				// cast type to make sure it can access the methods properly
                return ((HyperspaceDrive) entity).launch(ctx);
            }
        }
        return "Error: Could not find Hyperspace Drive with name '" + targetName + "'.";
    }
}