package org.uob.a2.engine;

import org.junit.jupiter.api.Test;
import org.uob.a2.model.ResourceType;
import org.uob.a2.engine.Context;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProducerTest {

    /** 
     * Helper that creates an Engine + State + Context and registers
     * whatever producers the student has created.
     */
    private static class TestHarness {
        SimulationState state = new SimulationState();
        Engine engine = new Engine(state);
        Context ctx = new Context(engine, state);

        TestHarness() {
           
           engine.initialiseDefaults();
           
        }
    }

    @Test
    void producerProduceTest() {

        TestHarness h = new TestHarness();
        List<Producer> producers = h.state.getProducers();

        assertFalse(producers.isEmpty(),
                "Engine must register at least one Producer.");
        int mark = 0;
        for (Producer p : producers) {

            ResourceType type = p.getProduct();
            int amount      = p.getAmount();

            // Give clean starting inventory
            int before = h.state.getResourceAmount(type);

            // Do one tick of work
            p.tick(h.ctx);

            int after = h.state.getResourceAmount(type);

            assertEquals(before + amount,
                    after,
                    "Producer " + p.getClass().getSimpleName() +
                    " should produce exactly " + amount +
                    " units of " + type);
            
            mark += (before + amount == after) ? 2 : 0;
        }

             

            System.out.println("AUTOMARK::Producer.producerProduceTest: " + mark + "/2");
    }
}
