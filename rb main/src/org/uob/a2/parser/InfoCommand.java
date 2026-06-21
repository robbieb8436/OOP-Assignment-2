package org.uob.a2.parser;

import org.uob.a2.engine.*;
import org.uob.a2.model.*; 
import org.uob.a2.parser.*;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Map;

public class InfoCommand extends Command {
	public InfoCommand(List<String> words) {
        super(words);
    }

    @Override
    public String execute(Context ctx) {
        SimulationState state = ctx.getSimulationState();
        
        // default to "resources" if no argument is given.
        String topic = words.size() > 1 ? words.get(1).toLowerCase() : "resources"; 

        switch (topic) {
            case "resources":
            case "r":
                return displayResources(state);
            
            case "entities":
            case "e":
                return displayEntities(state);

			case "prices":
			case "p":
				return displayPrices(state);
            
            default:
                return "Error: Unknown info topic '" + words.get(1) + "'. Try 'resources' (r) or 'entities' (e).";
        }
    }

    // retrieve and display resources
    private String displayResources(SimulationState state) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n--- RESOURCE INVENTORY ---\n");
        sb.append(String.format("%-20s %10s\n", "RESOURCE TYPE", "AMOUNT"));
        sb.append("---------------------------------\n");

        // get current inventory map
        Map<ResourceType, Integer> inventory = state.getInventory();
        
        for (ResourceType type : ResourceType.values()) {
             int amount = inventory.getOrDefault(type, 0);
             // only display resources the player has
             if (amount > 0) { 
                sb.append(String.format("%-20s %10d\n", type.name(), amount));
             }
        }
        sb.append("---------------------------------\n");

		// show valid resource types
		sb.append("Valid Resource Types:\n");
	    StringBuilder resourceList = new StringBuilder();
    
	    int count = 0;
	    for (ResourceType type : ResourceType.values()) {
	        String typeName = type.name();
        
	        // skip ORE and METAL as they are just for tests
	        if (typeName.equals("ORE") || typeName.equals("METAL")) {
	            continue;
	        }
        
	        if (count > 0) {
	            resourceList.append(", ");
	        }
	        resourceList.append(typeName);
	        count++;
	    }
    
	    // print the final list
	    sb.append(resourceList.toString());
	    sb.append("\n");
        return sb.toString();
    }

	// display the entities the player currently has
    private String displayEntities(SimulationState state) {
        StringBuilder sb = new StringBuilder();
        List<Entity> entities = state.getEntities();

		// create string for output, and spcae into columns
        sb.append("\n--- ACTIVE ENTITIES (Total: ").append(entities.size()).append(") ---\n");
        sb.append(String.format("%-20s %-15s %10s\n", "NAME", "TYPE", "STATE/LEVEL"));
        sb.append("----------------------------------------------------\n");
        
        if (entities.isEmpty()) {
            return sb.append("No entities built yet. Use 'build' to start.").toString();
        }

        for (Entity e : entities) {
			// use java built in class methods to get the name of entity class
            String type = e.getClass().getSimpleName();
            String stateInfo = "";
            
            if (e instanceof Consumer) {
                // display the level for the HyperspaceDrive
                stateInfo = "Level: " + ((Consumer) e).getLevel();
            } else if (e instanceof Producer) {
                // display output resource
                stateInfo = ((Producer) e).getProduct().name();
            } else if (e instanceof Converter) {
                // display conversion type
                stateInfo = ((Converter) e).getInput().name() + " -> " + ((Converter) e).getOutput().name();
            }

            sb.append(String.format("%-20s %-15s %10s\n", e.getName(), type, stateInfo));
        }
        sb.append("----------------------------------------------------\n");
        return sb.toString();
    }

	private String displayPrices(SimulationState state) {
		StringBuilder sb = new StringBuilder();
        sb.append("\n=======================================================\n");
        sb.append("           SPACE MINING SIMULATION OVERVIEW\n");
        sb.append("=======================================================\n");
        
        // --- PRODUCERS ---
        sb.append("\n--- PRODUCERS (Generate Raw Resources) ---\n");
        sb.append("-------------------------------------------------------\n");
        
        sb.append("Lunar Drill:\n");
        sb.append("  | Produces: LUNAR_STONE (10)\n");
        sb.append("  | Cost:     500 CREDIT\n");
        sb.append("\n");
        
        sb.append("Asteroid Mine:\n");
        sb.append("  | Produces: GOLD_ORE (10)\n");
        sb.append("  | Cost:     500 CREDIT\n");
        sb.append("\n");
        
        sb.append("Geode Scanner:\n");
        sb.append("  | Produces: GEODE_FRAGMENT (5)\n");
        sb.append("  | Cost:     2 GOLD_BAR\n");
        sb.append("\n");
        
        sb.append("Tachyon Harvester:\n");
        sb.append("  | Produces: TACHYON_FRAGMENT (5)\n");
        sb.append("  | Cost:     5 LUNAR_STONE, 5 GOLD_BAR\n");
        
        // --- CONVERTERS ---
        sb.append("\n--- CONVERTERS (Refine Materials) ---\n");
        sb.append("-------------------------------------------------------\n");
        
        sb.append("Refining Forge:\n");
        sb.append("  | Converts: GOLD_ORE (10) -> GOLD_BAR (1)\n");
        sb.append("  | Cost:     10 LUNAR_STONE\n");
        sb.append("\n");
        
        sb.append("Crystal Synthesiser:\n");
        sb.append("  | Converts: GEODE_FRAGMENT (10) -> GEODE_CRYSTAL (1)\n");
        sb.append("  | Cost:     10 GOLD_BAR\n");
        sb.append("\n");
        
        sb.append("Tachyon Amplifier:\n");
        sb.append("  | Converts: TACHYON_FRAGMENT (10) -> TACHYON_CRYSTAL (1)\n");
        sb.append("  | Cost:     1 GEODE_CRYSTAL, 1 GOLD_BAR\n");
        
        // --- CONSUMER ---
        sb.append("\n--- CONSUMER (End Goal) ---\n");
        sb.append("-------------------------------------------------------\n");
        
        sb.append("Hyperspace Drive:\n");
        sb.append("  | Goal:     Consume crystals to increase internal Level.\n");
        sb.append("  | Consumes: GEODE_CRYSTAL (5) + TACHYON_CRYSTAL (5) per Level\n");
        sb.append("  | Action:   'launch <name>' to perform JUMP for large CREDIT influx.\n");
        sb.append("  | Cost:     10 GEODE_CRYSTAL, 10 TACHYON_CRYSTAL, 50 GOLD_BAR\n");
        sb.append("=======================================================\n");
        
        return sb.toString();
	}
}