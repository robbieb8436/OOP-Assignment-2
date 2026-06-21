package org.uob.a2.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uob.a2.model.ResourceType;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import static org.junit.jupiter.api.Assertions.*;

public class EngineTest {

    private SimulationState state;
    private Engine engine;

    @BeforeEach
    void setUp() {
        state = new SimulationState();
        engine = new Engine(state);
    }

    @Test
    void engineStartTest() {
        int mark = 0;
        
        mark += (0 == engine.getCurrentTick()) ? 1 : 0;
       
        System.out.println("AUTOMARK::Engine.engineStartTest: " + mark + "/1");
    }

    @Test
    void nextTickTest() {
        int mark = 0;

        String display1 = engine.nextTick();
        mark += (1 == engine.getCurrentTick()) ? 1 : 0;
        mark += (1 == state.getHistory().size()) ? 1 : 0;

        String display2 = engine.nextTick();
        mark += (2 == engine.getCurrentTick()) ? 1 : 0;
        mark += (2 == state.getHistory().size()) ? 1 : 0;
        
        mark += (display1 != null) ? 1 : 0;
        mark += (display2 != null) ? 1 : 0;
       
        System.out.println("AUTOMARK::Engine.nextTickTest: " + mark + "/6");
    }

    @Test
    void saveTest() throws Exception {
        // ensure data dir exists for the test
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            assertTrue(dataDir.mkdirs(), "Failed to create data directory for tests");
        }
        int mark = 0;
        
        // set some extra resources
        state.addResource(ResourceType.METAL, 50);
        state.addResource(ResourceType.ORE, 7);

        String filename = "engine_save_test.csv";
        String result = engine.save(filename);

        File outFile = new File(dataDir, filename);
       

        // Read file and check it contains the expected lines
        try (BufferedReader br = new BufferedReader(new FileReader(outFile))) {
            String line;
            boolean sawTick = false;
            boolean sawCredits = false;
            boolean sawMetal = false;
            boolean sawOre = false;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("CurrentTick,")) {
                   
                    sawTick = true;
                }
                if (line.startsWith("CREDITS,")) {
                   
                    sawCredits = true;
                }
                if (line.startsWith("METAL,")) {
                   
                    sawMetal = true;
                }
                if (line.startsWith("ORE,")) {
                    
                    sawOre = true;
                }
            }

            assertTrue(sawTick, "Save file must contain CurrentTick line");
            assertTrue(sawCredits, "Save file must contain CREDITS line");
            assertTrue(sawMetal, "Save file must contain METAL line");
            assertTrue(sawOre, "Save file must contain ORE line");

             mark += (sawTick) ? 1 : 0;
             mark += (sawCredits) ? 1 : 0;
             mark += (sawMetal) ? 1 : 0;
             mark += (sawOre) ? 1 : 0;
       

       
        
        }
        System.out.println("AUTOMARK::Engine.saveTest: " + mark + "/4");
    }

    @Test
    void loadTest() throws Exception {
        // ensure data dir exists
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            assertTrue(dataDir.mkdirs(), "Failed to create data directory for tests");
        }
        
        int mark = 0;

        state.addResource(ResourceType.METAL, 20);
        state.addResource(ResourceType.ORE, 3);
        String filename = "engine_load_test.csv";
        engine.save(filename);

        //Change the state to ensure load works
        state.updateResource(ResourceType.CREDITS, 0);
        state.updateResource(ResourceType.METAL, 0);
        state.updateResource(ResourceType.ORE, 0);
    
        String result = engine.load(filename);
        System.out.println("getresrouces metal: " + state.getResourceAmount(ResourceType.CREDITS));
        mark += (1000 == state.getResourceAmount(ResourceType.CREDITS)) ? 1 : 0;
        mark += (20 == state.getResourceAmount(ResourceType.METAL)) ? 1 : 0;
        mark += (3 == state.getResourceAmount(ResourceType.ORE)) ? 1 : 0;
        mark += (0 == engine.getCurrentTick()) ? 1 : 0;
        System.out.println("AUTOMARK::Engine.loadTest: " + mark + "/4");
    }
}
