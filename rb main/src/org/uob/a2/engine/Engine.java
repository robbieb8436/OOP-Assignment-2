package org.uob.a2.engine;

import org.uob.a2.model.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.EnumMap;

public class Engine {

	// attributes
	private final SimulationState state;
    private int currentTick;

	// constructor
    public Engine(SimulationState state) { 
        this.state = state;
        this.currentTick = 0;
        
        state.updateResource(ResourceType.CREDITS, 1000); 
    }

	// getters
    public int getCurrentTick() { 
        return this.currentTick;
    }

	public SimulationState getSimulationState() {
        return this.state;
    }

	// next tick to advance tick and show report of what occured
	public String nextTick() {
        StringBuilder tickSummary = new StringBuilder();
        this.currentTick++;
        tickSummary.append("\n--- ADVANCING TO TICK ").append(this.currentTick).append(" ---\n");

        List<Tickable> entities = state.getAllTickableEntities();

        for (Tickable entity : entities) {
            String result = entity.tick(new Context(this, this.state));
            
            if (result != null && !result.trim().isEmpty()) {
                tickSummary.append("* ").append(result).append("\n");
            }
        }
        
        state.updateHistory(); 

        tickSummary.append("--- TICK ").append(this.currentTick).append(" COMPLETE ---\n");
        return tickSummary.toString();
    }

	// graph method called from GraphCommand.java
	public String graph(String resourceTypeString) {
		// get the list of resource history
	    List<Map<ResourceType, Integer>> history = this.state.getHistory();

	    if (history.isEmpty()) {
		     return "Cannot generate graph: Simulation history is empty (Run 'tick' (t) a few times first).";
	    }

	    ResourceType targetResource;
	    try {
			// convert the input string to the enum resource type
	        targetResource = ResourceType.valueOf(resourceTypeString.toUpperCase());
	    } catch (IllegalArgumentException e) {
	        return "Error: Unknown resource type '" + resourceTypeString + "'. Try doing it in all caps.";
	    }

		// max width of the graph
	    final int MAX_GRAPH_WIDTH = 50; 
		// max history length for graph
		final int DISPLAY_LIMIT = 50;
	
		// find where to start in history
		int startIndex = Math.max(0, history.size() - DISPLAY_LIMIT);

	    // this creates a view of the list starting from startIndex
	    history = history.subList(startIndex, history.size());
    
	    // find the maximum value for the scale of the graph (only from the visible history)
	    int maxAmount = 0;
	    for (Map<ResourceType, Integer> snapshot : history) {
	        int amount = snapshot.getOrDefault(targetResource, 0);
		        if (amount > maxAmount) {
	            maxAmount = amount;
	        }
	    }

		// if the resource has not been acquired yet then print error
	    if (maxAmount == 0) {
	        return "Resource " + targetResource.name() + " has not been acquired.";
	    }

		// determine how much of the resource is represented by each #
	    double scaleFactor = (double) maxAmount / MAX_GRAPH_WIDTH;

		// create graph with appropriate scale and spacings
	    StringBuilder graphOutput = new StringBuilder();
	    graphOutput.append("\n--- HISTORY GRAPH FOR ").append(targetResource.name()).append(" ---\n");
	    graphOutput.append(String.format("MAX VALUE: %d (Scale: 1 '#' = %.2f units)\n", maxAmount, scaleFactor));
	    graphOutput.append("---------------------------------------------------\n");
	
		// itereate through each snapshot in the history (only the last 50 now)
	    for (int i = 0; i < history.size(); i++) {
	        Map<ResourceType, Integer> snapshot = history.get(i);
	        int currentAmount = snapshot.getOrDefault(targetResource, 0);
	        // calculate the bar length to the nearest unit
	        int barLength = (int) Math.round(currentAmount / scaleFactor);
        
	        String bar = "#".repeat(barLength);
	        // offset tick num based on subset of history
	        int tickNumber = startIndex + i + 1;
        
	        // format and output
	        graphOutput.append(String.format("[T %2d] %6d | %s\n", tickNumber, currentAmount, bar));
	    }
    
	    graphOutput.append("---------------------------------------------------\n");
    
	    return graphOutput.toString();
	}

