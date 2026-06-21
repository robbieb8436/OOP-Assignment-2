package org.uob.a2.engine;

import org.junit.jupiter.api.Test;
import org.uob.a2.model.ResourceType;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationStateTest {

    @Test
    void newSimuluationTest() {
        SimulationState state = new SimulationState();
        int mark = 0;
        // Should be zero for any resource
        mark += (0 == state.getResourceAmount(ResourceType.CREDITS)) ? 1 : 0;
        mark += (0 == state.getResourceAmount(ResourceType.METAL)) ? 1 : 0;
        mark += (0 == state.getResourceAmount(ResourceType.ORE)) ? 1 : 0;
        
        System.out.println("AUTOMARK::SimulationState.newSimuluationTest: " + mark + "/3");
    }

    @Test
    void addResourceTest() {
        SimulationState state = new SimulationState();
        int mark = 0;
        state.addResource(ResourceType.METAL, 10);
        
        mark += (10 == state.getResourceAmount(ResourceType.METAL)) ? 1 : 0;

        state.addResource(ResourceType.METAL, 5);
        mark += (15 == state.getResourceAmount(ResourceType.METAL)) ? 1 : 0;
        
        System.out.println("AUTOMARK::SimulationState.addResourceTest: " + mark + "/2");
    
    }

    @Test
    void updateResourceTest() {
        SimulationState state = new SimulationState();
        int mark = 0;
        state.addResource(ResourceType.ORE, 5);
     
        mark += (5 == state.getResourceAmount(ResourceType.ORE)) ? 1 : 0;

        state.updateResource(ResourceType.ORE, 20);
    
        mark += (20 == state.getResourceAmount(ResourceType.ORE)) ? 1 : 0;
        
        System.out.println("AUTOMARK::SimulationState.updateResourceTest: " + mark + "/2");
    }

    @Test
    void removeResourceFailsTest() {
        SimulationState state = new SimulationState();
        int mark = 0;
        state.addResource(ResourceType.ORE, 3);
        boolean removed = state.removeResource(ResourceType.ORE, 5);
        
        mark += (!removed) ? 1 : 0;
        mark += (3 == state.getResourceAmount(ResourceType.ORE)) ? 1 : 0;
        
        System.out.println("AUTOMARK::SimulationState.removeResourceFailsTest: " + mark + "/2");
    }

    @Test
    void removeResourceSucceedsTest() {
        SimulationState state = new SimulationState();
        int mark = 0;
        state.addResource(ResourceType.ORE, 10);
        boolean removed = state.removeResource(ResourceType.ORE, 4);

        mark += (removed) ? 1 : 0;
        mark += (6 == state.getResourceAmount(ResourceType.ORE)) ? 1 : 0;

        System.out.println("AUTOMARK::SimulationState.removeResourceSucceedsTest: " + mark + "/2");

    }

    @Test
    void updateHistoryTest() {
        SimulationState state = new SimulationState();

        int mark = 0;
        // tick 0-ish
        state.addResource(ResourceType.CREDITS, 100);
        state.addResource(ResourceType.METAL, 5);
        state.updateHistory();

        // mutate live inventory
        state.addResource(ResourceType.METAL, 5); // now 10
        state.updateHistory();

        List<Map<ResourceType, Integer>> history = state.getHistory();
        mark += (2 == history.size()) ? 1 : 0;

        Map<ResourceType, Integer> first = history.get(0);
        Map<ResourceType, Integer> second = history.get(1);
    
         mark += (100 == first.get(ResourceType.CREDITS)) ? 1 : 0;
         mark += (5 == first.get(ResourceType.METAL)) ? 1 : 0;

         mark += (100 == second.get(ResourceType.CREDITS)) ? 1 : 0;
         mark += (10 == second.get(ResourceType.METAL)) ? 1 : 0;

       
        System.out.println("AUTOMARK::SimulationState.updateHistoryTest: " + mark + "/5");
    }
}
