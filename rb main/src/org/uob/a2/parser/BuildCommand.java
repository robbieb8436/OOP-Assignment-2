package org.uob.a2.parser;

import org.uob.a2.engine.*;
import org.uob.a2.model.*; 
import org.uob.a2.parser.*;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.ArrayList;

public class BuildCommand extends Command {

    public BuildCommand(List<String> words) {
        super(words);
    }

    @Override
    public String execute(Context ctx) {
        if (words.size() < 2) {
            return "Usage: build <entity_type> (e.g., 'build lunar drill')";
        }

        String entityType = words.get(1).toLowerCase();
        
        String entityTypeClarifier = "";
        if (words.size() > 2) {
            entityTypeClarifier = words.get(2).toLowerCase();
        }

        SimulationState state = ctx.getSimulationState();
        Entity newEntity = null;
        String fullEntityType = ""; 

        String uniqueName = entityType + "_" + state.getEntities().size();

		// check what they want to build and clarify they fully specified it
        try {
            switch (entityType) {
                case "lunar":
                    if (entityTypeClarifier.equals("drill")) {
                        fullEntityType = "Lunar Drill";
                        newEntity = new LunarDrill(uniqueName);
                    }
                    break;
                case "asteroid":
                    if (entityTypeClarifier.equals("mine")) {
                        fullEntityType = "Asteroid Mine";
                        newEntity = new AsteroidMine(uniqueName);
                    }
                    break;
                case "geode":
                    if (entityTypeClarifier.equals("scanner")) {
                        fullEntityType = "Geode Scanner";
                        newEntity = new GeodeScanner(uniqueName);
                    }
                    break;
                case "tachyon":
                    if (entityTypeClarifier.equals("harvester")) {
                        fullEntityType = "Tachyon Harvester";
                        newEntity = new TachyonHarvester(uniqueName);
                    } else if (entityTypeClarifier.equals("amplifier")) {
                        fullEntityType = "Tachyon Amplifier";
                        newEntity = new TachyonAmplifier(uniqueName);
                    }
                    break;
                case "refining":
                    if (entityTypeClarifier.equals("forge")) {
                        fullEntityType = "Refining Forge";
                        newEntity = new RefiningForge(uniqueName);
                    }
                    break;
                case "crystal":
                    if (entityTypeClarifier.equals("synthesiser")) { 
                        fullEntityType = "Crystal Synthesiser";
                        newEntity = new CrystalSynthesiser(uniqueName);
                    }
                    break;
                case "hyperspace":
                    if (entityTypeClarifier.equals("drive")) {
                        fullEntityType = "Hyperspace Drive";
                        newEntity = new HyperspaceDrive(uniqueName);
                    }
                    break;
	            default:
	                return "Unknown entity type: " + entityType;
            }

            if (newEntity == null) {
                 return "Unknown entity or missing clarifier: try 'build lunar drill'.";
            }

			// once created and passed checking ensure they have the resources
            Map<ResourceType, Integer> cost = newEntity.getBuildCost();

			// use specifiers to produce a unique error message based on required resources
            for (Map.Entry<ResourceType, Integer> entry : cost.entrySet()) {
                if (state.getResourceAmount(entry.getKey()) < entry.getValue()) {
                    return String.format("Cannot build %s. Insufficient %s (Need: %d, Have: %d).", 
                        fullEntityType, 
                        entry.getKey(), 
                        entry.getValue(), 
                        state.getResourceAmount(entry.getKey())
                    );
                }
            }

			// if they do then remove resources
            for (Map.Entry<ResourceType, Integer> entry : cost.entrySet()) {
                state.removeResource(entry.getKey(), entry.getValue());
            }

            state.addEntity(newEntity);
            
            return "Successfully built " + fullEntityType + " (" + newEntity.getName() + ").";
            
        } catch (Exception e) {
            return "An error occurred during building: " + e.getMessage() + ". Check if model classes are complete.";
        }
    }
}