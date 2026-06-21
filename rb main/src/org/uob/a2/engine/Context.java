package org.uob.a2.engine;

import org.uob.a2.engine.Engine;
import org.uob.a2.engine.SimulationState;

public class Context {
    private final Engine engine;
    private final SimulationState state;

    public Context(Engine engine, SimulationState state) {
        this.engine = engine;
        this.state = state;
    }

    public Engine getEngine() { 
        return engine; 
    }
    
    public SimulationState getSimulationState() { 
        return state; 
    }
}
