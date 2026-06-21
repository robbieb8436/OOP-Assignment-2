package org.uob.a2.engine;

import org.junit.jupiter.api.Test;
import org.uob.a2.engine.Context;             
import org.uob.a2.model.ResourceType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ConverterTest {

    /** Small helper harness */
    private static class TestHarness {
        SimulationState state = new SimulationState();
        Engine engine = new Engine(state);
        Context ctx = new Context(engine, state);

        TestHarness() {
           
            engine.initialiseDefaults();
        }
    }

    @Test
    void convertersConsume() {
        TestHarness h = new TestHarness();

        List<Converter> converters = h.state.getConverters(); // assuming you have this

        assertFalse(converters.isEmpty(),
                "Engine must register at least one Converter implementation.");
        
        int mark = 0;
        for (Converter c : converters) {
            ResourceType inType  = c.getInput();
            ResourceType outType = c.getOutput();
            int inAmount         = c.getInputAmount();
            int outAmount        = c.getOutputAmount();

            // Give them exactly enough input to convert once
            h.state.updateResource(inType, inAmount);
            int beforeIn  = h.state.getResourceAmount(inType);
            int beforeOut = h.state.getResourceAmount(outType);

            c.tick(h.ctx);  

            int afterIn  = h.state.getResourceAmount(inType);
            int afterOut = h.state.getResourceAmount(outType);

             mark += (beforeIn - inAmount == afterIn) ? 2 : 0;
             mark += (beforeOut + outAmount == afterOut) ? 2 : 0;

             System.out.println("AUTOMARK::Converter.convertersConsume: " + mark + "/4");
        }
    }

    @Test
    void convertersInsufficientInput() {
        TestHarness h = new TestHarness();
        List<Converter> converters = h.state.getConverters();

        assertFalse(converters.isEmpty(),
                "Engine must register at least one Converter implementation.");
        
        int mark = 0;
        for (Converter c : converters) {
            ResourceType inType  = c.getInput();
            ResourceType outType = c.getOutput();
            int needed           = c.getInputAmount();

            // Give them less than they need
            h.state.updateResource(inType, Math.max(0, needed - 1));
            int beforeIn  = h.state.getResourceAmount(inType);
            int beforeOut = h.state.getResourceAmount(outType);

            c.tick(h.ctx);

            int afterIn  = h.state.getResourceAmount(inType);
            int afterOut = h.state.getResourceAmount(outType);

             mark += (beforeIn == afterIn) ? 2 : 0;
             mark += (beforeOut  == afterOut) ? 2 : 0;

             System.out.println("AUTOMARK::Converter.convertersInsufficientInput: " + mark + "/4");
        }
    }
}