	// save method called from SaveCommand.java
	public String save(String filename) {
	    SimulationState state = this.state;
		// directory for files to pass test
		final String DATA_DIR = "data";
	    File dataDir = new File(DATA_DIR);
	    if (!dataDir.exists()) {
	        // creates the directory if it doesn't exist
	        dataDir.mkdirs(); 
	    }
    
        // construct the full file path: data/filename
	    String fullPath = DATA_DIR + File.separator + filename;
    
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath))) {
	        
	        // save tick count
	        writer.write("CurrentTick," + this.currentTick);
			// wanted to have this but the test said no
			//writer.write("TICK:" + this.currentTick);
	        writer.newLine();

	        // save resources
	        writer.write("RESOURCES:");
	        writer.newLine();
        
	        // write each resource and its amount on a new line 
	        for (Map.Entry<ResourceType, Integer> entry : state.getInventory().entrySet()) {
	            writer.write(entry.getKey().name() + "," + entry.getValue());
	            writer.newLine();
	        }

	        // save entities
	        writer.write("ENTITIES:");
	        writer.newLine();
        
	        // use the toCSV() method for every single entity instance
	        for (Entity entity : state.getEntities()) {
	            writer.write(entity.toCSV());
	            writer.newLine();
	        }

	        // save the graph
			writer.write("HISTORY:");
	        writer.newLine();
        
	        // write the number of ticks recorded
	        writer.write("TICKS_RECORDED:" + state.getHistory().size());
	        writer.newLine();

	        // loop through each snapshot in the history list
	        for (Map<ResourceType, Integer> snapshot : state.getHistory()) {
	            // convert the entire map to a string representation for one-line saving
	            // format: RESOURCE1:AMOUNT,RESOURCE2:AMOUNT,..
	            StringBuilder historyLine = new StringBuilder();
            
	            boolean first = true;
	            for (Map.Entry<ResourceType, Integer> entry : snapshot.entrySet()) {
	                if (!first) {
	                    historyLine.append(",");
	                }
	                historyLine.append(entry.getKey().name()).append(":").append(entry.getValue());
	                first = false;
	            }
	            writer.write(historyLine.toString());
	            writer.newLine();
	        }
        
	        writer.flush();
        
	        return "Simulation state successfully saved to " + filename;
        
	    } catch (IOException e) {
	        // handle errors like permission denied or file not found
	        return "ERROR: Failed to save file " + filename + ". " + e.getMessage();
	    }
	}

	// load command called from LoadCommand.java
	public String load(String filename) { 
		String fullPath = "data" + File.separator + filename;
        try (BufferedReader reader = new BufferedReader(new FileReader(fullPath))) {
            String line;
            String currentSection = "";
            
            // set a new state variable
            SimulationState newState = new SimulationState();
            
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                //if (line.startsWith("TICK:")) {
				if (line.startsWith("CurrentTick,")) {
                    // load tick count
                    String[] parts = line.split(",");
        
			        if (parts.length > 1) {
			            try {
			                this.currentTick = Integer.parseInt(parts[1].trim()); 
			            } catch (NumberFormatException e) {
			                throw new IOException("Failed to parse Tick count: " + parts[1]);
			            }
			        }
                    currentSection = ""; 
                } else if (line.equals("RESOURCES:")) {
                    currentSection = "RESOURCES";
                } else if (line.equals("ENTITIES:")) {
                    currentSection = "ENTITIES";
                } else if (line.equals("HISTORY:")) {
                    currentSection = "HISTORY";
                } 
                
                // load data based on section
                else if (currentSection.equals("RESOURCES")) {
                    // format: RESOURCE_TYPE:AMOUNT 
                    String[] parts = line.split(",");
                    if (parts.length == 2) {
                        try {
                            ResourceType type = ResourceType.valueOf(parts[0].trim());
                            int amount = Integer.parseInt(parts[1].trim());
                            newState.updateResource(type, amount);
                        } catch (IllegalArgumentException ignored) {
                            // skip bad resource lines
                        }
                    }
                } else if (currentSection.equals("ENTITIES")) {
                    // format: TYPE,NAME,PRODUCT,AMOUNT,
                    Entity entity = createEntityFromCSV(line);
                    if (entity != null) {
                        newState.addEntity(entity);
                    }
                } else if (currentSection.equals("HISTORY")) {
                    if (!line.startsWith("TICKS_RECORDED:")) {
                        loadHistorySnapshot(newState, line);
                    }
                }
            }
            
            // overwrite the old state with the newly loaded state
			this.state.setStateFromLoad(
			    newState.getInventory(),
			    newState.getProducers(),
			    newState.getConverters(),
			    newState.getConsumers(),
			    newState.getHistory());
			
            return "Simulation state successfully loaded from " + filename + ". Current Tick: " + this.currentTick;
            
        } catch (IOException e) {
            return "ERROR: Failed to load file " + filename + ". File may not exist or is corrupted.";
        } catch (Exception e) {
            return "ERROR: An unexpected error occurred during loading: " + e.getMessage();
        }
    }
    
    // helper methods for loading

	// reconstruct the entities
    private Entity createEntityFromCSV(String csvLine) {
        String[] parts = csvLine.split(",");
        if (parts.length < 4) return null;

        String type = parts[0];
        String name = parts[1];
        
        // this large switch handles recreating the correct entity type
        switch (type) {
            case "LunarDrill":
            case "AsteroidMine":
            case "GeodeScanner":
            case "TachyonHarvester":
                // all Producers use constructor: name, productType, amount
				// only need name as rest is defined in constructor
                if (type.equals("LunarDrill")) return new LunarDrill(name); 
                if (type.equals("AsteroidMine")) return new AsteroidMine(name); 
                if (type.equals("GeodeScanner")) return new GeodeScanner(name); 
                if (type.equals("TachyonHarvester")) return new TachyonHarvester(name); 
                break;
            
            case "RefiningForge":
            case "CrystalSynthesiser":
            case "TachyonAmplifier":
                // all Converters use constructor: name, input, inAmount, output, outAmount
				// only need name as rest is defined in constructor
                if (type.equals("RefiningForge")) return new RefiningForge(name); 
                if (type.equals("CrystalSynthesiser")) return new CrystalSynthesiser(name); 
                if (type.equals("TachyonAmplifier")) return new TachyonAmplifier(name); 
                break;
                
            case "HyperspaceDrive":
                int level = Integer.parseInt(parts[2].trim());
                
                HyperspaceDrive drive = new HyperspaceDrive(name);
                // CRITICAL: manually set the internal state field
                drive.setLevel(level); 
                return drive;
        }
		// return null if doesn't match
		// basically skipping
        return null;
    }

	// resave the history back to simulation state
    private void loadHistorySnapshot(SimulationState state, String historyLine) {
        Map<ResourceType, Integer> snapshot = new EnumMap<>(ResourceType.class);
        String[] entries = historyLine.split(",");
        
        for (String entry : entries) {
            String[] parts = entry.split(":");
            if (parts.length == 2) {
                try {
                    ResourceType type = ResourceType.valueOf(parts[0]);
                    int amount = Integer.parseInt(parts[1]);
                    snapshot.put(type, amount);
                } catch (IllegalArgumentException ignored) {
                    // ignore corrupted entries
                }
            }
        }
        if (!snapshot.isEmpty()) {
            state.getHistory().add(snapshot);
        }
    }

    // USED FOR TESTING
    public void initialiseDefaults() {
        //ADD ALL THE ENTITIES YOU CREATE TO THE PRODUCER, CONVERTER AND CONSUMER LISTS IN SIMULATIONSTATE HERE.
		// Defaults for testing
		this.state.addEntity(new LunarDrill("Default_LunarDrill"));
	    this.state.addEntity(new AsteroidMine("Default_AsteroidMine"));
	    this.state.addEntity(new GeodeScanner("Default_GeodeScanner"));
	    this.state.addEntity(new TachyonHarvester("Default_TachyonHarvester"));

	    this.state.addEntity(new RefiningForge("Default_RefiningForge"));
	    this.state.addEntity(new CrystalSynthesiser("Default_CrystalSynthesiser"));
	    this.state.addEntity(new TachyonAmplifier("Default_TachyonAmplifier"));

	    this.state.addEntity(new HyperspaceDrive("Default_HyperspaceDrive"));
    }

    

}