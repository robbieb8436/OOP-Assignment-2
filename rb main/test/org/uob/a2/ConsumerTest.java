package org.uob.a2.engine;

import org.junit.jupiter.api.Test;
import org.uob.a2.engine.Context;              
import org.uob.a2.model.ResourceType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ConsumerTest {

    private static class TestHarness {
        SimulationState state = new SimulationState();
        Engine engine = new Engine(state);
        Context ctx = new Context(engine, state);

        TestHarness() {
             engine.initialiseDefaults();
        }
    }

    @Test
    void consumersReduce() {
        TestHarness h = new TestHarness();
        List<Consumer> consumers = h.state.getConsumers();

        int mark = 0;
        for (Consumer c : consumers) {
            ResourceType type = c.getProduct();
            int amount        = c.getAmount();

            // Give them exactly enough to consume once
            h.state.updateResource(type, amount);

            int before = h.state.getResourceAmount(type);

            c.tick(h.ctx);  

            int after = h.state.getResourceAmount(type);

             mark += (before - amount == after) ? 2 : 0;
        }

                
       
        System.out.println("AUTOMARK::Consumer.consumersReduce: " + mark + "/2");
    }

    @Test
    void consumersNegative() {
        TestHarness h = new TestHarness();
        List<Consumer> consumers = h.state.getConsumers();

        assertFalse(consumers.isEmpty(),
                "Engine must register at least one Consumer implementation.");

        int mark = 0;
        for (Consumer c : consumers) {
            ResourceType type = c.getProduct();
            int amount        = c.getAmount();

            // Ensure zero in the state
            h.state.updateResource(type, 0);
            int before = h.state.getResourceAmount(type);
            mark += (0 == before) ? 1 : 0;
         

            c.tick(h.ctx);
            int after = h.state.getResourceAmount(type);

             mark += (0 == after) ? 1 : 0;
        }

        System.out.println("AUTOMARK::Consumer.consumersNegative: " + mark + "/2");
    }
}
