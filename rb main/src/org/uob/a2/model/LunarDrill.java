package org.uob.a2.model;

import org.uob.a2.engine.Context;
import org.uob.a2.engine.Producer;
import org.uob.a2.engine.SimulationState;
import org.uob.a2.engine.Tickable;
import java.util.Map;

public class LunarDrill extends Producer implements Tickable {
	// Defined Production Rate: 10 LUNAR_STONE per tick
    private static final int PRODUCTION_AMOUNT = 10;
    
    // Defined Build Cost: 500 CREDIT
    private static final Map<ResourceType, Integer> LUNAR_DRILL_COST = Map.of(ResourceType.CREDITS, 500);

	// constructor
    public LunarDrill(String name) {
        super(name, ResourceType.LUNAR_STONE, PRODUCTION_AMOUNT); 
    }
	
    @Override
    public Map<ResourceType, Integer> getBuildCost() {
        return LUNAR_DRILL_COST;
    }

    @Override
    public String tick(Context ctx) {
        produce(ctx);
        
        return this.getName() + " produced " + this.getAmount() + " " + this.getProduct();
    }

	// produce to be called in tick
	@Override
    public void produce(Context ctx) {
        ctx.getSimulationState().addResource(this.getProduct(), this.getAmount());
    }

	// string output for CSV file
	@Override
    public String toCSV() {
        return "LunarDrill," + this.getName() + "," + this.getProduct().name() + "," + this.getAmount();
    }
}